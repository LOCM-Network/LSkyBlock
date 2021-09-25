package me.locm.lskyblock.skyblock;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Island {

    @Getter
    private String owner;

    public Island(String player){
        this.owner = player;
    }

    public boolean addMember(Player player){
        return true;
    }

    public boolean removeMember(Player player){
        return true;
    }

    public boolean isMember(Player player){
        return true;
    }

    public void setSettings(){

    }

    public void setSpawn(){

    }

    public List<String> getMembers(){
        List<String> members = new ArrayList<String>();
        return members;
    }

    public Map<String, String> getSettings(){
        Map<String, String> settings = new HashMap<>();
        return settings;
    }

    public Position getSpawn(){
        return new Position();
    }
}
