package twilightforest.capabilities.optifine;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import twilightforest.TwilightForestMod;

public interface IOptifinePlayer extends INBTSerializable<CompoundTag> {

	ResourceLocation ID = TwilightForestMod.prefix("cap_optifine");

	void setPlayer(Player player);

	boolean hasOptifine();

	void setHasOptifine(boolean hasIt);
}
