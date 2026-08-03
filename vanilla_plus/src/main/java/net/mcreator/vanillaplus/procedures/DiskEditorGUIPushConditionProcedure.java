package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.vanillaplus.init.VanillaPlusModMenus;
import net.mcreator.vanillaplus.init.VanillaPlusModItems;

public class DiskEditorGUIPushConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof VanillaPlusModMenus.MenuAccessor _menu0 ? _menu0.getSlots().get(0).getItem() : ItemStack.EMPTY).getItem() == VanillaPlusModItems.EMPTY_DISK.get()
				|| (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof VanillaPlusModMenus.MenuAccessor _menu2 ? _menu2.getSlots().get(0).getItem() : ItemStack.EMPTY).getItem() == VanillaPlusModItems.EDITED_DISK.get();
	}
}