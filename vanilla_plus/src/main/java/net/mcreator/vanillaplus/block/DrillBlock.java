package net.mcreator.vanillaplus.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.MenuProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.vanillaplus.procedures.DrillOnTickUpdateProcedure;
import net.mcreator.vanillaplus.procedures.DrillOnNeightbourChangesProcedure;
import net.mcreator.vanillaplus.block.entity.DrillBlockEntity;

import javax.annotation.Nullable;

import java.util.function.Function;

public class DrillBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public DrillBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.METAL).strength(5f, 15f).requiresCorrectToolForDrops().noOcclusion().pushReaction(PushReaction.PUSH_ONLY).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(7, 7, 14, 9, 9, 16), box(6, 6, 12, 10, 10, 14), box(5, 5, 10, 11, 11, 12), box(4, 4, 8, 12, 12, 10), box(2, 2, 0, 14, 14, 8));
				case EAST -> Shapes.or(box(0, 7, 7, 2, 9, 9), box(2, 6, 6, 4, 10, 10), box(4, 5, 5, 6, 11, 11), box(6, 4, 4, 8, 12, 12), box(8, 2, 2, 16, 14, 14));
				case WEST -> Shapes.or(box(14, 7, 7, 16, 9, 9), box(12, 6, 6, 14, 10, 10), box(10, 5, 5, 12, 11, 11), box(8, 4, 4, 10, 12, 12), box(0, 2, 2, 8, 14, 14));
				case UP -> Shapes.or(box(7, 0, 7, 9, 2, 9), box(6, 2, 6, 10, 4, 10), box(5, 4, 5, 11, 6, 11), box(4, 6, 4, 12, 8, 12), box(2, 8, 2, 14, 16, 14));
				case DOWN -> Shapes.or(box(7, 14, 7, 9, 16, 9), box(6, 12, 6, 10, 14, 10), box(5, 10, 5, 11, 12, 11), box(4, 8, 4, 12, 10, 12), box(2, 0, 2, 14, 8, 14));
				default -> Shapes.or(box(7, 7, 0, 9, 9, 2), box(6, 6, 2, 10, 10, 4), box(5, 5, 4, 11, 11, 6), box(4, 4, 6, 12, 12, 8), box(2, 2, 8, 14, 14, 16));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return true;
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 1);
		DrillOnNeightbourChangesProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
	}

	@Override
	public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, orientation, moving);
		DrillOnNeightbourChangesProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		DrillOnTickUpdateProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ(), blockstate);
		world.scheduleTick(pos, this, 1);
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DrillBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}
}