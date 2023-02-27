package org.kayteam.chunkloader.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.chunkloader.ChunkLoader;

import java.util.Arrays;

import static org.kayteam.chunkloader.Extensions.ItemStackExtension.createGuiItem;

public class Command_CreateChunkLoader {
    public void createChunkLoader(Player player) {
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

        player.getInventory().addItem(cl);
    }


}
