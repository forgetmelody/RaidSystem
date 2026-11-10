package io.github.forgetmelody.raidsystem.raid;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

public final class RaiderHelper {
    private RaiderHelper() {
    }

    public static @Nullable Raid getCurrentRaid(ServerLevel level, Entity entity) {
        OptionalInt raidId = getRaidId(entity);
        if (raidId.isPresent()) {
            return Raids.get(level).get(raidId.orElseThrow());
        }

        return null;
    }

    public static void setCurrentRaid(ServerLevel level, Entity entity, @Nullable Raid raid) {
        if (raid == null) {
            setRaidId(entity, -1);
        } else {
            Raids.get(level).getId(raid).ifPresent(id -> setRaidId(entity, id));
        }
    }

    @ExpectPlatform
    public static boolean isCaptain(Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean setCaptain(Entity entity, boolean captain) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static OptionalInt getRaidId(Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setRaidId(Entity entity, int raidId) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static OptionalInt getWave(Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setWave(Entity entity, int wave) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static OptionalInt getTicksOutsideRaid(Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setTicksOutsideRaid(Entity entity, int ticks) {
        throw new AssertionError();
    }
}
