package io.github.forgetmelody.raidsystem.raid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

public final class Raids extends SavedData {
    public static Codec<Raids> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RaidWithId.CODEC.listOf().fieldOf("raids").forGetter(raids -> raids.raidMap.int2ReferenceEntrySet().stream().map(RaidWithId::from).toList()),
            Codec.INT.fieldOf("tick").forGetter(raids -> raids.tick),
            Codec.INT.fieldOf("next_id").forGetter(raids -> raids.nextId)
    ).apply(instance, Raids::new));
    private final Int2ReferenceMap<Raid> raidMap = new Int2ReferenceOpenHashMap<>();
    private int tick;
    private int nextId;

    public Raids() {
    }

    private Raids(List<RaidWithId> raidWithIdList, int tick, int nextId) {
        raidWithIdList.forEach(raidWithId -> this.raidMap.put(raidWithId.id, raidWithId.raid));
        this.tick = tick;
        this.nextId = nextId;
    }

    @ExpectPlatform
    public static Raids get(ServerLevel level) {
        throw new AssertionError();
    }

    public @Nullable Raid get(int id) {
        return this.raidMap.get(id);
    }

    public OptionalInt getId(Raid raid) {
        for (Int2ReferenceMap.Entry<Raid> entry : this.raidMap.int2ReferenceEntrySet()) {
            if (entry.getValue() == raid) {
                return OptionalInt.of(entry.getIntKey());
            }
        }

        return OptionalInt.empty();
    }

    private record RaidWithId(int id, Raid raid) {
        public static Codec<RaidWithId> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("id").forGetter(RaidWithId::id),
                Raid.MAP_CODEC.forGetter(RaidWithId::raid)
        ).apply(instance, RaidWithId::new));

        public static RaidWithId from(Int2ReferenceMap.Entry<Raid> entry) {
            return new RaidWithId(entry.getIntKey(), entry.getValue());
        }
    }
}
