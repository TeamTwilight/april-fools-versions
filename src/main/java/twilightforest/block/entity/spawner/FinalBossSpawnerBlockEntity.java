package twilightforest.block.entity.spawner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import twilightforest.capabilities.CapabilityList;
import twilightforest.entity.boss.Interloper;
import twilightforest.events.CapabilityEvents;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFEntities;
import twilightforest.init.TFParticleType;

public class FinalBossSpawnerBlockEntity extends BossSpawnerBlockEntity<Interloper> {
	public static final String NBT_TAG_SPAWNERD = "twilightforest_spawnerd";

	public FinalBossSpawnerBlockEntity(BlockPos pos, BlockState state) {
		super(TFBlockEntities.FINAL_BOSS_SPAWNER.get(), TFEntities.INTERLOPER_BOSS.get(), pos, state);
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state) {
		for (Player player : level.players()) {
			CompoundTag tagCompound = player.getPersistentData();
			CompoundTag playerData = tagCompound.getCompound(Player.PERSISTED_NBT_TAG);
			if (!playerData.contains(NBT_TAG_SPAWNERD)) {
				Vec3 here = Vec3.atBottomCenterOf(pos);
				player.teleportTo(here.x, here.y, here.z);
				playerData.putBoolean(NBT_TAG_SPAWNERD, true);
				player.getCapability(CapabilityList.FEATHER_FAN_FALLING).ifPresent(featherFanFallCapability -> featherFanFallCapability.setFalling(true));
				playerData.putLong(CapabilityEvents.NBT_TAG_HOME, pos.asLong());
			}
		}
	}

	//no spawning for you
	@Override
	protected boolean spawnMyBoss(ServerLevelAccessor world) {
		return false;
	}

	@Override
	public ParticleOptions getSpawnerParticle() {
		return TFParticleType.ANNIHILATE.get();
	}
}
