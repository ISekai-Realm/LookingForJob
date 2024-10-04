package net.biryeongtrain.lookingforjob.job;

import net.biryeongtrain.lookingforjob.LookingForJob;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Jobs {
    private static final ConcurrentMap<Identifier, Job<?>> JOBS = new ConcurrentHashMap<>();
    public static SimpleBlockRelatedJob NONE = register(new SimpleBlockRelatedJob(Identifier.of(LookingForJob.MOD_ID, "none")));
    public static SimpleBlockRelatedJob TEST_JOB = register(new SimpleBlockRelatedJob(Identifier.of(LookingForJob.MOD_ID, "test_job")));
    public static SimpleBlockRelatedJob MINER = register(new SimpleBlockRelatedJob(Identifier.of(LookingForJob.MOD_ID, "miner")));


    private static <T extends Job<?>> T register(T job) {
        job.registerEvents();
        JOBS.put(job.getJobId(), job);
        return job;
    }

    public static Optional<Job<?>> getJob(Identifier id) {
        return Optional.ofNullable(JOBS.get(id));
    }
}
