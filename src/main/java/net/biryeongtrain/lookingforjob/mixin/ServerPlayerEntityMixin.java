package net.biryeongtrain.lookingforjob.mixin;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.job.Job;
import net.biryeongtrain.lookingforjob.job.PlayerJobData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityExt {
    @Unique
    private Int2ReferenceMap<PlayerJobData<Job<?>>> lookingForJob$jobData = new Int2ReferenceOpenHashMap<>();


    @Override
    public List<Job<?>> lookingForJob$getPlayerJobs() {
        return List.of();
    }

    @Override
    public <T extends Job<?>> PlayerJobData<T> lookingForJob$setPlayerJob(T job, int jobPriority) {
        var data = new PlayerJobData<T>((ServerPlayerEntity) (Object) this, job, jobPriority);
        this.lookingForJob$jobData.put(jobPriority, (PlayerJobData<Job<?>>) data);
        return data;
    }

    @Override
    public @Nullable <T extends Job<?>> PlayerJobData<T> lookingForJob$getJobData(T job) {
        return (PlayerJobData<T>) this.lookingForJob$jobData.values().stream().filter(data -> data.getJob() == job).findFirst().orElse(null);
    }

}
