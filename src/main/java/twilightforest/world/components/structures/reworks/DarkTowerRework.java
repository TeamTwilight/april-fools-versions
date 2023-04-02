package twilightforest.world.components.structures.reworks;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
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
import twilightforest.data.BlockTagGenerator;
import twilightforest.loot.TFTreasure;
import twilightforest.world.components.structures.TwilightTemplateStructurePiece;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFStructureProcessors;

import javax.annotation.Nullable;
import java.util.Random;

public class DarkTowerRework extends TwilightTemplateStructurePiece {
	public DarkTowerRework(ServerLevel serverLevel, CompoundTag compoundTag) {
		super(TFFeature.ReworkedDarkTower, compoundTag, serverLevel, readSettings(compoundTag).addProcessor(TowerwoodProcessor.INSTANCE));
	}

	public DarkTowerRework(StructureManager structureManager, BlockPos templatePosition) {
		super(TFFeature.ReworkedDarkTower, 0, structureManager, TwilightForestMod.prefix("tower"), makeSettings(Rotation.NONE).addProcessor(TowerwoodProcessor.INSTANCE), templatePosition);
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox sbb) {
		if (!boundingBox.isInside(pos)) return;

		if ("chest".equals(function)) {
			TFTreasure.DARKTOWER_CACHE.generateLootContainer(level, pos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, this.placeSettings.getRotation().rotate(Direction.NORTH)), 16 | 4 | 2, random.nextLong());
		} else if ("pot".equals(function)) {
			level.setBlock(pos, BlockTagGenerator.DARK_TOWER_ALLOWED_POTS.getRandomElement(random).defaultBlockState(), 3);
		}  else if ("fish".equals(function)) {
			for(int i = 0; i < 7; i++) {
				TropicalFish fish = EntityType.TROPICAL_FISH.create(level.getLevel());
				fish.setVariant(random.nextInt(TropicalFish.COMMON_VARIANTS.length));
				fish.moveTo(pos.getX(), pos.getY(), pos.getZ());
				fish.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
				level.addFreshEntity(fish);
			}
		} else if ("nether_mob".equals(function)) {
			EntityType<?> mob;
			switch (random.nextInt(4)) {
				default -> mob = EntityType.ZOMBIFIED_PIGLIN;
				case 1 -> mob = EntityType.WITHER_SKELETON;
				case 2 -> mob = EntityType.BLAZE;
				case 3 -> mob = EntityType.PIGLIN_BRUTE;
			}
			Monster spawned = (Monster) mob.create(level.getLevel());
			spawned.moveTo(pos.getX(), pos.getY(), pos.getZ());
			if(spawned instanceof PiglinBrute brute) {
				brute.setImmuneToZombification(true);
			}
			spawned.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
			level.addFreshEntity(spawned);
		}
	}

	@Override
	public NoiseEffect getNoiseEffect() {
		return NoiseEffect.BEARD;
	}

	public static class TowerwoodProcessor extends StructureProcessor {
		public static final TowerwoodProcessor INSTANCE = new TowerwoodProcessor();
		public static final Codec<TowerwoodProcessor> CODEC = Codec.unit(() -> INSTANCE);

		@Nullable
		@Override
		public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos oldPos, BlockPos newPos, StructureTemplate.StructureBlockInfo oldInfo, StructureTemplate.StructureBlockInfo newInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
			Random random = settings.getRandom(newInfo.pos);

			random.setSeed(random.nextLong() * 5);

			BlockState state = newInfo.state;
			Block block = state.getBlock();

			if (block == TFBlocks.TOWERWOOD.get() && random.nextBoolean())
				return new StructureTemplate.StructureBlockInfo(newInfo.pos, random.nextBoolean() ? TFBlocks.MOSSY_TOWERWOOD.get().defaultBlockState() : random.nextBoolean() ? TFBlocks.CRACKED_TOWERWOOD.get().defaultBlockState() : TFBlocks.INFESTED_TOWERWOOD.get().defaultBlockState(), null);

			return newInfo;
		}

		@Override
		protected StructureProcessorType<?> getType() {
			return TFStructureProcessors.TOWERWOOD;
		}
	}
}