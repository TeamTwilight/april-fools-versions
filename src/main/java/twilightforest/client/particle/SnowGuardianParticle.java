package twilightforest.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnowGuardianParticle extends SnowParticle {

	SnowGuardianParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, float scale) {
		super(world, x, y, z, vx, vy, vz, scale);
		this.lifetime = 10 + this.random.nextInt(15);
		this.rCol = this.gCol = this.bCol = 0.75F + this.random.nextFloat() * 0.25F;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SnowGuardianParticle particle = new SnowGuardianParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 0.75F);
			particle.pickSprite(this.spriteSet);
			return particle;
		}
	}
}
