package com.ezinnovations.disablephantoms.commands;

import com.ezinnovations.disablephantoms.DisablePhantoms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles /disablephantoms command and tab completion.
 */
public final class DisablePhantomsCommand implements CommandExecutor, TabCompleter {

    private static final String PERMISSION = "disablephantoms.admin";
    private static final String PREFIX = "§8[§bDisablePhantoms§8] §7";

    private final DisablePhantoms plugin;

    /**
     * Creates a new command handler.
     *
     * @param plugin plugin instance
     */
    public DisablePhantomsCommand(DisablePhantoms plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(PREFIX + ChatColor.AQUA + "DisablePhantoms v" + plugin.getDescription().getVersion());
            sender.sendMessage(PREFIX + "Usage: /disablephantoms <reload|status|add|remove|list>");
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "reload" -> {
                plugin.reloadPluginConfig();
                sender.sendMessage(PREFIX + ChatColor.GREEN + "Config reloaded successfully.");
                return true;
            }
            case "status" -> {
                sender.sendMessage(PREFIX + "Mode: " + ChatColor.AQUA + plugin.getMode().name());
                String worlds = plugin.getWorldSet().isEmpty()
                        ? "(none)"
                        : String.join(", ", plugin.getWorldSet());
                sender.sendMessage(PREFIX + "Worlds: " + ChatColor.AQUA + worlds);
                return true;
            }
            case "add" -> {
                if (args.length < 2) {
                    sender.sendMessage(PREFIX + ChatColor.RED + "Usage: /disablephantoms add <world>");
                    return true;
                }
                handleAdd(sender, args[1]);
                return true;
            }
            case "remove" -> {
                if (args.length < 2) {
                    sender.sendMessage(PREFIX + ChatColor.RED + "Usage: /disablephantoms remove <world>");
                    return true;
                }
                handleRemove(sender, args[1]);
                return true;
            }
            case "list" -> {
                handleList(sender);
                return true;
            }
            default -> {
                sender.sendMessage(PREFIX + ChatColor.RED + "Unknown subcommand. Use /disablephantoms <reload|status|add|remove|list>");
                return true;
            }
        }
    }

    private void handleAdd(CommandSender sender, String worldNameRaw) {
        String normalized = worldNameRaw.toLowerCase(Locale.ROOT);
        Set<String> worldSet = plugin.getWorldSet();

        if (Bukkit.getWorld(worldNameRaw) == null) {
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "Warning: world '" + worldNameRaw + "' is not currently loaded/found. Added anyway.");
        }

        if (!worldSet.add(normalized)) {
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "World " + worldNameRaw + " is already in the list.");
            return;
        }

        plugin.getConfig().set("worlds", new ArrayList<>(worldSet));
        plugin.saveConfig();
        sender.sendMessage(PREFIX + ChatColor.GREEN + "Added " + worldNameRaw + " to the list.");
    }

    private void handleRemove(CommandSender sender, String worldNameRaw) {
        String normalized = worldNameRaw.toLowerCase(Locale.ROOT);
        Set<String> worldSet = plugin.getWorldSet();

        if (!worldSet.remove(normalized)) {
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "World " + worldNameRaw + " was not in the list.");
            return;
        }

        plugin.getConfig().set("worlds", new ArrayList<>(worldSet));
        plugin.saveConfig();
        sender.sendMessage(PREFIX + ChatColor.GREEN + "Removed " + worldNameRaw + " from the list.");
    }

    private void handleList(CommandSender sender) {
        sender.sendMessage(PREFIX + ChatColor.AQUA + "World Phantom Status:");
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            boolean inSet = plugin.getWorldSet().contains(worldName.toLowerCase(Locale.ROOT));
            boolean disabled = switch (plugin.getMode()) {
                case BLACKLIST -> inSet;
                case WHITELIST -> !inSet;
            };

            String status = disabled
                    ? ChatColor.RED + "DISABLED"
                    : ChatColor.GREEN + "ENABLED";
            sender.sendMessage(ChatColor.GRAY + "  " + worldName + " — " + status);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            return List.of();
        }

        if (args.length == 1) {
            return filterByPrefix(List.of("reload", "status", "add", "remove", "list"), args[0]);
        }

        if (args.length == 2) {
            String sub = args[0].toLowerCase(Locale.ROOT);
            String typed = args[1].toLowerCase(Locale.ROOT);

            if ("add".equals(sub)) {
                Set<String> configured = plugin.getWorldSet();
                List<String> candidates = Bukkit.getWorlds().stream()
                        .map(World::getName)
                        .filter(name -> !configured.contains(name.toLowerCase(Locale.ROOT)))
                        .sorted(Comparator.naturalOrder())
                        .toList();
                return filterByPrefix(candidates, typed);
            }

            if ("remove".equals(sub)) {
                List<String> candidates = plugin.getWorldSet().stream()
                        .sorted(Comparator.naturalOrder())
                        .toList();
                return filterByPrefix(candidates, typed);
            }
        }

        return List.of();
    }

    private List<String> filterByPrefix(List<String> options, String input) {
        String normalized = input.toLowerCase(Locale.ROOT);
        return options.stream()
                .filter(value -> value.toLowerCase(Locale.ROOT).startsWith(normalized))
                .collect(Collectors.toList());
    }
}
