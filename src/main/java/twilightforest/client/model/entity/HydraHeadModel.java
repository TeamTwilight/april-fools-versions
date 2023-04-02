package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.HydraHead;
import twilightforest.entity.boss.HydraPart;

public class HydraHeadModel extends ListModel<HydraHead> {

	public final ModelPart head;
	public ModelPart mouth;

	public HydraHeadModel(ModelPart root) {
		this.head = root.getChild("head");
		this.head.offsetScale(new Vector3f(4.5F, 4.5F, 4.5F));
		this.mouth = head.getChild("mouth");
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition partRoot = mesh.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 244)
						.addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F)
						.texOffs(20, 244)
						.addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(0.0F, 3.0F, 0.0F));

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

		return LayerDefinition.create(mesh, 512, 256);
	}

	@Override
	public void setupAnim(HydraHead entity, float v, float v1, float v2, float v3, float v4) { }

	@Override
	public void prepareMobModel(HydraHead entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		head.yRot = getRotationY(entity, partialTicks);
		head.xRot = getRotationX(entity, partialTicks);

		float mouthOpenLast = entity.getMouthOpenLast();
		float mouthOpenReal = entity.getMouthOpen();
		float mouthOpen = Mth.lerp(partialTicks, mouthOpenLast, mouthOpenReal);
		head.xRot -= (float) (mouthOpen * (Math.PI / 12.0));
		mouth.xRot = (float) (mouthOpen * (Math.PI / 3.0));
	}

	public float getRotationY(HydraPart whichHead, float time) {
		float yaw = whichHead.yRotO + (whichHead.getYRot() - whichHead.yRotO) * time;

		return yaw / 57.29578F;
	}

	public float getRotationX(HydraPart whichHead, float time) {
		return (whichHead.xRotO + (whichHead.getXRot() - whichHead.xRotO) * time) / 57.29578F;
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(head);
	}
}
