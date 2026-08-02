package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.vanillaplus.init.VanillaPlusModBlocks;
import net.mcreator.vanillaplus.VanillaPlusMod;

public class XorGateOffNeighbourBlockChangesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		boolean power = false;
		Direction dir1 = Direction.NORTH;
		Direction dir2 = Direction.NORTH;
		dir1 = (getDirectionFromBlockState(blockstate)).getClockWise(Direction.Axis.Y);
		dir2 = (getDirectionFromBlockState(blockstate)).getCounterClockWise(Direction.Axis.Y);
		power = (world instanceof Level _level9 && _level9.hasNeighborSignal(BlockPos.containing(x + dir1.getStepX(), y + dir1.getStepY(), z + dir1.getStepZ())))
				^ (world instanceof Level _level13 && _level13.hasNeighborSignal(BlockPos.containing(x + dir2.getStepX(), y + dir2.getStepY(), z + dir2.getStepZ())));
		if (power) {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VanillaPlusModBlocks.XOR_GATE_OFF.get()) {
				VanillaPlusMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = VanillaPlusModBlocks.XOR_GATE_ON.get().defaultBlockState();
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
		} else {
			if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VanillaPlusModBlocks.XOR_GATE_ON.get()) {
				VanillaPlusMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = VanillaPlusModBlocks.XOR_GATE_OFF.get().defaultBlockState();
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