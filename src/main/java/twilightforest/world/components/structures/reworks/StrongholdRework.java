package twilightforest.world.components.structures.reworks;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.loot.TFTreasure;
import twilightforest.world.components.processors.CobbleVariants;
import twilightforest.world.components.processors.StoneBricksVariants;
import twilightforest.world.components.structures.TwilightTemplateStructurePiece;
import twilightforest.world.components.structures.courtyard.NagaCourtyardPieces;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.Random;

public class StrongholdRework extends TwilightTemplateStructurePiece {
	public StrongholdRework(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.ReworkedStronghold, compoundTag, serverLevel, readSettings(compoundTag).addProcessor(StrongholdProcessor.INSTANCE).addProcessor(StoneBricksVariants.INSTANCE).addProcessor(CobbleVariants.INSTANCE));
	}

	public StrongholdRework(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.ReworkedStronghold, 0, structureManager, TwilightForestMod.prefix("stronghold"), makeSettings(Rotation.NONE).addProcessor(StrongholdProcessor.INSTANCE).addProcessor(StoneBricksVariants.INSTANCE).addProcessor(CobbleVariants.INSTANCE), templatePosition);
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {
		if (!boundingBox.isInside(pos)) return;

		if ("chest".equals(function)) {
			TFTreasure.STRONGHOLD_ROOM.generateLootContainer(level, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)), 16 | 4 | 2, random.nextLong());
		}
	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}

	public static class StrongholdProcessor extends StructureProcessor {
		public static final StrongholdProcessor INSTANCE = new StrongholdProcessor();
		public static final Codec<StrongholdProcessor> CODEC = Codec.unit(() -> INSTANCE);

		@Nullable
		@Override
		public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos oldPos, BlockPos newPos, StructureTemplate.StructureBlockInfo oldInfo, StructureTemplate.StructureBlockInfo newInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
			Random random = settings.getRandom(newInfo.pos);

			random.setSeed(random.nextLong() * 5);

			BlockState state = newInfo.state;
			Block block = state.getBlock();

			if (block == TFBlocks.UNDERBRICK.get() && random.nextBoolean())
				return new StructureTemplate.StructureBlockInfo(newInfo.pos, random.nextBoolean() ? TFBlocks.MOSSY_UNDERBRICK.get().defaultBlockState() : TFBlocks.CRACKED_UNDERBRICK.get().defaultBlockState(), null);

			return newInfo;
		}

		@Override
		protected StructureProcessorType<?> getType() {
			return TFStructureProcessors.STRONGHOLD;
		}
	}
}
