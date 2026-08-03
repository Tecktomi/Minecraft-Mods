package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

public class EditedDiskInformationProcedure {
	public static String execute(ItemStack itemstack) {
		String output = "";
		double iter = 0;
		double iter_max = 0;
		output = "";
		iter = 0;
		iter_max = itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDoubleOr("state", 0);
		while (iter < iter_max) {
			if (itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr(("state_" + iter), false)) {
				output = output + "1";
			} else {
				output = output + "0";
			}
			iter = iter + 1;
		}
		return output;
	}
}