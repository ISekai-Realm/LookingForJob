package net.biryeongtrain.lookingforjob.event;

import net.biryeongtrain.lookingforjob.job.Job;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public interface PlayerGainJobEvent {
    StimulusEvent<PlayerGainJobEvent> EVENT = StimulusEvent.create(PlayerGainJobEvent.class, ctx -> (player, job, isFirstJob, hasJob) -> {
        try {
            for (var listener : ctx.getListeners()) {
                var result = listener.onGainJob(player, job, isFirstJob, hasJob);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }
        } catch (Throwable t) {
            ctx.handleException(t);
        }

        return ActionResult.PASS;
    });

    ActionResult onGainJob(ServerPlayerEntity player, Job job, boolean isFirstJob, boolean hasJob);
}
