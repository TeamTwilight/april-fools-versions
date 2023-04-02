package twilightforest.events;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFPortalBlock;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.fan.FeatherFanFallCapability;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.capabilities.thrown.YetiThrowCapability;
import twilightforest.network.TFPacketHandler;
import twilightforest.network.UpdateShieldPacket;
import twilightforest.world.registration.TFGenerationSettings;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class CapabilityEvents {

	public static final String NBT_TAG_TWILIGHT = "twilightforest_banished";
	public static final String NBT_TAG_FINAL = "twilightforest_final";
	public static final String NBT_TAG_HOME = "twilightforest_home";

	@SubscribeEvent
	public static void updateCaps(LivingEvent.LivingTickEvent event) {
		event.getEntity().getCapability(CapabilityList.SHIELDS).ifPresent(IShieldCapability::update);
		event.getEntity().getCapability(CapabilityList.FEATHER_FAN_FALLING).ifPresent(FeatherFanFallCapability::update);
		event.getEntity().getCapability(CapabilityList.YETI_THROWN).ifPresent(YetiThrowCapability::update);
	}

	@SubscribeEvent
	public static void livingAttack(LivingAttackEvent event) {
		LivingEntity living = event.getEntity();
		// shields
		if (!living.getLevel().isClientSide() && !event.getSource().isBypassArmor()) {
			living.getCapability(CapabilityList.SHIELDS).ifPresent(cap -> {
				if (cap.shieldsLeft() > 0) {
					cap.breakShield();
					event.setCanceled(true);
				}
			});
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
		if (event.isEndConquered()) {
			updateCapabilities(serverPlayer, serverPlayer);
		}
		CompoundTag tagCompound = serverPlayer.getPersistentData();
		CompoundTag playerData = tagCompound.getCompound(Player.PERSISTED_NBT_TAG);

		if (serverPlayer.getRespawnPosition() == null) {
			playerData.putBoolean(NBT_TAG_TWILIGHT, false); // set to false so that the method works
			tagCompound.put(Player.PERSISTED_NBT_TAG, playerData); // commit
			banishNewbieToTwilightZone(serverPlayer);
		}
		BlockPos pos = BlockPos.of(playerData.getLong(NBT_TAG_HOME));
		Vec3 destination = Vec3.atBottomCenterOf(pos);
		serverPlayer.teleportTo(destination.x, destination.y, destination.z);
		serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, true, false, false));
	}

	/**
	 * When player logs in, report conflict status, set progression status
	 */
	@SubscribeEvent
	public static void playerLogsIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer serverPlayer) {
			updateCapabilities(serverPlayer, event.getEntity());
			banishNewbieToTwilightZone(serverPlayer);
		}
	}

	@SubscribeEvent
	public static void playerPortals(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.getEntity().getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
			updateCapabilities(player, event.getEntity());
		}
	}

	@SubscribeEvent
	public static void onStartTracking(PlayerEvent.StartTracking event) {
		updateCapabilities((ServerPlayer) event.getEntity(), event.getTarget());
	}

	// send any capabilities that are needed client-side
	private static void updateCapabilities(ServerPlayer clientTarget, Entity shielded) {
		shielded.getCapability(CapabilityList.SHIELDS).ifPresent(cap -> {
			if (cap.shieldsLeft() > 0) {
				TFPacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> clientTarget), new UpdateShieldPacket(shielded, cap));
			}
		});
	}

	// Teleport first-time players to Twilight Forest
	private static void banishNewbieToTwilightZone(Player player) {
		CompoundTag tagCompound = player.getPersistentData();
		CompoundTag playerData = tagCompound.getCompound(Player.PERSISTED_NBT_TAG);

		// getBoolean returns false, if false or didn't exist
		boolean shouldBanishPlayer = !playerData.getBoolean(NBT_TAG_TWILIGHT);

		playerData.putBoolean(NBT_TAG_TWILIGHT, true); // set true once player has spawned either way
		tagCompound.put(Player.PERSISTED_NBT_TAG, playerData); // commit

		if (shouldBanishPlayer) TFPortalBlock.attemptSendEntity(player, true, false); // See ya hate to be ya

		if (player instanceof ServerPlayer serverPlayer) {
			if (!playerData.contains(NBT_TAG_FINAL) && player.level.dimension() == TFGenerationSettings.DIMENSION_KEY) {
				ServerLevel serverLevel = serverPlayer.getLevel();
				HolderSet<Structure> holderSet = serverLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY).getHolder(ResourceKey.create(Registry.STRUCTURE_REGISTRY, TwilightForestMod.prefix("final_castle"))).map(HolderSet::direct).orElseThrow();
				Pair<BlockPos, Holder<Structure>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel, holderSet, player.blockPosition(), 100, false);

				if (pair != null) {
					BlockPos pos = pair.getFirst();
					pos = new BlockPos(pos.getX(), serverLevel.getMaxBuildHeight(), pos.getZ());
					Vec3 destination = Vec3.atBottomCenterOf(pos);
					player.teleportTo(destination.x, destination.y, destination.z);

					player.getCapability(CapabilityList.FEATHER_FAN_FALLING).ifPresent(featherFanFallCapability -> featherFanFallCapability.setFalling(true));
					player.setItemSlot(EquipmentSlot.MAINHAND, Items.WATER_BUCKET.getDefaultInstance());
					player.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.BREAD, 64));
					playerData.putFloat(NBT_TAG_HOME, pos.asLong());
					serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0, true, false, false));
				}

				playerData.putBoolean(NBT_TAG_FINAL, true);
			}
		}
	}
}
