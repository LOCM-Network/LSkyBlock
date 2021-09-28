package me.locm.lskyblock;

import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import lombok.SneakyThrows;
import me.locm.lskyblock.command.SkyblockCommand;
import me.locm.lskyblock.generator.IslandGenerator;
import me.locm.lskyblock.oregen.OregenEntity;
import me.locm.lskyblock.provider.Cached;
import me.locm.lskyblock.provider.SQLiteProvider;
import me.locm.lskyblock.skyblock.Island;

import java.util.List;

public class LSkyblock extends PluginBase {

    private static Cached cached;
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
        cached = new Cached();
        getServer().getCommandMap().register("LSkyblock", new SkyblockCommand());
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getLogger().info("Checked database....");
        SQLiteProvider.create();
        getLogger().info("Create island cached");
        List<Island> islands = new SQLiteProvider().getIslands();
        int i = 0;
        for(Island island : islands){
            cached.putIsland(island.getOwner(), island);
            i += 1;
        }
        getLogger().info("Create cached with: " + i + " islands");
        getLogger().info("LSkyblock for Locm Server");
    }

    @Override
    public void onDisable(){
        List<Island> islands = getCached().getIslands();
        SQLiteProvider provider = new SQLiteProvider();
        getLogger().info("Save database....");
        for(Island island : islands){
            provider.updateIsland(island);
        }
    }

    public static LSkyblock getInstance(){
        return instance;
    }

    public static Cached getCached(){
        return cached;
    }

}
