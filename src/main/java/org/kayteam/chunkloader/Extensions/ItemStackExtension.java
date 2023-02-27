package org.kayteam.chunkloader.Extensions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackExtension {
    public static ItemStack createGuiItem(final Material material, short data, int stackSize, final String name, final List<String> lore) {
        ItemStack item = new ItemStack(material, stackSize, data);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name.length() > 0 ? name : ChatColor.RESET + "");

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
