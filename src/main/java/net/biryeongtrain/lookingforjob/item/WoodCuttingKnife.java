package net.biryeongtrain.lookingforjob.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.biryeongtrain.lookingforjob.block.BlockRegistries;
import net.biryeongtrain.lookingforjob.duck.ServerPlayerEntityExt;
import net.biryeongtrain.lookingforjob.job.Jobs;
import net.biryeongtrain.lookingforjob.job.PlayerJobData;
import net.biryeongtrain.lookingforjob.utils.GamePlayUtils;
import net.biryeongtrain.lookingforjob.utils.TextUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class WoodCuttingKnife extends Item implements PolymerItem {
    private final Random RANDOM = new Random();
    public final int PERCENT = 30;

    public WoodCuttingKnife(Settings settings) {
        super(settings);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return ItemModels.WOOD_CUTTING_KNIFE.item();
    }

    @Override
    public void modifyClientTooltip(List<Text> tooltip, ItemStack stack, @Nullable ServerPlayerEntity player) {
        if (player == null) {
            return;
        }

        var job = ((ServerPlayerEntityExt) player).lookingForJob$getJobData(Jobs.WOOD_CUTTER);
        boolean hasJob = job != null;
        var text1 = TextUtils.getTranslationText("item.wood_cutting_knife.lore.1");
        tooltip.add(text1
                .styled(style -> style
                        .withColor(TextColor.parse("#83ff70")
                                .result()
                                .orElse(TextColor.fromFormatting(Formatting.GREEN))))
        );

        if (hasJob) {
            var text2 = TextUtils.getTranslationText("item.wood_cutting_knife.lore.has_job");
            tooltip.add(text2
                    .styled(style -> style
                            .withColor(TextColor.parse("#83ff70")
                                    .result()
                                    .orElse(TextColor.fromFormatting(Formatting.RED))))
            );
        } else {
            var text3 = TextUtils.getTranslationText("item.wood_cutting_knife.lore.no_job");
            tooltip.add(text3
                    .styled(style -> style
                            .withColor(TextColor.parse("#fc5d44")
                                    .result()
                                    .orElse(TextColor.fromFormatting(Formatting.GREEN))))
            );
        }
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return ItemModels.WOOD_CUTTING_KNIFE.value();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.PASS;
        }
        var world = context.getWorld();
        var state = world.getBlockState(context.getBlockPos());
        if (state.getBlock() == Blocks.OAK_LOG) {
            this.tryDamageLog(context, state);
        }

        return super.useOnBlock(context);
    }

    private void tryDamageLog(ItemUsageContext context, BlockState state) {
        if (context.getPlayer() == null) {
            return;
        }

        var world = context.getWorld();
        if (state.getBlock() == Blocks.OAK_LOG) {

            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            ServerPlayerEntityExt playerExt = (ServerPlayerEntityExt) player;

            var jobData = playerExt.lookingForJob$getJobData(Jobs.WOOD_CUTTER);
            if (jobData == null) {
                return;
            }

            this.damageLog(context, jobData, state);
        }
    }

    private void damageLog(ItemUsageContext context, PlayerJobData data, BlockState state) {
        var world = context.getWorld();
        var player = context.getPlayer();
        if (player == null) {
            return;
        }

        var stack = context.getStack();
        var damage = stack.getDamage();
        var maxDamage = stack.getMaxDamage();
        if (damage >= maxDamage) {
            return;
        }

        var random = RANDOM.nextInt(100);
        boolean canSap = random <= PERCENT;

        stack.damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
        world.setBlockState(context.getBlockPos(), this.getDamagedSapLog(state, canSap));

        GamePlayUtils.playSound(world, context.getBlockPos(), canSap ? SoundEvents.ITEM_AXE_WAX_OFF : SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS);
        player.getItemCooldownManager().set(this, 200);
        player.swingHand(context.getHand(), true);
    }

    private BlockState getDamagedSapLog(BlockState state, boolean canSap) {
        var block = canSap ? BlockRegistries.DAMAGED_WITH_SAP_LOG : BlockRegistries.DAMAGED_LOG;
        return block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS));
    }
}
