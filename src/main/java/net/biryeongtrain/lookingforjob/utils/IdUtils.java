package net.biryeongtrain.lookingforjob.utils;

import net.biryeongtrain.lookingforjob.LookingForJob;
import net.minecraft.util.Identifier;

public class IdUtils {
    public static Identifier getId(String path) {
        return Identifier.of(LookingForJob.MOD_ID, path);
    }

    public static Identifier getBlockModelId(String path) {
        return getId("block/" + path);
    }

    public static Identifier getItemModelId(String path) {
        return getId("item/" + path);
    }
}
