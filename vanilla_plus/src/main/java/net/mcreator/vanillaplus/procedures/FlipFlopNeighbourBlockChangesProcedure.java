package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.vanillaplus.init.VanillaPlusModBlocks;
import net.mcreator.vanillaplus.VanillaPlusMod;

public class FlipFlopNeighbourBlockChangesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		boolean power = false;
		Direction dir = Direction.NORTH;
		dir = (getDirectionFromBlockState(blockstate)).getOpposite();
		power = world instanceof Level _level6 && _level6.hasNeighborSignal(BlockPos.containing(x + dir.getStepX(), y + dir.getStepY(), z + dir.getStepZ()));
		if (!(getPropertyByName(blockstate, "powered") instanceof BooleanProperty _getbp8 && blockstate.getValue(_getbp8)) && power) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VanillaPlusModBlocks.FLIP_FLOP.get()) {
				VanillaPlusMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = VanillaPlusModBlocks.FLIP_FLOP_ON.get().defaultBlockState();
						BlockState _bso = world.getBlockState(_bp);
						for (Property<?> _propertyOld : _bso.getProperties()) {
							Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
							if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
								try {
									_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
								} catch (Exception e) {
								}
						}
						world.setBlock(_bp, _bs, 3);
					}
				});
			} else {
				VanillaPlusMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = VanillaPlusModBlocks.FLIP_FLOP.get().defaultBlockState();
						BlockState _bso = world.getBlockState(_bp);
						for (Property<?> _propertyOld : _bso.getProperties()) {
							Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
							if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
								try {
									_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
								} catch (Exception e) {
								}
						}
						world.setBlock(_bp, _bs, 3);
					}
				});
			}
		}
		{
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			if (_bs.getBlock().getStateDefinition().getProperty("powered") instanceof BooleanProperty _booleanProp)
				world.setBlock(_pos, _bs.setValue(_booleanProp, power), 3);
		}
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		if (getPropertyByName(blockState, "facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class)
			return (Direction) blockState.getValue(ep);
		if (getPropertyByName(blockState, "axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class)
			return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}