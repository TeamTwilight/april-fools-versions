package twilightforest.world.components.structures.icetower;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

import java.util.Random;

public class IceTowerBeardComponent extends TFStructureComponentOld {

	protected int size;
	protected int height;

	public IceTowerBeardComponent(ServerLevel level, CompoundTag nbt) {
		super(IceTowerPieces.TFITBea, nbt);
		this.size = nbt.getInt("beardSize");
		this.height = nbt.getInt("beardHeight");
	}

	public IceTowerBeardComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(IceTowerPieces.TFITBea, feature, i, x, y, z);

		// same alignment
		this.setOrientation(wing.getOrientation());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = Math.round(this.size * 1.414F);

		this.deco = wing.deco;

		// just hang out at the very bottom of the tower
		this.boundingBox = new BoundingBox(wing.getBoundingBox().minX(), wing.getBoundingBox().minY() - this.height, wing.getBoundingBox().minZ(), wing.getBoundingBox().maxX(), wing.getBoundingBox().minY(), wing.getBoundingBox().maxZ());
	}

	@Override
	protected void addAdditionalSaveData(ServerLevel level, CompoundTag tagCompound) {
		super.addAdditionalSaveData(level, tagCompound);
		tagCompound.putInt("beardSize", this.size);
		tagCompound.putInt("beardHeight", this.height);
	}

	/**
	 * Makes a dark tower type beard
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				//int rHeight = this.size - (int) MathHelper.sqrt_float(x * z); // interesting office building pattern
				int rHeight = Math.round(Mth.sqrt(x * x + z * z));
				//int rHeight = MathHelper.ceiling_float_int(Math.min(x * x / 9F, z * z / 9F));

				for (int y = 0; y < rHeight; y++) {
					this.placeBlock(world, deco.blockState, x, this.height - y, z, sbb);

				}
			}
		}
		return true;
	}
}
