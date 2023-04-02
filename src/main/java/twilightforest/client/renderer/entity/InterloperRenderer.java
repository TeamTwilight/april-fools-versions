package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import twilightforest.entity.boss.Interloper;
import twilightforest.entity.monster.Kobold;

public class InterloperRenderer<T extends Interloper> extends EntityRenderer<T> {
	public InterloperRenderer(EntityRendererProvider.Context mgr) {
		super(mgr);
	}

	@Override
	public void render(T interloper, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
		matrixStack.pushPose();
		matrixStack.translate(0.0D, interloper.getBbHeight() * 0.5D, 0.0D);
		EntityDimensions dimensions = interloper.getDimensions(interloper.getPose());
		matrixStack.scale(dimensions.width, dimensions.height, dimensions.width);
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		PoseStack.Pose posestack$pose = matrixStack.last();
		Matrix4f matrix4f = posestack$pose.pose();
		Matrix3f matrix3f = posestack$pose.normal();

		Interloper.Stage stage = interloper.getStage();

		int r = 255;
		int g = 255;
		int b = 255;

		if (stage == Interloper.Stage.RGB) {
			r = (int)(interloper.getEntityData().get(Kobold.R) * 255.0F);
			g = (int)(interloper.getEntityData().get(Kobold.G) * 255.0F);
			b = (int)(interloper.getEntityData().get(Kobold.B) * 255.0F);
		} else if (stage == Interloper.Stage.RGB2) {
			r = (int)(interloper.getEntityData().get(Kobold.R) * 255.0F);
			r = (r + interloper.tickCount) % 256;
			g = (int)(interloper.getEntityData().get(Kobold.G) * 255.0F);
			g = (g + interloper.tickCount) % 256;
			b = (int)(interloper.getEntityData().get(Kobold.B) * 255.0F);
			b = (b + interloper.tickCount) % 256;
		}

		VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(interloper)));
		vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1, r, g, b);
		vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1, r, g, b);
		vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0, r, g, b);
		vertex(vertexconsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0, r, g, b);

		matrixStack.popPose();
		super.render(interloper, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	private static void vertex(VertexConsumer vertex, Matrix4f matrix4f, Matrix3f matrix3f, int packedLight, float v, int i, int j, int k, int r, int g, int b) {
		vertex.vertex(matrix4f, v - 0.5F, (float)i - 0.5F, 0.0F).color(r, g, b, 255).uv((float)j, (float)k).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}

	@Override
	public ResourceLocation getTextureLocation(T t) {
		return t.getStage().getTextureLocation();
	}
}
