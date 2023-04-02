package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LichTrophyModel extends GenericTrophyModel {

	public final ModelPart head;
	public ModelPart crown;

	public LichTrophyModel(ModelPart part) {
		this.head = part.getChild("head");
		this.crown = this.head.getChild("crown");
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

		head.addOrReplaceChild("crown", CubeListBuilder.create()
						.texOffs(32, 29)
						.addBox(-3.5F, 4.0F, -3.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.5F)),
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

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = z * ((float) Math.PI / 180F);
		this.head.y = -4.0F;
		this.crown.y = -14.0F;
	}

	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
