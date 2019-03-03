package me.drakonn.townykeys;

import com.palmergames.bukkit.towny.Towny;
import me.drakonn.townykeys.command.Command;
import me.drakonn.townykeys.datamanagers.ItemManager;
import me.drakonn.townykeys.datamanagers.MessageManager;
import me.drakonn.townykeys.keys.ChestKey;
import org.bukkit.plugin.java.JavaPlugin;

public class TownyKeys extends JavaPlugin {

    public Towny towny;
    private static TownyKeys instance;
    private ItemManager itemManager = new ItemManager(this);
    private MessageManager messageManager = new MessageManager(this);
    public ChestKey chestKey;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setTowny();
        loadData();
        registerListeners();
        getCommand("townykeys").setExecutor(new Command());
    }

    public void loadData()
    {
        messageManager.loadMessages();
        chestKey = new ChestKey(itemManager.getItem());
    }

    private void registerListeners()
    {
        getServer().getPluginManager().registerEvents(chestKey, this);
    }

    private void setTowny()
    {
        if(getServer().getPluginManager().getPlugin("Towny") != null) {
            towny = (Towny)getServer().getPluginManager().getPlugin("Towny");
            return;
        }
        System.out.println("[TownyKeys] Towny is not installed, Disabling townykeys");
        getServer().getPluginManager().disablePlugin(this);
    }

    public static TownyKeys getInstance() { return instance; }
}
