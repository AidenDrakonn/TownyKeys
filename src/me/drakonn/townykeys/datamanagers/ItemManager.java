package me.drakonn.townykeys.datamanagers;

import me.drakonn.townykeys.TownyKeys;
import me.drakonn.townykeys.util.Util;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemManager {

    private TownyKeys plugin;
    public ItemManager (TownyKeys plugin)
    {
        this.plugin = plugin;
    }

    public ItemStack getItem()
    {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("keyitem");
        Material material = Material.getMaterial(section.getString("material"));
        String name = section.getString("name");
        List<String> lore = section.getStringList("lore");
        ItemStack item = Util.createItem(material, name, lore);

        if(section.getBoolean("enchanted"))
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);

        return item;
    }
}
