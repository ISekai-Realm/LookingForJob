package net.biryeongtrain.lookingforjob.job;

import eu.pb4.polymer.core.api.utils.PolymerUtils;
import it.unimi.dsi.fastutil.ints.Int2DoubleArrayMap;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.event.PlayerGainJobEvent;
import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import xyz.nucleoid.stimuli.Stimuli;
import xyz.nucleoid.stimuli.event.block.BlockBreakEvent;

public class SimpleBlockRelatedJob extends AbstractBlockRelatedJob {
    private static boolean eventRegistered = false;

    public SimpleBlockRelatedJob(Identifier id) {
        super(id);
    }

    @Override
    public boolean isEventRegistered() {
        return eventRegistered;
    }

    @Override
    public SimpleBlockRelatedJob registerEvents() {
        if (isEventRegistered()) {
            return this;
        }
        Stimuli.global().listen(BlockBreakEvent.EVENT, (player, world, pos) -> {
            var blockstate = world.getBlockState(pos);
            var block = blockstate.getBlock();

            var playerJobs = ((ServerPlayerEntityExt)player).lookingForJob$getPlayerJobs();
            var job = playerJobs.stream().filter(j -> j instanceof SimpleBlockRelatedJob).toList();

            if (player.isCreative() || !player.getMainHandStack().isIn(ItemTags.PICKAXES)) {
                return ActionResult.PASS;
            }
            for (Job<?> j : job) {
                var simpleJob = (SimpleBlockRelatedJob) j;
                PlayerJobData jobData = ((ServerPlayerEntityExt)player).lookingForJob$getJobData(simpleJob);
                if (simpleJob.addExp(block, Reason.GENERIC, jobData)) {
                    // TODO : REMOVE THIS. this is for testing
                    player.sendMessage(Text.literal("You have gained " + simpleJob.getValidatedBlockOrTag(block) + " experience points.").formatted(Formatting.GREEN), false);
                    player.sendMessage(Text.literal("To level Up : %s / %s".formatted(jobData.getExp(), simpleJob.getNextLevelUpPoint(jobData.getLevel())).formatted(Formatting.GREEN)), false);
                }
            }
            return ActionResult.PASS;
        });

        Stimuli.global().listen(PlayerGainJobEvent.EVENT, ((player, job, isFirstJob, hasJob) -> {
            if (job == Jobs.MINER) {
                PolymerUtils.reloadWorld(player);
            }
            return ActionResult.PASS;
        }));
        SimpleBlockRelatedJob.eventRegistered = true;
        return this;
    }

    @Override
    public double getValidatedBlockOrTag(Block block) {
        var expContainer = this.getExpPoints();
        double blockExp;
        if ((blockExp = this.getExpPoints().getExp(Registries.BLOCK, block)) != 0) {
            return blockExp;
        }
        var tagStream = block.getDefaultState().streamTags();

        double value;
        var filteredTagStream = tagStream.filter(tag -> expContainer.getExp(tag) != 0);

        return filteredTagStream.mapToDouble(expContainer::getExp).max().orElse(0);
    }

    @Override
    public <R> boolean hasBlockOrTag(R target) {
        if (target instanceof Block) {
            return getValidatedBlockOrTag((Block) target) != 0;
        }
        return false;
    }
}
