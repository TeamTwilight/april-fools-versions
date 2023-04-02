package twilightforest.entity.boss.finalboss;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public interface BossPhaseInstance {
	/**
	 * Generates particle effects appropriate to the phase (or sometimes sounds).
	 * Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
	 */
	void doClientTick();

	/**
	 * Gives the phase a chance to update its status.
	 * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
	 */
	void doServerTick();

	/**
	 * Called when this phase is set to active
	 */
	void begin();

	void end();

	float getFlySpeed();

	BossPhase<? extends BossPhaseInstance> getPhase();

	/**
	 * Returns the location the dragon is flying toward
	 */
	@Nullable
	Vec3 getFlyTargetLocation();
}
