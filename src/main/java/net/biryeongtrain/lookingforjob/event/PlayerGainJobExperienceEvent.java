package net.biryeongtrain.lookingforjob.event;

import net.biryeongtrain.lookingforjob.job.PlayerJobData;
import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public class PlayerGainJobExperienceEvent {
    public static final StimulusEvent<GainJobExperience> PLAYER_EXP_GET_EVENT =
            StimulusEvent.create(GainJobExperience.class,
                    (ctx) -> (data, targetId, exp, reason) -> {
                        try {
                            for (var listener : ctx.getListeners()) {
                                var result = listener.onGainJobExperience(data, targetId, exp, reason);

                                if (result != ActionResult.PASS) {
                                    return result;
                                }
                            }
                        } catch (Throwable t) {
                            ctx.handleException(t);
                        }
                        return ActionResult.PASS;
                    });

    public static final StimulusEvent<LevelUp> PLAYER_LEVEL_UP_EVENT =
            StimulusEvent.create(LevelUp.class,
                    (ctx) -> ((currentLevel, currentExp, hasEnoughExp, keepExperience) -> {
                        try {
                            for (var listener : ctx.getListeners()) {
                                var result = listener.onLevelUp(currentLevel, currentExp, hasEnoughExp, keepExperience);

                                if (result != ActionResult.PASS) {
                                    return result;
                                }
                            }
                        } catch (Throwable t) {
                            ctx.handleException(t);
                        }

                        return ActionResult.PASS;
                    })
            );


    @FunctionalInterface
    public interface LevelUp {
        ActionResult onLevelUp(int currentLevel, double currentExp, boolean hasEnoughExp, boolean keepExperience);
    }

    @FunctionalInterface
    public interface GainJobExperience {
        ActionResult onGainJobExperience(PlayerJobData data, Identifier targetId, double exp, Reason reason);
    }
}
