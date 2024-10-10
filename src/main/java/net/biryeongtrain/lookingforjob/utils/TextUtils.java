package net.biryeongtrain.lookingforjob.utils;

import net.biryeongtrain.lookingforjob.LookingForJob;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextUtils {
    public static MutableText getTranslationText(String key) {
        return Text.translatable(getTranslationKey(key));
    }

    public static String getTranslationKey(String key) {
        return LookingForJob.MOD_ID + "." + key;
    }

    public static String getPlaceholderText(String key) {
        return "%" + LookingForJob.MOD_ID + ":" + key + "%";
    }
}
