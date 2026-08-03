package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.vanillaplus.init.VanillaPlusModItems;

public class DiskEditorGUIClearProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _serverLevel) {
			BlockEntity _be = _serverLevel.getBlockEntity(BlockPos.containing(x, y, z));
			if (_be instanceof Container _container) {
				ItemStack _setstack = new ItemStack(VanillaPlusModItems.EMPTY_DISK.get()).copy();
				_setstack.setCount(1);
				_container.setItem(0, _setstack);
			}
		}
	}
}