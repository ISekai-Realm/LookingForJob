package net.biryeongtrain.lookingforjob.datagen;

import net.biryeongtrain.lookingforjob.block.BlockRegistries;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(BlockRegistries.TIER_1_MINER_ORE, (block) -> new LootTable.Builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.DIAMOND)
                                .conditionally(RandomChanceLootCondition.builder(0.1F))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))))
                        .with(ItemEntry.builder(Items.RAW_COPPER)
                                .conditionally(RandomChanceLootCondition.builder(0.7F))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))))
                        .with(ItemEntry.builder(Items.RAW_IRON)
                                .conditionally(RandomChanceLootCondition.builder(0.25F))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))))
                        .with(ItemEntry.builder(Items.RAW_GOLD)
                                .conditionally(RandomChanceLootCondition.builder(0.4F))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F))))
                )
        );
    }
}
