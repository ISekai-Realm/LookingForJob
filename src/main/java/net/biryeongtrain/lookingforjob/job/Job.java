package net.biryeongtrain.lookingforjob.job;

import it.unimi.dsi.fastutil.ints.Int2DoubleArrayMap;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface Job<T> {
    void registerEvents();
    Identifier getJobId();
    Map<T, Double> getExpPoints();
    void addExp(T target, PlayerJobData<Job<T>> data);
    Int2DoubleArrayMap getLevelUpExp();
}
