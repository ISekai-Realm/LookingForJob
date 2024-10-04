package net.biryeongtrain.lookingforjob.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.job.Jobs;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SetPlayerJobCommand {
    public static LiteralCommandNode<ServerCommandSource> register() {
        return CommandManager.literal("setJob")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("job", IdentifierArgumentType.identifier())
                                .then(CommandManager.argument("priority", IntegerArgumentType.integer(0))
                                        .executes(context -> setJob(context, EntityArgumentType.getPlayer(context, "player"), IdentifierArgumentType.getIdentifier(context, "job"), IntegerArgumentType.getInteger(context, "priority")))
                                )
                        )
                ).build();

    }

    public static int setJob(CommandContext<ServerCommandSource> ctx, ServerPlayerEntity player, Identifier jobId, int priority) {
        var jobOptional = Jobs.getJob(jobId);

        if (jobOptional.isEmpty()) {
            ctx.getSource().sendError(Text.literal("[ERROR] Job not found"));
            return 0;
        }
        var ext = (ServerPlayerEntityExt) player;
        ext.lookingForJob$setPlayerJob(jobOptional.get(), priority);
        return 1;
    }
}
