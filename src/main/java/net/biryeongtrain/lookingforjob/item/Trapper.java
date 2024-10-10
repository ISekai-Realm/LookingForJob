package net.biryeongtrain.lookingforjob.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.job.Jobs;
import net.biryeongtrain.lookingforjob.utils.TextUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Trapper extends Item implements PolymerItem {
    public Trapper(Settings settings) {
        super(settings);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return ItemModels.TRAPPER_MODEL.item();
    }

    @Override
    public void modifyClientTooltip(List<Text> tooltip, ItemStack stack, @Nullable ServerPlayerEntity player) {
        if (player == null) {
            return;
        }

        var job = ((ServerPlayerEntityExt) player).lookingForJob$getJobData(Jobs.WOOD_CUTTER);
        boolean hasJob = job != null;
        var text1 = TextUtils.getTranslationText("item.trapper.lore.1");
        tooltip.add(text1
                .styled(style -> style
                        .withColor(TextColor.parse("#83ff70")
                                .result()
                                .orElse(TextColor.fromFormatting(Formatting.GREEN))))
        );

        if (hasJob) {
            var text2 = TextUtils.getTranslationText("item.trapper.lore.no_job");
            tooltip.add(text2
                    .styled(style -> style
                            .withColor(TextColor.parse("#fc5d44")
                                    .result()
                                    .orElse(TextColor.fromFormatting(Formatting.RED))))
            );
        } else {
            var text3 = TextUtils.getTranslationText("item.trapper.lore.no_job");
            tooltip.add(text3
                    .styled(style -> style
                            .withColor(TextColor.parse("#83ff70")
                                    .result()
                                    .orElse(TextColor.fromFormatting(Formatting.GREEN))))
            );
        }


    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return ItemModels.TRAPPER_MODEL.value();
    }
}
