package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FinalBossTrophyModel extends GenericTrophyModel {
	private final ModelPart head;
	private final ModelPart jaw;

	public FinalBossTrophyModel(ModelPart p_171097_) {
		this.head = p_171097_.getChild("head");
		this.jaw = this.head.getChild("jaw");
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
						.addBox("upper_lip", -6.0F, -1.0F, -23.0F, 12, 5, 16, 176, 44)
						.addBox("upper_head", -8.0F, -8.0F, -8.0F, 16, 16, 16, 112, 30).mirror(true)
						.addBox("scale", -5.0F, -12.0F, -3.0F, 2, 4, 6, 0, 0)
						.addBox("nostril", -5.0F, -3.0F, -20.0F, 2, 2, 4, 112, 0).mirror(false)
						.addBox("scale", 3.0F, -12.0F, -2.0F, 2, 4, 6, 0, 0)
						.addBox("nostril", 3.0F, -3.0F, -20.0F, 2, 2, 4, 112, 0),
				PartPose.ZERO);

		partdefinition1.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(176, 65)
						.addBox("jaw", -6.0F, 0.0F, -14.0F, 12.0F, 4.0F, 16.0F),
				PartPose.offset(0.0F, 4.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = z * ((float) Math.PI / 180F);
	}

	@Override
	public void openMouthForTrophy(float mouthOpen) {
		this.head.yRot = 0;
		this.head.xRot = 0;

		this.head.xRot -= (float) (mouthOpen * (Math.PI / 12.0));
		this.jaw.xRot = (float) (mouthOpen * (Math.PI / 3.0));
	}

	public void renderToBuffer(PoseStack pMatrixStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
		this.head.render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
	}
}
