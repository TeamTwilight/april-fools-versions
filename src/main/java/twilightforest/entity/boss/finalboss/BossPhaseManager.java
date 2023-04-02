package twilightforest.entity.boss.finalboss;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.TwilightForestMod;

public class BossPhaseManager {
	private final PlateauBoss dragon;
	private final BossPhaseInstance[] phases = new BossPhaseInstance[EnderDragonPhase.getCount()];
	private BossPhaseInstance currentPhase;

	public BossPhaseManager(PlateauBoss boss) {
		this.dragon = boss;
		this.setPhase(BossPhase.CIRCLE_ARENA);
	}

	public void setPhase(BossPhase<?> pPhase) {
		if (this.currentPhase == null || pPhase != this.currentPhase.getPhase()) {
			if (this.currentPhase != null) {
				this.currentPhase.end();
			}

			this.currentPhase = this.getPhase(pPhase);
			if (!this.dragon.level.isClientSide) {
				this.dragon.getEntityData().set(EnderDragon.DATA_PHASE, pPhase.getId());
			}

			TwilightForestMod.LOGGER.debug("Dragon is now in phase {} on the {}", pPhase, this.dragon.level.isClientSide ? "client" : "server");
			this.currentPhase.begin();
		}
	}

	public BossPhaseInstance getCurrentPhase() {
		return this.currentPhase;
	}

	@SuppressWarnings("unchecked")
	public <T extends BossPhaseInstance> T getPhase(BossPhase<T> pPhase) {
		int i = pPhase.getId();
		if (this.phases[i] == null) {
			this.phases[i] = pPhase.createInstance(this.dragon);
		}

		return (T)this.phases[i];
	}
}
