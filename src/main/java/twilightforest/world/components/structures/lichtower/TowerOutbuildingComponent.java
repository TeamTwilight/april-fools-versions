package twilightforest.world.components.structures.lichtower;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
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

public class TowerOutbuildingComponent extends TowerWingComponent {

	public TowerOutbuildingComponent(ServerLevel level, CompoundTag nbt) {
		super(LichTowerPieces.TFLTOut, nbt);
	}

	protected TowerOutbuildingComponent(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(LichTowerPieces.TFLTOut, feature, i, x, y, z, pSize, pHeight, direction);
	}

	/**
	 * NO BEARDS!
	 */
	@Override
	public void makeABeard(StructurePiece parent, StructurePieceAccessor list, Random rand) {
    }

	/**
	 * Outbuildings should not make new wings close to the ground.
	 */
	@Override
	public boolean makeTowerWing(StructurePieceAccessor list, Random rand, int index, int x, int y, int z, int wingSize, int wingHeight, Rotation direction) {
		if (y > 7) {
			return super.makeTowerWing(list, rand, index, x, y, z, wingSize, wingHeight, direction);
		} else {
			return false;
		}
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		final BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
		for (int x = 0; x < this.size; x++) {
			for (int z = 0; z < this.size; z++) {
				this.fillColumnDown(world, cobblestone, x, -1, z, sbb);
			}
		}
		return super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
	}
}
