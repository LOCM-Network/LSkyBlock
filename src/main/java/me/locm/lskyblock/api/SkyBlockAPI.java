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
            return new SQLiteProvider().getIsland(player.getName());
        }
        //create island if not exist
        if(createIsland(player)) return getIsland(player);
        return null;
    }

    public static boolean createIsland(Player player){
        if(!hasIsland(player)){
            //TODO: create island
            addIsland(player);
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
