package twilightforest.entity.boss.finalboss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CircleArena implements BossPhaseInstance {
	private static final TargetingConditions NEW_TARGET_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight();
	private Path currentPath;
	private Vec3 targetLocation;
	private boolean clockwise;
	private int fireballCharge;
	protected final PlateauBoss dragon;
	private LivingEntity attackTarget;

	public CircleArena(PlateauBoss boss) {
		this.dragon = boss;
	}

	@Override
	public BossPhase<CircleArena> getPhase() {
		return BossPhase.CIRCLE_ARENA;
	}

	@Override
	public void doClientTick() {

	}

	@Override
	public void doServerTick() {

		if(attackTarget == null && this.dragon.tickCount % 40 == 0) {
			this.attackTarget = this.dragon.level.getNearestPlayer(TargetingConditions.forCombat().range(150.0D), this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
		}

		if (this.attackTarget != null && this.dragon.hasLineOfSight(this.attackTarget)) {
			++this.fireballCharge;
			Vec3 vec31 = (new Vec3(this.attackTarget.getX() - this.dragon.getX(), 0.0D, this.attackTarget.getZ() - this.dragon.getZ())).normalize();
			Vec3 vec3 = (new Vec3(Mth.sin(this.dragon.getYRot() * ((float)Math.PI / 180F)), 0.0D, -Mth.cos(this.dragon.getYRot() * ((float)Math.PI / 180F)))).normalize();
			float f1 = (float)vec3.dot(vec31);
			float f = (float)(Math.acos(f1) * (double)(180F / (float)Math.PI));
			f = f + 0.5F;
			if (this.fireballCharge >= 5 && f >= 0.0F && f < 10.0F) {
				Vec3 vec32 = this.dragon.getViewVector(1.0F);
				double d6 = this.dragon.head.getX() - vec32.x * 2;
				double d7 = this.dragon.head.getY(0.5D) + 0.5D;
				double d8 = this.dragon.head.getZ() - vec32.z * 2;
				double d9 = this.attackTarget.getX() - d6;
				double d10 = this.attackTarget.getY(0.5D) - d7;
				double d11 = this.attackTarget.getZ() - d8;
				if (!this.dragon.isSilent()) {
					this.dragon.level.levelEvent(null, 1017, this.dragon.blockPosition(), 0);
				}

				AbstractHurtingProjectile projectile;
				switch (this.dragon.getRandom().nextInt(5)) {

					default -> projectile = new TwilightFireball(this.dragon.level, this.dragon, d9, d10, d11, new MobEffectInstance(MobEffects.HARM, 100));
					case 2, 3 -> projectile = new TwilightFireball(this.dragon.level, this.dragon, d9, d10, d11, new MobEffectInstance(MobEffects.WITHER, 30));
					case 4 -> projectile = new LargeFireball(this.dragon.level, this.dragon, d9, d10, d11, 0);
				}
				projectile.moveTo(d6, d7, d8, 0.0F, 0.0F);
				this.dragon.level.addFreshEntity(projectile);
				this.fireballCharge = 0;
				if (this.currentPath != null) {
					while(!this.currentPath.isDone()) {
						this.currentPath.advance();
					}
				}
			}
		} else if (this.fireballCharge > 0) {
			--this.fireballCharge;
		}


	double d0 = this.targetLocation == null ? 0.0D : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
		if (d0 < 100.0D || d0 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
			this.findNewTarget();
		}

	}

	@Override
	public void begin() {
		this.currentPath = null;
		this.targetLocation = null;
		this.attackTarget = this.dragon.level.getNearestPlayer(TargetingConditions.forCombat().range(150.0D), this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
	}

	@Override
	public void end() {

	}

	@Nullable
	@Override
	public Vec3 getFlyTargetLocation() {
		return this.targetLocation;
	}

	private void findNewTarget() {
		if (this.currentPath != null && this.currentPath.isDone()) {
			BlockPos blockpos = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.dragon.getRestrictCenter());


			Player player = this.dragon.level.getNearestPlayer(NEW_TARGET_TARGETING, this.dragon, blockpos.getX(), blockpos.getY(), blockpos.getZ());

			if (player != null && this.dragon.getRandom().nextInt(300) == 0) {
				this.divebomb(player);
				return;
			}
		}

		if (this.currentPath == null || this.currentPath.isDone()) {
			int j = this.dragon.findClosestNode();
			int k = j;
			if (this.dragon.getRandom().nextInt(8) == 0) {
				this.clockwise = !this.clockwise;
				k = j + 6;
			}

			if (this.clockwise) {
				++k;
			} else {
				--k;
			}

			k = k - 12;
			k = k & 7;
			k = k + 12;

			this.currentPath = this.dragon.findPath(j, k, null);
			if (this.currentPath != null) {
				this.currentPath.advance();
			}
		}

		this.navigateToNextPathNode();
	}

	@Override
	public float getFlySpeed() {
		return 0.6F;
	}

	private void divebomb(Player pPlayer) {
		this.dragon.getPhaseManager().setPhase(BossPhase.DIVEBOMB);
		this.dragon.getPhaseManager().getPhase(BossPhase.DIVEBOMB).setTarget(pPlayer.position());
	}

	private void navigateToNextPathNode() {
		if (this.currentPath != null && !this.currentPath.isDone()) {
			Vec3i vec3i = this.currentPath.getNextNodePos();
			this.currentPath.advance();
			double d0 = vec3i.getX();
			double d1 = vec3i.getZ();

			double d2;
			do {
				d2 = (float) vec3i.getY() + this.dragon.getRandom().nextFloat() * 20.0F;
			} while (d2 < (double) vec3i.getY());

			this.targetLocation = new Vec3(d0, this.dragon.getRestrictCenter().getY() + 10 + this.dragon.getRandom().nextInt(20) * (this.dragon.getRandom().nextBoolean() ? -1 : 1), d1);
		}

	}
}
