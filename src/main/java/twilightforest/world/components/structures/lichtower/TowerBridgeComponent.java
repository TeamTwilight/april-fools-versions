package twilightforest.world.components.structures.lichtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import twilightforest.world.registration.TFFeature;

import java.util.Random;

public class TowerBridgeComponent extends TowerWingComponent {

	int dSize;
	int dHeight;

	public TowerBridgeComponent(ServerLevel level, CompoundTag nbt) {
		super(LichTowerPieces.TFLTBri, nbt);
	}

	protected TowerBridgeComponent(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(LichTowerPieces.TFLTBri, feature, i, x, y, z, 3, 3, direction);

		this.dSize = pSize;
		this.dHeight = pHeight;
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		int[] dest = new int[]{2, 1, 1};//getValidOpening(rand, 0);
		makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], dSize, dHeight, Rotation.NONE);
	}

	/**
	 * Gets the bounding box of the tower wing we would like to make.
	 *
	 * @return
	 */
	public BoundingBox getWingBB() {
		int[] dest = offsetTowerCoords(2, 1, 1, dSize, this.getOrientation());
		return getFeatureType().getComponentToAddBoundingBox(dest[0], dest[1], dest[2], 0, 0, 0, dSize - 1, dHeight - 1, dSize - 1, this.getOrientation());
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// make walls
		for (int x = 0; x < 3; x++) {
			placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, 2, 0, sbb);
			placeBlock(world, Blocks.OAK_FENCE.defaultBlockState(), x, 2, 2, sbb);
			placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), x, 1, 0, sbb);
			placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), x, 1, 2, sbb);
			placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), x, 0, 0, sbb);
			placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), x, 0, 1, sbb);
			placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), x, 0, 2, sbb);
			placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), x, -1, 1, sbb);
		}

		// try two blocks outside the boundries
		placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), -1, -1, 1, sbb);
		placeBlock(world, Blocks.STONE_BRICKS.defaultBlockState(), 3, -1, 1, sbb);

		// clear bridge walkway
		this.generateAirBox(world, sbb, 0, 1, 1, 2, 2, 1);

		// marker blocks
//        setBlockState(world, Blocks.WOOL, this.coordBaseMode, size / 2, 2, size / 2, sbb);
//        setBlockState(world, Blocks.GOLD_BLOCK, 0, 0, 0, 0, sbb);

		// door opening?
//        makeDoorOpening(world, sbb);

		return true;
	}
}
