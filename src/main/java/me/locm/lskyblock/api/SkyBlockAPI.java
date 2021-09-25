package me.locm.lskyblock.api;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import me.locm.lskyblock.provider.SQLiteProvider;
import me.locm.lskyblock.provider.YamlProvider;
import me.locm.lskyblock.skyblock.Island;


public class SkyBlockAPI { //TODO

    public static boolean hasIsland(Player player){
        return true;
    }

    public static Island getIsland(Player player){
        if(hasIsland(player)){
            return new Island(player.getName());
        }
        //create island if not exist
        createIsland(player);
        return getIsland(player);
    }

    public static boolean createIsland(Player player){
        if(!hasIsland(player)){
            //TODO: create island
            return true;
        }
        return false;
    }

    public static void addIsland(Player player){
        YamlProvider provider = new YamlProvider();
        provider.addIsland();
    }

    public static Island getIslandByPos(Position pos){
        for (Island island : new SQLiteProvider().getIslands()){
            if(island.isInside(pos)){
                return island;
            }
        }
        return null;
    }
}
