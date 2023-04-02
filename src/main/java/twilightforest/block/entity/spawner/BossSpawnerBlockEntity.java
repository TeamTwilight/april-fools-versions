package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.TFBlocks;
import twilightforest.block.entity.TFSignBlockEntity;
import twilightforest.capabilities.CapabilityList;
import twilightforest.entity.TFEntities;

public abstract class BossSpawnerBlockEntity<T extends Mob> extends BlockEntity {

	protected static final int SHORT_RANGE = 9, LONG_RANGE = 50;

	protected final EntityType<T> entityType;
	protected boolean spawnedBoss = false;

	protected BossSpawnerBlockEntity(BlockEntityType<?> type, EntityType<T> entityType, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.entityType = entityType;
	}

	public boolean anyPlayerInRange() {
		return level.hasNearbyAlivePlayer(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D, getRange());
	}

	public static void tick(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity<?> te) {

		if(!level.isClientSide) {
			if (te.entityType == TFEntities.HYDRA || te.entityType == TFEntities.NAGA || te.entityType == TFEntities.SNOW_QUEEN) {
				Player closestPlayer = level.getNearestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, te.getRange(), false);
				if (closestPlayer != null && closestPlayer.getCapability(CapabilityList.OPTITRASH).isPresent() && closestPlayer.getCapability(CapabilityList.OPTITRASH).resolve().get().hasOptifine()) {
					level.setBlock(pos.above(), TFBlocks.SORTING_WALL_SIGN.get().defaultBlockState().setValue(WallSignBlock.FACING, closestPlayer.getDirection().getOpposite()), 2);
					TFSignBlockEntity sign = new TFSignBlockEntity(pos.above(), TFBlocks.SORTING_WALL_SIGN.get().defaultBlockState());
					sign.setMessage(0, new TextComponent("Optifine"));
					sign.setMessage(1, new TextComponent("compatible"));
					sign.setMessage(2, new TranslatableComponent("%s", te.entityType.getDescription().getString()));
					sign.setMessage(3, new TextComponent("Spawner"));
					level.setBlockEntity(sign);
					if(level.getBlockEntity(pos.above()) instanceof TFSignBlockEntity tfSignBlock) {
						tfSignBlock.setColor(DyeColor.WHITE);
						tfSignBlock.setHasGlowingText(true);
					}
				}
			}
		}

		if (te.spawnedBoss || !te.anyPlayerInRange()) {
			return;
		}
		if (level.isClientSide) {
			// particles
			double rx = (pos.getX() - 0.2F) + (level.random.nextFloat() * 1.25F);
			double ry = (pos.getY() - 0.2F) + (level.random.nextFloat() * 1.25F);
			double rz = (pos.getZ() - 0.2F) + (level.random.nextFloat() * 1.25F);
			//level.addParticle(ParticleTypes.SMOKE, rx, ry, rz, 0.0D, 0.0D, 0.0D);
			level.addParticle(te.getSpawnerParticle(), rx, ry, rz, 0.0D, 0.0D, 0.0D);
		} else {
			if (level.getDifficulty() != Difficulty.PEACEFUL) {
				if (te.spawnMyBoss((ServerLevel)level)) {
					level.destroyBlock(pos, false);
					te.spawnedBoss = true;
				}
			}
		}
	}

	protected boolean spawnMyBoss(ServerLevelAccessor world) {
		// create creature
		T myCreature = makeMyCreature();

		myCreature.moveTo(worldPosition, world.getLevel().random.nextFloat() * 360F, 0.0F);
		myCreature.finalizeSpawn(world, world.getCurrentDifficultyAt(worldPosition), MobSpawnType.SPAWNER, null, null);

		// set creature's home to this
		initializeCreature(myCreature);

		// spawn it
		return world.addFreshEntity(myCreature);
	}

	public abstract ParticleOptions getSpawnerParticle();

	protected void initializeCreature(T myCreature) {
		myCreature.restrictTo(worldPosition, 46);
	}

	protected int getRange() {
		return SHORT_RANGE;
	}

	protected T makeMyCreature() {
		return entityType.create(level);
	}
}
