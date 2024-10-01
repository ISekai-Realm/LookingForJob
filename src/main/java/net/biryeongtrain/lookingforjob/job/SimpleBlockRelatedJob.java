package net.biryeongtrain.lookingforjob.job;

import it.unimi.dsi.fastutil.ints.Int2DoubleArrayMap;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.block.BlockBreakEvent;

public class SimpleBlockRelatedJob extends AbstractBlockRelatedJob {
    public SimpleBlockRelatedJob(Identifier id) {
        super(id);
    }

    @Override
    public void registerEvents() {
        Stimuli.global().listen(BlockBreakEvent.EVENT, (player, world, pos) -> {
            var block = world.getBlockState(pos).getBlock();

            return ActionResult.PASS;
        });
    }

    @Override
    public Int2DoubleArrayMap getLevelUpExp() {
        return null;
    }
}
