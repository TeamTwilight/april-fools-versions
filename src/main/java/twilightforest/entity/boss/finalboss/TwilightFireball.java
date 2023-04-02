package twilightforest.entity.boss.finalboss;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class TwilightFireball extends AbstractHurtingProjectile {

	private MobEffectInstance effect;

	public TwilightFireball(EntityType<? extends DragonFireball> p_36892_, Level p_36893_) {
		super(p_36892_, p_36893_);
	}

	public TwilightFireball(Level p_36903_, LivingEntity p_36904_, double p_36905_, double p_36906_, double p_36907_, MobEffectInstance effect) {
		super(EntityType.DRAGON_FIREBALL, p_36904_, p_36905_, p_36906_, p_36907_, p_36903_);
		this.effect = effect;
	}

	@Override
	protected void onHitBlock(BlockHitResult p_37258_) {
		super.onHitBlock(p_37258_);
		if (!this.level.isClientSide) {
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
			AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
			Entity entity = this.getOwner();
			if (entity instanceof LivingEntity) {
				areaeffectcloud.setOwner((LivingEntity) entity);
			}

			areaeffectcloud.setParticle(ParticleTypes.GLOW_SQUID_INK);
			areaeffectcloud.setRadius(3.0F);
			areaeffectcloud.setDuration(600);
			areaeffectcloud.setRadiusPerTick((7.0F - areaeffectcloud.getRadius()) / (float) areaeffectcloud.getDuration());
			areaeffectcloud.addEffect(effect);
			if (!list.isEmpty()) {
				for (LivingEntity livingentity : list) {
					double d0 = this.distanceToSqr(livingentity);
					if (d0 < 16.0D) {
						areaeffectcloud.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
						break;
					}
				}
			}

			this.level.levelEvent(2006, this.blockPosition(), this.isSilent() ? -1 : 1);
			this.level.addFreshEntity(areaeffectcloud);
			this.discard();
		}
	}

	public boolean isPickable() {
		return false;
	}

	public boolean hurt(DamageSource pSource, float pAmount) {
		return false;
	}

	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.DRAGON_BREATH;
	}

	protected boolean shouldBurn() {
		return false;
	}
}