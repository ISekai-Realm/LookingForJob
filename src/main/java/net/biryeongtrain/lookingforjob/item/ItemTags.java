package net.biryeongtrain.lookingforjob.item;

import net.biryeongtrain.lookingforjob.utils.IdUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ItemTags {
    public static final TagKey<Item> WOOD_DAMAGEABLE = TagKey.of(RegistryKeys.ITEM, IdUtils.getId("wood_damageable"));

    public static void register() {

    }
}
