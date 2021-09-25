package me.locm.lskyblock;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import me.locm.lskyblock.event.PlayerCreateIslandEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onIslandCreate(PlayerCreateIslandEvent event){
        Player player = event.getPlayer();
    }
}
