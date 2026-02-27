package com.ezinnovations.disablephantoms;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Locale;

/**
 * Cancels phantom spawns based on world mode configuration.
 */
public final class PhantomListener implements Listener {

    private final DisablePhantoms plugin;

    /**
     * Creates a new listener.
     *
     * @param plugin plugin instance
     */
    public PhantomListener(DisablePhantoms plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreCreatureSpawn(PreCreatureSpawnEvent event) {
        if (event.getType() != EntityType.PHANTOM) {
            return;
        }

        World world = event.getSpawnLocation().getWorld();
        if (world == null) {
            return;
        }

        String worldName = world.getName().toLowerCase(Locale.ROOT);
        boolean inSet = plugin.getWorldSet().contains(worldName);

        boolean shouldCancel = switch (plugin.getMode()) {
            case BLACKLIST -> inSet;
            case WHITELIST -> !inSet;
        };

        if (shouldCancel) {
            event.setCancelled(true);
            event.setShouldAbortSpawn(true);
            if (plugin.isLogBlockedSpawns()) {
                plugin.getLogger().info("[DisablePhantoms] Blocked phantom spawn in " + world.getName());
            }
        }
    }
}
