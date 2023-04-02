package twilightforest.entity.boss.finalboss;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BinaryHeap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.apache.logging.log4j.core.jmx.Server;
import twilightforest.advancements.TFAdvancements;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.registration.TFGenerationSettings;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PlateauBoss extends PathfinderMob implements Enemy {

	private final ServerBossEvent bossInfo = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);
	private final List<ServerPlayer> hurtBy = new ArrayList<>();

	public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(PlateauBoss.class, EntityDataSerializers.INT);

	private final PlateauBossPart[] subEntities;
	public final PlateauBossPart head;
	private final PlateauBossPart neck;
	private final PlateauBossPart body;
	private final PlateauBossPart tail1;
	private final PlateauBossPart tail2;
	private final PlateauBossPart tail3;
	private final PlateauBossPart wing1;
	private final PlateauBossPart wing2;

	public final double[][] positions = new double[64][3];
	public int posPointer = -1;
	public float oFlapTime;
	public float flapTime;
	private int growlTime = 100;
	public boolean inWall;
	public float yRotA;
	public int dragonDeathTime;

	private final BossPhaseManager phaseManager;

	private final Node[] nodes = new Node[24];
	private final int[] nodeAdjacency = new int[24];
	private final BinaryHeap openSet = new BinaryHeap();

	public PlateauBoss(EntityType<? extends PlateauBoss> type, Level world) {
		super(TFEntities.PLATEAU_BOSS, world);
		this.xpReward = 647;

		this.head = new PlateauBossPart(this, "head", 1.0F, 1.0F);
		this.neck = new PlateauBossPart(this, "neck", 3.0F, 3.0F);
		this.body = new PlateauBossPart(this, "body", 5.0F, 3.0F);
		this.tail1 = new PlateauBossPart(this, "tail", 2.0F, 2.0F);
		this.tail2 = new PlateauBossPart(this, "tail", 2.0F, 2.0F);
		this.tail3 = new PlateauBossPart(this, "tail", 2.0F, 2.0F);
		this.wing1 = new PlateauBossPart(this, "wing", 4.0F, 2.0F);
		this.wing2 = new PlateauBossPart(this, "wing", 4.0F, 2.0F);
		this.subEntities = new PlateauBossPart[]{this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.wing1, this.wing2};
		this.setHealth(this.getMaxHealth());
		this.noPhysics = true;
		this.noCulling = true;

		this.phaseManager = new BossPhaseManager(this);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 1024.0D)
				.add(Attributes.ATTACK_DAMAGE, 35.0D)
				.add(Attributes.FOLLOW_RANGE, 400.0D);
	}

	@Override
	public boolean isFlapping() {
		float f = Mth.cos(this.flapTime * ((float) Math.PI * 2F));
		float f1 = Mth.cos(this.oFlapTime * ((float) Math.PI * 2F));
		return f1 <= -0.3F && f >= -0.3F;
	}

	@Override
	public void onFlap() {
		if (this.level.isClientSide && !this.isSilent()) {
			this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_FLAP, this.getSoundSource(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
		}

	}

	public double[] getLatencyPos(int p_31102_, float p_31103_) {
		if (this.isDeadOrDying()) {
			p_31103_ = 0.0F;
		}

		p_31103_ = 1.0F - p_31103_;
		int i = this.posPointer - p_31102_ & 63;
		int j = this.posPointer - p_31102_ - 1 & 63;
		double[] adouble = new double[3];
		double d0 = this.positions[i][0];
		double d1 = Mth.wrapDegrees(this.positions[j][0] - d0);
		adouble[0] = d0 + d1 * (double) p_31103_;
		d0 = this.positions[i][1];
		d1 = this.positions[j][1] - d0;
		adouble[1] = d0 + d1 * (double) p_31103_;
		adouble[2] = Mth.lerp(p_31103_, this.positions[i][2], this.positions[j][2]);
		return adouble;
	}

	@Override
	public void aiStep() {
		this.processFlappingMovement();
		if (this.level.isClientSide) {
			this.setHealth(this.getHealth());
			if (!this.isSilent() && --this.growlTime < 0) {
				this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_GROWL, this.getSoundSource(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
				this.growlTime = 200 + this.random.nextInt(200);
			}
		}

		this.oFlapTime = this.flapTime;
		if (this.isDeadOrDying()) {
			float f8 = (this.random.nextFloat() - 0.5F) * 8.0F;
			float f9 = (this.random.nextFloat() - 0.5F) * 4.0F;
			float f10 = (this.random.nextFloat() - 0.5F) * 8.0F;
			this.level.addParticle(ParticleTypes.EXPLOSION, this.getX() + (double)f8, this.getY() + 2.0D + (double)f9, this.getZ() + (double)f10, 0.0D, 0.0D, 0.0D);
		} else {
			Vec3 vec3 = this.getDeltaMovement();
			float f = 0.2F / ((float)vec3.horizontalDistance() * 10.0F + 1.0F);
			f = f * (float)Math.pow(2.0D, vec3.y);
			if (this.inWall) {
				this.flapTime += f * 0.5F;
			} else {
				this.flapTime += f;
			}

			this.setYRot(Mth.wrapDegrees(this.getYRot()));
			if (this.isNoAi()) {
				this.flapTime = 0.5F;
			} else {
				if (this.posPointer < 0) {
					for(int i = 0; i < this.positions.length; ++i) {
						this.positions[i][0] = this.getYRot();
						this.positions[i][1] = this.getY();
					}
				}

				if (++this.posPointer == this.positions.length) {
					this.posPointer = 0;
				}

				this.positions[this.posPointer][0] = this.getYRot();
				this.positions[this.posPointer][1] = this.getY();
				if (this.level.isClientSide) {
					if (this.lerpSteps > 0) {
						double d7 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
						double d0 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
						double d1 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
						double d2 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
						this.setYRot(this.getYRot() + (float)d2 / (float)this.lerpSteps);
						this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
						--this.lerpSteps;
						this.setPos(d7, d0, d1);
						this.setRot(this.getYRot(), this.getXRot());
					}

					this.phaseManager.getCurrentPhase().doClientTick();
				} else {
					BossPhaseInstance dragonphaseinstance = this.phaseManager.getCurrentPhase();
					dragonphaseinstance.doServerTick();
					if (this.phaseManager.getCurrentPhase() != dragonphaseinstance) {
						dragonphaseinstance = this.phaseManager.getCurrentPhase();
						dragonphaseinstance.doServerTick();
					}

					Vec3 vec31 = dragonphaseinstance.getFlyTargetLocation();
					if (vec31 != null) {
						double d8 = vec31.x - this.getX();
						double d9 = vec31.y - this.getY();
						double d10 = vec31.z - this.getZ();
						double d3 = d8 * d8 + d9 * d9 + d10 * d10;
						float f5 = dragonphaseinstance.getFlySpeed();
						double d4 = Math.sqrt(d8 * d8 + d10 * d10);
						if (d4 > 0.0D) {
							d9 = Mth.clamp(d9 / d4, -f5, f5);
						}

						this.setDeltaMovement(this.getDeltaMovement().add(0.0D, d9 * 0.01D, 0.0D));
						this.setYRot(Mth.wrapDegrees(this.getYRot()));
						Vec3 vec32 = vec31.subtract(this.getX(), this.getY(), this.getZ()).normalize();
						Vec3 vec33 = (new Vec3(Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), this.getDeltaMovement().y, -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)))).normalize();
						float f6 = Math.max(((float)vec33.dot(vec32) + 0.5F) / 1.5F, 0.0F);
						if (Math.abs(d8) > (double)1.0E-5F || Math.abs(d10) > (double)1.0E-5F) {
							double d5 = Mth.clamp(Mth.wrapDegrees(180.0D - Mth.atan2(d8, d10) * (double)(180F / (float)Math.PI) - (double)this.getYRot()), -50.0D, 50.0D);
							this.yRotA *= 0.8F;
							this.yRotA = (float)((double)this.yRotA + d5 * (double)this.getTurnSpeed());
							this.setYRot(this.getYRot() + this.yRotA * 0.1F);
						}

						float f18 = (float)(2.0D / (d3 + 1.0D));
						this.moveRelative(0.06F * (f6 * f18 + (1.0F - f18)), new Vec3(0.0D, 0.0D, -1.0D));
						if (this.inWall) {
							this.move(MoverType.SELF, this.getDeltaMovement().scale(0.8F));
						} else {
							this.move(MoverType.SELF, this.getDeltaMovement());
						}

						Vec3 vec34 = this.getDeltaMovement().normalize();
						double d6 = 0.8D + 0.15D * (vec34.dot(vec33) + 1.0D) / 2.0D;
						this.setDeltaMovement(this.getDeltaMovement().multiply(d6, 0.91F, d6));
					}
				}

				this.yBodyRot = this.getYRot();
				Vec3[] avec3 = new Vec3[this.subEntities.length];

				for(int j = 0; j < this.subEntities.length; ++j) {
					avec3[j] = new Vec3(this.subEntities[j].getX(), this.subEntities[j].getY(), this.subEntities[j].getZ());
				}

				float f11 = (float)(this.getLatencyPos(5, 1.0F)[1] - this.getLatencyPos(10, 1.0F)[1]) * 10.0F * ((float)Math.PI / 180F);
				float f12 = Mth.cos(f11);
				float f1 = Mth.sin(f11);
				float f13 = this.getYRot() * ((float)Math.PI / 180F);
				float f2 = Mth.sin(f13);
				float f14 = Mth.cos(f13);
				this.tickPart(this.body, f2 * 0.5F, 0.0D, -f14 * 0.5F);
				this.tickPart(this.wing1, f14 * 4.5F, 2.0D, f2 * 4.5F);
				this.tickPart(this.wing2, f14 * -4.5F, 2.0D, f2 * -4.5F);
				if (!this.level.isClientSide && this.hurtTime == 0) {
					this.knockBack(this.level.getEntities(this, this.wing1.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
					this.knockBack(this.level.getEntities(this, this.wing2.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
					this.hurt(this.level.getEntities(this, this.head.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
					this.hurt(this.level.getEntities(this, this.neck.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
				}

				float f3 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F) - this.yRotA * 0.01F);
				float f15 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F) - this.yRotA * 0.01F);
				float f4 = this.getHeadYOffset();
				this.tickPart(this.head, f3 * 6.5F * f12, f4 + f1 * 6.5F, -f15 * 6.5F * f12);
				this.tickPart(this.neck, f3 * 5.5F * f12, f4 + f1 * 5.5F, -f15 * 5.5F * f12);
				double[] adouble = this.getLatencyPos(5, 1.0F);

				for(int k = 0; k < 3; ++k) {
					PlateauBossPart part = null;
					if (k == 0) {
						part = this.tail1;
					}

					if (k == 1) {
						part = this.tail2;
					}

					if (k == 2) {
						part = this.tail3;
					}

					double[] adouble1 = this.getLatencyPos(12 + k * 2, 1.0F);
					float f16 = this.getYRot() * ((float)Math.PI / 180F) + this.rotWrap(adouble1[0] - adouble[0]) * ((float)Math.PI / 180F);
					float f17 = Mth.sin(f16);
					float f19 = Mth.cos(f16);
					float f21 = (float)(k + 1) * 2.0F;
					this.tickPart(part, -(f2 * 1.5F + f17 * f21) * f12, adouble1[1] - adouble[1] - (double)((f21 + 1.5F) * f1) + 1.5D, (f14 * 1.5F + f19 * f21) * f12);
				}

				if (!this.level.isClientSide) {
					this.inWall = this.checkWalls(this.head.getBoundingBox()) | this.checkWalls(this.neck.getBoundingBox()) | this.checkWalls(this.body.getBoundingBox());
					bossInfo.setProgress(getHealth() / getMaxHealth());

					if (!this.hasRestriction()) {
						this.restrictTo(this.blockPosition(), 75);
					}
				}

				for(int l = 0; l < this.subEntities.length; ++l) {
					this.subEntities[l].xo = avec3[l].x;
					this.subEntities[l].yo = avec3[l].y;
					this.subEntities[l].zo = avec3[l].z;
					this.subEntities[l].xOld = avec3[l].x;
					this.subEntities[l].yOld = avec3[l].y;
					this.subEntities[l].zOld = avec3[l].z;
				}

			}
		}
	}

	private float rotWrap(double pAngle) {
		return (float)Mth.wrapDegrees(pAngle);
	}

	public float getTurnSpeed() {
		float f = (float) this.getDeltaMovement().horizontalDistance() + 1.0F;
		float f1 = Math.min(f, 40.0F);
		return 0.7F / f1 / f;
	}

	private float getHeadYOffset() {
		double[] adouble = this.getLatencyPos(5, 1.0F);
		double[] adouble1 = this.getLatencyPos(0, 1.0F);
		return (float) (adouble[1] - adouble1[1]);
	}

	public float getHeadPartYOffset(int p_31109_) {
		double d0;
		BlockPos blockpos = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.getRestrictCenter());
		double d1 = Math.max(Math.sqrt(blockpos.distSqr(this.position(), true)) / 4.0D, 1.0D);
		d0 = (double) p_31109_ / d1;

		return (float) d0;
	}

	private boolean checkWalls(AABB pArea) {
		int i = Mth.floor(pArea.minX);
		int j = Mth.floor(pArea.minY);
		int k = Mth.floor(pArea.minZ);
		int l = Mth.floor(pArea.maxX);
		int i1 = Mth.floor(pArea.maxY);
		int j1 = Mth.floor(pArea.maxZ);
		boolean flag = false;
		boolean flag1 = false;

		for (int k1 = i; k1 <= l; ++k1) {
			for (int l1 = j; l1 <= i1; ++l1) {
				for (int i2 = k; i2 <= j1; ++i2) {
					BlockPos blockpos = new BlockPos(k1, l1, i2);
					BlockState blockstate = this.level.getBlockState(blockpos);
					if (!blockstate.isAir() && blockstate.getMaterial() != Material.FIRE) {
						if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.level, blockpos, this) && !BlockTags.DRAGON_IMMUNE.contains(blockstate.getBlock()) &&
								//this is fucking stupid but I dont give the slightest fuck
								!blockstate.getBlock().getRegistryName().getPath().contains("castle_")) {
							flag1 = this.level.removeBlock(blockpos, false) || flag1;
						} else {
							flag = true;
						}
					}
				}
			}
		}

		if (flag1) {
			BlockPos blockpos1 = new BlockPos(i + this.random.nextInt(l - i + 1), j + this.random.nextInt(i1 - j + 1), k + this.random.nextInt(j1 - k + 1));
			this.level.levelEvent(2008, blockpos1, 0);
		}

		return flag;
	}

	private void tickPart(PlateauBossPart pPart, double pOffsetX, double pOffsetY, double pOffsetZ) {
		pPart.setPos(this.getX() + pOffsetX, this.getY() + pOffsetY, this.getZ() + pOffsetZ);
	}

	private void knockBack(List<Entity> pEntities) {
		double d0 = (this.body.getBoundingBox().minX + this.body.getBoundingBox().maxX) / 2.0D;
		double d1 = (this.body.getBoundingBox().minZ + this.body.getBoundingBox().maxZ) / 2.0D;

		for (Entity entity : pEntities) {
			if (entity instanceof LivingEntity living) {
				double d2 = living.getX() - d0;
				double d3 = living.getZ() - d1;
				double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
				living.push(d2 / d4 * 4.0D, 0.2F, d3 / d4 * 4.0D);
				if (living.getLastHurtByMobTimestamp() < living.tickCount - 2) {
					living.hurt(DamageSource.mobAttack(this), 5.0F);
					this.doEnchantDamageEffects(this, living);
				}
			}
		}

	}

	private void hurt(List<Entity> pEntities) {
		for (Entity entity : pEntities) {
			if (entity instanceof LivingEntity) {
				if (entity.hurt(DamageSource.mobAttack(this), 10.0F)) {
					if (this.phaseManager.getCurrentPhase().getPhase() == BossPhase.DIVEBOMB) {
						entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, 10.0D, 0.0D));
					}
				}
				this.doEnchantDamageEffects(this, entity);
			}
		}

	}

	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return false;
	}

	@Override
	public void checkDespawn() {
		if (level.getDifficulty() == Difficulty.PEACEFUL) {
			if (!hasRestriction()) {
				level.setBlockAndUpdate(getRestrictCenter(), TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get().defaultBlockState());
			}
			discard();
		} else {
			super.checkDespawn();
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() instanceof ServerPlayer player && !hurtBy.contains(player)) {
			hurtBy.add(player);
		}

		amount = Math.min(amount, 5.0F);

		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource cause) {
		super.die(cause);
		if (!level.isClientSide) {
			TFGenerationSettings.markStructureConquered(level, new BlockPos(this.blockPosition()), TFFeature.FINAL_CASTLE);
			for (ServerPlayer player : hurtBy) {
				TFAdvancements.HURT_BOSS.trigger(player, this);
			}
		}
	}

	@Override
	public void kill() {
		this.remove(Entity.RemovalReason.KILLED);
	}

	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		BlockPos home = this.getRestrictCenter();
		compound.put("Home", newDoubleList(home.getX(), home.getY(), home.getZ()));
		compound.putBoolean("HasHome", this.hasRestriction());
		compound.putInt("DragonPhase", this.phaseManager.getCurrentPhase().getPhase().getId());
		super.addAdditionalSaveData(compound);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		if (compound.contains("DragonPhase")) {
			this.phaseManager.setPhase(BossPhase.getById(compound.getInt("DragonPhase")));
		}

		if (compound.contains("Home", 9)) {
			ListTag nbttaglist = compound.getList("Home", 6);
			int hx = (int) nbttaglist.getDouble(0);
			int hy = (int) nbttaglist.getDouble(1);
			int hz = (int) nbttaglist.getDouble(2);
			this.restrictTo(new BlockPos(hx, hy, hz), 30);
		}
		if (!compound.getBoolean("HasHome")) {
			this.hasRestriction();
		}
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_PHASE, EnderDragonPhase.HOVERING.getId());
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		if (DATA_PHASE.equals(pKey) && this.level.isClientSide) {
			this.phaseManager.setPhase(BossPhase.getById(this.getEntityData().get(DATA_PHASE)));
		}

		super.onSyncedDataUpdated(pKey);
	}

	@Override
	protected boolean canRide(Entity entityIn) {
		return false;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	public BossPhaseManager getPhaseManager() {
		return this.phaseManager;
	}

	@Override
	public boolean addEffect(MobEffectInstance p_182394_, @Nullable Entity p_182395_) {
		return false;
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}

	@Nullable
	@Override
	public PartEntity<?>[] getParts() {
		return this.subEntities;
	}

	public boolean isPickable() {
		return false;
	}

	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENDER_DRAGON_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.ENDER_DRAGON_HURT;
	}

	protected float getSoundVolume() {
		return 5.0F;
	}

	public boolean canAttack(LivingEntity pTarget) {
		return pTarget.canBeSeenAsEnemy();
	}

	@Override
	protected void tickDeath() {

		++this.dragonDeathTime;
		if (this.dragonDeathTime >= 180 && this.dragonDeathTime <= 200) {
			float f = (this.random.nextFloat() - 0.5F) * 8.0F;
			float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
			float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
			this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double) f, this.getY() + 2.0D + (double) f1, this.getZ() + (double) f2, 0.0D, 0.0D, 0.0D);
		}

		boolean flag = this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
		int i = 500;

		if (this.level instanceof ServerLevel) {
			if (this.dragonDeathTime > 150 && this.dragonDeathTime % 5 == 0 && flag) {
				ExperienceOrb.award((ServerLevel) this.level, this.position(), Mth.floor((float) i * 0.08F));
			}

			if (this.dragonDeathTime == 1 && !this.isSilent()) {
				this.level.globalLevelEvent(1028, this.blockPosition(), 0);
			}
		}

		this.move(MoverType.SELF, new Vec3(0.0D, 0.1F, 0.0D));
		this.setYRot(this.getYRot() + 20.0F);
		this.yBodyRot = this.getYRot();
		if (this.dragonDeathTime == 200 && this.level instanceof ServerLevel) {
			if (flag) {
				ExperienceOrb.award((ServerLevel) this.level, this.position(), Mth.floor((float) i * 0.2F));
			}

			this.remove(Entity.RemovalReason.KILLED);
			TFTreasure.PLATEAU_BOSS.generateChest((ServerLevel)level, this.getRestrictCenter(), Direction.NORTH, false);
		}

	}

	public int findClosestNode() {
		if (this.nodes[0] == null) {
			for (int i = 0; i < 24; ++i) {
				int l;
				int i1;
				if (i < 12) {
					l = Mth.floor(60.0F * Mth.cos(2.0F * (-(float) Math.PI + 0.2617994F * (float) i)));
					i1 = Mth.floor(60.0F * Mth.sin(2.0F * (-(float) Math.PI + 0.2617994F * (float) i)));
				} else if (i < 20) {
					int k = i - 12;
					l = Mth.floor(40.0F * Mth.cos(2.0F * (-(float) Math.PI + ((float) Math.PI / 8F) * (float) k)));
					i1 = Mth.floor(40.0F * Mth.sin(2.0F * (-(float) Math.PI + ((float) Math.PI / 8F) * (float) k)));
				} else {
					int k1 = i - 20;
					l = Mth.floor(20.0F * Mth.cos(2.0F * (-(float) Math.PI + ((float) Math.PI / 4F) * (float) k1)));
					i1 = Mth.floor(20.0F * Mth.sin(2.0F * (-(float) Math.PI + ((float) Math.PI / 4F) * (float) k1)));
				}

				l += this.getRestrictCenter().getX();
				i1 += this.getRestrictCenter().getZ();
				int j1 = Math.max(this.level.getSeaLevel() + 10, this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(l, 170, i1)).getY());
				this.nodes[i] = new Node(l, j1, i1);
			}

			this.nodeAdjacency[0] = 6146;
			this.nodeAdjacency[1] = 8197;
			this.nodeAdjacency[2] = 8202;
			this.nodeAdjacency[3] = 16404;
			this.nodeAdjacency[4] = 32808;
			this.nodeAdjacency[5] = 32848;
			this.nodeAdjacency[6] = 65696;
			this.nodeAdjacency[7] = 131392;
			this.nodeAdjacency[8] = 131712;
			this.nodeAdjacency[9] = 263424;
			this.nodeAdjacency[10] = 526848;
			this.nodeAdjacency[11] = 525313;
			this.nodeAdjacency[12] = 1581057;
			this.nodeAdjacency[13] = 3166214;
			this.nodeAdjacency[14] = 2138120;
			this.nodeAdjacency[15] = 6373424;
			this.nodeAdjacency[16] = 4358208;
			this.nodeAdjacency[17] = 12910976;
			this.nodeAdjacency[18] = 9044480;
			this.nodeAdjacency[19] = 9706496;
			this.nodeAdjacency[20] = 15216640;
			this.nodeAdjacency[21] = 13688832;
			this.nodeAdjacency[22] = 11763712;
			this.nodeAdjacency[23] = 8257536;
		}

		return this.findClosestNode(this.getX(), this.getY(), this.getZ());
	}

	public int findClosestNode(double pX, double pY, double pZ) {
		float f = 10000.0F;
		int i = 0;
		Node node = new Node(Mth.floor(pX), Mth.floor(pY), Mth.floor(pZ));

		for (int k = 12; k < 24; ++k) {
			if (this.nodes[k] != null) {
				float f1 = this.nodes[k].distanceToSqr(node);
				if (f1 < f) {
					f = f1;
					i = k;
				}
			}
		}

		return i;
	}

	@Nullable
	public Path findPath(int pStartIdx, int pFinishIdx, @Nullable Node pAndThen) {
		for (int i = 0; i < 24; ++i) {
			Node node = this.nodes[i];
			node.closed = false;
			node.f = 0.0F;
			node.g = 0.0F;
			node.h = 0.0F;
			node.cameFrom = null;
			node.heapIdx = -1;
		}

		Node node4 = this.nodes[pStartIdx];
		Node node5 = this.nodes[pFinishIdx];
		node4.g = 0.0F;
		node4.h = node4.distanceTo(node5);
		node4.f = node4.h;
		this.openSet.clear();
		this.openSet.insert(node4);
		Node node1 = node4;

		while (!this.openSet.isEmpty()) {
			Node node2 = this.openSet.pop();
			if (node2.equals(node5)) {
				if (pAndThen != null) {
					pAndThen.cameFrom = node5;
					node5 = pAndThen;
				}

				return this.reconstructPath(node5);
			}

			if (node2.distanceTo(node5) < node1.distanceTo(node5)) {
				node1 = node2;
			}

			node2.closed = true;
			int k = 0;

			for (int l = 0; l < 24; ++l) {
				if (this.nodes[l] == node2) {
					k = l;
					break;
				}
			}

			for (int i1 = 12; i1 < 24; ++i1) {
				if ((this.nodeAdjacency[k] & 1 << i1) > 0) {
					Node node3 = this.nodes[i1];
					if (!node3.closed) {
						float f = node2.g + node2.distanceTo(node3);
						if (!node3.inOpenSet() || f < node3.g) {
							node3.cameFrom = node2;
							node3.g = f;
							node3.h = node3.distanceTo(node5);
							if (node3.inOpenSet()) {
								this.openSet.changeCost(node3, node3.g + node3.h);
							} else {
								node3.f = node3.g + node3.h;
								this.openSet.insert(node3);
							}
						}
					}
				}
			}
		}

		if (node1 == node4) {
			return null;
		} else {
			LOGGER.debug("Failed to find path from {} to {}", pStartIdx, pFinishIdx);
			if (pAndThen != null) {
				pAndThen.cameFrom = node1;
				node1 = pAndThen;
			}

			return this.reconstructPath(node1);
		}
	}

	private Path reconstructPath(Node pFinish) {
		List<Node> list = Lists.newArrayList();
		Node node = pFinish;
		list.add(0, pFinish);

		while (node.cameFrom != null) {
			node = node.cameFrom;
			list.add(0, node);
		}

		return new Path(list, new BlockPos(pFinish.x, pFinish.y, pFinish.z), true);
	}
}
