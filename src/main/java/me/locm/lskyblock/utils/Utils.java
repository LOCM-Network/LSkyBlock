package me.locm.lskyblock.utils;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

public class Utils {

    public static Position stringToPos(String string){
        String[] parts = string.split(":");
        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        Level level = Server.getInstance().getLevelByName(parts[3]);
        return new Position(x, y, z, level);
    }

    public static String posToString(Position pos){
        return pos.x + ":" + pos.y + ":" + pos.z + ":" + pos.getLevel().getName();
    }
}
