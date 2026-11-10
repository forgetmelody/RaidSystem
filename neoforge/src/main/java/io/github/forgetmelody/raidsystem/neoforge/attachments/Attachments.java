package io.github.forgetmelody.raidsystem.neoforge.attachments;

import com.mojang.serialization.Codec;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.attachment.AttachmentType;

public final class Attachments {
    public static final AttachmentType<Unit> CAPTAIN = AttachmentType.builder(() -> Unit.INSTANCE).serialize(Unit.CODEC.fieldOf("captain")).build();
    public static final AttachmentType<Integer> RAID_ID = AttachmentType.builder(() -> -1).serialize(Codec.INT.fieldOf("raid_id")).build();
    public static final AttachmentType<Integer> WAVE = AttachmentType.builder(() -> -1).serialize(Codec.INT.fieldOf("raid_id")).build();
    public static final AttachmentType<Integer> TICKS_OUTSIDE_RAID = AttachmentType.builder(() -> -1).serialize(Codec.INT.fieldOf("ticks_outside_raid")).build();

    private Attachments() {
    }
}
