package net.biryeongtrain.lookingforjob.job;

import net.biryeongtrain.lookingforjob.job.exp.JobExpLevelContainer;
import net.biryeongtrain.lookingforjob.job.exp.Reason;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public interface Job<T> {
    boolean isEventRegistered();
    Registry<T> getRegistry();
    Job<T> registerEvents();
    Identifier getJobId();
    UUID getBossBarId();
    BossBar.Color getBossBarColor();
    JobExpLevelContainer getExpPoints();
    Text getTranslationText();
    boolean addExp(Block target, Reason reason, PlayerJobData data);
    default double getNextLevelUpPoint(int level) {
        return JobExpLevelContainer.get(this).getRequirementLevelUp(level + 1);
    }
    double getValidatedBlockOrTag(T target);
    <R> boolean hasBlockOrTag(R target);
}
