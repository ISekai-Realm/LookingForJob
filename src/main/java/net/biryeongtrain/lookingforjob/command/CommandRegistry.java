package net.biryeongtrain.lookingforjob.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandRegistry {
    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        var jobNode = CommandManager.literal("job").requires(source -> source.hasPermissionLevel(2)).build();

        dispatcher.getRoot().addChild(jobNode);
        jobNode.addChild(SetPlayerJobCommand.register());
    }
}
