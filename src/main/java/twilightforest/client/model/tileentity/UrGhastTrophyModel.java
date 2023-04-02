package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import twilightforest.TwilightForestMod;

public class UrGhastTrophyModel extends GenericTrophyModel {
	protected final static int tentacleCount = 9;
	public final ModelPart root, head, hat, mouth;
	private final ModelPart[][] tentacles = new ModelPart[9][3];
	
	public UrGhastTrophyModel(ModelPart root) {
		this.root = root;
		this.head = this.root.getChild("head");
		this.head.offsetScale(new Vector3f(1F, 1F, 1F));
		this.hat = this.root.getChild("hat");
		this.mouth = this.head.getChild("mouth");

		for (int i = 0; i < this.tentacles.length; i++) {
			this.tentacles[i][0] = this.root.getChild("tentacle_" + i + "");
			this.tentacles[i][1] = this.tentacles[i][0].getChild("tentacle_" + i + "_extension");
			this.tentacles[i][2] = this.tentacles[i][1].getChild("tentacle_" + i + "_tip");
		}
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partRoot = meshDefinition.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F)
						.texOffs(20, 16)
						.addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(0.0F, 12.0F, 0.0F));

		partRoot.addOrReplaceChild("hat", CubeListBuilder.create(),
				PartPose.ZERO);

		head.addOrReplaceChild("mouth", CubeListBuilder.create()
						.texOffs(26, 21)
						.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 0.2181661564992912F, 0.0F, 0.0F));

		head.addOrReplaceChild("right_ear", CubeListBuilder.create()
						.texOffs(32, 16)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(-3.0F, -4.0F, 0.0F, 0.0F, 0.0F, -1.3089969389957472F));

		head.addOrReplaceChild("left_ear", CubeListBuilder.create()
						.texOffs(42, 16)
						.addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(3.0F, -4.0F, 0.0F, 0.0F, 0.0F, 1.3089969389957472F));

		for (int i = 0; i < tentacleCount; ++i) makeTentacle(partRoot, "tentacle_" + i, i);

		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	private static void makeTentacle(PartDefinition parent, String name, int iteration) {
		var tentacleBase = parent.addOrReplaceChild(name, CubeListBuilder.create()
						.addBox(-1.5F, 0.0F, -1.5F, 3F, 5F, 3F),
				switch (iteration) {
					case 0 -> PartPose.offset(1.8F, 7, 1.8F);
					case 1 -> PartPose.offset(-1.8F, 7, 1.8F);
					case 2 -> PartPose.offset(0F, 7, 0F);
					case 3 -> PartPose.offset(2.2F, 7, -1.8F);
					case 4 -> PartPose.offset(-2.2F, 7, -1.8F);
					case 5 -> PartPose.offsetAndRotation(-3F, 6.5F, -0.4F, 0F, 0F, Mth.PI / 4.0F);
					case 6 -> PartPose.offsetAndRotation(-3F, 5.5F, 1.2F, 0F, 0F, Mth.PI / 3.0F);
					case 7 -> PartPose.offsetAndRotation(3F, 6.5F, -0.4F, 0F, 0F, -Mth.PI / 4.0F);
					case 8 -> PartPose.offsetAndRotation(3F, 5.5F, 1.2F, 0F, 0F, -Mth.PI / 3.0F);
					default -> {
						TwilightForestMod.LOGGER.warn("Out of bounds with Ur-Ghast Trophy limb creation: Iteration " + iteration);
						yield PartPose.ZERO;
					}
				});

		var tentacleExtension = tentacleBase.addOrReplaceChild(name + "_extension", CubeListBuilder.create()
						.texOffs(0, 3)
						.addBox(-1.5F, -1.5F, -1.5F, 3F, 6F, 3F),
				PartPose.offset(0, 6.66F, 0));

		tentacleExtension.addOrReplaceChild(name + "_tip", CubeListBuilder.create()
						.texOffs(0, 9)
						.addBox(-1.5F, 1F, -1.5F, 3F, 4, 3F),
				PartPose.offset(0, 4, 0));

	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		this.root.yRot = y * ((float) Math.PI / 180F);
		this.root.xRot = z * ((float) Math.PI / 180F);
		for (int i = 0; i < this.tentacles.length; ++i) {

			float wiggle = Math.min(x, 0.6F);

			float time = ((x * .35F) + (i * 9)) / 2.0F;

			this.tentacles[i][1].xRot = (Mth.cos(time * 0.6662F) - (float) Math.PI / 3.0F) * wiggle;
			this.tentacles[i][2].xRot = Mth.cos(time * 0.7774F) * 1.2F * wiggle;

			this.tentacles[i][1].xRot = 0.2F + Mth.cos(time * 0.3335F) * 0.15F;
			this.tentacles[i][2].xRot = 0.1F + Mth.cos(time * 0.4445F) * 0.20F;

			float yTwist = 0.4F;

			this.tentacles[i][0].xRot = 0.2F * Mth.sin(time * 0.3F + i) + 0.4F;
			this.tentacles[i][0].yRot = yTwist * Mth.sin(time * 0.3F);
		}
	}

	public void setTranslate(PoseStack matrix, float x, float y, float z) {
		matrix.translate(x, y, z);
	}

	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}
