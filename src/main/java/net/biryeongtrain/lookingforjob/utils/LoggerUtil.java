package net.biryeongtrain.lookingforjob.utils;

import net.biryeongtrain.lookingforjob.LookingForJob;

public class LoggerUtil {
    public static void logError(String message) {
        LookingForJob.LOGGER.error(message);
    }
}
