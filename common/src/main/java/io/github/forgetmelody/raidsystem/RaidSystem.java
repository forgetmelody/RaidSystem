package io.github.forgetmelody.raidsystem;

import net.minecraft.resources.Identifier;

public final class RaidSystem {
    public static final String MOD_ID = "raid_system";

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
