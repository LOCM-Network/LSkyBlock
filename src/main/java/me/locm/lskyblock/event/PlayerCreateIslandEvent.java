package me.locm.lskyblock.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;

public class PlayerCreateIslandEvent extends PlayerEvent{

    private static final HandlerList handlers = new HandlerList();

    public PlayerCreateIslandEvent(Player player){
        this.player = player;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
