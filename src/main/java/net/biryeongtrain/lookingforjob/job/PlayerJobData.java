package net.biryeongtrain.lookingforjob.job;

import net.biryeongtrain.lookingforjob.event.PlayerGainJobEvent;
import net.biryeongtrain.lookingforjob.event.PlayerGainJobExperienceEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import xyz.nucleoid.stimuli.Stimuli;

public class PlayerJobData<T extends Job<?>> {
    private final T job;
    private final int jobPriority;
    private final ServerPlayerEntity player;

    private int level;
    private double exp;

    public PlayerJobData(ServerPlayerEntity player, T job, int jobPriority) {
        this.player = player;
        this.job = job;
        this.jobPriority = jobPriority;
    }

    public T getJob() {
        return this.job;
    }


    public boolean levelUp(boolean keepExperiences, boolean force) {
        if (!force) {
            try (var invokers = Stimuli.select().forEntity(player)) {
                var result = invokers.get(PlayerGainJobExperienceEvent.PLAYER_LEVEL_UP_EVENT).onLevelUp(this.level, this.exp, this.hasEnoughExp(), keepExperiences);

                if (result == ActionResult.FAIL) {
                    return false;
                }
            }
            //TODO : LevelUp Logic
        }
        return levelUp();
    }

    private boolean levelUp() {
        return true;
    }

    private boolean hasEnoughExp() {
        return false;
    }
}
