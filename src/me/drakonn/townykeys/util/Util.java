package me.drakonn.townykeys.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Util
{
    public static ItemStack createItem(final Material mat, final int amt, final int durability, final String name,
                                       final List<String> lore) {
        final ItemStack item = new ItemStack(mat, amt);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color(name));
        meta.setLore(Util.color(lore));
        if (durability != 0)
            item.setDurability((short) durability);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(final Material mat, final String name, final List<String> lore) {
        return createItem(mat, 1, 0, name, lore);
    }

    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(final List<String> list) {
        final List<String> colored = new ArrayList<String>();
        for (final String s : list)
            colored.add(color(s));
        return colored;
    }

    public static boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public static void givePlayerItem(Player player, ItemStack item)
    {
        if (player.getInventory().firstEmpty() != -1)
        {
            player.getInventory().addItem(item);
        }
        else if (getSlot(player, item.getType()) != -1)
        {
            player.getInventory().addItem(item);
        }
        else
        {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    public static int getSlot(Player p, Material type)
    {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if ((p.getInventory().getItem(i).getType() == type) && (p.getInventory().getItem(i).getAmount() < p.getInventory().getItem(i).getMaxStackSize())) {
                return i;
            }
        }
        return -1;
    }
}
