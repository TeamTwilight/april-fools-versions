package twilightforest.world.components.structures.courtyard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import twilightforest.world.registration.TFFeature;
import twilightforest.TwilightForestMod;

public class NagaCourtyardHedgeCornerComponent extends NagaCourtyardHedgeAbstractComponent {
    public NagaCourtyardHedgeCornerComponent(ServerLevel level, CompoundTag nbt) {
        super(level, NagaCourtyardPieces.TFNCCr, nbt, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner_big"));
    }

    public NagaCourtyardHedgeCornerComponent(TFFeature feature, int i, int x, int y, int z, Rotation rotation) {
        super(NagaCourtyardPieces.TFNCCr, feature, i, x, y, z, rotation, new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner"), new ResourceLocation(TwilightForestMod.ID, "courtyard/hedge_corner_big"));
    }
}
