package me.locm.lskyblock.provider;

import cn.nukkit.utils.Config;
import me.locm.lskyblock.LSkyblock;

public class YamlProvider {

    private static Config config;

    public YamlProvider(){
        config = LSkyblock.getInstance().getConfig();
    }

    public void addIsland(){
        int last = config.getInt("islands");
        config.set("islands", last + 1);
        config.save();
        config.reload();
    }

    public int getIslands(){
        return config.getInt("islands");
    }
}
