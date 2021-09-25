package me.locm.lskyblock.api;

import cn.nukkit.Player;
import me.locm.lskyblock.provider.YamlProvider;

public class SkyBlockAPI { //TODO

    public static boolean hasIsland(Player player){
        return true;
    }

    public static void addIsland(Player player){
        YamlProvider provider = new YamlProvider();
        provider.addIsland();
    }
}
