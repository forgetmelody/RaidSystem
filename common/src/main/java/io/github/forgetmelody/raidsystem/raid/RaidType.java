package io.github.forgetmelody.raidsystem.raid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.raidsystem.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;

public record RaidType(
        Component description,
        DataComponentMap components
) {
    public static final Codec<RaidType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("description").forGetter(RaidType::description),
            DataComponentMap.CODEC.fieldOf("components").forGetter(RaidType::components)
    ).apply(instance, RaidType::new));
    public static final Codec<Holder<RaidType>> REFERENCE_CODEC = RegistryFixedCodec.create(Registries.RAID_TYPE);
}
