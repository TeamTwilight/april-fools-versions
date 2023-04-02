package twilightforest.client.model.entity.finalcastle;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.finalcastle.CastleGuardianEntity;

/**
 * guardian - Drullkus
 * Created using Tabula 7.0.0
 */

// TODO Optimize, holy fudge
@SuppressWarnings({"unused", "WeakerAccess"})
public class CastleGuardianModel extends ListModel<CastleGuardianEntity> {
	public ModelPart MainBody;
	public ModelPart MidBody;
	public ModelPart Head;
	public ModelPart PlateGroup1;
	public ModelPart PlateGroup2;
	public ModelPart PlateGroup3;
	public ModelPart PlateGroup4;
	public ModelPart PlateGroup5;
	public ModelPart PlateGroup6;
	public ModelPart Plating;
	public ModelPart SpikeWrapper;
	public ModelPart InnerSupport;
	public ModelPart Support;
	public ModelPart Spike;
	public ModelPart SpikeCap;
	public ModelPart Plating_1;
	public ModelPart SpikeWrapper_1;
	public ModelPart InnerSupport_1;
	public ModelPart Support_1;
	public ModelPart Spike_1;
	public ModelPart SpikeCap_1;
	public ModelPart Plating_2;
	public ModelPart SpikeWrapper_2;
	public ModelPart InnerSupport_2;
	public ModelPart Support_2;
	public ModelPart Spike_2;
	public ModelPart SpikeCap_2;
	public ModelPart Plating_3;
	public ModelPart SpikeWrapper_3;
	public ModelPart InnerSupport_3;
	public ModelPart Support_3;
	public ModelPart Spike_3;
	public ModelPart SpikeCap_3;
	public ModelPart Plating_4;
	public ModelPart SpikeWrapper_4;
	public ModelPart InnerSupport_4;
	public ModelPart Support_4;
	public ModelPart Spike_4;
	public ModelPart SpikeCap_4;
	public ModelPart Plating_5;
	public ModelPart SpikeWrapper_5;
	public ModelPart InnerSupport_5;
	public ModelPart Support_5;
	public ModelPart Spike_5;
	public ModelPart SpikeCap_5;
	public ModelPart RunedSiding1;
	public ModelPart Bridge1;
	public ModelPart RunedSiding2;
	public ModelPart Bridge2;
	public ModelPart RunedSiding3;
	public ModelPart Bridge3;
	public ModelPart RunedSiding4;
	public ModelPart Bridge4;
	public ModelPart Cover;
	public ModelPart HeadGroup2;
	public ModelPart HeadGroup3;
	public ModelPart HeadGroup4;
	public ModelPart HeadGroup5;
	public ModelPart HeadGroup6;
	public ModelPart EyeBack;
	public ModelPart HeadGroup1;
	public ModelPart HeadPlate;
	public ModelPart HeadSpikeWrapper;
	public ModelPart HeadExtended;
	public ModelPart HeadSpike;
	public ModelPart HeadPlate_1;
	public ModelPart HeadSpikeWrapper_1;
	public ModelPart HeadExtended_1;
	public ModelPart HeadSpike_1;
	public ModelPart HeadPlate_2;
	public ModelPart HeadSpikeWrapper_2;
	public ModelPart HeadExtended_2;
	public ModelPart HeadSpike_2;
	public ModelPart HeadPlate_3;
	public ModelPart HeadSpikeWrapper_3;
	public ModelPart HeadExtended_3;
	public ModelPart HeadSpike_3;
	public ModelPart HeadPlate_4;
	public ModelPart HeadSpikeWrapper_4;
	public ModelPart HeadExtended_4;
	public ModelPart HeadSpike_4;
	public ModelPart Eye;
	public ModelPart HeadPlate_5;
	public ModelPart HeadSpikeWrapper_5;
	public ModelPart HeadExtended_5;
	public ModelPart EyeFrameR;
	public ModelPart EyeFrameL;
	public ModelPart EyeFrameLB;
	public ModelPart EyeFrameRT;
	public ModelPart EyeFrameLT;
	public ModelPart EyeFrameRB;
	public ModelPart HeadSpike_5;

