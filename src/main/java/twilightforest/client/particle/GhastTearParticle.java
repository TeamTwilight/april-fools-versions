package twilightforest.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.world.item.Item;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import twilightforest.init.TFSounds;

@OnlyIn(Dist.CLIENT)
public class GhastTearParticle extends TextureSheetParticle {

	public GhastTearParticle(ClientLevel level, double x, double y, double z, Item item) {
		super(level, x, y, z, 0.0D, 0.0D, 0.0D);
		this.sprite = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item).getParticleIcon(ModelData.EMPTY);
		this.rCol = this.gCol = this.bCol = 1.0F;
		this.quadSize = 2.0F;
		this.gravity = 0.6F;

		this.lifetime = 20 + random.nextInt(40);
		this.hasPhysics = true;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.TERRAIN_SHEET;
	}

	@Override
	public void tick() {
		if (this.onGround && this.random.nextBoolean()) {
			level.playSound(null, this.x, this.y + 1.0D, this.z, TFSounds.TEAR_BREAK.get(), SoundSource.HOSTILE, 0.5F, 1.0F);

			ItemStack itemID = new ItemStack(Items.GHAST_TEAR);
			for (int i = 0; i < 20; ++i) {
				double gaussX = this.random.nextGaussian() * 0.1D;
				double gaussY = this.random.nextGaussian() * 0.2D;
				double gaussZ = this.random.nextGaussian() * 0.1D;

				level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemID), this.x + this.random.nextFloat() - this.random.nextFloat(), this.y + 0.5F, this.z + this.random.nextFloat(), gaussX, gaussY, gaussZ);
				level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);

			}
			this.remove();
		}
		super.tick();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new GhastTearParticle(level, x, y, z, Items.GHAST_TEAR);
		}
	}
}
