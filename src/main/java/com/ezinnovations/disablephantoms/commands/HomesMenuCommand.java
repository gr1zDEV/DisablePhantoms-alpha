package com.ezinnovations.disablephantoms.commands;

import com.ezinnovations.disablephantoms.gui.HomesMenuFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Opens a sample homes menu matching the requested layout.
 */
public final class HomesMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open the homes menu.");
            return true;
        }

        player.openInventory(HomesMenuFactory.createHomesMenu());
        return true;
    }
}
