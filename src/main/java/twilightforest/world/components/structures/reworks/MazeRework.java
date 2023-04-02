package twilightforest.world.components.structures.reworks;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.loot.TFTreasure;
import twilightforest.world.components.structures.TwilightTemplateStructurePiece;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.Random;

public class MazeRework extends TwilightTemplateStructurePiece {
	public MazeRework(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.ReworkedLabyrinth, compoundTag, serverLevel, readSettings(compoundTag).addProcessor(MazestoneProcessor.INSTANCE));
	}

	public MazeRework(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.ReworkedLabyrinth, 0, structureManager, TwilightForestMod.prefix("labyrinth"), makeSettings(Rotation.NONE).addProcessor(MazestoneProcessor.INSTANCE), templatePosition);
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {
		if (!boundingBox.isInside(pos)) return;

		if ("chest".equals(function)) {
			TFTreasure.LABYRINTH_ROOM.generateLootContainer(level, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)), 16 | 4 | 2, random.nextLong());
		} else if("chest_special".equals(function)) {
			TFTreasure.LABYRINTH_VAULT.generateLootContainer(level, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)), 16 | 4 | 2, random.nextLong());
		}
	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}

	public static class MazestoneProcessor extends StructureProcessor {
		public static final MazestoneProcessor INSTANCE = new MazestoneProcessor();
		public static final Codec<MazestoneProcessor> CODEC = Codec.unit(() -> INSTANCE);

		@Nullable
		@Override
		public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos oldPos, BlockPos newPos, StructureTemplate.StructureBlockInfo oldInfo, StructureTemplate.StructureBlockInfo newInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
			Random random = settings.getRandom(newInfo.pos);

			random.setSeed(random.nextLong() * 5);

			BlockState state = newInfo.state;
			Block block = state.getBlock();

			if (block == TFBlocks.MAZESTONE_BRICK.get() && random.nextBoolean())
				return new StructureTemplate.StructureBlockInfo(newInfo.pos, random.nextBoolean() ? TFBlocks.MOSSY_MAZESTONE.get().defaultBlockState() : TFBlocks.CRACKED_MAZESTONE.get().defaultBlockState(), null);

			return newInfo;
		}

		@Override
		protected StructureProcessorType<?> getType() {
			return TFStructureProcessors.MAZESTONE;
		}
	}
}
