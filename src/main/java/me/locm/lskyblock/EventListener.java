package me.locm.lskyblock;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.ItemFrameDropItemEvent;
import cn.nukkit.event.player.PlayerBucketEmptyEvent;
import cn.nukkit.event.player.PlayerBucketFillEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.utils.TextFormat;
import me.locm.lskyblock.api.SkyBlockAPI;
import me.locm.lskyblock.event.PlayerCreateIslandEvent;
import me.locm.lskyblock.skyblock.Island;

public class EventListener implements Listener {

    @EventHandler
    public void onIslandCreate(PlayerCreateIslandEvent event){
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onJoin(PlayerLocallyInitializedEvent event){
        Player player = event.getPlayer();
        if(SkyBlockAPI.createIsland(player)){
            player.sendMessage(TextFormat.colorize("&eDao cua ban da duoc tao, /island de dich chuyen toi"));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
                event.setCancelled(!player.isOp());
            }
        }
        event.setCancelled(!player.isOp());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
                event.setCancelled(!player.isOp());
            }
        }
        event.setCancelled(!player.isOp());
    }

    @EventHandler
    public void onFrameDrop(ItemFrameDropItemEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
                event.setCancelled(!player.isOp());
            }
        }
        event.setCancelled(!player.isOp());
    }

    @EventHandler
    public void onBucketEmty(PlayerBucketEmptyEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlockClicked());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
                event.setCancelled(!player.isOp());
            }
        }
        event.setCancelled(!player.isOp());
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlockClicked());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
            }
        }
        event.setCancelled(!player.isOp());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getLevel().getName().startsWith("skyblock")){
            Island island = SkyBlockAPI.getIslandByPos(event.getBlock());
            if(island != null && !island.isMember(player)){
                if(island.getOwner().equals(player.getName())) return;
            }
        }
        event.setCancelled(!player.isOp());
    }

}
