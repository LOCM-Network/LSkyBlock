package me.locm.lskyblock;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.ItemFrameDropItemEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import me.locm.lskyblock.api.SkyBlockAPI;
import me.locm.lskyblock.event.PlayerCreateIslandEvent;
import me.locm.lskyblock.skyblock.Island;
import me.locm.lskyblock.utils.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class EventListener implements Listener {

    @EventHandler
    public void onIslandCreate(PlayerCreateIslandEvent event){
        Player player = event.getPlayer();
        List<Item> items = Utils.getIslandItem();
        for(Item item : items){
            player.getInventory().addItem(item);
        }
        player.sendMessage(TextFormat.colorize("Do khoi dau da duoc them vao tui"));
    }

    @EventHandler
    public void onJoin(PlayerLocallyInitializedEvent event) throws SQLException {
        Player player = event.getPlayer();
        if(SkyBlockAPI.createIsland(player)){
            player.sendMessage(TextFormat.colorize("&eDao cua ban da duoc tao, /island de dich chuyen toi"));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        event.setDropExp(0);
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName().toLowerCase()) || player.isOp()) return;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setKeepInventory(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName().toLowerCase()) || player.isOp()) return;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFrameDrop(ItemFrameDropItemEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName().toLowerCase()) || player.isOp()) return;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBucketEmty(PlayerBucketEmptyEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlockClicked());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName().toLowerCase()) || player.isOp()) return;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlockClicked());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName().toLowerCase()) || player.isOp()) return;
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
            }
        }
    }

}
