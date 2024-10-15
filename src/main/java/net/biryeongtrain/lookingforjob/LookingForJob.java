package net.biryeongtrain.lookingforjob;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.biryeongtrain.lookingforjob.block.BlockRegistries;
import net.biryeongtrain.lookingforjob.command.CommandRegistry;
import net.biryeongtrain.lookingforjob.data.JobDataLoader;
import net.biryeongtrain.lookingforjob.item.ItemModels;
import net.biryeongtrain.lookingforjob.item.ItemRegistry;
import net.biryeongtrain.lookingforjob.papi.TextPlaceHolders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookingForJob implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("LookingForJob");
    public static String MOD_ID = "looking-for-job";
    
    @Override
    public void onInitialize() {
        ItemModels.register();
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new JobDataLoader());
        TextPlaceHolders.register();
        CommandRegistrationCallback.EVENT.register(CommandRegistry::registerCommands);
        BlockRegistries.register();
        ItemRegistry.register();
    }
}
