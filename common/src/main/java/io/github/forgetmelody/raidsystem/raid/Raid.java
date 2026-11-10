package io.github.forgetmelody.raidsystem.raid;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.raidsystem.components.DataComponents;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;

import java.util.Collection;
import java.util.function.Consumer;

public final class Raid {
    public static MapCodec<Raid> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RaidType.REFERENCE_CODEC.fieldOf("type").forGetter(Raid::getType),
            DataComponentPatch.CODEC.fieldOf("components").forGetter(raid -> raid.components.asPatch())
    ).apply(instance, Raid::new));
    private final RandomSource random = RandomSource.create();
    private final Int2ReferenceMap<ReferenceSet<Mob>> groupRaiderMap = new Int2ReferenceOpenHashMap<>();
    private final Int2ReferenceMap<Mob> groupToLeaderMap = new Int2ReferenceOpenHashMap<>();
    private final Holder<RaidType> type;
    private final PatchedDataComponentMap components;

    public Raid(Holder<RaidType> type, int numGroups, BlockPos center) {
        this.type = type;
        this.components = new PatchedDataComponentMap(type.value().components());
        if (type.value().components().has(DataComponents.RAID_BAR_SETTINGS)) {
            RaidBarSettings settings = type.value().components().get(DataComponents.RAID_BAR_SETTINGS);
            assert settings != null;
            ServerBossEvent raidBar = new ServerBossEvent(
                    Mth.createInsecureUUID(this.random),
                    settings.raidName(),
                    settings.color(),
                    settings.overlay()
            );
            raidBar.setProgress(0.0F);
            this.components.set(
                    DataComponents.RAID_BAR,
                    raidBar
            );
        }
        this.components.set(DataComponents.NUM_GROUPS, numGroups);
        this.components.set(DataComponents.CENTER, center);
        this.components.set(DataComponents.ACTIVE, true);
        this.components.set(DataComponents.STATUS, RaidStatus.ONGOING);
        this.components.set(
                DataComponents.RAID_COOLDOWN_TICKS,
                type.value().components().getOrDefault(DataComponents.MAX_RAID_COOLDOWN_TICKS, 300)
        );
    }

    private Raid(
            Holder<RaidType> type,
            DataComponentPatch patch
    ) {
        this.type = type;
        this.components = PatchedDataComponentMap.fromPatch(type.value().components(), patch);
    }

    public Holder<RaidType> getType() {
        return this.type;
    }

    public DataComponentMap getComponents() {
        return this.components;
    }

    public DataComponentMap getPrototype() {
        return this.type.value().components();
    }

    public BlockPos getCenter() {
        return this.components.get(DataComponents.CENTER);
    }

    public boolean isActive() {
        return this.components.getOrDefault(DataComponents.ACTIVE, false);
    }

    public void setActive(boolean active) {
        if (active) {
            this.components.set(DataComponents.ACTIVE, true);
        } else {
            this.components.remove(DataComponents.ACTIVE);
        }
    }

    public boolean isStarted() {
        return this.components.getOrDefault(DataComponents.STARTED, false);
    }

    public void setStarted(boolean started) {
        if (started) {
            this.components.set(DataComponents.STARTED, true);
        } else {
            this.components.remove(DataComponents.STARTED);
        }
    }

    public boolean isOngoing() {
        return this.getStatus() == RaidStatus.ONGOING;
    }

    public boolean isVictory() {
        return this.getStatus() == RaidStatus.VICTORY;
    }

    public boolean isLoss() {
        return this.getStatus() == RaidStatus.LOSS;
    }

    public boolean isOver() {
        return this.isVictory() || this.isLoss();
    }

    public RaidStatus getStatus() {
        return this.components.getOrDefault(DataComponents.STATUS, RaidStatus.STOPPED);
    }

    public void setStatus(RaidStatus status) {
        this.components.set(DataComponents.STATUS, status);
    }

    public void updateRaidBar(Consumer<ServerBossEvent> consumer) {
        ServerBossEvent raidBar = this.components.get(DataComponents.RAID_BAR);
        if (raidBar != null) {
            consumer.accept(raidBar);
        }
    }

    public int getTotalRaidersAlive() {
        return this.groupRaiderMap.values().stream().mapToInt(Collection::size).sum();
    }

    public void tick(ServerLevel level) {
        if (this.isOngoing()) {
            boolean oldActive = this.isActive();
            //noinspection deprecation
            this.setActive(level.hasChunkAt(this.getCenter()));

            if (oldActive != this.isActive()) {
                this.updateRaidBar(bar -> bar.setVisible(this.isActive()));
            }


        } else if (this.isOver()) {

            if (this.isVictory()) {

            } else {

            }
        }
    }
}
