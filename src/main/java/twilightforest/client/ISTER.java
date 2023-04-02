package twilightforest.client;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.AbstractTrophyBlock;
import twilightforest.block.KeepsakeCasketBlock;
import twilightforest.block.TFChestBlock;
import twilightforest.block.entity.KeepsakeCasketBlockEntity;
import twilightforest.block.entity.TwilightChestEntity;
import twilightforest.client.model.tileentity.GenericTrophyModel;
import twilightforest.client.renderer.tileentity.SkullCandleTileEntityRenderer;
import twilightforest.client.renderer.tileentity.TrophyTileEntityRenderer;
import twilightforest.enums.BossVariant;
import twilightforest.init.TFBlocks;

import java.util.HashMap;
import java.util.Map;

public class ISTER extends BlockEntityWithoutLevelRenderer {
	private final KeepsakeCasketBlockEntity casket = new KeepsakeCasketBlockEntity(BlockPos.ZERO, TFBlocks.KEEPSAKE_CASKET.get().defaultBlockState());
	private final Map<Block, TwilightChestEntity> chestEntities = Util.make(new HashMap<>(), map -> {
		makeInstance(map, TFBlocks.TWILIGHT_OAK_CHEST);
		makeInstance(map, TFBlocks.CANOPY_CHEST);
		makeInstance(map, TFBlocks.MANGROVE_CHEST);
		makeInstance(map, TFBlocks.DARKWOOD_CHEST);
		makeInstance(map, TFBlocks.TIME_CHEST);
		makeInstance(map, TFBlocks.TRANSFORMATION_CHEST);
		makeInstance(map, TFBlocks.MINING_CHEST);
		makeInstance(map, TFBlocks.SORTING_CHEST);
	});

	public ISTER() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType camera, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
		Item item = stack.getItem();
		if (item instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			if (block instanceof AbstractTrophyBlock trophyBlock) {
				BossVariant variant = trophyBlock.getVariant();
				GenericTrophyModel trophy = TrophyTileEntityRenderer.createTrophyRenderers(Minecraft.getInstance().getEntityModels()).get(variant);

				if (camera == ItemTransforms.TransformType.GUI) {
					ModelResourceLocation back = new ModelResourceLocation(TwilightForestMod.prefix(((AbstractTrophyBlock) block).getVariant().getTrophyType().getModelName()), "inventory");
					BakedModel modelBack = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(back);

					Lighting.setupForFlatItems();
					MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
					ms.pushPose();
					Lighting.setupForFlatItems();
					ms.translate(0.5F, 0.5F, -1.5F);
					Minecraft.getInstance().getItemRenderer().render(TrophyTileEntityRenderer.stack, ItemTransforms.TransformType.GUI, false, ms, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, modelBack.applyTransform(camera, ms, false));
					ms.popPose();
					bufferSource.endBatch();
					Lighting.setupFor3DItems();

					ms.pushPose();
					ms.translate(0.5F, 0.5F, 0.5F);
					if (trophyBlock.getVariant() == BossVariant.HYDRA || trophyBlock.getVariant() == BossVariant.QUEST_RAM)
						ms.scale(0.9F, 0.9F, 0.9F);
					ms.mulPose(Vector3f.XP.rotationDegrees(30));
					ms.mulPose(Vector3f.YN.rotationDegrees(TFConfig.CLIENT_CONFIG.rotateTrophyHeadsGui.get() ? TFClientEvents.rotationTicker : -45));
					ms.translate(-0.5F, -0.5F, -0.5F);
					ms.translate(0.0F, 0.25F, 0.0F);
					if (trophyBlock.getVariant() == BossVariant.UR_GHAST) ms.translate(0.0F, 0.5F, 0.0F);
					if (trophyBlock.getVariant() == BossVariant.ALPHA_YETI) ms.translate(0.0F, -0.15F, 0.0F);
					TrophyTileEntityRenderer.render(null, 180.0F, trophy, variant, TFClientEvents.time + Minecraft.getInstance().getDeltaFrameTime(), ms, buffers, light, camera);
					ms.popPose();

				} else {
					TrophyTileEntityRenderer.render(null, 180.0F, trophy, variant, TFClientEvents.time + Minecraft.getInstance().getDeltaFrameTime(), ms, buffers, light, camera);
				}
			} else if (block instanceof KeepsakeCasketBlock) {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.casket, ms, buffers, light, overlay);
			} else if (block instanceof TFChestBlock) {
				Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.chestEntities.get(block), ms, buffers, light, overlay);
			} else if (block instanceof AbstractSkullCandleBlock candleBlock) {
				GameProfile gameprofile = null;
				if (stack.hasTag()) {
					CompoundTag compoundtag = stack.getTag();
					if (compoundtag.contains("SkullOwner", 10)) {
						gameprofile = NbtUtils.readGameProfile(compoundtag.getCompound("SkullOwner"));
					} else if (compoundtag.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundtag.getString("SkullOwner"))) {
						gameprofile = new GameProfile(null, compoundtag.getString("SkullOwner"));
						compoundtag.remove("SkullOwner");
						SkullBlockEntity.updateGameprofile(gameprofile, (p_172560_) ->
								compoundtag.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), p_172560_)));
					}
				}

				SkullBlock.Type type = candleBlock.getType();
				SkullModelBase base = SkullCandleTileEntityRenderer.createSkullRenderers(Minecraft.getInstance().getEntityModels()).get(type);
				RenderType renderType = SkullCandleTileEntityRenderer.getRenderType(type, gameprofile);
				SkullCandleTileEntityRenderer.renderSkull(null, 180.0F, 0.0F, ms, buffers, light, base, renderType);

				//we put the candle
				ms.translate(0.0F, 0.5F, 0.0F);
				CompoundTag tag = stack.getTagElement("BlockEntityTag");
				if (tag != null && tag.contains("CandleColor") && tag.contains("CandleAmount")) {
					if (tag.getInt("CandleAmount") <= 0) tag.putInt("CandleAmount", 1);
					Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
							AbstractSkullCandleBlock.candleColorToCandle(AbstractSkullCandleBlock.CandleColors.colorFromInt(tag.getInt("CandleColor")).getSerializedName())
									.defaultBlockState().setValue(CandleBlock.CANDLES, tag.getInt("CandleAmount")), ms, buffers, light, overlay, ModelData.EMPTY, RenderType.cutout());
				}
			} else {
				if (block instanceof EntityBlock be) {
					BlockEntity blockEntity = be.newBlockEntity(BlockPos.ZERO, block.defaultBlockState());
					if (blockEntity != null) {
						Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(blockEntity).render(null, 0, ms, buffers, light, overlay);
					}
				}
			}
		}
	}

	public static void makeInstance(Map<Block, TwilightChestEntity> map, RegistryObject<? extends ChestBlock> registryObject) {
		ChestBlock block = registryObject.get();

		map.put(block, new TwilightChestEntity(BlockPos.ZERO, block.defaultBlockState()));
	}
}
