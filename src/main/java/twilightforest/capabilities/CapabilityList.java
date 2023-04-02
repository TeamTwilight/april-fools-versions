package twilightforest.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import twilightforest.capabilities.optifine.IOptifinePlayer;
import twilightforest.capabilities.optifine.OptifinePlayerHandler;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.capabilities.shield.ShieldCapabilityHandler;

import javax.annotation.Nonnull;

public class CapabilityList {

	public static final Capability<IShieldCapability> SHIELDS = CapabilityManager.get(new CapabilityToken<>(){});
	public static final Capability<IOptifinePlayer> OPTITRASH = CapabilityManager.get(new CapabilityToken<>(){});

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(IShieldCapability.class);
		event.register(IOptifinePlayer.class);
	}

	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof LivingEntity) {
			e.addCapability(IShieldCapability.ID, new ICapabilitySerializable<CompoundTag>() {

				LazyOptional<IShieldCapability> inst = LazyOptional.of(() -> {
					ShieldCapabilityHandler i = new ShieldCapabilityHandler();
					i.setEntity((LivingEntity) e.getObject());
					return i;
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return SHIELDS.orEmpty(capability, inst.cast());
				}

				@Override
				public CompoundTag serializeNBT() {
					return inst.orElseThrow(NullPointerException::new).serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt) {
					inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
				}
			});
		}
		if(e.getObject() instanceof Player player) {
			e.addCapability(IOptifinePlayer.ID, new ICapabilitySerializable<CompoundTag>() {

				final LazyOptional<IOptifinePlayer> inst = LazyOptional.of(() -> {
					OptifinePlayerHandler i = new OptifinePlayerHandler();
					i.setPlayer(player);
					return i;
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return OPTITRASH.orEmpty(capability, inst.cast());
				}

				@Override
				public CompoundTag serializeNBT() {
					return inst.orElseThrow(NullPointerException::new).serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt) {
					inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
				}
			});
		}
	}
}
