package twilightforest.world.components.structures.reworks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.processors.NagastoneVariants;
import twilightforest.world.components.processors.StoneBricksVariants;
import twilightforest.world.components.structures.TwilightTemplateStructurePiece;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class NagaCourtyardRework extends TwilightTemplateStructurePiece {
	public NagaCourtyardRework(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.ReworkedCourtyard, compoundTag, serverLevel, readSettings(compoundTag).addProcessor(NagastoneVariants.INSTANCE).addProcessor(StoneBricksVariants.INSTANCE));
	}

	public NagaCourtyardRework(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.ReworkedCourtyard, 0, structureManager, TwilightForestMod.prefix("courtyard"), makeSettings(Rotation.NONE).addProcessor(NagastoneVariants.INSTANCE).addProcessor(StoneBricksVariants.INSTANCE), templatePosition);
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {
		//courtyard has nothing to process
	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}
}
