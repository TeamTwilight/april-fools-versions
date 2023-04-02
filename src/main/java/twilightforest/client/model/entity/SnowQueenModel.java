package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.boss.SnowQueen;

/**
 * ModelSnowQueen - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SnowQueenModel extends HumanoidModel<SnowQueen> {
    public SnowQueenModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition create() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partRoot = mesh.getRoot();

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

        partRoot.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F)
                        .texOffs(32, 45)
                        .addBox(-4.5F, 10.0F, -2.5F, 9.0F, 14.0F, 5.0F),
                PartPose.ZERO);

        /*partRoot.addOrReplaceChild("head", CubeListBuilder.create()
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO);*/

        partRoot.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(14, 32)
                        .addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));

        partRoot.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(16, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(1.9F, 12.0F, 0.0F));

        partRoot.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-1.9F, 12.0F, 0.0F));

        partRoot.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        var hat = partRoot.addOrReplaceChild("hat", CubeListBuilder.create()
                        .addBox(0, 0, 0, 0, 0, 0),
                PartPose.ZERO);

        hat.addOrReplaceChild("crown_front", CubeListBuilder.create()
                        .texOffs(46, 12)
                        .addBox(-4.5F, -4.0F, 0.0F, 9.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -4.0F, -3F, 0.39269908169872414F, 0.0F, 0.0F));

        hat.addOrReplaceChild("crown_right", CubeListBuilder.create()
                        .texOffs(27, 16)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(-3.5F, -4.0F, 0.0F, 0.39269908169872414F, 1.5707963267948966F, 0.0F));

        hat.addOrReplaceChild("crown_left", CubeListBuilder.create()
                        .texOffs(27, 12)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(3.5F, -4.0F, 0.0F, -0.39269908169872414F, 1.5707963267948966F, 0.0F));

        hat.addOrReplaceChild("crown_back", CubeListBuilder.create()
                .texOffs(46, 16)
                .addBox(-4.5F, -4.0F, 0.0F, 9.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -4.0F, 3F, -0.39269908169872414F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(SnowQueen entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // in beam phase, arms forwards
        if (entity.getCurrentPhase() == SnowQueen.Phase.BEAM) {
            if (entity.isBreathing()) {
                float f6 = Mth.sin(this.attackTime * Mth.PI);
                float f7 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * Mth.PI);
                this.rightArm.zRot = 0.0F;
                this.leftArm.zRot = 0.0F;
                this.rightArm.yRot = -(0.1F - f6 * 0.6F);
                this.leftArm.yRot = 0.1F - f6 * 0.6F;
                this.rightArm.xRot = -Mth.HALF_PI;
                this.leftArm.xRot = -Mth.HALF_PI;
                this.rightArm.xRot -= f6 * 1.2F - f7 * 0.4F;
                this.leftArm.xRot -= f6 * 1.2F - f7 * 0.4F;
                this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
                this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
            } else {
                // arms up
                this.rightArm.xRot += Mth.PI;
                this.leftArm.xRot += Mth.PI;
            }
        }
    }
}
