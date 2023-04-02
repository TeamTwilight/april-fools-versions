package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class MinoshroomTrophyModel extends GenericTrophyModel {

	public final ModelPart head;

	public MinoshroomTrophyModel(ModelPart part) {
		this.head = part.getChild("head");
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partRoot = meshdefinition.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-3.5F, -9.0F, -3.0F, 7.0F, 6.0F, 6.0F)
						.texOffs(12, 22)
						.addBox(-1.5F, -6.0F, -6.0F, 3.0F, 2.0F, 3.0F)
						//Horns
						.texOffs(32, 0)
						.addBox(-7.5F, -8.5F, -0.5F, 4.0F, 2.0F, 3.0F)
						.texOffs(32, 5)
						.addBox(-7.5F, -11.5F, -0.5F, 2.0F, 3.0F, 3.0F)
						.texOffs(46, 0)
						.addBox(3.5F, -8.5F, -0.5F, 4.0F, 2.0F, 3.0F)
						.texOffs(46, 5)
						.addBox(5.5F, -11.5F, -0.5F, 2.0F, 3.0F, 3.0F),
				PartPose.offset(0.0F, -2.0F, 0.0F));

		head.addOrReplaceChild("mouth", CubeListBuilder.create()
						.texOffs(12, 17)
						.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, -4.0F, -3.0F, 0.2181661564992912F, 0.0F, 0.0F));

		head.addOrReplaceChild("right_ear", CubeListBuilder.create()
						.texOffs(0, 12)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(-3.0F, -7.0F, -2.0F, 0.0F, 0.0F, -1.3089969389957472F));

		head.addOrReplaceChild("left_ear", CubeListBuilder.create()
						.texOffs(10, 12)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(3.0F, -7.0F, -2.0F, 0.0F, 0.0F, 1.3089969389957472F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = z * ((float) Math.PI / 180F);
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrix.translate(0.0F, .25F, 0.0F);
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
