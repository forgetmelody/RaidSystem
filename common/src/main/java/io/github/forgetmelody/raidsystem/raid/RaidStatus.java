package io.github.forgetmelody.raidsystem.raid;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum RaidStatus implements StringRepresentable {
    ONGOING("ongoing"),
    VICTORY("victory"),
    LOSS("loss"),
    STOPPED("stopped");
    public static Codec<RaidStatus> CODEC = StringRepresentable.fromEnum(RaidStatus::values);
    private final String name;

    RaidStatus(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
