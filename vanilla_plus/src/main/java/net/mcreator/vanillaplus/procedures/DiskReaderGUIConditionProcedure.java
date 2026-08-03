package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.item.ItemStack;

import net.mcreator.vanillaplus.init.VanillaPlusModItems;

public class DiskReaderGUIConditionProcedure {
	public static boolean execute(ItemStack itemstack) {
		return !(itemstack.getItem() == VanillaPlusModItems.EDITED_DISK.get());
	}
}