package net.biryeongtrain.lookingforjob.datagen;

import net.biryeongtrain.lookingforjob.block.BlockRegistries;
import net.biryeongtrain.lookingforjob.item.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistries.TIER_1_MINER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistries.DEEPSLATE_TIER_1_MINER_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.WOOD_CUTTING_KNIFE, Models.HANDHELD);
    }
}
