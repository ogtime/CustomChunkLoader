package org.kayteam.chunkloader.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.chunkloader.chunk.ChunkManager;
import org.kayteam.chunkloader.util.ChunkUtil;
import org.kayteam.storageapi.storage.YML;

import java.util.Arrays;

import static org.kayteam.chunkloader.Extensions.ItemStackExtension.createGuiItem;

public class ChunkLoaderListener implements Listener {
    @EventHandler
    private void onChunkLoaderPlaceEvent(BlockPlaceEvent event) {
        // Check for block type
        if (event.getBlock().getType() != Material.BEACON) {
            return;
        }
        String displayName = event.getPlayer().getItemInHand().getItemMeta().getDisplayName();
        if (displayName == null) {
            return;
        }

        // Set names
        displayName = displayName.replaceAll("§", "&");
        String configName = ChunkLoader.getInstance().getConfig().getString("block-name");

        // Check for chunkloader name
        if (!displayName.equals(configName)) {
            return;
        }

        ChunkManager chunkManager = ChunkLoader.getChunkManager();
        YML data = ChunkLoader.data;
        Location playerLocation = event.getPlayer().getLocation();
        Chunk chunkLocation = playerLocation.getChunk();
        String chunkCoords = "X: " + chunkLocation.getX() + "; Z: " + chunkLocation.getZ();
        String chunkFormated = ChunkUtil.toString(chunkLocation);

        if (!data.getStringList("chunks-list").contains(chunkFormated)) {
            chunkManager.addChunk(chunkLocation);

            ChunkLoader.messages.sendMessage(event.getPlayer(), "addchunk.correct", new String[][]{
                    {"%chunk_coords%", chunkCoords}
            });
        } else {
            ChunkLoader.messages.sendMessage(event.getPlayer(), "addchunk.exist", new String[][]{
                    {"%chunk_coords%", chunkCoords}
            });
        }
    }

    @EventHandler
    private void onChunkLoaderBreakEvent(BlockBreakEvent event) {
        // Check for block type
        if (event.getBlock().getType() != Material.BEACON) {
            return;
        }

        ChunkLoader.getChunkManager().deleteChunk(event.getPlayer().getLocation().getChunk(), event.getPlayer());
        event.setCancelled(true);
        event.getBlock().getLocation().getBlock().setType(Material.AIR);

        String displayName = ChunkLoader.getInstance().getConfig().getString("block-name");
        if (displayName == null) {
            return;
        }

        displayName = displayName.replaceAll("&", "§");

        ItemStack cl = createGuiItem(
                Material.BEACON,
                (short) 0,
                1,
                displayName,
                Arrays.asList(
                        "",
                        "§7Place this §bChunkLoader§7 in a chunk to",
                        "§7have it fully load an entire chunk while no",
                        "§7players are nearby!"
                )
        );

        event.getPlayer().getInventory().addItem(cl);
    }
}

