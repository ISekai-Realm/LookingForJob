package net.biryeongtrain.lookingforjob.job;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.parsers.TagParser;
import net.biryeongtrain.lookingforjob.event.PlayerGainJobExperienceEvent;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.biryeongtrain.lookingforjob.utils.TextUtils;
import xyz.nucleoid.stimuli.Stimuli;

import java.util.UUID;

public class JobDataBossBar extends BossBar {
    private long age = 0L;
    private int displayInterval;
    private long displayedTime = -1L;
    private DisplayType displayType = DisplayType.INTERVAL;
    private PlayerJobData data;

    public JobDataBossBar(UUID uuid, Color color, int displayInterval, PlayerJobData data) {
        super(uuid, Text.empty(), color, Style.NOTCHED_20);
        this.displayInterval = displayInterval;
    }

    public JobDataBossBar(UUID uuid, Color color, PlayerJobData data) {
        this(uuid, color, 20, data);
        this.data = data;
    }

    public long getAge() {
        return age;
    }

    public int getDisplayInterval() {
        return displayInterval;
    }

    public void setDisplayInterval(int displayInterval) {
        this.displayInterval = displayInterval;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(DisplayType displayType) {
        this.displayType = displayType;
    }

    public void tick() {
        age++;

        if (displayType == DisplayType.INTERVAL && displayedTime == age) {
            data.getPlayer().networkHandler.sendPacket(BossBarS2CPacket.remove(this.getUuid()));
        }
    }

    public void update(PlayerJobData data) {
        long percent = Math.round(data.getExp() / data.getRequiredToNextLevel() * 100);
        this.setPercent((float) percent / 100);
        var text = Placeholders.parseText(TagParser.QUICK_TEXT.parseNode(
                TextUtils.getPlaceholderText("job_name %s".formatted(data.getJobPriority())) +
                        " - " +
                        TextUtils.getPlaceholderText("job_exp %s".formatted(data.getJobPriority())) +
                        " / " +
                        TextUtils.getPlaceholderText("job_next_level_exp %s".formatted(data.getJobPriority()))
        ), PlaceholderContext.of(data.getPlayer()));
        this.setName(text);

        if (displayType == DisplayType.INTERVAL) {
            data.getPlayer().networkHandler.sendPacket(BossBarS2CPacket.add(this));
            this.displayedTime = age + displayInterval;
        } else if (displayType == DisplayType.ALWAYS) {
            data.getPlayer().networkHandler.sendPacket(BossBarS2CPacket.updateName(this));
            data.getPlayer().networkHandler.sendPacket(BossBarS2CPacket.updateProgress(this));
            data.getPlayer().networkHandler.sendPacket(BossBarS2CPacket.updateStyle(this));
        }
    }

    static {
        Stimuli.global().listen(PlayerGainJobExperienceEvent.PLAYER_EXP_GET_EVENT, (data, targetId, exp, reason) -> {
            data.getBossBar().update(data);

            return ActionResult.PASS;
        });
    }


    public enum DisplayType {
        ALWAYS,
        INTERVAL,
        NEVER
    }
}
