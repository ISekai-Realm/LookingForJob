package net.biryeongtrain.lookingforjob.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import xyz.nucleoid.stimuli.event.StimulusEvent;

public class PlayerGainJobExperienceEvent {
    public static final Event<GainJobExperience> PLAYER_EXP_GET_EVENT =
            EventFactory.createArrayBacked(GainJobExperience.class,
                    (listeners) -> (player, targetId, exp, isFirstJob, isWillLevelUp) -> {
                        for (GainJobExperience listener : listeners) {
                            listener.onGainJobExperience(player, targetId, exp, isFirstJob, isWillLevelUp);
                        }
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
        void onGainJobExperience(ServerPlayerEntity player, Identifier targetId, double exp, boolean isFirstJob, boolean isWillLevelUp);
    }
}
