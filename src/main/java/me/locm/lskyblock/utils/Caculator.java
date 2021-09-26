package me.locm.lskyblock.utils;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import me.locm.lskyblock.generator.IslandGenerator;

public class Caculator {

    public static Position getStartPosById(int id){
        return getDefaultSpawn(id).add(-75, 0, -75);
    }

    public static Position getEndPosById(int id){
        return getDefaultSpawn(id).add(75, 0, 75);
    }

    public static Position getDefaultSpawn(int is){
        int skyBlockLevelNum = is / 1000000;
        int x = ((((is % 1000000) / 1000) + 1) * 20 - 20) * 16 + 8;
        int z = (((is % 1000000) % 1000) * 20 - 20) * 16 + 8;
        Level level = Server.getInstance().getLevelByName("skyblock" + skyBlockLevelNum);
        if (level == null) {
            if (!Server.getInstance().loadLevel("skyblock" + skyBlockLevelNum)) {
                Server.getInstance().generateLevel("skyblock" + skyBlockLevelNum, 0, IslandGenerator.class);
            }
            level = Server.getInstance().getLevelByName("skyblock" + skyBlockLevelNum);
        }
        return new Position(x, 13, z, level);
    }

    public static int getTickByLevel(int level){
        return 15 - level;
    }
}
