package com.ezinnovations.disablephantoms.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Builds the homes menu inventory.
 */
public final class HomesMenuFactory {

    public static final String HOMES_TITLE = "HOMES";
    private static final int INVENTORY_SIZE = 27;

    private HomesMenuFactory() {
    }

    /**
     * Creates a static homes menu layout inspired by the provided screenshot.
     *
     * @return homes inventory
     */
    public static Inventory createHomesMenu() {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, HOMES_TITLE);

        // Slot mapping chosen to visually match the provided screenshot.
        inventory.setItem(9, new ItemStack(Material.REDSTONE));
        inventory.setItem(10, new ItemStack(Material.REDSTONE_BLOCK));

        inventory.setItem(12, new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        inventory.setItem(13, new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        inventory.setItem(14, new ItemStack(Material.IRON_HORSE_ARMOR));
        inventory.setItem(15, new ItemStack(Material.IRON_HORSE_ARMOR));
        inventory.setItem(16, new ItemStack(Material.CHAINMAIL_HORSE_ARMOR));

        inventory.setItem(18, new ItemStack(Material.RED_DYE));
        inventory.setItem(21, new ItemStack(Material.LAPIS_LAZULI));
        inventory.setItem(22, new ItemStack(Material.LAPIS_LAZULI));

        inventory.setItem(24, new ItemStack(Material.TRIPWIRE_HOOK));
        inventory.setItem(25, new ItemStack(Material.TRIPWIRE_HOOK));

        return inventory;
    }
}
