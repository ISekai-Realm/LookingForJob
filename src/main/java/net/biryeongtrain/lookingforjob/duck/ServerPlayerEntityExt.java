package net.biryeongtrain.lookingforjob.duck;

import net.biryeongtrain.lookingforjob.job.Job;
import net.biryeongtrain.lookingforjob.job.PlayerJobData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ServerPlayerEntityExt {
    List<Job<?>> lookingForJob$getPlayerJobs();
    <T extends Job<?>> PlayerJobData<T> lookingForJob$setPlayerJob(T job, int jobPriority);
    @Nullable
    <T extends Job<?>> PlayerJobData<T> lookingForJob$getJobData(T job);

}
