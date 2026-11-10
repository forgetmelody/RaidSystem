package io.github.forgetmelody.raidsystem.raid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.BossEvent;

public record RaidBarSettings(
        Component raidName,
        Component raidVictoryName,
        Component raidDefeatName,
        BossEvent.BossBarColor color,
        BossEvent.BossBarOverlay overlay
) {
    public static final RaidBarSettings INSTANCE = new RaidBarSettings(
            Component.translatable("event.minecraft.raid"),
            Component.translatable("event.minecraft.raid.victory.full"),
            Component.translatable("event.minecraft.raid.defeat.full"),
            BossEvent.BossBarColor.RED,
            BossEvent.BossBarOverlay.NOTCHED_10
    );
    public static final Codec<RaidBarSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.optionalFieldOf("raid_name", Component.translatable("event.minecraft.raid")).forGetter(RaidBarSettings::raidName),
            ComponentSerialization.CODEC.optionalFieldOf("raid_victory_name", Component.translatable("event.minecraft.raid.victory.full")).forGetter(RaidBarSettings::raidVictoryName),
            ComponentSerialization.CODEC.optionalFieldOf("raid_defeat_name", Component.translatable("event.minecraft.raid.defeat.full")).forGetter(RaidBarSettings::raidDefeatName),
            BossEvent.BossBarColor.CODEC.optionalFieldOf("color", BossEvent.BossBarColor.RED).forGetter(RaidBarSettings::color),
            BossEvent.BossBarOverlay.CODEC.optionalFieldOf("overlay", BossEvent.BossBarOverlay.NOTCHED_10).forGetter(RaidBarSettings::overlay)
    ).apply(instance, RaidBarSettings::new));
}
