package net.biryeongtrain.lookingforjob.block;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.biryeongtrain.lookingforjob.utils.IdUtils;

public class BlockRegistries {
    public static final Block TIER_1_MINER_ORE = register("tier_1_miner_ore", new MinerExtraLootBlock(
            PolymerBlockResourceUtils
                    .requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(IdUtils.getBlockModelId("tier_1_miner_ore"))),
            UniformIntProvider.create(1, 3),
            AbstractBlock.Settings.create().nonOpaque().requiresTool().hardness(3.0F).resistance(3.0F)));

    public static final Block DEEPSLATE_TIER_1_MINER_ORE = register(
            "deepslate_tier_1_miner_ore",
            new MinerExtraLootBlock(
                    PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(IdUtils.getBlockModelId("deepslate_tier_1_miner_ore"))),
                    UniformIntProvider.create(1, 3),
                    AbstractBlock.Settings.create().nonOpaque().requiresTool().hardness(4.5F).resistance(4.5F)));

    private static Block register(String id, Block block) {
        Registry.register(Registries.BLOCK, IdUtils.getId(id), block);
        return block;
    }

    public static void register() {
        // NO-OP
    }
}
