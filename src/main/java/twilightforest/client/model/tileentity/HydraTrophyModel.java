package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HydraTrophyModel extends GenericTrophyModel {

	public final ModelPart head;
	public ModelPart plate;
	public ModelPart mouth;

	public HydraTrophyModel(ModelPart part) {
		this.head = part.getChild("head");
		this.plate = this.head.getChild("plate");
		this.mouth = this.head.getChild("mouth");
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partRoot = meshdefinition.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 244)
						.addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F)
						.texOffs(20, 244)
						.addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F),
				PartPose.ZERO);

		head.addOrReplaceChild("right_ear", CubeListBuilder.create()
						.texOffs(32, 244)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(-3.0F, -4.0F, -1.0F, 0.0F, 0.0F, -1.3089969389957472F));

		head.addOrReplaceChild("left_ear", CubeListBuilder.create()
						.texOffs(42, 244)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(3.0F, -4.0F, -1.0F, 0.0F, 0.0F, 1.3089969389957472F));

		head.addOrReplaceChild("mouth", CubeListBuilder.create()
						.texOffs(26, 249)
						.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 0.2181661564992912F, 0.0F, 0.0F));

		head.addOrReplaceChild("plate", CubeListBuilder.create()
						.texOffs(0, 234)
						.addBox(-4.5F, -5.0F, 0.0F, 9.0F, 9.0F, 1.0F)
						.texOffs(20, 234)
						.addBox(-0.5F, -3.0F, -1.0F, 1.0F, 7.0F, 1.0F),
				PartPose.offsetAndRotation(0.0F, -6.0F, 2.0F, -0.7853981633974483F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 256);
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = z * ((float) Math.PI / 180F);
		this.head.y = 16.0F;
	}
	
	public void openMouthForTrophy(float mouthOpen) {
		this.head.yRot = 0;
		this.head.xRot = 0;

		this.head.xRot -= (float) (mouthOpen * (Math.PI / 12.0));
		this.mouth.xRot = (float) (mouthOpen * (Math.PI / 3.0));
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
