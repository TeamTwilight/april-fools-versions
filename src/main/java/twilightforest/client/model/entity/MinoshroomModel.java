package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.boss.Minoshroom;

/**
 * ModelMinoshroom - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class MinoshroomModel extends HumanoidModel<Minoshroom> {
    public final ModelPart cowTorso;
    public ModelPart rightFrontLeg;
    public ModelPart leftFrontLeg;
    public ModelPart rightBackLeg;
    public ModelPart leftBackLeg;

    public MinoshroomModel(ModelPart root) {
        super(root);
        this.cowTorso = root.getChild("cow_torso");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightBackLeg = root.getChild("right_back_leg");
        this.leftBackLeg = root.getChild("left_back_leg");
    }

    public static LayerDefinition create() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partRoot = mesh.getRoot();

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

				PartPose.offset(0.0F, -6.0F, -7.0F));

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

        partRoot.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 29)
                .addBox(-5.0F, -3.0F, 0.0F, 10.0F, 12.0F, 5.0F),
                PartPose.offset(0.0F, -6.0F, -9.0F));

        partRoot.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(46, 15)
                        .addBox(0.0F, -1.0F, -2.0F, 4.0F, 14.0F, 5.0F),
                PartPose.offset(5.0F, -8.0F, -7.0F));

        partRoot.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(28, 15)
                        .addBox(-4.0F, -1.0F, -2.0F, 4.0F, 14.0F, 5.0F),
                PartPose.offset(-5.0F, -8.0F, -7.0F));

        partRoot.addOrReplaceChild("cow_torso", CubeListBuilder.create()
                        .texOffs(20, 36)
                        .addBox(-6.0F, -14.0F, -2.0F, 12.0F, 18.0F, 10.0F)
                        .texOffs(0, 20)
                        .addBox(-2.0F, -2.0F, -3.0F, 4.0F, 6.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 10.0F, 6.0F, 1.5707963267948966F, 0.0F, 0.0F));

        partRoot.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-4.0F, 12.0F, -6.0F));

        partRoot.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(4.0F, 12.0F, -6.0F));

        partRoot.addOrReplaceChild("right_back_leg", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-4.0F, 12.0F, 7.0F));

        partRoot.addOrReplaceChild("left_back_leg", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(4.0F, 12.0F, 7.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.cowTorso, this.leftBackLeg, this.rightBackLeg, this.leftFrontLeg, this.rightFrontLeg);
    }

    @Override
    public void setupAnim(Minoshroom entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // copied from HumanoidModel
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.head.xRot = headPitch / (180F / (float) Math.PI);
        this.hat.copyFrom(this.head);

		this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;
		boolean rightHanded = entity.getMainArm() == HumanoidArm.RIGHT;
		if (entity.isUsingItem()) {
			boolean useHand = entity.getUsedItemHand() == InteractionHand.MAIN_HAND;
			if (useHand == rightHanded) {
				this.poseRightArm(entity);
			} else {
				this.poseLeftArm(entity);
			}
		} else {
			boolean bothHands = rightHanded ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
			if (rightHanded != bothHands) {
				this.poseLeftArm(entity);
				this.poseRightArm(entity);
			} else {
				this.poseRightArm(entity);
				this.poseLeftArm(entity);
			}
		}

		this.setupAttackAnimation(entity, ageInTicks);
		AnimationUtils.bobArms(this.leftArm, this.rightArm, ageInTicks);

        // copied from QuadrepedModel
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        float f = ageInTicks - entity.tickCount;
        float f1 = entity.getChargeAnimationScale(f);
        f1 = f1 * f1;

		this.leftFrontLeg.y = 12.0F + (-7F * f1);
		this.leftFrontLeg.z = -4.0F + (-1F * f1);
		this.rightFrontLeg.y = this.leftFrontLeg.y;
		this.rightFrontLeg.z = this.leftFrontLeg.z;
		this.body.y = -6F - f1;
		this.body.z = -9F + f1;
		this.rightArm.y = -8F - f1;
		this.rightArm.z = -7F + f1;
		this.leftArm.y = rightArm.y;
		this.leftArm.z = rightArm.z;

		if (f1 > 0) {
			if (entity.getMainArm() == HumanoidArm.RIGHT) {
				this.rightArm.xRot = f1 * -1.8F;
				this.leftArm.xRot = 0.0F;
				this.rightArm.zRot = -0.2F;
			} else {
				this.rightArm.xRot = 0.0F;
				this.leftArm.xRot = f1 * -1.8F;
				this.leftArm.zRot = 0.2F;
			}
			this.cowTorso.xRot = ((float) Math.PI / 2F) - f1 * (float) Math.PI * 0.2F;
			this.leftFrontLeg.xRot -= f1 * (float) Math.PI * 0.3F;
			this.rightFrontLeg.xRot -= f1 * (float) Math.PI * 0.3F;
		}
    }
}
