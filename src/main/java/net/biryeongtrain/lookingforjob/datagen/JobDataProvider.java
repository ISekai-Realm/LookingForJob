package net.biryeongtrain.lookingforjob.datagen;

import net.biryeongtrain.lookingforjob.job.JobExpLevelContainer;
import net.biryeongtrain.lookingforjob.job.Jobs;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class JobDataProvider extends FabricCodecDataProvider<JobExpLevelContainer> {
    protected JobDataProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.DATA_PACK, "job_data", JobExpLevelContainer.CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, JobExpLevelContainer> provider, RegistryWrapper.WrapperLookup lookup) {
        provider.accept(Jobs.MINER.getJobId(),
                new JobExpLevelContainer.Builder<>(Registries.BLOCK)
                        .addTagExp(BlockTags.COAL_ORES, 5)
                        .addTagExp(BlockTags.COPPER_ORES, 7)
                        .addTagExp(BlockTags.IRON_ORES, 10)
                        .addTagExp(BlockTags.GOLD_ORES, 13)
                        .addTagExp(BlockTags.REDSTONE_ORES, 8)
                        .addTagExp(BlockTags.LAPIS_ORES, 8)
                        .addTagExp(BlockTags.DIAMOND_ORES, 30)
                        .addTagExp(BlockTags.BASE_STONE_OVERWORLD, 1)

                        .setDefaultLevelType(30)
                        .build()
        );

        provider.accept(Jobs.WOOD_CUTTER.getJobId(),
            new JobExpLevelContainer.Builder<>(Registries.BLOCK)
                    .addTagExp(BlockTags.LOGS, 3)

                    .setDefaultLevelType(15)
                    .build()
                );
    }

    @Override
    public String getName() {
        return "looking_for_job";
    }
}
