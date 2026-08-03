package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.Identifier;

public class DiskEditorGUIValidPlacementProcedure {
	public static boolean execute(ItemStack itemstack) {
		return !itemstack.is(ItemTags.create(Identifier.parse("minecraft:disks")));
	}
}