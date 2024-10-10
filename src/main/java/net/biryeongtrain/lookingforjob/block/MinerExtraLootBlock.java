package net.biryeongtrain.lookingforjob.block;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.job.Jobs;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.World;

public class MinerExtraLootBlock extends ExperienceDroppingBlock implements PolymerBlock, PolymerTexturedBlock {
    private final BlockState polymerBlockState;
    public MinerExtraLootBlock(BlockState polymerBlockState, IntProvider experienceDropped, Settings settings) {
        super(experienceDropped, settings);
        this.polymerBlockState = polymerBlockState;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return polymerBlockState;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        var jobData = ((ServerPlayerEntityExt) player).lookingForJob$getJobData(Jobs.MINER);
        if (jobData == null) {
            return Blocks.STONE.getDefaultState();
        }

        return polymerBlockState;
    }

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, ServerPlayerEntity player) {
        return Blocks.STONE.getDefaultState();
    }
}
