package net.biryeongtrain.lookingforjob.block;

import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;

public class DamagedLog extends PillarBlock implements PolymerBlock, PolymerTexturedBlock {
    private final BlockState polymerBlock;
    public DamagedLog(Settings settings, BlockState polymerBlock) {
        super(settings);
        this.polymerBlock = polymerBlock;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return this.polymerBlock;
    }
}
