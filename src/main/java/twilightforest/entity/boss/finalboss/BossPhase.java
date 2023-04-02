package twilightforest.entity.boss.finalboss;

import java.lang.reflect.Constructor;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class BossPhase<T extends BossPhaseInstance> {
	private static BossPhase<?>[] phases = new BossPhase[0];
	public static final BossPhase<CircleArena> CIRCLE_ARENA = create(CircleArena.class, "CircleArena");
	public static final BossPhase<DiveBomb> DIVEBOMB = create(DiveBomb.class, "DiveBomb");
	private final Class<? extends BossPhaseInstance> instanceClass;
	private final int id;
	private final String name;

	private BossPhase(int p_31394_, Class<? extends BossPhaseInstance> p_31395_, String p_31396_) {
		this.id = p_31394_;
		this.instanceClass = p_31395_;
		this.name = p_31396_;
	}

	public BossPhaseInstance createInstance(PlateauBoss pDragon) {
		try {
			Constructor<? extends BossPhaseInstance> constructor = this.getConstructor();
			return constructor.newInstance(pDragon);
		} catch (Exception exception) {
			throw new Error(exception);
		}
	}

	protected Constructor<? extends BossPhaseInstance> getConstructor() throws NoSuchMethodException {
		return this.instanceClass.getConstructor(PlateauBoss.class);
	}

	public int getId() {
		return this.id;
	}

	public String toString() {
		return this.name + " (#" + this.id + ")";
	}

	/**
	 * Gets a phase by its ID. If the phase is out of bounds (negative or beyond the end of the phase array), returns
	 * {@link #CIRCLE_ARENA}.
	 */
	public static BossPhase<?> getById(int pId) {
		return pId >= 0 && pId < phases.length ? phases[pId] : CIRCLE_ARENA;
	}

	public static int getCount() {
		return phases.length;
	}

	private static <T extends BossPhaseInstance> BossPhase<T> create(Class<T> pPhase, String pName) {
		BossPhase<T> phase = new BossPhase<>(phases.length, pPhase, pName);
		phases = Arrays.copyOf(phases, phases.length + 1);
		phases[phase.getId()] = phase;
		return phase;
	}
}