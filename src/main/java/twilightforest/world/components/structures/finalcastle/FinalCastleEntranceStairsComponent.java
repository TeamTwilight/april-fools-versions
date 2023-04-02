package twilightforest.world.components.structures.finalcastle;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import twilightforest.world.registration.TFFeature;
import twilightforest.world.components.structures.TFStructureComponentOld;

import java.util.Random;

/**
 * Stair blocks heading to the entrance tower doors
 */
public class FinalCastleEntranceStairsComponent extends TFStructureComponentOld {

	public FinalCastleEntranceStairsComponent(ServerLevel level, CompoundTag nbt) {
		super(FinalCastlePieces.TFFCEnSt, nbt);
	}

	public FinalCastleEntranceStairsComponent(TFFeature feature, int index, int x, int y, int z, Direction direction) {
		super(FinalCastlePieces.TFFCEnSt, feature, index, x, y, z);
		this.setOrientation(direction);
		this.boundingBox = TFStructureComponentOld.getComponentToAddBoundingBox2(x, y, z, 0, -1, -5, 12, 0, 12, direction);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random randomIn, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		int size = 13;

		for (int x = 1; x < size; x++) {

			this.placeStairs(world, sbb, x, 1 - x, 5, Direction.EAST);

			for (int z = 0; z <= x; z++) {

				if (z > 0 && z <= size / 2) {
					this.placeStairs(world, sbb, x, 1 - x, 5 - z, Direction.EAST);
					this.placeStairs(world, sbb, x, 1 - x, 5 + z, Direction.EAST);
				}

				if (x <= size / 2) {
					this.placeStairs(world, sbb, z, 1 - x, 5 - x, Direction.NORTH);
					this.placeStairs(world, sbb, z, 1 - x, 5 + x, Direction.SOUTH);
				}
			}
		}

		this.fillColumnDown(world, deco.blockState, 0, 0, 5, sbb);

		return true;
	}

	private void placeStairs(WorldGenLevel world, BoundingBox sbb, int x, int y, int z, Direction facing) {
		if (this.getBlock(world, x, y, z, sbb).getMaterial().isReplaceable()) { //TODO: Probably doesn't support replaceable blocks
			//this.setBlockState(world, deco.blockState, x, y, z, sbb);
			this.placeBlock(world, deco.stairState.setValue(StairBlock.FACING, facing), x, y, z, sbb);
			this.fillColumnDown(world, deco.blockState, x, y - 1, z, sbb);
		}
	}
}
