package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.finalboss.PlateauBoss;

import javax.annotation.Nullable;
import java.util.Random;

public class FinalBossRenderer extends EntityRenderer<PlateauBoss> {

	private static final ResourceLocation DRAGON_LOCATION = TwilightForestMod.getModelTexture("twilight_dragon.png");
	private static final ResourceLocation DRAGON_EXPLODING_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
	private static final ResourceLocation DRAGON_EYES_LOCATION = TwilightForestMod.getModelTexture("twilight_dragon_eyes.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(DRAGON_LOCATION);
	private static final RenderType DECAL = RenderType.entityDecal(DRAGON_LOCATION);
	private static final RenderType EYES = RenderType.eyes(DRAGON_EYES_LOCATION);
	private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D);
	public final DragonModel model;

	public FinalBossRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.model = new DragonModel(context.bakeLayer(ModelLayers.ENDER_DRAGON));
	}

	public void render(PlateauBoss pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
		pMatrixStack.pushPose();
		float f = (float)pEntity.getLatencyPos(7, pPartialTicks)[0];
		float f1 = (float)(pEntity.getLatencyPos(5, pPartialTicks)[1] - pEntity.getLatencyPos(10, pPartialTicks)[1]);
		pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(-f));
		pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(f1 * 10.0F));
		pMatrixStack.translate(0.0D, 0.0D, 1.0D);
		pMatrixStack.scale(-1.0F, -1.0F, 1.0F);
		pMatrixStack.translate(0.0D, -1.501F, 0.0D);
		boolean flag = pEntity.hurtTime > 0;
		this.model.prepareMobModel(pEntity, 0.0F, 0.0F, pPartialTicks);
		if (pEntity.dragonDeathTime > 0) {
			float f2 = (float)pEntity.dragonDeathTime / 200.0F;
			VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.dragonExplosionAlpha(DRAGON_EXPLODING_LOCATION));
			this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, f2);
			VertexConsumer vertexconsumer1 = pBuffer.getBuffer(DECAL);
			this.model.renderToBuffer(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.pack(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
		} else {
			VertexConsumer vertexconsumer3 = pBuffer.getBuffer(RENDER_TYPE);
			this.model.renderToBuffer(pMatrixStack, vertexconsumer3, pPackedLight, OverlayTexture.pack(0.0F, flag), 1.0F, 1.0F, 1.0F, 1.0F);
		}

		VertexConsumer vertexconsumer4 = pBuffer.getBuffer(EYES);
		this.model.renderToBuffer(pMatrixStack, vertexconsumer4, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		if (pEntity.dragonDeathTime > 0) {
			float f5 = ((float)pEntity.dragonDeathTime + pPartialTicks) / 200.0F;
			float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
			Random random = new Random(432L);
			VertexConsumer vertexconsumer2 = pBuffer.getBuffer(RenderType.lightning());
			pMatrixStack.pushPose();
			pMatrixStack.translate(0.0D, -1.0D, -2.0D);

			for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
				pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
				pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
				pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
				pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
				pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
				pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
				float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
				float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
				Matrix4f matrix4f = pMatrixStack.last().pose();
				int j = (int)(255.0F * (1.0F - f7));
				vertex01(vertexconsumer2, matrix4f, j);
				vertex2(vertexconsumer2, matrix4f, f3, f4);
				vertex3(vertexconsumer2, matrix4f, f3, f4);
				vertex01(vertexconsumer2, matrix4f, j);
				vertex3(vertexconsumer2, matrix4f, f3, f4);
				vertex4(vertexconsumer2, matrix4f, f3, f4);
				vertex01(vertexconsumer2, matrix4f, j);
				vertex4(vertexconsumer2, matrix4f, f3, f4);
				vertex2(vertexconsumer2, matrix4f, f3, f4);
			}

			pMatrixStack.popPose();
		}

		pMatrixStack.popPose();

		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	private static void vertex01(VertexConsumer p_114220_, Matrix4f p_114221_, int p_114222_) {
		p_114220_.vertex(p_114221_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_114222_).endVertex();
	}

	private static void vertex2(VertexConsumer p_114215_, Matrix4f p_114216_, float p_114217_, float p_114218_) {
		p_114215_.vertex(p_114216_, -HALF_SQRT_3 * p_114218_, p_114217_, -0.5F * p_114218_).color(255, 0, 255, 0).endVertex();
	}

	private static void vertex3(VertexConsumer p_114224_, Matrix4f p_114225_, float p_114226_, float p_114227_) {
		p_114224_.vertex(p_114225_, HALF_SQRT_3 * p_114227_, p_114226_, -0.5F * p_114227_).color(255, 0, 255, 0).endVertex();
	}

	private static void vertex4(VertexConsumer p_114229_, Matrix4f p_114230_, float p_114231_, float p_114232_) {
		p_114229_.vertex(p_114230_, 0.0F, p_114231_, p_114232_).color(255, 0, 255, 0).endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(PlateauBoss pEntity) {
		return TwilightForestMod.getModelTexture("twilight_dragon.png");
	}
	@OnlyIn(Dist.CLIENT)
	public static class DragonModel extends EntityModel<PlateauBoss> {
		private final ModelPart head;
		private final ModelPart neck;
		private final ModelPart jaw;
		private final ModelPart body;
		private final ModelPart leftWing;
		private final ModelPart leftWingTip;
		private final ModelPart leftFrontLeg;
		private final ModelPart leftFrontLegTip;
		private final ModelPart leftFrontFoot;
		private final ModelPart leftRearLeg;
		private final ModelPart leftRearLegTip;
		private final ModelPart leftRearFoot;
		private final ModelPart rightWing;
		private final ModelPart rightWingTip;
		private final ModelPart rightFrontLeg;
		private final ModelPart rightFrontLegTip;
		private final ModelPart rightFrontFoot;
		private final ModelPart rightRearLeg;
		private final ModelPart rightRearLegTip;
		private final ModelPart rightRearFoot;
		@Nullable
		private PlateauBoss entity;
		private float a;

		public DragonModel(ModelPart p_173976_) {
			this.head = p_173976_.getChild("head");
			this.jaw = this.head.getChild("jaw");
			this.neck = p_173976_.getChild("neck");
			this.body = p_173976_.getChild("body");
			this.leftWing = p_173976_.getChild("left_wing");
			this.leftWingTip = this.leftWing.getChild("left_wing_tip");
			this.leftFrontLeg = p_173976_.getChild("left_front_leg");
			this.leftFrontLegTip = this.leftFrontLeg.getChild("left_front_leg_tip");
			this.leftFrontFoot = this.leftFrontLegTip.getChild("left_front_foot");
			this.leftRearLeg = p_173976_.getChild("left_hind_leg");
			this.leftRearLegTip = this.leftRearLeg.getChild("left_hind_leg_tip");
			this.leftRearFoot = this.leftRearLegTip.getChild("left_hind_foot");
			this.rightWing = p_173976_.getChild("right_wing");
			this.rightWingTip = this.rightWing.getChild("right_wing_tip");
			this.rightFrontLeg = p_173976_.getChild("right_front_leg");
			this.rightFrontLegTip = this.rightFrontLeg.getChild("right_front_leg_tip");
			this.rightFrontFoot = this.rightFrontLegTip.getChild("right_front_foot");
			this.rightRearLeg = p_173976_.getChild("right_hind_leg");
			this.rightRearLegTip = this.rightRearLeg.getChild("right_hind_leg_tip");
			this.rightRearFoot = this.rightRearLegTip.getChild("right_hind_foot");
		}

		public void prepareMobModel(PlateauBoss pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
			this.entity = pEntity;
			this.a = pPartialTick;
		}

		/**
		 * Sets this entity's model rotation angles
		 */
		public void setupAnim(PlateauBoss pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		}

		public void renderToBuffer(PoseStack pMatrixStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
			pMatrixStack.pushPose();
			float f = Mth.lerp(this.a, this.entity.oFlapTime, this.entity.flapTime);
			this.jaw.xRot = (float)(Math.sin(f * ((float)Math.PI * 2F)) + 1.0D) * 0.2F;
			float f1 = (float)(Math.sin(f * ((float)Math.PI * 2F) - 1.0F) + 1.0D);
			f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
			pMatrixStack.translate(0.0D, f1 - 2.0F, -3.0D);
			pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(f1 * 2.0F));
			float f2 = 0.0F;
			float f3 = 20.0F;
			float f4 = -12.0F;
			double[] adouble = this.entity.getLatencyPos(6, this.a);
			float f6 = Mth.rotWrap(this.entity.getLatencyPos(5, this.a)[0] - this.entity.getLatencyPos(10, this.a)[0]);
			float f7 = Mth.rotWrap(this.entity.getLatencyPos(5, this.a)[0] + (double)(f6 / 2.0F));
			float f8 = f * ((float)Math.PI * 2F);

			for(int i = 0; i < 5; ++i) {
				double[] adouble1 = this.entity.getLatencyPos(5 - i, this.a);
				float f9 = (float)Math.cos((float)i * 0.45F + f8) * 0.15F;
				this.neck.yRot = Mth.rotWrap(adouble1[0] - adouble[0]) * ((float)Math.PI / 180F) * 1.5F;
				this.neck.xRot = f9 + this.entity.getHeadPartYOffset(i) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
				this.neck.zRot = -Mth.rotWrap(adouble1[0] - (double)f7) * ((float)Math.PI / 180F) * 1.5F;
				this.neck.y = f3;
				this.neck.z = f4;
				this.neck.x = f2;
				f3 = (float)((double)f3 + Math.sin(this.neck.xRot) * 10.0D);
				f4 = (float)((double)f4 - Math.cos(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0D);
				f2 = (float)((double)f2 - Math.sin(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0D);
				this.neck.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, pAlpha);
			}

			this.head.y = f3;
			this.head.z = f4;
			this.head.x = f2;
			double[] adouble2 = this.entity.getLatencyPos(0, this.a);
			this.head.yRot = Mth.rotWrap(adouble2[0] - adouble[0]) * ((float)Math.PI / 180F);
			this.head.xRot = Mth.rotWrap(this.entity.getHeadPartYOffset(6)) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
			this.head.zRot = -Mth.rotWrap(adouble2[0] - (double)f7) * ((float)Math.PI / 180F);
			this.head.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, pAlpha);
			pMatrixStack.pushPose();
			pMatrixStack.translate(0.0D, 1.0D, 0.0D);
			pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(-f6 * 1.5F));
			pMatrixStack.translate(0.0D, -1.0D, 0.0D);
			this.body.zRot = 0.0F;
			this.body.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, pAlpha);
			float f10 = f * ((float)Math.PI * 2F);
			this.leftWing.xRot = 0.125F - (float)Math.cos(f10) * 0.2F;
			this.leftWing.yRot = -0.25F;
			this.leftWing.zRot = -((float)(Math.sin(f10) + 0.125D)) * 0.8F;
			this.leftWingTip.zRot = (float)(Math.sin(f10 + 2.0F) + 0.5D) * 0.75F;
			this.rightWing.xRot = this.leftWing.xRot;
			this.rightWing.yRot = -this.leftWing.yRot;
			this.rightWing.zRot = -this.leftWing.zRot;
			this.rightWingTip.zRot = -this.leftWingTip.zRot;
			this.renderSide(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, f1, this.leftWing, this.leftFrontLeg, this.leftFrontLegTip, this.leftFrontFoot, this.leftRearLeg, this.leftRearLegTip, this.leftRearFoot, pAlpha);
			this.renderSide(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, f1, this.rightWing, this.rightFrontLeg, this.rightFrontLegTip, this.rightFrontFoot, this.rightRearLeg, this.rightRearLegTip, this.rightRearFoot, pAlpha);
			pMatrixStack.popPose();
			float f11 = -((float)Math.sin(f * ((float)Math.PI * 2F))) * 0.0F;
			f8 = f * ((float)Math.PI * 2F);
			f3 = 10.0F;
			f4 = 60.0F;
			f2 = 0.0F;
			adouble = this.entity.getLatencyPos(11, this.a);

			for(int j = 0; j < 12; ++j) {
				adouble2 = this.entity.getLatencyPos(12 + j, this.a);
				f11 = (float)((double)f11 + Math.sin((float)j * 0.45F + f8) * (double)0.05F);
				this.neck.yRot = (Mth.rotWrap(adouble2[0] - adouble[0]) * 1.5F + 180.0F) * ((float)Math.PI / 180F);
				this.neck.xRot = f11 + (float)(adouble2[1] - adouble[1]) * ((float)Math.PI / 180F) * 1.5F * 5.0F;
				this.neck.zRot = Mth.rotWrap(adouble2[0] - (double)f7) * ((float)Math.PI / 180F) * 1.5F;
				this.neck.y = f3;
				this.neck.z = f4;
				this.neck.x = f2;
				f3 = (float)((double)f3 + Math.sin(this.neck.xRot) * 10.0D);
				f4 = (float)((double)f4 - Math.cos(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0D);
				f2 = (float)((double)f2 - Math.sin(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0D);
				this.neck.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, pAlpha);
			}

			pMatrixStack.popPose();
		}

		private void renderSide(PoseStack p_173978_, VertexConsumer p_173979_, int p_173980_, int p_173981_, float p_173982_, ModelPart p_173983_, ModelPart p_173984_, ModelPart p_173985_, ModelPart p_173986_, ModelPart p_173987_, ModelPart p_173988_, ModelPart p_173989_, float p_173990_) {
			p_173987_.xRot = 1.0F + p_173982_ * 0.1F;
			p_173988_.xRot = 0.5F + p_173982_ * 0.1F;
			p_173989_.xRot = 0.75F + p_173982_ * 0.1F;
			p_173984_.xRot = 1.3F + p_173982_ * 0.1F;
			p_173985_.xRot = -0.5F - p_173982_ * 0.1F;
			p_173986_.xRot = 0.75F + p_173982_ * 0.1F;
			p_173983_.render(p_173978_, p_173979_, p_173980_, p_173981_, 1.0F, 1.0F, 1.0F, p_173990_);
			p_173984_.render(p_173978_, p_173979_, p_173980_, p_173981_, 1.0F, 1.0F, 1.0F, p_173990_);
			p_173987_.render(p_173978_, p_173979_, p_173980_, p_173981_, 1.0F, 1.0F, 1.0F, p_173990_);
		}
	}

}