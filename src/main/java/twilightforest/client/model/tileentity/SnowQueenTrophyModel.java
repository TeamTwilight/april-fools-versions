package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SnowQueenTrophyModel extends GenericTrophyModel {

	public final ModelPart head;
	public ModelPart crownFront;
	public ModelPart crownBack;
	public ModelPart crownRight;
	public ModelPart crownLeft;

	public SnowQueenTrophyModel(ModelPart part) {
		this.head = part.getChild("head");
		this.crownRight = this.head.getChild("crown_right");
		this.crownBack = this.head.getChild("crown_back");
		this.crownLeft = this.head.getChild("crown_left");
		this.crownFront = this.head.getChild("crown_front");

	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partRoot = meshdefinition.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F)
						.texOffs(20, 0)
						.addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(0.0F, 12.0F, 0.0F));

		head.addOrReplaceChild("mouth", CubeListBuilder.create()
						.texOffs(26, 5)
						.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 0.2181661564992912F, 0.0F, 0.0F));

		head.addOrReplaceChild("right_ear", CubeListBuilder.create()
						.texOffs(32, 0)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(-3.0F, -4.0F, 0.0F, 0.0F, 0.0F, -1.3089969389957472F));

		head.addOrReplaceChild("left_ear", CubeListBuilder.create()
						.texOffs(42, 0)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(3.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.3089969389957472F));

        /*partRoot.addOrReplaceChild("head", CubeListBuilder.create()
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);*/

		head.addOrReplaceChild("crown_front", CubeListBuilder.create()
						.texOffs(46, 12)
						.addBox(-4.5F, -4.0F, 0.0F, 9.0F, 4.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, -4.0F, -3F, 0.39269908169872414F, 0.0F, 0.0F));

		head.addOrReplaceChild("crown_right", CubeListBuilder.create()
						.texOffs(27, 16)
						.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F),
				PartPose.offsetAndRotation(-3.5F, -4.0F, 0.0F, 0.39269908169872414F, 1.5707963267948966F, 0.0F));

		head.addOrReplaceChild("crown_left", CubeListBuilder.create()
						.texOffs(27, 12)
						.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F),
				PartPose.offsetAndRotation(3.5F, -4.0F, 0.0F, -0.39269908169872414F, 1.5707963267948966F, 0.0F));

		head.addOrReplaceChild("crown_back", CubeListBuilder.create()
						.texOffs(46, 16)
						.addBox(-4.5F, -4.0F, 0.0F, 9.0F, 4.0F, 0.0F),
				PartPose.offsetAndRotation(0.0F, -4.0F, 3F, -0.39269908169872414F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = z * ((float) Math.PI / 180F);
		this.head.y = 0.0F;
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
