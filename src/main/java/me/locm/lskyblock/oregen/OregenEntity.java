package me.locm.lskyblock.oregen;

import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySpawnable;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import me.locm.lskyblock.utils.Caculator;
import me.locm.lskyblock.utils.Utils;

public class OregenEntity extends BlockEntitySpawnable {

    private int level;
    private int currentTick = 0;

    public OregenEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected void initBlockEntity(){
        if (!this.namedTag.contains("levelgen")) {
            this.namedTag.putInt("levelgen", 0);
        }
        this.level = this.namedTag.getInt("levelgen");
        super.initBlockEntity();
    }

    @Override
    public CompoundTag getSpawnCompound() {
        return new CompoundTag()
                .putString("id", "ore.gen")
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putInt("levelgen", this.level);
    }

    @Override
    public boolean onUpdate(){
        if(this.currentTick == 0){
            Block upblock = this.getLevel().getBlock(this.add(0D, 1D));
            if(upblock.getId() == 0){
                this.getLevel().setBlock(upblock, Utils.getRandomBlockByLevel(this.level));
                this.currentTick = Caculator.getTickByLevel(this.level);
            }
        }else{
            this.currentTick -= 1;
        }
        return true;
    }

    @Override
    public boolean isBlockEntityValid() {
        return false;
    }

    @Override
    public String getName() {
        return "OreGenerator";
    }
}
