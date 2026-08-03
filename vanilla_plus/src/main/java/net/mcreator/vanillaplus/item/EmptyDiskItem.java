package net.mcreator.vanillaplus.item;

import net.minecraft.world.item.Item;

public class EmptyDiskItem extends Item {
	public EmptyDiskItem(Item.Properties properties) {
		super(properties.stacksTo(16));
	}
}