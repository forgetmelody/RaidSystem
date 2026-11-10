package io.github.forgetmelody.raidsystem.components;

import com.mojang.serialization.Codec;
import io.github.forgetmelody.raidsystem.raid.RaidBarSettings;
import io.github.forgetmelody.raidsystem.raid.RaidStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.util.ExtraCodecs;

import java.util.Set;
import java.util.UUID;
import java.util.function.UnaryOperator;

public final class DataComponents {
    public static DataComponentType<RaidBarSettings> RAID_BAR_SETTINGS = create(builder -> builder.persistent(RaidBarSettings.CODEC));
    public static DataComponentType<Integer> MAX_RAID_COOLDOWN_TICKS = create(builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT));

    public static DataComponentType<Set<UUID>> HEROES_OF_THE_VILLAGE = create(builder -> builder.persistent(UUIDUtil.CODEC_SET));
    public static DataComponentType<ServerBossEvent> RAID_BAR = createTransient();
    public static DataComponentType<Integer> NUM_GROUPS = create(builder -> builder.persistent(Codec.INT));
    public static DataComponentType<Integer> GROUPS_SPAWNED = create(builder -> builder.persistent(Codec.INT));
    public static DataComponentType<RaidStatus> STATUS = create(builder -> builder.persistent(RaidStatus.CODEC));
    public static DataComponentType<Boolean> ACTIVE = create(builder -> builder.persistent(Codec.BOOL));
    public static DataComponentType<Boolean> STARTED = create(builder -> builder.persistent(Codec.BOOL));
    public static DataComponentType<BlockPos> CENTER = create(builder -> builder.persistent(BlockPos.CODEC));
    public static DataComponentType<BlockPos> WAVE_SPAWN_POS = create(builder -> builder.persistent(BlockPos.CODEC));
    public static DataComponentType<Integer> TICKS_ACTIVE = create(builder -> builder.persistent(Codec.INT));
    public static DataComponentType<Integer> POST_RAID_TICKS = create(builder -> builder.persistent(Codec.INT));
    public static DataComponentType<Integer> RAID_COOLDOWN_TICKS = create(builder -> builder.persistent(Codec.INT));
    public static DataComponentType<Integer> CELEBRATION_TICKS = create(builder -> builder.persistent(Codec.INT));

    private DataComponents() {
    }

    private static <T> DataComponentType<T> createTransient() {
        return DataComponentType.<T>builder().build();
    }

    private static <T> DataComponentType<T> create(UnaryOperator<DataComponentType.Builder<T>> builder) {
        return builder.apply(DataComponentType.builder()).build();
    }
}
