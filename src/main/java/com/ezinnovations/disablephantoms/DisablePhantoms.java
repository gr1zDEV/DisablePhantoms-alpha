package com.ezinnovations.disablephantoms;

import com.ezinnovations.disablephantoms.commands.DisablePhantomsCommand;
import com.ezinnovations.disablephantoms.commands.HomesMenuCommand;
import com.ezinnovations.disablephantoms.gui.HomesMenuListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Main plugin entrypoint for DisablePhantoms.
 */
public final class DisablePhantoms extends JavaPlugin {

    /**
     * Spawn filtering mode.
     */
    public enum Mode {
        BLACKLIST,
        WHITELIST
    }

    private volatile Mode mode = Mode.BLACKLIST;
    private volatile Set<String> worldSet = new HashSet<>();
    private volatile boolean logBlockedSpawns;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadPluginConfig();

        getServer().getPluginManager().registerEvents(new PhantomListener(this), this);
        getServer().getPluginManager().registerEvents(new HomesMenuListener(), this);

        DisablePhantomsCommand commandHandler = new DisablePhantomsCommand(this);
        HomesMenuCommand homesMenuCommand = new HomesMenuCommand();
        if (getCommand("disablephantoms") != null) {
            getCommand("disablephantoms").setExecutor(commandHandler);
            getCommand("disablephantoms").setTabCompleter(commandHandler);
        }
        if (getCommand("homes") != null) {
            getCommand("homes").setExecutor(homesMenuCommand);
        }

        Logger logger = getLogger();
        logger.info("[DisablePhantoms] Enabled — Mode: " + mode + ", Worlds: " + new ArrayList<>(worldSet));
    }

    /**
     * Reloads the plugin configuration from disk and updates cached values.
     */
    public void reloadPluginConfig() {
        reloadConfig();

        String configuredMode = getConfig().getString("mode", Mode.BLACKLIST.name());
        try {
            this.mode = Mode.valueOf(configuredMode.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            this.mode = Mode.BLACKLIST;
            getLogger().warning("[DisablePhantoms] Invalid mode in config.yml. Falling back to BLACKLIST.");
        }

        List<String> configuredWorlds = getConfig().getStringList("worlds");
        Set<String> normalized = new HashSet<>();
        if (configuredWorlds != null) {
            for (String world : configuredWorlds) {
                if (world != null && !world.isBlank()) {
                    normalized.add(world.toLowerCase(Locale.ROOT));
                }
            }
        }
        this.worldSet = normalized;

        this.logBlockedSpawns = getConfig().getBoolean("log-blocked-spawns", false);
    }

    /**
     * Gets the configured mode.
     *
     * @return current mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Gets the configured world set (lowercase names).
     *
     * @return configured world set
     */
    public Set<String> getWorldSet() {
        return worldSet;
    }

    /**
     * Gets whether blocked phantom spawns should be logged.
     *
     * @return true if blocked spawns should be logged
     */
    public boolean isLogBlockedSpawns() {
        return logBlockedSpawns;
    }
}
