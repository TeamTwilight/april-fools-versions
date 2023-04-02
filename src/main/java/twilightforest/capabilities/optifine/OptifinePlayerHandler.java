package twilightforest.capabilities.optifine;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import twilightforest.network.TFPacketHandler;
import twilightforest.network.ThisIdiotHasOptifinePacket;

public class OptifinePlayerHandler implements IOptifinePlayer {

	private LivingEntity dumbass;
	private boolean hasOptifine;

	@Override
	public void setPlayer(Player player) {
		this.dumbass = player;
	}

	@Override
	public boolean hasOptifine() {
		return this.hasOptifine;
	}

	@Override
	public void setHasOptifine(boolean hasIt) {
		this.hasOptifine = hasIt;
		this.sendUpdate();
	}

	private void sendUpdate() {
		if (dumbass instanceof LocalPlayer)
			TFPacketHandler.CHANNEL.sendToServer(new ThisIdiotHasOptifinePacket(dumbass, this));
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("hasOptifine", this.hasOptifine());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.setHasOptifine(tag.getBoolean("hasOptifine"));
	}
}
