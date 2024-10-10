package net.biryeongtrain.lookingforjob.job;

import com.google.common.base.Objects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.event.PlayerGainJobExperienceEvent;
import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import xyz.nucleoid.stimuli.Stimuli;

public class PlayerJobData {
    private final Job<?> job;

    private final int jobPriority;
    private ServerPlayerEntity player;
    private final JobDataBossBar bossBar;

    public static final Codec<PlayerJobData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("job").forGetter(o -> ((PlayerJobData) o).getJob().getJobId()),
            Codec.INT.fieldOf("job_priority").forGetter(PlayerJobData::getJobPriority),
            Codec.INT.fieldOf("level").forGetter(PlayerJobData::getLevel),
            Codec.DOUBLE.fieldOf("exp").forGetter(PlayerJobData::getExp)
    ).apply(instance, (job, jobPriority, level, exp) -> new PlayerJobData(exp, level, jobPriority, Jobs.getJob(job).orElse(Jobs.NONE))));

    private int level;
    private double exp;

    public PlayerJobData(double exp, int level, int jobPriority, Job<?> job) {
        this.exp = exp;
        this.level = level;
        this.jobPriority = jobPriority;
        this.job = job;
        this.bossBar = new JobDataBossBar(job.getBossBarId(), job.getBossBarColor(), this);
    }

    public PlayerJobData(ServerPlayerEntity player, Job<?> job, int jobPriority) {
        this(0, 1, jobPriority, job);
        this.player = player;
        this.bossBar.setDisplayInterval(((ServerPlayerEntityExt) player).lookingForJob$getDisplayInterval());
    }

    public double getRequiredToNextLevel() {
        return job.getNextLevelUpPoint(level);
    }

    public Job<?> getJob() {
        return this.job;
    }

    public int getJobPriority() {
        return jobPriority;
    }

    public int getLevel() {
        return level;
    }

    public double getExp() {
        return exp;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(ServerPlayerEntity player) {
        if (this.player != null) {
            throw new IllegalStateException("Player is already set");
        }
        this.player = player;
        this.bossBar.setDisplayInterval(((ServerPlayerEntityExt) player).lookingForJob$getDisplayInterval());
    }

    public boolean levelUp(boolean keepExperiences, boolean force) {
        if (!force) {
            try (var invokers = Stimuli.select().forEntity(player)) {
                var result = invokers.get(PlayerGainJobExperienceEvent.PLAYER_LEVEL_UP_EVENT).onLevelUp(this.level, this.exp, this.hasEnoughExp(), keepExperiences);

                if (result == ActionResult.FAIL) {
                    return false;
                }
            }
            var needExp = job.getNextLevelUpPoint(this.level);
            if (!keepExperiences) {
                this.exp -= needExp;
            }
            this.level++;

            //TODO : LevelUp Logic
        }
        return true;
    }

    public boolean gainExp(double exp, Identifier id, Reason reason) {
        try (var invokers = Stimuli.select().forEntity(player)) {
            var result = invokers.get(PlayerGainJobExperienceEvent.PLAYER_EXP_GET_EVENT).onGainJobExperience(this, id, exp, reason);

            if (result == ActionResult.FAIL) {
                return false;
            }
        }

        this.exp += exp;
        if (this.exp >= job.getNextLevelUpPoint(this.level)) {
            return levelUp(false, false);
        }
        return true;
    }

    public JobDataBossBar getBossBar() {
        return bossBar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerJobData that = (PlayerJobData) o;
        return jobPriority == that.jobPriority && level == that.level && Double.compare(exp, that.exp) == 0 && Objects.equal(job, that.job) && Objects.equal(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(job, jobPriority, player, level, exp);
    }

    private boolean hasEnoughExp() {
        return false;
    }
}
