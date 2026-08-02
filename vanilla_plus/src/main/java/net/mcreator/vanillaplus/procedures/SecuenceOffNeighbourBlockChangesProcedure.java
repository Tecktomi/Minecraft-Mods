package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.util.ProblemReporter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.vanillaplus.init.VanillaPlusModBlocks;
import net.mcreator.vanillaplus.VanillaPlusMod;

public class SecuenceOffNeighbourBlockChangesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		Direction dir = Direction.NORTH;
		boolean power = false;
		double new_state = 0;
		dir = (getDirectionFromBlockState(blockstate)).getOpposite();
		power = world instanceof Level _level6 && _level6.hasNeighborSignal(BlockPos.containing(x + dir.getStepX(), y + dir.getStepY(), z + dir.getStepZ()));
		if (!(getPropertyByName(blockstate, "powered") instanceof BooleanProperty _getbp8 && blockstate.getValue(_getbp8)) && power) {
			new_state = (getBlockNBTNumber(world, BlockPos.containing(x, y, z), "state") + 1) % getBlockNBTNumber(world, BlockPos.containing(x, y, z), "state_max");
			if (!world.isClientSide()) {
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockEntity _blockEntity = world.getBlockEntity(_bp);
				BlockState _bs = world.getBlockState(_bp);
				if (_blockEntity != null) {
					_blockEntity.getPersistentData().putDouble("state", new_state);
				}
				if (world instanceof Level _level)
					_level.sendBlockUpdated(_bp, _bs, _bs, 3);
			}
			if (blockstate.getBlock() == VanillaPlusModBlocks.SECUENCE_OFF.get() && getBlockNBTLogic(world, BlockPos.containing(x, y, z), ("state_" + new_state))) {
				VanillaPlusMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = VanillaPlusModBlocks.SECUENCE_ON.get().defaultBlockState();
						BlockState _bso = world.getBlockState(_bp);
						for (Property<?> _propertyOld : _bso.getProperties()) {
							Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
							if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
								try {
									_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
								} catch (Exception e) {
								}
						}
						BlockEntity _be = world.getBlockEntity(_bp);
						CompoundTag _bnbt = null;
						if (_be != null) {
							_bnbt = _be.saveWithFullMetadata(world.registryAccess());
							_be.setRemoved();
						}
						world.setBlock(_bp, _bs, 3);
						if (_bnbt != null) {
							_be = world.getBlockEntity(_bp);
							if (_be != null) {
								try {
									_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
								} catch (Exception ignored) {
								}
							}
						}
					}
				});
			} else if (blockstate.getBlock() == VanillaPlusModBlocks.SECUENCE_ON.get() && !getBlockNBTLogic(world, BlockPos.containing(x, y, z), ("state_" + new_state))) {
				VanillaPlusMod.queueServerWork(1, () -> {
					{
						BlockPos _bp = BlockPos.containing(x, y, z);
						BlockState _bs = VanillaPlusModBlocks.SECUENCE_OFF.get().defaultBlockState();
						BlockState _bso = world.getBlockState(_bp);
						for (Property<?> _propertyOld : _bso.getProperties()) {
							Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
							if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
								try {
									_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
								} catch (Exception e) {
								}
						}
						BlockEntity _be = world.getBlockEntity(_bp);
						CompoundTag _bnbt = null;
						if (_be != null) {
							_bnbt = _be.saveWithFullMetadata(world.registryAccess());
							_be.setRemoved();
						}
						world.setBlock(_bp, _bs, 3);
						if (_bnbt != null) {
							_be = world.getBlockEntity(_bp);
							if (_be != null) {
								try {
									_be.loadWithComponents(TagValueInput.create(ProblemReporter.DISCARDING, world.registryAccess(), _bnbt));
								} catch (Exception ignored) {
								}
							}
						}
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

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}