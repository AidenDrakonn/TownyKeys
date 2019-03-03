package me.drakonn.townykeys.command;

import me.drakonn.townykeys.TownyKeys;
import me.drakonn.townykeys.datamanagers.MessageManager;
import me.drakonn.townykeys.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(!command.getLabel().equalsIgnoreCase("townykeys"))
            return true;

        if(commandSender instanceof Player)
        {
            Player player = (Player)commandSender;
            if(!player.hasPermission("townykeys.give"))
            {
                player.sendMessage("§7[§bTownyKeys§7] §fYou don't have permission to do that!");
                return true;
            }
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("help"))
        {
            for(String string : MessageManager.help)
            {
                commandSender.sendMessage(string);
            }
            return true;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("reload"))
        {
            TownyKeys.getInstance().loadData();
            commandSender.sendMessage("§7[§bTownyKeys§7] §fConfig has been reloaded");
            return true;
        }

        if(args.length >= 2 && args[0].equalsIgnoreCase("give")) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null)
            {
                commandSender.sendMessage("[TowneyKeys] "+args[1]+" is not online");
                return true;
            }

            ItemStack item = TownyKeys.getInstance().chestKey.getItem();
            if(args.length == 3 && Util.isInt(args[2]))
            {
                item.setAmount(Integer.valueOf(args[2]));
            }

            Util.givePlayerItem(target, item);
            String message = MessageManager.keyAdded;
            message = message.replaceAll("%amount%", Integer.toString(item.getAmount()));
            target.sendMessage(message);
        }
        return true;
    }
}
