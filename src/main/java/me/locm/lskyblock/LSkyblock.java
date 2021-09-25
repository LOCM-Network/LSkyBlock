package me.locm.lskyblock;

import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import me.locm.lskyblock.command.SkyblockCommand;
import me.locm.lskyblock.generator.IslandGenerator;

public class LSkyblock extends PluginBase {

    private static LSkyblock instance;

    @Override
    public void onLoad(){
        instance = this;
        saveDefaultConfig();
        Generator.addGenerator(IslandGenerator.class, "skyblock", 3);
        getServer().getCommandMap().register("LSkyblock", new SkyblockCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    public static LSkyblock getInstance(){
        return instance;
    }

}
