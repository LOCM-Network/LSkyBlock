package me.locm.lskyblock.skyblock;

import cn.nukkit.level.Position;
import lombok.Getter;

@Getter
public class IslandArea {

    private final Position start;
    private final Position end;

    public IslandArea(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public boolean isInside(Position pos) {
        double bigZ, smallZ;
        double bigX, smallX;
        bigZ = Math.max(this.start.z, this.end.z);
        bigX = Math.max(this.start.x, this.end.x);
        smallZ = Math.min(this.start.z, this.end.z);
        smallX = Math.min(this.start.x, this.end.x);
        return pos.z < bigZ && pos.z > smallZ && pos.x < bigX && pos.x > smallX;
    }

}
