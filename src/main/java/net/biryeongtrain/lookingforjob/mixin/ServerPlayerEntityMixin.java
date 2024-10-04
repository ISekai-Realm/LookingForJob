package net.biryeongtrain.lookingforjob.mixin;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.job.Job;
import net.biryeongtrain.lookingforjob.job.PlayerJobData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityExt {
    @Unique
    private Int2ReferenceMap<PlayerJobData> lookingForJob$jobData = new Int2ReferenceOpenHashMap<>();


    @Override
    public List<Job<?>> lookingForJob$getPlayerJobs() {
        List<Job<?>> list = new ArrayList<>();
        this.lookingForJob$jobData.values().forEach(data -> list.add(data.getJob()));
        return list;
    }

    @Override
    public <T extends Job<?>> PlayerJobData lookingForJob$setPlayerJob(T job, int jobPriority) {
        var data = new PlayerJobData((ServerPlayerEntity) (Object) this, job, jobPriority);
        this.lookingForJob$jobData.put(jobPriority, data);
        return data;
    }

    @Override
    public @Nullable <T extends Job<?>> PlayerJobData lookingForJob$getJobData(T job) {
        return  this.lookingForJob$jobData.values().stream().filter(data -> data.getJob() == job).findFirst().orElse(null);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void lookingForJob$writeJobData(NbtCompound nbt, CallbackInfo ci) {
        var jobData = new NbtCompound();
        this.lookingForJob$jobData.values().forEach(data -> {
            PlayerJobData.CODEC.encodeStart(NbtOps.INSTANCE, data).ifError(error -> LoggerUtil.logError(error.message()))
                    .result().ifPresentOrElse((success) -> jobData.put(String.valueOf(data.getJobPriority()), success), () -> LoggerUtil.logError("Failed to encode job data" + data.toString()));
        });

        nbt.put("jobData", jobData);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void lookingForJob$readJobData(NbtCompound nbt, CallbackInfo ci) {
        var jobData = nbt.getCompound("jobData");
        jobData.getKeys().forEach(key -> {
            var dataPair = PlayerJobData.CODEC.decode(NbtOps.INSTANCE, jobData.getCompound(key)).result().orElse(null);
            if (dataPair != null) {
                var data = dataPair.getFirst();
                data.setPlayer((ServerPlayerEntity) (Object) this);
                this.lookingForJob$jobData.put(Integer.parseInt(key), data);
            }
        });
    }
}
