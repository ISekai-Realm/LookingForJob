package net.biryeongtrain.lookingforjob.job;

import it.unimi.dsi.fastutil.ints.Int2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2DoubleMap;

public record JobExpLevelContainer<T>(Reference2DoubleMap<T> expPoints, Int2DoubleArrayMap requirementLevelUp) {
    public double getExp(T target) {
        return this.expPoints.getDouble(target);
    }

    public double getRequirementLevelUp(int level) {
        return this.requirementLevelUp.get(level);
    }
}
