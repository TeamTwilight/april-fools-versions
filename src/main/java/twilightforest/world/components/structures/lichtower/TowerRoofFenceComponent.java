package twilightforest.world.components.structures.lichtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class TowerRoofFenceComponent extends TowerRoofComponent {

	public TowerRoofFenceComponent(ServerLevel level, CompoundTag nbt) {
		super(LichTowerPieces.TFLTRF, nbt);
	}

	public TowerRoofFenceComponent(TFFeature feature, int i, TowerWingComponent wing, int x, int y, int z) {
		super(LichTowerPieces.TFLTRF, feature, i, x, y, z);

		// same alignment
		this.setOrientation(wing.getOrientation());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = 0;

		// just hang out at the very top of the tower
		makeCapBB(wing);
	}

	/**
	 * A fence around the roof!
	 */
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		int y = height + 1;
		for (int x = 0; x <= size - 1; x++) {
			for (int z = 0; z <= size - 1; z++) {
				if (x == 0 || x == size - 1 || z == 0 || z == size - 1) {
					placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, y, z, sbb);
				}
			}
		}
		return true;
	}
}
