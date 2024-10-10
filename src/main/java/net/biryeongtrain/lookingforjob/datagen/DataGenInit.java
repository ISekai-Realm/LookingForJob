package net.biryeongtrain.lookingforjob.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenInit implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(JobDataProvider::new);
        pack.addProvider(EnglishLanguageProvider::new);
        pack.addProvider(KoreanLanguageProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(ModelProvider::new);
    }
}
