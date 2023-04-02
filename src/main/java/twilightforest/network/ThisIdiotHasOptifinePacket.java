package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.optifine.IOptifinePlayer;

import java.util.function.Supplier;

public class ThisIdiotHasOptifinePacket {

	private final int entityID;
	private final boolean hasTheSoCalledPerformanceMod;

	public ThisIdiotHasOptifinePacket(int id, IOptifinePlayer cap) {
		this.entityID = id;
		this.hasTheSoCalledPerformanceMod = cap.hasOptifine();
	}

	public ThisIdiotHasOptifinePacket(Entity entity, IOptifinePlayer cap) {
		this(entity.getId(), cap);
	}

	public ThisIdiotHasOptifinePacket(FriendlyByteBuf buf) {
		this.entityID = buf.readInt();
		this.hasTheSoCalledPerformanceMod = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeBoolean(this.hasTheSoCalledPerformanceMod);
	}

	public static class Handler {

		public static boolean onMessage(ThisIdiotHasOptifinePacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				ServerPlayer player = ctx.get().getSender();
				if(player != null) {
					player.getCapability(CapabilityList.OPTITRASH).ifPresent(cap ->
							cap.setHasOptifine(message.hasTheSoCalledPerformanceMod));
				}
			});

			return true;
		}
	}
}
