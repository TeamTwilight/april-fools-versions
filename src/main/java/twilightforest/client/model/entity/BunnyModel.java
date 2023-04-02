// Date: 4/28/2012 9:36:32 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import twilightforest.entity.passive.DwarfRabbit;

public class BunnyModel extends QuadrupedModel<DwarfRabbit> {
	public BunnyModel(ModelPart root) {
		super(root, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = QuadrupedModel.createBodyMesh(1, CubeDeformation.NONE);
		PartDefinition partRoot = mesh.getRoot();

		partRoot.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 8)
						.addBox(-2F, -1F, -2F, 4, 3, 5)
						.texOffs(0, 18) // Tail
						.addBox(-1F, -2F, 3F, 2, 2, 2)
						.mirror(),
				PartPose.offset(0F, 21F, 0F));

		partRoot.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(0F, 0F, 0F, 1, 1, 1)
						.mirror(),
				PartPose.offset(-2F, 23F, 2F));

		partRoot.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(0F, 0F, 0F, 1, 1, 1)
						.mirror(),
				PartPose.offset(1F, 23F, 2F));

		partRoot.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(0F, 0F, 0F, 1, 1, 1)
						.mirror(),
				PartPose.offset(-2F, 23F, -2F));

		partRoot.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(0F, 0F, 0F, 1, 1, 1)
						.mirror(),
				PartPose.offset(1F, 23F, -2F));

		partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-2F, -4F, -3F, 4, 4, 4, new CubeDeformation(0.1F))
						.texOffs(16, 0)
						.addBox(-2.5F, -8F, -0.5F, 2, 4, 1)
						.texOffs(16, 0)
						.addBox(0.5F, -8F, -0.5F, 2, 4, 1)
						.mirror(),
				PartPose.offset(0F, 22F, -1F));

		return LayerDefinition.create(mesh, 32, 32);
	}


	@Override
	public void renderToBuffer(PoseStack ms, VertexConsumer buffer, int light, int overlay, float red, float green, float blue, float alpha) {
		if (this.young) {
			ms.pushPose();
			ms.scale(0.85F, 0.85F, 0.85F);
			ms.translate(0.0F, 0.25F, 0.0F);
			ImmutableList.of(this.head).forEach((p_103597_) -> {
				p_103597_.render(ms, buffer, light, overlay, red, green, blue, alpha);
			});
			ms.popPose();
			ms.pushPose();
			ms.scale(0.8F, 0.8F, 0.8F);
			ms.translate(0.0F, 0.37F, 0.0F);
			ImmutableList.of(this.body, this.leftFrontLeg, this.rightFrontLeg, this.leftHindLeg, this.rightHindLeg).forEach((p_103587_) -> {
				p_103587_.render(ms, buffer, light, overlay, red, green, blue, alpha);
			});
			ms.popPose();
		} else {
			super.renderToBuffer(ms, buffer, light, overlay, red, green, blue, alpha);
		}
	}

	@Override
	public void setupAnim(DwarfRabbit entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * Mth.DEG_TO_RAD;
		this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;
		this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;
		this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}
