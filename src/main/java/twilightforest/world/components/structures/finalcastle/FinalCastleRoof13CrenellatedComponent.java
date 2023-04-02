package twilightforest.world.components.structures.finalcastle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.util.RotationUtil;

import java.util.Random;

public class FinalCastleRoof13CrenellatedComponent extends TFStructureComponentOld {

	public FinalCastleRoof13CrenellatedComponent(ServerLevel level, CompoundTag nbt) {
		super(FinalCastlePieces.TFFCRo13Cr, nbt);
	}

	//TODO: Parameter "rand" is unused. Remove?
	public FinalCastleRoof13CrenellatedComponent(TFFeature feature, Random rand, int i, TFStructureComponentOld sideTower, int x, int y, int z) {
		super(FinalCastlePieces.TFFCRo13Cr, feature, i, x, y, z);

		int height = 5;

		this.setOrientation(sideTower.getOrientation());
		this.boundingBox = new BoundingBox(sideTower.getBoundingBox().minX() - 2, sideTower.getBoundingBox().maxY() - 1, sideTower.getBoundingBox().minZ() - 2, sideTower.getBoundingBox().maxX() + 2, sideTower.getBoundingBox().maxY() + height - 1, sideTower.getBoundingBox().maxZ() + 2);
	}

	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random randomIn, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// assume square
		int size = this.boundingBox.maxX() - this.boundingBox.minX();

		for (Rotation rotation : RotationUtil.ROTATIONS) {
			// corner
			this.fillBlocksRotated(world, sbb, 0, -1, 0, 3, 3, 3, deco.blockState, rotation);
			this.setBlockStateRotated(world, deco.blockState, 1, -2, 2, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 1, -2, 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 2, -2, 1, rotation, sbb);

			// walls
			this.fillBlocksRotated(world, sbb, 4, 0, 1, size - 4, 1, 1, deco.blockState, rotation);

			// smaller crenellations
			for (int x = 5; x < size - 5; x += 4) {
				this.fillBlocksRotated(world, sbb, x, 0, 0, x + 2, 3, 2, deco.blockState, rotation);
				this.setBlockStateRotated(world, deco.blockState, x + 1, -1, 1, rotation, sbb);
				this.setBlockStateRotated(world, deco.blockState, x + 1, -2, 1, rotation, sbb);
			}
		}

		return true;
	}
}
