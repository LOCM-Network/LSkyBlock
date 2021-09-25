package me.locm.lskyblock.generator;
import java.util.Map;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockSapling;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.object.tree.ObjectTree;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

public class IslandGenerator extends Generator {

    private ChunkManager level;
    private NukkitRandom random;
    private final Map<String, Object> options;


    public IslandGenerator(Map<String, Object> options) {
        this.options = options;
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
        this.random = random;
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        BaseFullChunk chunk = level.getChunk(chunkX, chunkZ);
        if (chunkX % 20 == 0 && chunkZ % 20 == 0) {
            for(int x = 4; x < 11; x++){
                for(int z = 4; z < 11; z++){
                    chunk.setBlock(x, 4, z, Block.GRASS);
                }
            }
            for(int x = 5; x < 10; x++){
                for(int z = 5; z < 10; z++){
                    chunk.setBlock(x, 3, z, Block.DIRT);
                }
            }
            for(int x = 6; x < 9; x++){
                for(int z = 6; z < 9; z++){
                    chunk.setBlock(x, (73 - 64), z, Block.LEAVES);
                    chunk.setBlock(x, (66 - 64), z, Block.DIRT);
                }
            }
            chunk.setBlock(7, 0, 7, Block.DIRT);
            chunk.setBlock(7, 1, 7, Block.SAND);
            chunk.setBlock(7, 2, 7, Block.SAND);
            chunk.setBlock(7, 3, 7, Block.SAND);
            chunk.setBlock(4, 4, 4, Block.AIR);
            chunk.setBlock(4, 4, 10, Block.AIR);
            chunk.setBlock(10, 4, 4, Block.AIR);
            chunk.setBlock(10, 4, 10, Block.AIR);
            chunk.setBlock(7,1, 8, Block.DIRT);
            chunk.setBlock(8, 1, 7, Block.DIRT);
            chunk.setBlock(7, 1, 6, Block.DIRT);
            chunk.setBlock(6, 1, 7, Block.DIRT);
            chunk.setBlock(5, 2, 7, Block.DIRT);
            chunk.setBlock(7, 2, 5, Block.DIRT);
            chunk.setBlock(9, 2, 7, Block.DIRT);
            chunk.setBlock(7, 2, 9, Block.DIRT);
            chunk.setBlock(4, 3, 7, Block.DIRT);
            chunk.setBlock(7, 3, 4, Block.DIRT);
            chunk.setBlock(7, 3, 10, Block.DIRT);
            chunk.setBlock(10, 3, 7, Block.DIRT);
            ObjectTree.growTree(level, chunkX*16 + 8, 5, chunkZ*16 + 8, random, BlockSapling.OAK);
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {}

    @Override
    public Map<String, Object> getSettings() {
        return options;
    }

    @Override
    public String getName() {
        return "skyblock";
    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0, 7, 1);
    }

    @Override
    public ChunkManager getChunkManager() {
        return level;
    }

}