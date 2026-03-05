package com.ezinnovations.disablephantoms.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Keeps the homes menu read-only.
 */
public final class HomesMenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(HomesMenuFactory.HOMES_TITLE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals(HomesMenuFactory.HOMES_TITLE)) {
            event.setCancelled(true);
        }
    }
}
