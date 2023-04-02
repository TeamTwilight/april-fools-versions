package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

//This model doesnt require a legacy as the tongue will only show up in newer versions
public class NagaTrophyModel extends GenericTrophyModel {
	public final ModelPart head;

	public NagaTrophyModel(ModelPart part) {
		this.head = part.getChild("head");
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partRoot = meshdefinition.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F)
						.texOffs(20, 0)
						.addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		head.addOrReplaceChild("mouth", CubeListBuilder.create()
						.texOffs(26, 5)
						.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 0.2181661564992912F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = z * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha); 
	}
}
