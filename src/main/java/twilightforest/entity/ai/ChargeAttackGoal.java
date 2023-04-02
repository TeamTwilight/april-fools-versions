package twilightforest.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.entity.ITFCharger;
import twilightforest.util.EntityUtil;

import java.util.EnumSet;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class ChargeAttackGoal extends Goal {

	private static final double MIN_RANGE_SQ = 16.0D;
	private static final double MAX_RANGE_SQ = 64.0D;
	private static final int FREQ = 1;

	private PathfinderMob charger;
	private LivingEntity chargeTarget;
	private double chargeX;
	private double chargeY;
	private double chargeZ;

	protected float speed;

	private final boolean canBreak;

	private int windup;

	private boolean hasAttacked;

	public ChargeAttackGoal(PathfinderMob entityLiving, float f, boolean canBreak) {
		this.charger = entityLiving;
		this.speed = f;
		this.canBreak = canBreak;
		this.windup = 0;
		this.hasAttacked = false;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		this.chargeTarget = this.charger.getTarget();

		if (this.chargeTarget == null) {
			return false;
		} else {
			double distance = this.charger.distanceToSqr(this.chargeTarget);
			if (distance < MIN_RANGE_SQ || distance > MAX_RANGE_SQ) {
				return false;
			} else if (!this.charger.isOnGround()) {
				return false;
			} else {
				Vec3 chargePos = findChargePoint(charger, chargeTarget, 2.1);
				boolean canSeeTargetFromDest = charger.getSensing().hasLineOfSight(chargeTarget);
				if (!canSeeTargetFromDest) {
					return false;
				} else {
					chargeX = chargePos.x;
					chargeY = chargePos.y;
					chargeZ = chargePos.z;

					return this.charger.getRandom().nextInt(FREQ) == 0;
				}
			}

		}
	}

	@Override
	public void start() {
		this.windup = 15 + this.charger.getRandom().nextInt(30);
		this.charger.setSprinting(true);
	}

	@Override
	public boolean canContinueToUse() {
		return windup > 0 || !this.charger.getNavigation().isDone();
	}

	@Override
	public void tick() {
		// look where we're going
		this.charger.getLookControl().setLookAt(chargeX, chargeY - 1, chargeZ, 10.0F, this.charger.getMaxHeadXRot());

		if (windup > 0) {
			if (--windup == 0) {
				// actually charge!

				this.charger.getNavigation().moveTo(chargeX, chargeY, chargeZ, this.speed);
			} else {
				this.charger.animationSpeed += 0.8;

				if (this.charger instanceof ITFCharger) {
					((ITFCharger) charger).setCharging(true);
				}
			}
		} else if (canBreak) {
			if (!charger.level.isClientSide && ForgeEventFactory.getMobGriefingEvent(charger.level, charger)) {

				AABB bb = charger.getBoundingBox();
				int minx = Mth.floor(bb.minX - 0.75D);
				int miny = Mth.floor(bb.minY + 0.0D);
				int minz = Mth.floor(bb.minZ - 0.75D);
				int maxx = Mth.floor(bb.maxX + 0.75D);
				int maxy = Mth.floor(bb.maxY + 0.15D);
				int maxz = Mth.floor(bb.maxZ + 0.75D);

				BlockPos min = new BlockPos(minx, miny, minz);
				BlockPos max = new BlockPos(maxx, maxy, maxz);

				if (charger.level.hasChunksAt(min, max)) {
					for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
						if (EntityUtil.canDestroyBlock(charger.level, pos, charger) && charger.level.getBlockEntity(pos) == null) {
							charger.level.destroyBlock(pos, true);
						}
					}
				}
			}
		}

		// attack the target when we get in range
		double rangeSq = this.charger.getBbWidth() * 2.1F * this.charger.getBbWidth() * 2.1F;

		if (this.charger.distanceToSqr(this.chargeTarget.getX(), this.chargeTarget.getBoundingBox().minY, this.chargeTarget.getZ()) <= rangeSq) {
			if (!this.hasAttacked) {
				this.hasAttacked = true;
				this.charger.doHurtTarget(this.chargeTarget);
			}
		}

	}

	@Override
	public void stop() {
		this.windup = 0;
		this.chargeTarget = null;
		this.hasAttacked = false;
		this.charger.setSprinting(false);

		if (this.charger instanceof ITFCharger) {
			((ITFCharger) charger).setCharging(false);
		}
	}


	/**
	 * Finds a point that is overshoot blocks "beyond" the target from our position.
	 */
	protected Vec3 findChargePoint(Entity attacker, Entity target, double overshoot) {

		// compute angle
		double vecx = target.getX() - attacker.getX();
		double vecz = target.getZ() - attacker.getZ();
		float rangle = (float) (Math.atan2(vecz, vecx));

		double distance = Mth.sqrt((float) (vecx * vecx + vecz * vecz));

		// figure out where we're headed from the target angle
		double dx = Mth.cos(rangle) * (distance + overshoot);
		double dz = Mth.sin(rangle) * (distance + overshoot);

		// add that to the target entity's position, and we have our destination
		return new Vec3(attacker.getX() + dx, target.getY(), attacker.getZ() + dz);
	}


}
