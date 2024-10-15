package net.biryeongtrain.lookingforjob.item;

import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.biryeongtrain.lookingforjob.utils.IdUtils;
import net.minecraft.item.Items;

public class ItemModels {
    public static PolymerModelData WOOD_CUTTING_KNIFE = PolymerResourcePackUtils.requestModel(Items.PAPER, IdUtils.getItemModelId("wood_cutting_knife"));

    public static void register() {
        // NO-OP
    }
}
