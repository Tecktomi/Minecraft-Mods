package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Container;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.vanillaplus.init.VanillaPlusModMenus;
import net.mcreator.vanillaplus.init.VanillaPlusModItems;

public class DiskEditorGUIPush0Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double state = 0;
		ItemStack item = ItemStack.EMPTY;
		item = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof VanillaPlusModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get(0).getItem() : ItemStack.EMPTY).copy();
		if (item.getItem() == VanillaPlusModItems.EMPTY_DISK.get()) {
			item = new ItemStack(VanillaPlusModItems.EDITED_DISK.get()).copy();
		}
		state = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("state", 0);
		{
			final String _tagName = ("state_" + state);
			final boolean _tagValue = false;
			CustomData.update(DataComponents.CUSTOM_DATA, item, tag -> tag.putBoolean(_tagName, _tagValue));
		}
		{
			final String _tagName = "state";
			final double _tagValue = (state + 1);
			CustomData.update(DataComponents.CUSTOM_DATA, item, tag -> tag.putDouble(_tagName, _tagValue));
		}
		if (world instanceof ServerLevel _serverLevel) {
			BlockEntity _be = _serverLevel.getBlockEntity(BlockPos.containing(x, y, z));
			if (_be instanceof Container _container) {
				ItemStack _setstack = item.copy();
				_setstack.setCount(1);
				_container.setItem(0, _setstack);
			}
		}
	}
}