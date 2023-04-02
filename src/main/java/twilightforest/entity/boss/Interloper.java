package twilightforest.entity.boss;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.ai.goal.KoboldFollowOwnerGoal;
import twilightforest.entity.monster.Kobold;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;

public class Interloper extends Kobold {
	public static final EntityDataAccessor<Integer> STAGE = SynchedEntityData.defineId(Interloper.class, EntityDataSerializers.INT);

	public Interloper(EntityType<? extends Interloper> type, Level world) {
		super(type, world);
		this.xpReward = 647;
	}

	public static void summonMultiple(Level level, int i, BlockPos pos) {
		if (level instanceof ServerLevel serverLevel) {
			for (int k = 0; k < i; k++) {
				TFEntities.INTERLOPER_BOSS.get().spawn(serverLevel, null, null, pos.above(), MobSpawnType.MOB_SUMMONED, false, false);
			}
		}
	}


	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 10.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.32D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(STAGE, 0);
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
		this.entityData.set(STAGE, pLevel.getRandom().nextInt(5));
		this.entityData.set(R, pLevel.getRandom().nextFloat());
		this.entityData.set(G, pLevel.getRandom().nextFloat());
		this.entityData.set(B, pLevel.getRandom().nextFloat());
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	@Override
	public boolean isBaby() {
		return true;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (this.level.isClientSide) {
			boolean flag = this.isOwnedBy(player) || this.isTame();

			if (this.entityData.get(STAGE) == 6) {
				TwilightForestMod.GAME_TITLE = "Close your eyes";
				Minecraft.getInstance().updateTitle();
				return InteractionResult.SUCCESS;
			}

			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else {
			if (!this.isTame()) {
				if (this.random.nextInt(3) == 0) {
					this.setOwnerUUID(player.getUUID());
					this.navigation.stop();
					this.setTarget(null);
					this.level.broadcastEntityEvent(this, (byte)7);
				} else {
					this.level.broadcastEntityEvent(this, (byte)6);
				}
				return InteractionResult.SUCCESS;
			}

			return super.mobInteract(player, hand);
		}
	}


	@Override
	protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
		if (pDamageSource.getEntity() instanceof LivingEntity living && living.getMainHandItem().is(TFItems.KOBOLD_GUMMY.get())) {
			super.actuallyHurt(pDamageSource, Float.MAX_VALUE);
		}
		if (this.random.nextInt(1000) == 1) {
			this.entityData.set(STAGE, 6);
		}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new KoboldFollowOwnerGoal(this, 2.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return false;
	}

	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public float getVoicePitch() {
		return this.entityData.get(STAGE) == 6 ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
	}

	@Override
	protected float getWaterSlowDown() {
		return 1.0F;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Stage", this.entityData.get(STAGE));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.entityData.set(STAGE, tag.getInt("Stage"));
	}

	public Stage getStage() {
		return switch (this.entityData.get(STAGE)) {
			case 0 -> Stage.KOBOLD;
			case 1 -> Stage.OPPOSITE;
			case 2 -> Stage.NO_COLOR;
			case 3 -> Stage.RGB;
			case 4 -> Stage.RGB2;
			default ->  Stage.FINAL;
		};
	}

	public enum Stage {
		KOBOLD("final_kobold.png"),
		RGB("final_kobold_no_color.png"),
		NO_COLOR("final_kobold_no_color.png"),
		RGB2("final_kobold_no_color.png"),
		OPPOSITE("final_kobold_opposite_color.png"),
		INTERLOPER("interloper.png"),
		FINAL("final.png");

		Stage(String textureName) {
			this.textureLocation = TwilightForestMod.getModelTexture(textureName);
		}

		private final ResourceLocation textureLocation;

		public ResourceLocation getTextureLocation() {
			return textureLocation;
		}
	}
}
