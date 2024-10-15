package net.biryeongtrain.lookingforjob.job;

import net.biryeongtrain.lookingforjob.job.exp.JobExpLevelContainer;
import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public abstract class AbstractBlockRelatedJob implements Job<Block>{
    private final Identifier id;
    private final UUID bossBarId = UUID.randomUUID();
    private final BossBar.Color bossBarColor;
    private final String translationKey;

    public AbstractBlockRelatedJob(Identifier id, BossBar.Color color, String translationKey) {
        this.id = id;
        this.bossBarColor = color;
        this.translationKey = translationKey;
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
    public UUID getBossBarId() {
        return this.bossBarId;
    }

    @Override
    public BossBar.Color getBossBarColor() {
        return this.bossBarColor;
    }

    @Override
    public JobExpLevelContainer getExpPoints() {
        return JobExpLevelContainer.get(this);
    }

    @Override
    public Text getTranslationText() {
        return Text.translatable(this.translationKey);
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
