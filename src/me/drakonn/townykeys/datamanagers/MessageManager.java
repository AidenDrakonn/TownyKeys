package me.drakonn.townykeys.datamanagers;

import me.drakonn.townykeys.TownyKeys;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private TownyKeys plugin;

    public MessageManager(TownyKeys plugin) {
        this.plugin = plugin;
    }

    public static String notAtWar;
    public static String keyUsed;
    public static String invalidLocation;
    public static String notEnemyTown;
    public static String targetNotAtWar;
    public static String keyAdded;
    public static String yourOwnTown;
    public static List<String> help = new ArrayList<>();

    public void loadMessages() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("messages");
        notAtWar = ChatColor.translateAlternateColorCodes('&', section.getString("notatwar"));
        keyUsed = ChatColor.translateAlternateColorCodes('&', section.getString("keyused"));
        invalidLocation = ChatColor.translateAlternateColorCodes('&', section.getString("invalidlocation"));
        notEnemyTown = ChatColor.translateAlternateColorCodes('&', section.getString("notenemytown"));
        targetNotAtWar = ChatColor.translateAlternateColorCodes('&', section.getString("targetnotatwar"));
        keyAdded = ChatColor.translateAlternateColorCodes('&', section.getString("keyadded"));
        yourOwnTown = ChatColor.translateAlternateColorCodes('&', section.getString("yourowntown"));
        setHelp();
    }

    private void setHelp()
    {
        help.add("§7-----------------§bTownyKeys§7-----------------");
        help.add("§b/Townykeys give (player) [amount] §8- §fGive a player chest keys");
        help.add("§b/Townykeys reload §8- §fReload config file");
    }
}