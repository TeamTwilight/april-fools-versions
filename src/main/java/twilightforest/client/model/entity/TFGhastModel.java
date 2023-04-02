package twilightforest.client.model.entity;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.monster.CarminiteGhastguard;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class TFGhastModel<T extends CarminiteGhastguard> extends HierarchicalModel<T> {
	protected final static int tentacleCount = 9;
	public final ModelPart root, head, hat, mouth;
	public final ModelPart[] tentacles = new ModelPart[tentacleCount];

	public TFGhastModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.head.offsetScale(new Vector3f(1F, 1F, 1F));
		this.hat = root.getChild("hat");
		this.mouth = this.head.getChild("mouth");

		for (int i = 0; i < this.tentacles.length; i++) {
			this.tentacles[i] = this.head.getChild("tentacle_" + i);
			this.tentacles[i].offsetScale(new Vector3f(-0.6F, -0.6F, -0.6F));
		}
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition partRoot = mesh.getRoot();

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


		Random rand = new Random(1660L);

		for (int i = 0; i < TFGhastModel.tentacleCount; ++i) {
			makeTentacle(head, "tentacle_" + i, rand, i);
		}

		return LayerDefinition.create(mesh, 64, 32);
	}

	private static PartDefinition makeTentacle(PartDefinition parent, String name, Random random, int i) {
		final int length = random.nextInt(7) + 8;

		// Please ensure the model is working accurately before we port
		float xPoint = ((i % 3 - i / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 1.5F;
		float zPoint = (i / 3 / 2.0F * 2.0F - 1.0F) * 1.5F;

		return parent.addOrReplaceChild(name, CubeListBuilder.create()
						.addBox(-1.0F, -6.0F, -1.0F, 2, length, 2),
				PartPose.offset(xPoint, 1, zPoint));
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.root.xRot = headPitch / (180F / (float) Math.PI);

		for (int i = 0; i < this.tentacles.length; ++i) {
			this.tentacles[i].xRot = 0.2F * Mth.sin(ageInTicks * 0.3F + i) + 0.4F;
		}

		if (entity.isCharging()) {
			// open jaw
			this.mouth.xRot = 0.6F;
		} else {
			this.mouth.xRot = 0.20944F;
		}
	}
}
