package twilightforest.world.components.structures.reworks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TwilightForestMod;
import twilightforest.loot.TFTreasure;
import twilightforest.world.components.structures.TwilightTemplateStructurePiece;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class AuroraPalaceRework extends TwilightTemplateStructurePiece {
	public AuroraPalaceRework(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.ReworkedPalace, compoundTag, serverLevel, readSettings(compoundTag));
	}

	public AuroraPalaceRework(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.ReworkedPalace, 0, structureManager, TwilightForestMod.prefix("palace"), makeSettings(Rotation.NONE), templatePosition);
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {
		if (!boundingBox.isInside(pos)) return;

		if ("chest".equals(function)) {
			TFTreasure.AURORA_ROOM.generateLootContainer(level, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)), 16 | 4 | 2, random.nextLong());
		}
	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}
}
