package io.github.forgetmelody.raidsystem.registries;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

public final class BuiltInRegistries {
    public static final Registry<DataComponentType<?>> RAID_TYPE_COMPONENT = create(Registries.RAID_TYPE_COMPONENT);
    public static final Registry<DataComponentType<?>> RAID_COMPONENT = create(Registries.RAID_COMPONENT);

    private BuiltInRegistries() {
    }

    public static <T> Registry<T> create(ResourceKey<Registry<T>> key) {
        return new MappedRegistry<>(key, Lifecycle.stable());
    }
}
