package twilightforest.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import twilightforest.TwilightForestMod;

import java.util.function.Supplier;

public class EnforceProgressionStatusPacket {

	private final boolean enforce;

	public EnforceProgressionStatusPacket(FriendlyByteBuf buf) {
		this.enforce = buf.readBoolean();
	}

	public EnforceProgressionStatusPacket(boolean enforce) {
		this.enforce = enforce;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBoolean(enforce);
	}

	public static class Handler {

		public static boolean onMessage(EnforceProgressionStatusPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(new Runnable() {
				@Override
				public void run() {
					Minecraft.getInstance().level.getGameRules().getRule(TwilightForestMod.ENFORCED_PROGRESSION_RULE).set(message.enforce, null);
				}
			});
			return true;
		}
	}
}
