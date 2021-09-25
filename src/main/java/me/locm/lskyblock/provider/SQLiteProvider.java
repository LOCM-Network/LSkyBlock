package me.locm.lskyblock.provider;

import cn.nukkit.Player;
import lombok.Getter;
import me.locm.lskyblock.skyblock.Island;
import me.locm.lskyblock.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteProvider {

    public Island getIsland(String player){
        Island island =  new Island(player);
        String spawn = ""; //getSpawn method
        List<String> members = new ArrayList<String>(); //getMembers method
        boolean pvp = true; //getPvp method
        int id = 1; //getId Method
        island.setId(id);
        island.setMembers(members);
        island.setPvp(pvp);
        island.setSpawn(Utils.stringToPos(spawn));
        return island;
    }

    public void updateIsland(Island island){
        int id = island.getId();
        String owner = island.getOwner();
        List<String> members = island.getMembers();
        boolean pvp = island.getPvp();
        //TODO: update
    }

    public boolean hasIsland(String player){
        return false;
    }

    public List<Island> getIslands(){
        List<Island> islands = new ArrayList<Island>();
        //TODO: query
        return islands;
    }

    public void createIsland(){
        //TODO: query
        //id: number
        //player: player name
        //members: players
        //pvp-mod: 0(disable <default> ), 1(enable)
    }
}
