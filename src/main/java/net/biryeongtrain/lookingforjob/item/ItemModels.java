package net.biryeongtrain.lookingforjob.item;

import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.biryeongtrain.lookingforjob.utils.IdUtils;
import net.minecraft.item.Items;

public class ItemModels {
    public static PolymerModelData TRAPPER_MODEL = PolymerResourcePackUtils.requestModel(Items.PAPER, IdUtils.getItemModelId("trapper"));
}
