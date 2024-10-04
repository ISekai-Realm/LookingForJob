package net.biryeongtrain.lookingforjob;

import net.biryeongtrain.lookingforjob.command.CommandRegistry;
import net.biryeongtrain.lookingforjob.data.JobDataLoader;
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
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new JobDataLoader());

        CommandRegistrationCallback.EVENT.register(CommandRegistry::registerCommands);
    }
}
