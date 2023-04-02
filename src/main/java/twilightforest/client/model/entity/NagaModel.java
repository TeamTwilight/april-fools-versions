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
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelNagaHead - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class NagaModel<T extends Entity> extends ListModel<T> {
    public final ModelPart root;
    public final ModelPart head;
    public final ModelPart mouth;
    public ModelPart body;

    public NagaModel(ModelPart root) {
        this.root = root;

        this.head = root.getChild("head");
        this.head.offsetScale(new Vector3f(3F, 3F, 3F));
        this.mouth = this.head.getChild("mouth");
        this.body = root.getChild("body");
        this.body.offsetScale(new Vector3f(3F, 3F, 3F));
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

        partRoot.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-3.5F, -6.0F, -3.5F, 7.0F, 6.0F, 7.0F),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.head, this.body);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
