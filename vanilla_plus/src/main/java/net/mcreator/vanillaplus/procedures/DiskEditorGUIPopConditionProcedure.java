package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;

import net.mcreator.vanillaplus.init.VanillaPlusModMenus;
import net.mcreator.vanillaplus.init.VanillaPlusModItems;

public class DiskEditorGUIPopConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		ItemStack item = ItemStack.EMPTY;
		item = (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof VanillaPlusModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get(0).getItem() : ItemStack.EMPTY).copy();
		return item.getItem() == VanillaPlusModItems.EDITED_DISK.get() && item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("state", 0) > 0;
	}
}