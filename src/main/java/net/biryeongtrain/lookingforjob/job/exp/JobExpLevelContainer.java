package net.biryeongtrain.lookingforjob.job.exp;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.*;
import net.biryeongtrain.lookingforjob.job.Job;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public record JobExpLevelContainer(Map<Identifier, Double> typeExpPoints, Map<Identifier, Double> tagExpPoint, Map<String, Double> requirementLevelUp, Map<Identifier, Double> custom) {
    private static final Reference2ObjectMap<Job<?>, JobExpLevelContainer> map = new Reference2ObjectOpenHashMap<>();
    public static Codec<JobExpLevelContainer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Identifier.CODEC, Codec.DOUBLE).fieldOf("type_exp").forGetter(JobExpLevelContainer::typeExpPoints),
            Codec.unboundedMap(Identifier.CODEC, Codec.DOUBLE).fieldOf("tag_exp").forGetter(JobExpLevelContainer::tagExpPoint),
            Codec.unboundedMap(Codecs.NON_EMPTY_STRING, Codec.DOUBLE).fieldOf("requirement_level_up").forGetter(JobExpLevelContainer::requirementLevelUp),
            Codec.unboundedMap(Identifier.CODEC, Codec.DOUBLE).fieldOf("custom").forGetter(JobExpLevelContainer::custom)
    ).apply(instance, JobExpLevelContainer::new));

    public static void clear() {
        map.clear();
    }


    public static <T> JobExpLevelContainer register(Job<T> job, JobExpLevelContainer container) {
        map.put(job, container);
        return container;
    }

    public static <T> JobExpLevelContainer get(Job<T> job) {
        return map.get(job);
    }

    public <T> double getExp(Registry<T> registry, T target) {
        return this.typeExpPoints.getOrDefault(registry.getId(target), 0.);
    }

    public <T> double getExp(TagKey<T> target) {
        return this.tagExpPoint.getOrDefault(target.id(), 0.);
    }

    public double getRequirementLevelUp(int level) {
        if (!this.requirementLevelUp.containsKey(String.valueOf(level))) {
            return -1;
        }
        return this.requirementLevelUp.getOrDefault(String.valueOf(level), -1.0);
    }

    public static class Builder<T> {
        private final JobExpLevelContainer container;
        private final Registry<T> registry;

        public Builder(Registry<T> registry) {
            this.container = new JobExpLevelContainer(new HashMap<>(), new HashMap<>(), new Object2DoubleLinkedOpenHashMap<>(), new HashMap<>());
            this.registry = registry;
        }

        public Builder<T> addTypeExp(T target, double exp) {
            this.container.typeExpPoints.put(this.registry.getId(target), exp);
            return this;
        }

        public Builder<T> addTagExp(TagKey<T> target, double exp) {
            this.container.tagExpPoint.put(target.id(), exp);
            return this;
        }

        public Builder<T> addRequirementLevelUp(int level, double exp) {
            this.container.requirementLevelUp.put(String.valueOf(level), exp);
            return this;
        }

        public Builder<T> setDefaultLevelValue(double baseValue) {
            for (int i : IntStream.range(2, 100).boxed().toList()) {
                if (i <= 30) {
                    baseValue *= 1.06;
                } else if (i <= 60) {
                    baseValue *= 1.08;
                } else if (i <= 90) {
                    baseValue *= 1.10;
                } else {
                    baseValue *= 1.12;
                }
                this.container.requirementLevelUp.put(String.valueOf(i), baseValue);
            }

            return this;
        }

        public Builder<T> addCustomExp(Identifier target, double exp) {
            this.container.custom.put(target, exp);
            return this;
        }

        public JobExpLevelContainer build() {
            return this.container;
        }
    }
}
