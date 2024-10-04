package net.biryeongtrain.lookingforjob.job;

import it.unimi.dsi.fastutil.ints.Int2DoubleArrayMap;
import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface Job<T> {
    boolean isEventRegistered();
    Registry<T> getRegistry();
    Job<T> registerEvents();
    Identifier getJobId();
    JobExpLevelContainer getExpPoints();
    boolean addExp(Block target, Reason reason, PlayerJobData data);
    default double getNextLevelUpPoint(int level) {
        return JobExpLevelContainer.get(this).getRequirementLevelUp(level + 1);
    }
    double getValidatedBlockOrTag(T target);
    <R> boolean hasBlockOrTag(R target);
}
