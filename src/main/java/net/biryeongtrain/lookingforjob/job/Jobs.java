package net.biryeongtrain.lookingforjob.job;

import net.biryeongtrain.lookingforjob.LookingForJob;
import net.biryeongtrain.lookingforjob.utils.IdUtils;
import net.biryeongtrain.lookingforjob.utils.TextUtils;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Jobs {
    private static final ConcurrentMap<Identifier, Job<?>> JOBS = new ConcurrentHashMap<>();
    public static SimpleBlockBreakJob NONE = register(new SimpleBlockBreakJob(Identifier.of(LookingForJob.MOD_ID, "none"), BossBar.Color.RED, TextUtils.getTranslationKey("job.none.name")));
    public static SimpleBlockBreakJob TEST_JOB = register(new SimpleBlockBreakJob(Identifier.of(LookingForJob.MOD_ID, "test_job"), BossBar.Color.BLUE, TextUtils.getTranslationKey("job.test_job.name")));
    public static SimpleBlockBreakJob MINER = register(new SimpleBlockBreakJob(Identifier.of(LookingForJob.MOD_ID, "miner"), BossBar.Color.YELLOW, TextUtils.getTranslationKey("job.miner.name")));
    public static SimpleBlockBreakJob WOOD_CUTTER = register(new SimpleBlockBreakJob(IdUtils.getId("wood_cutter"), BossBar.Color.GREEN, TextUtils.getTranslationKey("job.wood_cutter.name")));


    private static <T extends Job<?>> T register(T job) {
        job.registerEvents();
        JOBS.put(job.getJobId(), job);
        return job;
    }

    public static Optional<Job<?>> getJob(Identifier id) {
        return Optional.ofNullable(JOBS.get(id));
    }
}
