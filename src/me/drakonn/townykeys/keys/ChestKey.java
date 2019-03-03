package me.drakonn.townykeys.keys;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.bukkit.towny.war.eventwar.War;
import me.drakonn.townykeys.TownyKeys;
import me.drakonn.townykeys.datamanagers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class ChestKey implements Listener {

    private ItemStack item;

    public ChestKey(ItemStack item) {
        this.item = item;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;

        if(event.getItem() == null)
            return;

        if(!isItem(event.getItem()))
            return;

        event.setCancelled(true);
        Player player = event.getPlayer();
        Location location = event.getClickedBlock().getLocation();
        Resident resident;
        Town locTown;
        Town playerTown;
        try {
            locTown = WorldCoord.parseWorldCoord(location).getTownBlock().getTown();
            resident = TownyUniverse.getDataSource().getResident(player.getName());
            playerTown = resident.getTown();

            if(locTown.hasResident(resident))
            {
                player.sendMessage(MessageManager.yourOwnTown);
                return;
            }
        } catch (NotRegisteredException e) {
            player.sendMessage(MessageManager.notEnemyTown);
            return;
        }

        War war = TownyKeys.getInstance().towny.getTownyUniverse().getWarEvent();

        if(war == null || !war.isWarTime()) {
            player.sendMessage(MessageManager.notAtWar);
            return;
        }

        if(!war.getWarringTowns().contains(playerTown))
        {
            player.sendMessage(MessageManager.notAtWar);
            return;
        }

        if(!war.getWarringTowns().contains(locTown))
        {
            player.sendMessage(MessageManager.targetNotAtWar);
            return;
        }

        try
        {
            if(locTown.hasNation() && playerTown.hasNation() && locTown.getNation().equals(playerTown.getNation()))
            {
                player.sendMessage(MessageManager.notEnemyTown);
                return;
            }
        }
        catch(NotRegisteredException e) {
        }

        try
        {
            if(!war.isWarringNation(locTown.getNation()) || !war.isWarringNation(playerTown.getNation()))
            {
                player.sendMessage(MessageManager.targetNotAtWar);
                return;
            }
        }
        catch(NotRegisteredException e) {
        }

        try
        {
            if(locTown.getNation().getEnemies().isEmpty() || playerTown.getNation().getEnemies().isEmpty())
            {
                player.sendMessage(MessageManager.notEnemyTown);
                return;
            }


            if(!locTown.getNation().getEnemies().contains(playerTown.getNation()))
            {
                player.sendMessage(MessageManager.notEnemyTown);
                return;
            }

            if(!playerTown.getNation().getEnemies().contains(locTown.getNation()))
            {
                player.sendMessage(MessageManager.notEnemyTown);
                return;
            }
        }
        catch(NotRegisteredException e) {
        }

        if(!(event.getClickedBlock().getState() instanceof Chest)) {
            player.sendMessage(MessageManager.invalidLocation);
            return;
        }

        Chest chest = (Chest) event.getClickedBlock().getState();
        int newAmount = event.getItem().getAmount()-1;
        if(newAmount != 0) {
            event.getItem().setAmount(newAmount);
        }
        else
        {
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        }
        player.sendMessage(MessageManager.keyUsed);
        player.openInventory(chest.getInventory());
    }

    private boolean isItem(ItemStack input)
    {
        if(input.isSimilar(item))
            return true;

        return false;
    }

    public ItemStack getItem() { return item.clone(); }

}
