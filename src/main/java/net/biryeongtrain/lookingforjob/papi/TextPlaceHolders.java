package net.biryeongtrain.lookingforjob.papi;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.biryeongtrain.lookingforjob.LookingForJob;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.utils.IdUtils;
import net.biryeongtrain.lookingforjob.utils.TextUtils;

public class TextPlaceHolders {
    public static void register() {
        Placeholders.register(IdUtils.getId("job_name"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("Player required");
            }

            if (arg == null) {
                return PlaceholderResult.invalid("Job index required");
            }

            try {
                int parsedIndex = Integer.parseInt(arg);
                var data = ((ServerPlayerEntityExt) ctx.player()).lookingForJob$getPlayerJob(parsedIndex);
                return PlaceholderResult.value(data == null ? TextUtils.getTranslationText("job.none.name") : data.getJob().getTranslationText());
            } catch (NumberFormatException e) {
                LookingForJob.LOGGER.error("Failed to parse job index", e);
                return PlaceholderResult.invalid("Invalid job index");
            }
        });

        Placeholders.register(IdUtils.getId("job_exp"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("Player required");
            }

            if (arg == null) {
                return PlaceholderResult.invalid("Job index required");
            }

            try {
                int parsedIndex = Integer.parseInt(arg);
                var data = ((ServerPlayerEntityExt) ctx.player()).lookingForJob$getPlayerJob(parsedIndex);
                return PlaceholderResult.value(data == null ? "0" : String.valueOf(Math.round(data.getExp())));
            } catch (NumberFormatException e) {
                LookingForJob.LOGGER.error("Failed to parse job index", e);
                return PlaceholderResult.invalid("Invalid job index");
            }
        });

        Placeholders.register(IdUtils.getId("job_level"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("Player required");
            }

            if (arg == null) {
                return PlaceholderResult.invalid("Job index required");
            }

            try {
                int parsedIndex = Integer.parseInt(arg);
                var data = ((ServerPlayerEntityExt) ctx.player()).lookingForJob$getPlayerJob(parsedIndex);
                return PlaceholderResult.value(data == null ? "0" : String.valueOf(data.getLevel()));
            } catch (NumberFormatException e) {
                LookingForJob.LOGGER.error("Failed to parse job index", e);
                return PlaceholderResult.invalid("Invalid job index");
            }
        });


        Placeholders.register(IdUtils.getId("job_next_level_exp"), (ctx, arg) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("Player required");
            }

            if (arg == null) {
                return PlaceholderResult.invalid("Job index required");
            }

            try {
                int parsedIndex = Integer.parseInt(arg);
                var data = ((ServerPlayerEntityExt) ctx.player()).lookingForJob$getPlayerJob(parsedIndex);
                return PlaceholderResult.value(data == null ? "0" : String.valueOf(Math.round(data.getJob().getNextLevelUpPoint(data.getLevel()))));
            } catch (NumberFormatException e) {
                LookingForJob.LOGGER.error("Failed to parse job index", e);
                return PlaceholderResult.invalid("Invalid job index");
            }
        });
    }
}