	public CastleGuardianModel() {
		this.texWidth = 128;
		this.texHeight = 64;
		this.SpikeWrapper_5 = new ModelPart(this, 0, 0);
		this.SpikeWrapper_5.setPos(0.0F, 0.0F, 0.0F);
		this.SpikeWrapper_5.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(SpikeWrapper_5, 0.0F, 0.5235987755982988F, 0.0F);
		this.EyeFrameL = new ModelPart(this, 0, 0);
		this.EyeFrameL.setPos(-4.0F, 0.0F, -9.2F);
		this.EyeFrameL.addBox(0.0F, -11.0F, 0.0F, 1, 5, 2, 0.0F);
		this.Plating = new ModelPart(this, 0, 32);
		this.Plating.setPos(0.0F, 0.0F, 0.0F);
		this.Plating.addBox(29.0F, -8.0F, -8.0F, 1, 16, 16, 0.0F);
		this.setRotateAngle(Plating, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
		this.SpikeCap = new ModelPart(this, 7, 25);
		this.SpikeCap.setPos(8.0F, -1.0F, 8.0F);
		this.SpikeCap.addBox(0.0F, 0.0F, 0.0F, 6, 11, 6, 0.0F);
		this.Spike_1 = new ModelPart(this, 0, 0);
		this.Spike_1.setPos(0.0F, -20.0F, 0.0F);
		this.Spike_1.addBox(1.0F, 0.0F, 1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(Spike_1, 0.0F, 0.7853981633974483F, -0.6065019150680295F);
		this.HeadExtended_2 = new ModelPart(this, 64, 33);
		this.HeadExtended_2.mirror = true;
		this.HeadExtended_2.setPos(0.0F, -18.0F, -5.0F);
		this.HeadExtended_2.addBox(-5.0F, 1.0F, 0.0F, 10, 1, 9, 0.0F);
		this.setRotateAngle(HeadExtended_2, -0.2181661564992912F, 0.0F, 0.0F);
		this.SpikeWrapper_1 = new ModelPart(this, 0, 0);
		this.SpikeWrapper_1.setPos(0.0F, 0.0F, 0.0F);
		this.SpikeWrapper_1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(SpikeWrapper_1, 0.0F, 0.5235987755982988F, 0.0F);
		this.Plating_1 = new ModelPart(this, 0, 32);
		this.Plating_1.setPos(0.0F, 0.0F, 0.0F);
		this.Plating_1.addBox(29.0F, -8.0F, -8.0F, 1, 16, 16, 0.0F);
		this.setRotateAngle(Plating_1, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
		this.HeadPlate_5 = new ModelPart(this, 86, 0);
		this.HeadPlate_5.setPos(0.0F, 0.0F, 0.0F);
		this.HeadPlate_5.addBox(-5.0F, -17.0F, -9.2F, 10, 17, 4, 0.0F);
		this.setRotateAngle(HeadPlate_5, 0.19634954084936207F, 0.0F, 0.0F);
		this.HeadGroup1 = new ModelPart(this, 0, 0);
		this.HeadGroup1.setPos(0.0F, -2.0F, 0.0F);
		this.HeadGroup1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.Support = new ModelPart(this, 31, 24);
		this.Support.setPos(0.0F, -7.0F, 0.0F);
		this.Support.addBox(23.5F, 0.0F, 0.0F, 6, 7, 7, 0.0F);
		this.Plating_5 = new ModelPart(this, 0, 32);
		this.Plating_5.setPos(0.0F, 0.0F, 0.0F);
		this.Plating_5.addBox(29.0F, -8.0F, -8.0F, 1, 16, 16, 0.0F);
		this.setRotateAngle(Plating_5, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
		this.Spike_4 = new ModelPart(this, 0, 0);
		this.Spike_4.setPos(0.0F, -20.0F, 0.0F);
		this.Spike_4.addBox(1.0F, 0.0F, 1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(Spike_4, 0.0F, 0.7853981633974483F, -0.6065019150680295F);
		this.MainBody = new ModelPart(this, 6, 49);
		this.MainBody.setPos(0.0F, 0.0F, 0.0F);
		this.MainBody.addBox(-7.0F, 18.8F, -7.5F, 14, 2, 14, 0.0F);
		this.setRotateAngle(MainBody, 0.0F, 1.5707963267948966F, 0.0F);
		this.HeadSpikeWrapper_3 = new ModelPart(this, 0, 0);
		this.HeadSpikeWrapper_3.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpikeWrapper_3.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadSpikeWrapper_3, 0.0F, 2.0943951023931953F, 0.0F);
		this.SpikeCap_3 = new ModelPart(this, 7, 25);
		this.SpikeCap_3.setPos(8.0F, -1.0F, 8.0F);
		this.SpikeCap_3.addBox(0.0F, 0.0F, 0.0F, 6, 11, 6, 0.0F);
		this.SpikeWrapper = new ModelPart(this, 0, 0);
		this.SpikeWrapper.setPos(0.0F, 0.0F, 0.0F);
		this.SpikeWrapper.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(SpikeWrapper, 0.0F, 0.5235987755982988F, 0.0F);
		this.Bridge3 = new ModelPart(this, 36, 0);
		this.Bridge3.setPos(0.0F, 0.0F, 0.0F);
		this.Bridge3.addBox(-4.0F, -3.0F, 6.0F, 8, 9, 3, 0.0F);
		this.setRotateAngle(Bridge3, -0.28361600344907856F, -2.356194490192345F, 0.0F);
		this.HeadSpikeWrapper_4 = new ModelPart(this, 0, 0);
		this.HeadSpikeWrapper_4.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpikeWrapper_4.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadSpikeWrapper_4, 0.0F, 2.0943951023931953F, 0.0F);
		this.HeadSpikeWrapper_5 = new ModelPart(this, 0, 0);
		this.HeadSpikeWrapper_5.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpikeWrapper_5.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadSpikeWrapper_5, 0.0F, 2.0943951023931953F, 0.0F);
		this.Support_3 = new ModelPart(this, 31, 24);
		this.Support_3.setPos(0.0F, -7.0F, 0.0F);
		this.Support_3.addBox(23.5F, 0.0F, 0.0F, 6, 7, 7, 0.0F);
		this.InnerSupport_3 = new ModelPart(this, 23, 38);
		this.InnerSupport_3.setPos(0.0F, -10.0F, 0.0F);
		this.InnerSupport_3.addBox(7.0F, -1.4F, -4.0F, 11, 2, 8, 0.0F);
		this.setRotateAngle(InnerSupport_3, 0.0F, 0.0F, -0.4553564018453205F);
		this.SpikeWrapper_4 = new ModelPart(this, 0, 0);
		this.SpikeWrapper_4.setPos(0.0F, 0.0F, 0.0F);
		this.SpikeWrapper_4.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(SpikeWrapper_4, 0.0F, 0.5235987755982988F, 0.0F);
		this.HeadSpikeWrapper_2 = new ModelPart(this, 0, 0);
		this.HeadSpikeWrapper_2.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpikeWrapper_2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadSpikeWrapper_2, 0.0F, 2.0943951023931953F, 0.0F);
		this.HeadSpike_4 = new ModelPart(this, 57, 21);
		this.HeadSpike_4.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpike_4.addBox(3.7F, -17.9F, 3.7F, 4, 17, 4, 0.0F);
		this.setRotateAngle(HeadSpike_4, 0.0F, 0.7853981633974483F, 0.26703537555513246F);
		this.HeadPlate_2 = new ModelPart(this, 58, 0);
		this.HeadPlate_2.setPos(0.0F, 0.0F, 0.0F);
		this.HeadPlate_2.addBox(-5.0F, -17.0F, -9.2F, 10, 17, 4, 0.0F);
		this.setRotateAngle(HeadPlate_2, 0.19634954084936207F, 0.0F, 0.0F);
		this.EyeFrameR = new ModelPart(this, 4, 0);
		this.EyeFrameR.setPos(3.0F, 0.0F, -9.2F);
		this.EyeFrameR.addBox(0.0F, -11.0F, 0.0F, 1, 5, 2, 0.0F);
		this.EyeFrameLT = new ModelPart(this, 0, 0);
		this.EyeFrameLT.setPos(-4.0F, -10.0F, -9.2F);
		this.EyeFrameLT.addBox(0.0F, -5.0F, 0.0F, 1, 5, 2, 0.0F);
		this.setRotateAngle(EyeFrameLT, 0.0F, 0.0F, 0.7853981633974483F);
		this.SpikeWrapper_3 = new ModelPart(this, 0, 0);
		this.SpikeWrapper_3.setPos(0.0F, 0.0F, 0.0F);
		this.SpikeWrapper_3.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(SpikeWrapper_3, 0.0F, 0.5235987755982988F, 0.0F);
		this.Eye = new ModelPart(this, 73, 21);
		this.Eye.setPos(0.0F, 4.8F, 0.5F);
		this.Eye.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
		this.setRotateAngle(Eye, -0.7853981633974483F, -0.6981317007977318F, 1.5707963267948966F);
		this.HeadSpike_5 = new ModelPart(this, 57, 21);
		this.HeadSpike_5.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpike_5.addBox(3.7F, -17.9F, 3.7F, 4, 17, 4, 0.0F);
		this.setRotateAngle(HeadSpike_5, 0.0F, 0.7853981633974483F, 0.26703537555513246F);
		this.Support_5 = new ModelPart(this, 31, 24);
		this.Support_5.setPos(0.0F, -7.0F, 0.0F);
		this.Support_5.addBox(23.5F, 0.0F, 0.0F, 6, 7, 7, 0.0F);
		this.InnerSupport_2 = new ModelPart(this, 23, 38);
		this.InnerSupport_2.setPos(0.0F, -10.0F, 0.0F);
		this.InnerSupport_2.addBox(7.0F, -1.4F, -4.0F, 11, 2, 8, 0.0F);
		this.setRotateAngle(InnerSupport_2, 0.0F, 0.0F, -0.4553564018453205F);
		this.HeadGroup3 = new ModelPart(this, 0, 0);
		this.HeadGroup3.setPos(0.0F, -2.0F, 0.0F);
		this.HeadGroup3.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadGroup3, 0.0F, 2.0943951023931953F, 0.0F);
		this.InnerSupport_5 = new ModelPart(this, 23, 38);
		this.InnerSupport_5.setPos(0.0F, -10.0F, 0.0F);
		this.InnerSupport_5.addBox(7.0F, -1.4F, -4.0F, 11, 2, 8, 0.0F);
		this.setRotateAngle(InnerSupport_5, 0.0F, 0.0F, -0.4553564018453205F);
		this.EyeBack = new ModelPart(this, 114, 0);
		this.EyeBack.setPos(0.0F, -13.4F, -9.8F);
		this.EyeBack.addBox(-3.0F, 0.0F, 0.0F, 6, 9, 1, 0.0F);
		this.HeadExtended = new ModelPart(this, 64, 33);
		this.HeadExtended.mirror = true;
		this.HeadExtended.setPos(0.0F, -18.0F, -5.0F);
		this.HeadExtended.addBox(-5.0F, 1.0F, 0.0F, 10, 1, 9, 0.0F);
		this.setRotateAngle(HeadExtended, -0.2181661564992912F, 0.0F, 0.0F);
		this.Support_1 = new ModelPart(this, 31, 24);
		this.Support_1.setPos(0.0F, -7.0F, 0.0F);
		this.Support_1.addBox(23.5F, 0.0F, 0.0F, 6, 7, 7, 0.0F);
		this.Plating_2 = new ModelPart(this, 0, 32);
		this.Plating_2.setPos(0.0F, 0.0F, 0.0F);
		this.Plating_2.addBox(29.0F, -8.0F, -8.0F, 1, 16, 16, 0.0F);
		this.setRotateAngle(Plating_2, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
		this.HeadPlate_3 = new ModelPart(this, 58, 0);
		this.HeadPlate_3.setPos(0.0F, 0.0F, 0.0F);
		this.HeadPlate_3.addBox(-5.0F, -17.0F, -9.2F, 10, 17, 4, 0.0F);
		this.setRotateAngle(HeadPlate_3, 0.19634954084936207F, 0.0F, 0.0F);
		this.HeadGroup6 = new ModelPart(this, 0, 0);
		this.HeadGroup6.setPos(0.0F, -2.0F, 0.0F);
		this.HeadGroup6.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadGroup6, 0.0F, 5.235987755982989F, 0.0F);
		this.MidBody = new ModelPart(this, 0, 0);
		this.MidBody.setPos(0.0F, 3.0F, 0.0F);
		this.MidBody.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.PlateGroup1 = new ModelPart(this, 0, 0);
		this.PlateGroup1.setPos(0.0F, 35.0F, 0.0F);
		this.PlateGroup1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.Bridge4 = new ModelPart(this, 36, 0);
		this.Bridge4.setPos(0.0F, 0.0F, 0.0F);
		this.Bridge4.addBox(-4.0F, -3.0F, 6.0F, 8, 9, 3, 0.0F);
		this.setRotateAngle(Bridge4, -0.28361600344907856F, -0.7853981633974483F, 0.0F);
		this.HeadSpikeWrapper_1 = new ModelPart(this, 0, 0);
		this.HeadSpikeWrapper_1.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpikeWrapper_1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadSpikeWrapper_1, 0.0F, 2.0943951023931953F, 0.0F);
		this.SpikeCap_2 = new ModelPart(this, 7, 25);
		this.SpikeCap_2.setPos(8.0F, -1.0F, 8.0F);
		this.SpikeCap_2.addBox(0.0F, 0.0F, 0.0F, 6, 11, 6, 0.0F);
		this.HeadSpike_1 = new ModelPart(this, 57, 21);
		this.HeadSpike_1.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpike_1.addBox(3.7F, -17.9F, 3.7F, 4, 17, 4, 0.0F);
		this.setRotateAngle(HeadSpike_1, 0.0F, 0.7853981633974483F, 0.26703537555513246F);
		this.InnerSupport_4 = new ModelPart(this, 23, 38);
		this.InnerSupport_4.setPos(0.0F, -10.0F, 0.0F);
		this.InnerSupport_4.addBox(7.0F, -1.4F, -4.0F, 11, 2, 8, 0.0F);
		this.setRotateAngle(InnerSupport_4, 0.0F, 0.0F, -0.4553564018453205F);
		this.RunedSiding4 = new ModelPart(this, 48, 48);
		this.RunedSiding4.setPos(0.0F, 0.0F, 0.0F);
		this.RunedSiding4.addBox(-4.0F, -4.0F, 6.0F, 8, 10, 4, 0.0F);
		this.setRotateAngle(RunedSiding4, -0.39269908169872414F, -1.5707963267948966F, 0.0F);
		this.SpikeCap_1 = new ModelPart(this, 7, 25);
		this.SpikeCap_1.setPos(8.0F, -1.0F, 8.0F);
		this.SpikeCap_1.addBox(0.0F, 0.0F, 0.0F, 6, 11, 6, 0.0F);
		this.Head = new ModelPart(this, 0, 0);
		this.Head.setPos(0.0F, 2.5F, 0.0F);
		this.Head.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.EyeFrameRT = new ModelPart(this, 6, 7);
		this.EyeFrameRT.setPos(4.0F, -10.0F, -9.2F);
		this.EyeFrameRT.addBox(0.0F, 0.4F, 0.0F, 1, 5, 2, 0.0F);
		this.setRotateAngle(EyeFrameRT, 0.0F, 0.0F, 2.356194490192345F);
		this.RunedSiding1 = new ModelPart(this, 48, 48);
		this.RunedSiding1.setPos(0.0F, 0.0F, 0.0F);
		this.RunedSiding1.addBox(-4.0F, -4.0F, 6.0F, 8, 10, 4, 0.0F);
		this.setRotateAngle(RunedSiding1, -0.39269908169872414F, 0.0F, 0.0F);
		this.PlateGroup3 = new ModelPart(this, 0, 0);
		this.PlateGroup3.setPos(0.0F, 35.0F, 0.0F);
		this.PlateGroup3.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(PlateGroup3, 0.0F, 2.0943951023931953F, 0.0F);
		this.Plating_3 = new ModelPart(this, 0, 32);
		this.Plating_3.setPos(0.0F, 0.0F, 0.0F);
		this.Plating_3.addBox(29.0F, -8.0F, -8.0F, 1, 16, 16, 0.0F);
		this.setRotateAngle(Plating_3, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
		this.Bridge1 = new ModelPart(this, 36, 0);
		this.Bridge1.setPos(0.0F, 0.0F, 0.0F);
		this.Bridge1.addBox(-4.0F, -3.0F, 6.0F, 8, 9, 3, 0.0F);
		this.setRotateAngle(Bridge1, -0.28361600344907856F, 0.7853981633974483F, 0.0F);
		this.EyeFrameRB = new ModelPart(this, 6, 0);
		this.EyeFrameRB.setPos(4.0F, -7.0F, -9.2F);
		this.EyeFrameRB.addBox(0.0F, -5.0F, 0.0F, 1, 5, 2, 0.0F);
		this.setRotateAngle(EyeFrameRB, 0.0F, 0.0F, -2.356194490192345F);
		this.PlateGroup4 = new ModelPart(this, 0, 0);
		this.PlateGroup4.setPos(0.0F, 35.0F, 0.0F);
		this.PlateGroup4.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(PlateGroup4, 0.0F, 3.141592653589793F, 0.0F);
		this.Cover = new ModelPart(this, 0, 0);
		this.Cover.setPos(0.0F, 0.0F, 0.0F);
		this.Cover.addBox(-6.0F, -0.6F, -6.0F, 12, 1, 12, 0.0F);
		this.setRotateAngle(Cover, 0.0F, 0.7853981633974483F, 0.0F);
		this.HeadExtended_1 = new ModelPart(this, 64, 33);
		this.HeadExtended_1.setPos(0.0F, -18.0F, -5.0F);
		this.HeadExtended_1.addBox(-5.0F, 1.0F, 0.0F, 10, 1, 9, 0.0F);
		this.setRotateAngle(HeadExtended_1, -0.2181661564992912F, 0.0F, 0.0F);
		this.Spike = new ModelPart(this, 0, 0);
		this.Spike.setPos(0.0F, -20.0F, 0.0F);
		this.Spike.addBox(1.0F, 0.0F, 1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(Spike, 0.0F, 0.7853981633974483F, -0.6065019150680295F);
		this.HeadExtended_4 = new ModelPart(this, 64, 33);
		this.HeadExtended_4.mirror = true;
		this.HeadExtended_4.setPos(0.0F, -18.0F, -5.0F);
		this.HeadExtended_4.addBox(-5.0F, 1.0F, 0.0F, 10, 1, 9, 0.0F);
		this.setRotateAngle(HeadExtended_4, -0.2181661564992912F, 0.0F, 0.0F);
		this.InnerSupport_1 = new ModelPart(this, 23, 38);
		this.InnerSupport_1.setPos(0.0F, -10.0F, 0.0F);
		this.InnerSupport_1.addBox(7.0F, -1.4F, -4.0F, 11, 2, 8, 0.0F);
		this.setRotateAngle(InnerSupport_1, 0.0F, 0.0F, -0.4553564018453205F);
		this.SpikeCap_5 = new ModelPart(this, 7, 25);
		this.SpikeCap_5.setPos(8.0F, -1.0F, 8.0F);
		this.SpikeCap_5.addBox(0.0F, 0.0F, 0.0F, 6, 11, 6, 0.0F);
		this.HeadExtended_5 = new ModelPart(this, 64, 33);
		this.HeadExtended_5.setPos(0.0F, -18.0F, -5.0F);
		this.HeadExtended_5.addBox(-5.0F, 1.0F, 0.0F, 10, 1, 9, 0.0F);
		this.setRotateAngle(HeadExtended_5, -0.2181661564992912F, 0.0F, 0.0F);
		this.Support_4 = new ModelPart(this, 31, 24);
		this.Support_4.setPos(0.0F, -7.0F, 0.0F);
		this.Support_4.addBox(23.5F, 0.0F, 0.0F, 6, 7, 7, 0.0F);
		this.HeadPlate_1 = new ModelPart(this, 58, 0);
		this.HeadPlate_1.setPos(0.0F, 0.0F, 0.0F);
		this.HeadPlate_1.addBox(-5.0F, -17.0F, -9.2F, 10, 17, 4, 0.0F);
		this.setRotateAngle(HeadPlate_1, 0.19634954084936207F, 0.0F, 0.0F);
		this.HeadGroup2 = new ModelPart(this, 0, 0);
		this.HeadGroup2.setPos(0.0F, -2.0F, 0.0F);
		this.HeadGroup2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadGroup2, 0.0F, 1.0471975511965976F, 0.0F);
		this.PlateGroup2 = new ModelPart(this, 0, 0);
		this.PlateGroup2.setPos(0.0F, 35.0F, 0.0F);
		this.PlateGroup2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(PlateGroup2, 0.0F, 1.0471975511965976F, 0.0F);
		this.SpikeWrapper_2 = new ModelPart(this, 0, 0);
		this.SpikeWrapper_2.setPos(0.0F, 0.0F, 0.0F);
		this.SpikeWrapper_2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(SpikeWrapper_2, 0.0F, 0.5235987755982988F, 0.0F);
		this.InnerSupport = new ModelPart(this, 23, 38);
		this.InnerSupport.setPos(0.0F, -10.0F, 0.0F);
		this.InnerSupport.addBox(7.0F, -1.4F, -4.0F, 11, 2, 8, 0.0F);
		this.setRotateAngle(InnerSupport, 0.0F, 0.0F, -0.4553564018453205F);
		this.Spike_2 = new ModelPart(this, 0, 0);
		this.Spike_2.setPos(0.0F, -20.0F, 0.0F);
		this.Spike_2.addBox(1.0F, 0.0F, 1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(Spike_2, 0.0F, 0.7853981633974483F, -0.6065019150680295F);
		this.RunedSiding3 = new ModelPart(this, 48, 48);
		this.RunedSiding3.setPos(0.0F, 0.0F, 0.0F);
		this.RunedSiding3.addBox(-4.0F, -4.0F, 6.0F, 8, 10, 4, 0.0F);
		this.setRotateAngle(RunedSiding3, -0.39269908169872414F, 3.141592653589793F, 0.0F);
		this.HeadPlate = new ModelPart(this, 58, 0);
		this.HeadPlate.setPos(0.0F, 0.0F, 0.0F);
		this.HeadPlate.addBox(-5.0F, -17.0F, -9.2F, 10, 17, 4, 0.0F);
		this.setRotateAngle(HeadPlate, 0.19634954084936207F, 0.0F, 0.0F);
		this.PlateGroup6 = new ModelPart(this, 0, 0);
		this.PlateGroup6.setPos(0.0F, 35.0F, 0.0F);
		this.PlateGroup6.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(PlateGroup6, 0.0F, 5.235987755982989F, 0.0F);
		this.HeadGroup4 = new ModelPart(this, 0, 0);
		this.HeadGroup4.setPos(0.0F, -2.0F, 0.0F);
		this.HeadGroup4.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadGroup4, 0.0F, 3.141592653589793F, 0.0F);
		this.RunedSiding2 = new ModelPart(this, 48, 48);
		this.RunedSiding2.setPos(0.0F, 0.0F, 0.0F);
		this.RunedSiding2.addBox(-4.0F, -4.0F, 6.0F, 8, 10, 4, 0.0F);
		this.setRotateAngle(RunedSiding2, -0.39269908169872414F, 1.5707963267948966F, 0.0F);
		this.Spike_5 = new ModelPart(this, 0, 0);
		this.Spike_5.setPos(0.0F, -20.0F, 0.0F);
		this.Spike_5.addBox(1.0F, 0.0F, 1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(Spike_5, 0.0F, 0.7853981633974483F, -0.6065019150680295F);
		this.Bridge2 = new ModelPart(this, 36, 0);
		this.Bridge2.setPos(0.0F, 0.0F, 0.0F);
		this.Bridge2.addBox(-4.0F, -3.0F, 6.0F, 8, 9, 3, 0.0F);
		this.setRotateAngle(Bridge2, -0.28361600344907856F, 2.356194490192345F, 0.0F);
		this.Spike_3 = new ModelPart(this, 0, 0);
		this.Spike_3.setPos(0.0F, -20.0F, 0.0F);
		this.Spike_3.addBox(1.0F, 0.0F, 1.0F, 12, 12, 12, 0.0F);
		this.setRotateAngle(Spike_3, 0.0F, 0.7853981633974483F, -0.6065019150680295F);
		this.HeadGroup5 = new ModelPart(this, 0, 0);
		this.HeadGroup5.setPos(0.0F, -2.0F, 0.0F);
		this.HeadGroup5.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadGroup5, 0.0F, 4.1887902047863905F, 0.0F);
		this.SpikeCap_4 = new ModelPart(this, 7, 25);
		this.SpikeCap_4.setPos(8.0F, -1.0F, 8.0F);
		this.SpikeCap_4.addBox(0.0F, 0.0F, 0.0F, 6, 11, 6, 0.0F);
		this.HeadSpikeWrapper = new ModelPart(this, 0, 0);
		this.HeadSpikeWrapper.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpikeWrapper.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(HeadSpikeWrapper, 0.0F, 2.0943951023931953F, 0.0F);
		this.HeadExtended_3 = new ModelPart(this, 64, 33);
		this.HeadExtended_3.setPos(0.0F, -18.0F, -5.0F);
		this.HeadExtended_3.addBox(-5.0F, 1.0F, 0.0F, 10, 1, 9, 0.0F);
		this.setRotateAngle(HeadExtended_3, -0.2181661564992912F, 0.0F, 0.0F);
		this.HeadPlate_4 = new ModelPart(this, 58, 0);
		this.HeadPlate_4.setPos(0.0F, 0.0F, 0.0F);
		this.HeadPlate_4.addBox(-5.0F, -17.0F, -9.2F, 10, 17, 4, 0.0F);
		this.setRotateAngle(HeadPlate_4, 0.19634954084936207F, 0.0F, 0.0F);
		this.PlateGroup5 = new ModelPart(this, 0, 0);
		this.PlateGroup5.setPos(0.0F, 35.0F, 0.0F);
		this.PlateGroup5.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
		this.setRotateAngle(PlateGroup5, 0.0F, 4.1887902047863905F, 0.0F);
		this.HeadSpike = new ModelPart(this, 57, 21);
		this.HeadSpike.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpike.addBox(3.7F, -17.9F, 3.7F, 4, 17, 4, 0.0F);
		this.setRotateAngle(HeadSpike, 0.0F, 0.7853981633974483F, 0.26703537555513246F);
		this.HeadSpike_2 = new ModelPart(this, 57, 21);
		this.HeadSpike_2.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpike_2.addBox(3.7F, -17.9F, 3.7F, 4, 17, 4, 0.0F);
		this.setRotateAngle(HeadSpike_2, 0.0F, 0.7853981633974483F, 0.26703537555513246F);
		this.HeadSpike_3 = new ModelPart(this, 57, 21);
		this.HeadSpike_3.setPos(0.0F, 0.0F, 0.0F);
		this.HeadSpike_3.addBox(3.7F, -17.9F, 3.7F, 4, 17, 4, 0.0F);
		this.setRotateAngle(HeadSpike_3, 0.0F, 0.7853981633974483F, 0.26703537555513246F);
		this.EyeFrameLB = new ModelPart(this, 0, 7);
		this.EyeFrameLB.setPos(-4.0F, -7.0F, -9.2F);
		this.EyeFrameLB.addBox(0.0F, 0.4F, 0.0F, 1, 5, 2, 0.0F);
		this.setRotateAngle(EyeFrameLB, 0.0F, 0.0F, -0.7853981633974483F);
		this.Support_2 = new ModelPart(this, 31, 24);
		this.Support_2.setPos(0.0F, -7.0F, 0.0F);
		this.Support_2.addBox(23.5F, 0.0F, 0.0F, 6, 7, 7, 0.0F);
		this.Plating_4 = new ModelPart(this, 0, 32);
		this.Plating_4.setPos(0.0F, 0.0F, 0.0F);
		this.Plating_4.addBox(29.0F, -8.0F, -8.0F, 1, 16, 16, 0.0F);
		this.setRotateAngle(Plating_4, 0.7853981633974483F, 0.0F, -0.7853981633974483F);
		this.PlateGroup6.addChild(this.SpikeWrapper_5);
		this.HeadPlate_5.addChild(this.EyeFrameL);
		this.PlateGroup1.addChild(this.Plating);
		this.Spike.addChild(this.SpikeCap);
		this.SpikeWrapper_1.addChild(this.Spike_1);
		this.HeadPlate_2.addChild(this.HeadExtended_2);
		this.PlateGroup2.addChild(this.SpikeWrapper_1);
		this.PlateGroup2.addChild(this.Plating_1);
		this.HeadGroup1.addChild(this.HeadPlate_5);
		this.Head.addChild(this.HeadGroup1);
		this.Plating.addChild(this.Support);
		this.PlateGroup6.addChild(this.Plating_5);
		this.SpikeWrapper_4.addChild(this.Spike_4);
		this.HeadGroup5.addChild(this.HeadSpikeWrapper_3);
		this.Spike_3.addChild(this.SpikeCap_3);
		this.PlateGroup1.addChild(this.SpikeWrapper);
		this.MidBody.addChild(this.Bridge3);
		this.HeadGroup6.addChild(this.HeadSpikeWrapper_4);
		this.HeadGroup1.addChild(this.HeadSpikeWrapper_5);
		this.Plating_3.addChild(this.Support_3);
		this.PlateGroup4.addChild(this.InnerSupport_3);
		this.PlateGroup5.addChild(this.SpikeWrapper_4);
		this.HeadGroup4.addChild(this.HeadSpikeWrapper_2);
		this.HeadSpikeWrapper_4.addChild(this.HeadSpike_4);
		this.HeadGroup4.addChild(this.HeadPlate_2);
		this.HeadPlate_5.addChild(this.EyeFrameR);
		this.HeadPlate_5.addChild(this.EyeFrameLT);
		this.PlateGroup4.addChild(this.SpikeWrapper_3);
		this.EyeBack.addChild(this.Eye);
		this.HeadSpikeWrapper_5.addChild(this.HeadSpike_5);
		this.Plating_5.addChild(this.Support_5);
		this.PlateGroup3.addChild(this.InnerSupport_2);
		this.Head.addChild(this.HeadGroup3);
		this.PlateGroup6.addChild(this.InnerSupport_5);
		this.Head.addChild(this.EyeBack);
		this.HeadPlate.addChild(this.HeadExtended);
		this.Plating_1.addChild(this.Support_1);
		this.PlateGroup3.addChild(this.Plating_2);
		this.HeadGroup5.addChild(this.HeadPlate_3);
		this.Head.addChild(this.HeadGroup6);
		this.MainBody.addChild(this.PlateGroup1);
		this.MidBody.addChild(this.Bridge4);
		this.HeadGroup3.addChild(this.HeadSpikeWrapper_1);
		this.Spike_2.addChild(this.SpikeCap_2);
		this.HeadSpikeWrapper_1.addChild(this.HeadSpike_1);
		this.PlateGroup5.addChild(this.InnerSupport_4);
		this.MidBody.addChild(this.RunedSiding4);
		this.Spike_1.addChild(this.SpikeCap_1);
		this.HeadPlate_5.addChild(this.EyeFrameRT);
		this.MidBody.addChild(this.RunedSiding1);
		this.MainBody.addChild(this.PlateGroup3);
		this.PlateGroup4.addChild(this.Plating_3);
		this.MidBody.addChild(this.Bridge1);
		this.HeadPlate_5.addChild(this.EyeFrameRB);
		this.MainBody.addChild(this.PlateGroup4);
		this.MidBody.addChild(this.Cover);
		this.HeadPlate_1.addChild(this.HeadExtended_1);
		this.SpikeWrapper.addChild(this.Spike);
		this.HeadPlate_4.addChild(this.HeadExtended_4);
		this.PlateGroup2.addChild(this.InnerSupport_1);
		this.Spike_5.addChild(this.SpikeCap_5);
		this.HeadPlate_5.addChild(this.HeadExtended_5);
		this.Plating_4.addChild(this.Support_4);
		this.HeadGroup3.addChild(this.HeadPlate_1);
		this.Head.addChild(this.HeadGroup2);
		this.MainBody.addChild(this.PlateGroup2);
		this.PlateGroup3.addChild(this.SpikeWrapper_2);
		this.PlateGroup1.addChild(this.InnerSupport);
		this.SpikeWrapper_2.addChild(this.Spike_2);
		this.MidBody.addChild(this.RunedSiding3);
		this.HeadGroup2.addChild(this.HeadPlate);
		this.MainBody.addChild(this.PlateGroup6);
		this.Head.addChild(this.HeadGroup4);
		this.MidBody.addChild(this.RunedSiding2);
		this.SpikeWrapper_5.addChild(this.Spike_5);
		this.MidBody.addChild(this.Bridge2);
		this.SpikeWrapper_3.addChild(this.Spike_3);
		this.Head.addChild(this.HeadGroup5);
		this.Spike_4.addChild(this.SpikeCap_4);
		this.HeadGroup2.addChild(this.HeadSpikeWrapper);
		this.HeadPlate_3.addChild(this.HeadExtended_3);
		this.HeadGroup6.addChild(this.HeadPlate_4);
		this.MainBody.addChild(this.PlateGroup5);
		this.HeadSpikeWrapper.addChild(this.HeadSpike);
		this.HeadSpikeWrapper_2.addChild(this.HeadSpike_2);
		this.HeadSpikeWrapper_3.addChild(this.HeadSpike_3);
		this.HeadPlate_5.addChild(this.EyeFrameLB);
		this.Plating_2.addChild(this.Support_2);
		this.PlateGroup5.addChild(this.Plating_4);
	}

	private static final Minecraft minecraft = Minecraft.getInstance();

//	@Override
//	public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		//final float partialTicks = minecraft.getRenderPartialTicks();
//	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(
				this.MainBody,
				this.MidBody,
				this.Head
		);
	}

	@Override
	public void setupAnim(CastleGuardianEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
