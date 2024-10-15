package net.biryeongtrain.lookingforjob.block;

import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DamagedWithSapLog extends PillarBlock implements PolymerBlock, PolymerTexturedBlock {
    private final BlockState polymerBlock;
    public DamagedWithSapLog(Settings settings, BlockState polymerBlock) {
        super(settings);
        this.polymerBlock = polymerBlock;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
       if (player.isSneaking()) {
           return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
       }

         return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return this.polymerBlock;
    }
}
