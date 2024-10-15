package net.biryeongtrain.lookingforjob.datagen;

import net.biryeongtrain.lookingforjob.item.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.biryeongtrain.lookingforjob.utils.TextUtils;

import java.util.concurrent.CompletableFuture;

public class EnglishLanguageProvider extends FabricLanguageProvider {
    protected EnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(TextUtils.getTranslationKey("job.miner.name"), "Miner");
        translationBuilder.add(ItemRegistry.WOOD_CUTTING_KNIFE, "Wood Cutting Knife");
        translationBuilder.add(TextUtils.getTranslationKey("item.wood_cutting_knife.lore.1"), "You can use this to tap on the oak tree to get sap.");
        translationBuilder.add(TextUtils.getTranslationKey("item.wood_cutting_knife.lore.has_job"), "Usable");
        translationBuilder.add(TextUtils.getTranslationKey("item.wood_cutting_knife.lore.no_job"), "Unusable");
        translationBuilder.add(TextUtils.getTranslationKey("job.wood_cutter.name"), "Wood Cutter");
    }
}
