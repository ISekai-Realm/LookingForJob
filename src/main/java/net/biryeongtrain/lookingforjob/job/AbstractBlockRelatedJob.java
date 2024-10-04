package net.biryeongtrain.lookingforjob.job;

import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Map;

public abstract class AbstractBlockRelatedJob implements Job<Block>{
    private final Identifier id;

    public AbstractBlockRelatedJob(Identifier id) {
        this.id = id;
    }

    @Override
    public Registry<Block> getRegistry() {
        return Registries.BLOCK;
    }

    @Override
    public Identifier getJobId() {
        return this.id;
    }

    @Override
    public JobExpLevelContainer getExpPoints() {
        return JobExpLevelContainer.get(this);
    }

    @Override
    public boolean addExp(Block target, Reason reason, PlayerJobData data) {
        var value = this.getValidatedBlockOrTag(target);
        var state = target.getDefaultState();
        if (value != 0) {
            var registryKey = state.getRegistryEntry().getKey().get();
            return data.gainExp(value, registryKey.getValue(), reason);
        }

        return false;
    }
}
