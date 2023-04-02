package twilightforest.entity.boss.finalboss;

import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;

public class DiveBomb implements BossPhaseInstance {
	private Vec3 targetLocation;
	private int timeSinceCharge;
	protected final PlateauBoss dragon;

	public DiveBomb(PlateauBoss boss) {
		this.dragon = boss;
	}

	@Override
	public void doClientTick() {

	}

	/**
	 * Gives the phase a chance to update its status.
	 * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
	 */
	public void doServerTick() {
		if (this.targetLocation == null) {
			TwilightForestMod.LOGGER.warn("Aborting charge player as no target was set.");
			this.dragon.getPhaseManager().setPhase(BossPhase.CIRCLE_ARENA);
		} else if (this.timeSinceCharge > 0 && this.timeSinceCharge++ >= 10) {
			this.dragon.getPhaseManager().setPhase(BossPhase.CIRCLE_ARENA);
		} else {
			double d0 = this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
			if (d0 < 100.0D || d0 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
				++this.timeSinceCharge;
			}

		}
	}

	/**
	 * Called when this phase is set to active
	 */
	public void begin() {
		this.targetLocation = null;
		this.timeSinceCharge = 0;
	}

	@Override
	public void end() {

	}

	public void setTarget(Vec3 p_31208_) {
		this.targetLocation = p_31208_;
	}

	/**
	 * Returns the maximum amount dragon may rise or fall during this phase
	 */
	public float getFlySpeed() {
		return 3.0F;
	}

	/**
	 * Returns the location the dragon is flying toward
	 */
	@Nullable
	public Vec3 getFlyTargetLocation() {
		return this.targetLocation;
	}

	public BossPhase<DiveBomb> getPhase() {
		return BossPhase.DIVEBOMB;
	}
}
