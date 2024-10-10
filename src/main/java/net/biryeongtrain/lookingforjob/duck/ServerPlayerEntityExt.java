package net.biryeongtrain.lookingforjob.duck;

import net.biryeongtrain.lookingforjob.job.Job;
import net.biryeongtrain.lookingforjob.job.PlayerJobData;
import net.minecraft.entity.boss.BossBar;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ServerPlayerEntityExt {
    List<Job<?>> lookingForJob$getPlayerJobs();

    <T extends Job<?>> PlayerJobData lookingForJob$setPlayerJob(T job, int jobPriority);

    @Nullable
    <T extends Job<?>> PlayerJobData lookingForJob$getJobData(T job);

    PlayerJobData lookingForJob$getPlayerJob(int priority);

    void lookingForJob$addBossBarDisappearQueue(long time, BossBar bossBar);
    int lookingForJob$getDisplayInterval();
}
