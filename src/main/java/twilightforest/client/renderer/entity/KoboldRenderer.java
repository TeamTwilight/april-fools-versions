package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.KoboldModel;
import twilightforest.entity.monster.Kobold;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class KoboldRenderer extends TFBipedRenderer<Kobold, KoboldModel> {

	public KoboldRenderer(EntityRendererProvider.Context context, KoboldModel model, float shadowSize, String textureName) {
		super(context, model, shadowSize, textureName);

		this.layers.removeIf(r -> r instanceof ItemInHandLayer);
		this.addLayer(new HeldItemLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	public void render(Kobold kobold, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<>(kobold, this, partialTicks, matrixStack, buffer, packedLight))) return;
		matrixStack.pushPose();
		this.model.attackTime = this.getAttackAnim(kobold, partialTicks);

		boolean shouldSit = kobold.isPassenger() && (kobold.getVehicle() != null && kobold.getVehicle().shouldRiderSit());
		this.model.riding = shouldSit;
		this.model.young = kobold.isBaby();
		float f = Mth.rotLerp(partialTicks, kobold.yBodyRotO, kobold.yBodyRot);
		float f1 = Mth.rotLerp(partialTicks, kobold.yHeadRotO, kobold.yHeadRot);
		float f2 = f1 - f;
		if (shouldSit && kobold.getVehicle() instanceof LivingEntity livingentity) {
			f = Mth.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
			f2 = f1 - f;
			float f3 = Mth.wrapDegrees(f2);
			if (f3 < -85.0F) {
				f3 = -85.0F;
			}

			if (f3 >= 85.0F) {
				f3 = 85.0F;
			}

			f = f1 - f3;
			if (f3 * f3 > 2500.0F) {
				f += f3 * 0.2F;
			}

			f2 = f1 - f;
		}

		float f6 = Mth.lerp(partialTicks, kobold.xRotO, kobold.getXRot());
		if (isEntityUpsideDown(kobold)) {
			f6 *= -1.0F;
			f2 *= -1.0F;
		}

		if (kobold.hasPose(Pose.SLEEPING)) {
			Direction direction = kobold.getBedOrientation();
			if (direction != null) {
				float f4 = kobold.getEyeHeight(Pose.STANDING) - 0.1F;
				matrixStack.translate((float)(-direction.getStepX()) * f4, 0.0D, (float)(-direction.getStepZ()) * f4);
			}
		}

		float f7 = this.getBob(kobold, partialTicks);
		this.setupRotations(kobold, matrixStack, f7, f, partialTicks);
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(kobold, matrixStack, partialTicks);
		matrixStack.translate(0.0D, -1.501F, 0.0D);
		float f8 = 0.0F;
		float f5 = 0.0F;
		if (!shouldSit && kobold.isAlive()) {
			f8 = Mth.lerp(partialTicks, kobold.animationSpeedOld, kobold.animationSpeed);
			f5 = kobold.animationPosition - kobold.animationSpeed * (1.0F - partialTicks);
			if (kobold.isBaby()) {
				f5 *= 3.0F;
			}

			if (f8 > 1.0F) {
				f8 = 1.0F;
			}
		}

		this.model.prepareMobModel(kobold, f5, f8, partialTicks);
		this.model.setupAnim(kobold, f5, f8, f7, f2, f6);
		Minecraft minecraft = Minecraft.getInstance();
		boolean flag = this.isBodyVisible(kobold);
		boolean flag1 = !flag && !kobold.isInvisibleTo(Objects.requireNonNull(minecraft.player));
		boolean flag2 = minecraft.shouldEntityAppearGlowing(kobold);

		if (kobold.isBaby()) {
			RenderType rendertype = this.getRenderType1(flag, flag1, flag2);
			if (rendertype != null) {
				VertexConsumer vertexconsumer = buffer.getBuffer(rendertype);
				int i = getOverlayCoords(kobold, this.getWhiteOverlayProgress(kobold, partialTicks));
				this.model.renderToBuffer(matrixStack, vertexconsumer, packedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
				RenderType rgbType = this.getRenderType2(flag, flag1, flag2);
				if (rgbType != null) {
					VertexConsumer rgbConsumer = buffer.getBuffer(rgbType);
					SynchedEntityData data = kobold.getEntityData();
					this.model.renderToBuffer(matrixStack, rgbConsumer, packedLight, i, data.get(Kobold.R), data.get(Kobold.G), data.get(Kobold.B), flag1 ? 0.15F : 1.0F);
				}
			}
		} else {
			RenderType rendertype = this.getRenderType(kobold, flag, flag1, flag2);
			if (rendertype != null) {
				VertexConsumer vertexconsumer = buffer.getBuffer(rendertype);
				int i = getOverlayCoords(kobold, this.getWhiteOverlayProgress(kobold, partialTicks));
				this.model.renderToBuffer(matrixStack, vertexconsumer, packedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
			}
		}

		if (!kobold.isSpectator()) {
			for(RenderLayer<Kobold, KoboldModel> renderLayer : this.layers) {
				renderLayer.render(matrixStack, buffer, packedLight, kobold, f5, f8, partialTicks, f7, f2, f6);
			}
		}

		matrixStack.popPose();
		var renderNameTagEvent = new net.minecraftforge.client.event.RenderNameTagEvent(kobold, kobold.getDisplayName(), this, matrixStack, buffer, packedLight, partialTicks);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameTagEvent);
		if (renderNameTagEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameTagEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(kobold))) {
			this.renderNameTag(kobold, renderNameTagEvent.getContent(), matrixStack, buffer, packedLight);
		}
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<>(kobold, this, partialTicks, matrixStack, buffer, packedLight));
	}

	@Nullable
	protected RenderType getRenderType1(boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
		ResourceLocation resourcelocation = TwilightForestMod.getModelTexture("kobold_clothes.png");
		if (pTranslucent) {
			return RenderType.itemEntityTranslucentCull(resourcelocation);
		} else if (pBodyVisible) {
			return this.model.renderType(resourcelocation);
		} else {
			return pGlowing ? RenderType.outline(resourcelocation) : null;
		}
	}

	@Nullable
	protected RenderType getRenderType2(boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
		ResourceLocation resourcelocation = TwilightForestMod.getModelTexture("kobold_rgb.png");
		if (pTranslucent) {
			return RenderType.itemEntityTranslucentCull(resourcelocation);
		} else if (pBodyVisible) {
			return this.model.renderType(resourcelocation);
		} else {
			return pGlowing ? RenderType.outline(resourcelocation) : null;
		}
	}

	/**
	 * [VanillaCopy] {@link net.minecraft.client.renderer.entity.layers.ItemInHandLayer} with additional transforms
	 */
	private static class HeldItemLayer extends RenderLayer<Kobold, KoboldModel> {
		private final ItemInHandRenderer handRenderer;

		public HeldItemLayer(RenderLayerParent<Kobold, KoboldModel> renderer, ItemInHandRenderer handRenderer) {
			super(renderer);
			this.handRenderer = handRenderer;
		}

		@Override
		public void render(PoseStack ms, MultiBufferSource buffers, int light, Kobold living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			boolean flag = living.getMainArm() == HumanoidArm.RIGHT;
			ItemStack itemstack = flag ? living.getOffhandItem() : living.getMainHandItem();
			ItemStack itemstack1 = flag ? living.getMainHandItem() : living.getOffhandItem();
			if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
				ms.pushPose();
				if (this.getParentModel().young) {
					ms.translate(0.0D, 0.75D, 0.0D);
					ms.scale(0.5F, 0.5F, 0.5F);
				}

				this.renderItem(living, itemstack1, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, ms, buffers, light);
				this.renderItem(living, itemstack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, ms, buffers, light);
				ms.popPose();
			}
		}

		private void renderItem(LivingEntity entity, ItemStack stack, ItemTransforms.TransformType transform, HumanoidArm handSide, PoseStack ms, MultiBufferSource buffers, int light) {
			if (!stack.isEmpty()) {
				ms.pushPose();
				this.getParentModel().translateToHand(handSide, ms);
				ms.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
				ms.mulPose(Vector3f.YP.rotationDegrees(180.0F));
				boolean flag = handSide == HumanoidArm.LEFT;
				ms.translate(0.05D, 0.125D, -0.35D);
				this.handRenderer.renderItem(entity, stack, transform, flag, ms, buffers, light);
				ms.popPose();
			}
		}
	}
}
