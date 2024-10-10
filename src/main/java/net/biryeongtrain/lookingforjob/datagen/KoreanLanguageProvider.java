package net.biryeongtrain.lookingforjob.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.biryeongtrain.lookingforjob.utils.TextUtils;

import java.util.concurrent.CompletableFuture;

public class KoreanLanguageProvider extends FabricLanguageProvider {

    protected KoreanLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "ko-kr", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(TextUtils.getTranslationKey("job.miner.name"), "광부");
    }
}
