package twilightforest.world.components.structures.reworks;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeaturePlacers;
import twilightforest.world.components.processors.StoneBricksVariants;
import twilightforest.world.components.structures.TwilightTemplateStructurePiece;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.Random;

public class FinalCastleRework extends TwilightTemplateStructurePiece {
	public FinalCastleRework(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.ReworkedCastle, compoundTag, serverLevel, readSettings(compoundTag).addProcessor(MazeRework.MazestoneProcessor.INSTANCE));
	}

	public FinalCastleRework(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.ReworkedCastle, 0, structureManager, TwilightForestMod.prefix("castle"), makeSettings(Rotation.NONE).addProcessor(CastleProcessor.INSTANCE), templatePosition);
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {

	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}

	public static class CastleProcessor extends StructureProcessor {
		public static final CastleProcessor INSTANCE = new CastleProcessor();
		public static final Codec<CastleProcessor> CODEC = Codec.unit(() -> INSTANCE);

		@Nullable
		@Override
		public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos oldPos, BlockPos newPos, StructureTemplate.StructureBlockInfo oldInfo, StructureTemplate.StructureBlockInfo newInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
			Random random = settings.getRandom(newInfo.pos);

			random.setSeed(random.nextLong() * 5);

			BlockState state = newInfo.state;
			Block block = state.getBlock();

			if (block == TFBlocks.CASTLE_BRICK.get() && random.nextBoolean())
				return new StructureTemplate.StructureBlockInfo(newInfo.pos, random.nextBoolean() ? TFBlocks.MOSSY_CASTLE_BRICK.get().defaultBlockState() : TFBlocks.CRACKED_CASTLE_BRICK.get().defaultBlockState(), null);

			if (block == TFBlocks.CASTLE_BRICK_STAIRS.get() && random.nextBoolean())
				return new StructureTemplate.StructureBlockInfo(newInfo.pos, FeaturePlacers.transferAllStateKeys(state, random.nextBoolean() ? TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get() : TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get()), null);

			return newInfo;
		}

		@Override
		protected StructureProcessorType<?> getType() {
			return TFStructureProcessors.CASTLE;
		}
	}
}