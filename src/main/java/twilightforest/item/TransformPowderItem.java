package twilightforest.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.monster.Kobold;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

import javax.annotation.Nonnull;

public class TransformPowderItem extends Item {

	public TransformPowderItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		if (!target.isAlive() || target instanceof Kobold) return InteractionResult.PASS;

		Kobold kobold = TFEntities.KOBOLD.get().create(target.level);
		if (kobold == null) return InteractionResult.PASS;
		kobold.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
		if (target.getLevel() instanceof ServerLevelAccessor world) {
			kobold.finalizeSpawn(world, target.getLevel().getCurrentDifficultyAt(target.blockPosition()), MobSpawnType.CONVERSION, null, null);
		}
		target.getLevel().addFreshEntity(kobold);
		target.discard();
		stack.shrink(1);

		kobold.spawnAnim();
		kobold.spawnAnim();

		target.playSound(TFSounds.POWDER_USE.get(), 1.0F + target.getLevel().getRandom().nextFloat(), target.getLevel().getRandom().nextFloat() * 0.7F + 0.3F);

		return InteractionResult.SUCCESS;
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
		if (level.isClientSide()) {
			AABB area = this.getEffectAABB(player);

			// particle effect
			for (int i = 0; i < 30; i++) {
				level.addParticle(ParticleTypes.CRIT, area.minX + level.getRandom().nextFloat() * (area.maxX - area.minX),
						area.minY + level.getRandom().nextFloat() * (area.maxY - area.minY),
						area.minZ + level.getRandom().nextFloat() * (area.maxZ - area.minZ),
						0, 0, 0);
			}

		}

		return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
	}

	private AABB getEffectAABB(Player player) {
		double range = 2.0D;
		double radius = 1.0D;
		Vec3 srcVec = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
		Vec3 lookVec = player.getLookAngle();
		Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);

		return new AABB(destVec.x() - radius, destVec.y() - radius, destVec.z() - radius, destVec.x() + radius, destVec.y() + radius, destVec.z() + radius);
	}
}