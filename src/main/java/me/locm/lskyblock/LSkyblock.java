package me.locm.lskyblock;

import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import lombok.SneakyThrows;
import me.locm.lskyblock.command.SkyblockCommand;
import me.locm.lskyblock.generator.IslandGenerator;
import me.locm.lskyblock.oregen.OregenEntity;
import me.locm.lskyblock.provider.SQLiteProvider;

public class LSkyblock extends PluginBase {

    private static LSkyblock instance;

    @Override
    public void onLoad(){
        instance = this;
        saveDefaultConfig();
        Generator.addGenerator(IslandGenerator.class, "skyblock", 3);
        BlockEntity.registerBlockEntity("OreGenerator", OregenEntity.class);
    }
    
    @SneakyThrows
    @Override
    public void onEnable(){
        getServer().getCommandMap().register("LSkyblock", new SkyblockCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getLogger().info("Checked database....");
        SQLiteProvider.create();
        getLogger().info("LSkyblock for Locm Server");
    }

    public static LSkyblock getInstance(){
        return instance;
    }

}
