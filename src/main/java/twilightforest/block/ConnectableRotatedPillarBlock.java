package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class ConnectableRotatedPillarBlock extends RotatedPillarBlock {
	private static final BooleanProperty NORTH = PipeBlock.NORTH;
	private static final BooleanProperty SOUTH = PipeBlock.SOUTH;
	private static final BooleanProperty WEST = PipeBlock.WEST;
	private static final BooleanProperty EAST = PipeBlock.EAST;
	private static final BooleanProperty UP = PipeBlock.UP;
	private static final BooleanProperty DOWN = PipeBlock.DOWN;

    final double boundingBoxWidthLower;
    final double boundingBoxWidthUpper;

    private final double boundingBoxHeightLower;
    private final double boundingBoxHeightUpper;

    ConnectableRotatedPillarBlock(Properties props, double size) {
        this(props, size, size);
    }

    ConnectableRotatedPillarBlock(Properties props, double width, double height) {
        super(props.noOcclusion());

        if (width >= 16d) {
            this.boundingBoxWidthLower = 0d;
            this.boundingBoxWidthUpper = 16d;
        } else {
            this.boundingBoxWidthLower = 8d-(width/2d);
            this.boundingBoxWidthUpper = 16d-this.boundingBoxWidthLower;
        }

        if (height >= 16d) {
            this.boundingBoxHeightLower = 0d;
            this.boundingBoxHeightUpper = 16d;
        } else {
            this.boundingBoxHeightLower = 8d-(height/2d);
            this.boundingBoxHeightUpper = 16d-this.boundingBoxHeightLower;
        }

        this.registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.Y)
                .setValue(NORTH, false).setValue(WEST, false)
                .setValue(SOUTH, false).setValue(EAST, false)
                .setValue(DOWN, false).setValue(UP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, DOWN, UP);
    }

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
		return state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(facing), this.canConnectTo(facingState, facingState.isFaceSturdy(world, facingPos, facing.getOpposite())));
	}

	public boolean canConnectTo(BlockState state, boolean solidSide) {
		return !isExceptionForConnection(state) && solidSide;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockGetter iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.south();
		BlockPos blockpos3 = blockpos.west();
		BlockPos blockpos4 = blockpos.east();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(NORTH, Boolean.valueOf(this.canConnectTo(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH)))).setValue(SOUTH, Boolean.valueOf(this.canConnectTo(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.NORTH)))).setValue(WEST, Boolean.valueOf(this.canConnectTo(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.EAST)))).setValue(EAST, Boolean.valueOf(this.canConnectTo(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.WEST))));
	}

	// Utility to make a bounding-box piece
    protected AABB getSidedAABBStraight(Direction facing, Direction.Axis axis) {
        return makeQuickAABB(
                facing == Direction.EAST  ? 16d : axis == Direction.Axis.X ? this.boundingBoxHeightLower : this.boundingBoxWidthLower,
                facing == Direction.UP    ? 16d : axis == Direction.Axis.Y ? this.boundingBoxHeightLower : this.boundingBoxWidthLower,
                facing == Direction.SOUTH ? 16d : axis == Direction.Axis.Z ? this.boundingBoxHeightLower : this.boundingBoxWidthLower,
                facing == Direction.WEST  ?  0d : axis == Direction.Axis.X ? this.boundingBoxHeightUpper : this.boundingBoxWidthUpper,
                facing == Direction.DOWN  ?  0d : axis == Direction.Axis.Y ? this.boundingBoxHeightUpper : this.boundingBoxWidthUpper,
                facing == Direction.NORTH ?  0d : axis == Direction.Axis.Z ? this.boundingBoxHeightUpper : this.boundingBoxWidthUpper);
    }

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(AXIS)) {
			case X -> box(
					0d,
					state.getValue(NORTH) ? 0d : this.boundingBoxWidthLower,
					state.getValue(WEST) ? 0d : this.boundingBoxWidthLower,
					16d,
					state.getValue(SOUTH) ? 16d : this.boundingBoxWidthUpper,
					state.getValue(EAST) ? 16d : this.boundingBoxWidthUpper
			);
			case Z -> box(
					state.getValue(EAST) ? 0d : this.boundingBoxWidthLower,
					state.getValue(SOUTH) ? 0d : this.boundingBoxWidthLower,
					0d,
					state.getValue(WEST) ? 16d : this.boundingBoxWidthUpper,
					state.getValue(NORTH) ? 16d : this.boundingBoxWidthUpper,
					16d
			);
			default -> box(
					state.getValue(WEST) ? 0d : this.boundingBoxWidthLower,
					0d,
					state.getValue(NORTH) ? 0d : this.boundingBoxWidthLower,
					state.getValue(EAST) ? 16d : this.boundingBoxWidthUpper,
					16d,
					state.getValue(SOUTH) ? 16d : this.boundingBoxWidthUpper
			);
		};
	}

    protected AABB makeQuickAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new AABB(
                x1/16.0d, y1/16.0d,
                z1/16.0d, x2/16.0d,
                y2/16.0d, z2/16.0d);
    }
}
