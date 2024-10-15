package net.biryeongtrain.lookingforjob.item;

import net.biryeongtrain.lookingforjob.block.BlockRegistries;
import net.biryeongtrain.lookingforjob.block.TexturedPolyBlockItem;
import net.biryeongtrain.lookingforjob.utils.IdUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemRegistry {
    public static Item TIER_1_MINER_ORE_BLOCK = register("tier_1_miner_ore", new TexturedPolyBlockItem(BlockRegistries.TIER_1_MINER_ORE, new Item.Settings().maxCount(64), "tier_1_miner_ore"));
    public static Item DEEPSLATE_TIER_1_MINER_ORE_BLOCK = register("deepslate_tier_1_miner_ore", new TexturedPolyBlockItem(BlockRegistries.DEEPSLATE_TIER_1_MINER_ORE, new Item.Settings().maxCount(64), "deepslate_tier_1_miner_ore"));
    public static Item WOOD_CUTTING_KNIFE = register("wood_cutting_knife", new WoodCuttingKnife(new Item.Settings().maxCount(1).maxDamage(60)));
    public static Item DAMAGED_WITH_SAP_LOG = register("damaged_with_sap_log", new TexturedPolyBlockItem(BlockRegistries.DAMAGED_WITH_SAP_LOG, new Item.Settings().maxCount(64), "damaged_with_sap_log"));
    public static Item DAMAGED_LOG = register("damaged_log", new TexturedPolyBlockItem(BlockRegistries.DAMAGED_LOG, new Item.Settings().maxCount(64), "damaged_log"));

    private static <T extends Item> T register(String id, T item) {
        Registry.register(Registries.ITEM, IdUtils.getId(id), item);
        return item;
    }

    public static void register() {
        // NO-OP
    }
}
