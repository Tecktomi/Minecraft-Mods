package net.mcreator.vanillaplus.procedures;

import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class PipeRouterOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		double iter = 0;
		double iter_max = 0;
		double xx = 0;
		double yy = 0;
		double zz = 0;
		double iter_2 = 0;
		double iter_3 = 0;
		ItemStack input = ItemStack.EMPTY;
		boolean flag = false;
		Direction dir = Direction.NORTH;
		if (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).getCount() > 0) {
			flag = false;
			iter_3 = 0;
			while (iter_3 < 6) {
				iter_3 = iter_3 + 1;
				iter_2 = (iter_3 + (getPropertyByName(blockstate, "output_dir") instanceof IntegerProperty _getip2 ? blockstate.getValue(_getip2) : -1)) % 6;
				if (iter_2 == 0) {
					dir = Direction.DOWN;
				} else if (iter_2 == 1) {
					dir = Direction.UP;
				} else if (iter_2 == 2) {
					dir = Direction.NORTH;
				} else if (iter_2 == 3) {
					dir = Direction.SOUTH;
				} else if (iter_2 == 4) {
					dir = Direction.WEST;
				} else {
					dir = Direction.EAST;
				}
				if (dir == (getBlockDirection(world, BlockPos.containing(x, y, z)))) {
					continue;
				}
				xx = x + dir.getStepX();
				yy = y + dir.getStepY();
				zz = z + dir.getStepZ();
				input = (itemFromBlockInventory(world, BlockPos.containing(x, y, z), 0).copy()).copy();
				iter = 0;
				iter_max = getBlockInventorySlotCount(world, BlockPos.containing(xx, yy, zz));
				while (iter < iter_max) {
					if (canInsertInBlockInventory(world, BlockPos.containing(xx, yy, zz), (int) iter, 1, input)) {
						insertInBlockInventory(world, BlockPos.containing(xx, yy, zz), (int) iter, 1, input, false);
						if (world instanceof ServerLevel _serverLevel) {
							BlockEntity _be = _serverLevel.getBlockEntity(BlockPos.containing(x, y, z));
							if (_be instanceof Container _container) {
								_container.getItem(0).shrink(1);
							}
						}
						flag = true;
						{
							int _value = (int) iter_2;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("output_dir") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						break;
					}
					iter = iter + 1;
				}
				if (flag) {
					break;
				}
			}
		}
	}

	private static ItemStack itemFromBlockInventory(LevelAccessor world, BlockPos pos, int slot) {
		if (world instanceof ILevelExtension ext) {
			ResourceHandler<ItemResource> itemHandler = ext.getCapability(Capabilities.Item.BLOCK, pos, null);
			if (itemHandler != null)
				return ItemUtil.getStack(itemHandler, slot);
		}
		return ItemStack.EMPTY;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	private static Direction getBlockDirection(LevelAccessor world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		Property<?> property = blockState.getBlock().getStateDefinition().getProperty("facing");
		if (property != null && blockState.getValue(property) instanceof Direction direction)
			return direction;
		else if (blockState.hasProperty(BlockStateProperties.AXIS))
			return Direction.fromAxisAndDirection(blockState.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
		else if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
			return Direction.fromAxisAndDirection(blockState.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}

	private static int getBlockInventorySlotCount(LevelAccessor world, BlockPos pos) {
		if (world instanceof ILevelExtension ext) {
			ResourceHandler<ItemResource> itemHandler = ext.getCapability(Capabilities.Item.BLOCK, pos, null);
			if (itemHandler != null)
				return itemHandler.size();
		}
		return 0;
	}

	public static boolean canInsertInBlockInventory(LevelAccessor world, BlockPos pos, int slotId, int amount, ItemStack itemstack) {
		if (world instanceof ILevelExtension ext) {
			ResourceHandler<ItemResource> itemHandler = ext.getCapability(Capabilities.Item.BLOCK, pos, null);
			if (itemHandler != null && slotId >= 0 && slotId < itemHandler.size()) {
				ItemStack inserted = itemstack.copy();
				inserted.setCount(amount);
				return ItemUtil.insertItemReturnRemaining(itemHandler, slotId, inserted, true, null).getCount() == 0;
			}
		}
		return false;
	}

	private static int insertInBlockInventory(LevelAccessor world, BlockPos pos, int slotId, int amount, ItemStack itemstack, boolean simulate) {
		if (world instanceof ILevelExtension ext) {
			ResourceHandler<ItemResource> itemHandler = ext.getCapability(Capabilities.Item.BLOCK, pos, null);
			if (itemHandler != null && slotId >= 0 && slotId < itemHandler.size()) {
				ItemStack inserted = itemstack.copy();
				inserted.setCount(amount);
				return ItemUtil.insertItemReturnRemaining(itemHandler, slotId, inserted, simulate, null).getCount();
			}
		}
		return itemstack.getCount();
	}
}