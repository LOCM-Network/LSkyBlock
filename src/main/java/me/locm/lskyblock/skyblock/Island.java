package me.locm.lskyblock.skyblock;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import lombok.Getter;
import lombok.Setter;
import me.locm.lskyblock.provider.SQLiteProvider;
import me.locm.lskyblock.utils.Caculator;

import java.util.List;

@Getter
@Setter
public class Island {

    private int id;
    private String owner;
    private Position spawn;
    private List<String> members;
    private boolean pvp;

    public Island(String player){
        this.owner = player;
    }

    public boolean getPvp(){
        return pvp;
    }

    public void addMember(Player player){
        new SQLiteProvider().addMember(this, player.getName());
    }

    public void removeMember(Player player){
        new SQLiteProvider().removeMember(this, player.getName());
    }

    public boolean isMember(Player player){
        return members.contains(player.getName());
    }

    public boolean isInside(Position pos){
        Position start = Caculator.getStartPosById(getId());
        Position end = Caculator.getEndPosById(getId());
        IslandArea area = new IslandArea(start, end);
        return area.isInside(pos);
    }

}
