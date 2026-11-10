package io.github.forgetmelody.raidsystem.registries;

import io.github.forgetmelody.raidsystem.RaidSystem;
import io.github.forgetmelody.raidsystem.raid.RaidType;
import io.github.forgetmelody.raidsystem.raid.RaiderType;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

public final class Registries {
    public static ResourceKey<Registry<DataComponentType<?>>> RAID_TYPE_COMPONENT = key("raid_type_component");
    public static ResourceKey<Registry<DataComponentType<?>>> RAID_COMPONENT = key("raid_component");
    public static ResourceKey<Registry<RaidType>> RAID_TYPE = key("raid_type");
    public static ResourceKey<Registry<RaiderType>> RAIDER_TYPE = key("raider_type");

    private Registries() {
    }

    public static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(RaidSystem.id(name));
    }
}
