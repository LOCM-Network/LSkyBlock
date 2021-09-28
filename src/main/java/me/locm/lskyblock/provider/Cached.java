package me.locm.lskyblock.provider;

import me.locm.lskyblock.skyblock.Island;

import java.util.LinkedHashMap;
import java.util.List;

public class Cached {

    private static LinkedHashMap<String, Island> islands = new LinkedHashMap<>();

    public Island getIsland(String name){
        if(islands.containsKey(name)){
            return islands.get(name);
        }
        return null;
    }

    public void putIsland(String name, Island island){
        islands.put(name, island);
    }

    public void updateIsland(String name, Island island){
        islands.replace(name, island);
    }

    public List<Island> getIslands(){
        return (List<Island>) islands.values();
    }

}
