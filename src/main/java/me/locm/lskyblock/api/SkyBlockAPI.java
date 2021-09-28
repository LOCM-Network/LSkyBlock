package me.locm.lskyblock.api;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import me.locm.lskyblock.LSkyblock;
import me.locm.lskyblock.event.PlayerCreateIslandEvent;
import me.locm.lskyblock.provider.SQLiteProvider;
import me.locm.lskyblock.provider.YamlProvider;
import me.locm.lskyblock.skyblock.Island;

import java.sql.SQLException;

public class SkyBlockAPI {

    public static boolean hasIsland(Player player){
        return new SQLiteProvider().hasIsland(player.getName());
    }

    public static Island getIsland(Player player) throws SQLException {
        if(hasIsland(player)){
            return new SQLiteProvider().getIsland(player.getName());
        }
        //create island if not exist
        if(createIsland(player)) return getIsland(player);
        return null;
    }

    public static boolean createIsland(Player player) throws SQLException {
        if(!hasIsland(player)){
            new SQLiteProvider().createIsland(player.getName(),
                    LSkyblock.getInstance().getConfig().getInt("islands"));
            addIsland(player);
            Server.getInstance().getPluginManager().callEvent(new PlayerCreateIslandEvent(player));
            return true;
        }
        return false;
    }

    public static void addIsland(Player player){
        YamlProvider provider = new YamlProvider();
        provider.addIsland();
    }

    public static Island getIslandByPos(Position pos){
        for (Island island : LSkyblock.getCached().getIslands()){
            if(island.isInside(pos)){
                return island;
            }
        }
        return null;
    }
}
