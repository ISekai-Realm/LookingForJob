package net.biryeongtrain.lookingforjob.utils;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GamePlayUtils {
    public static void playSound(World world, BlockPos pos, SoundEvent event, SoundCategory category) {
        var x = pos.getX() + 0.5;
        var y = pos.getY() + 0.5;
        var z = pos.getZ() + 0.5;

        world.playSound(null, x,y,z, event, category, 1.0f, world.random.nextFloat() * 0.1F + 0.9f);
    }
}
