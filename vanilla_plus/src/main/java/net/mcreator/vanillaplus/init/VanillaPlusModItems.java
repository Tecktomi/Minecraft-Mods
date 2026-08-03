/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.vanillaplus.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.vanillaplus.item.EmptyDiskItem;
import net.mcreator.vanillaplus.item.EditedDiskItem;
import net.mcreator.vanillaplus.block.*;
import net.mcreator.vanillaplus.VanillaPlusMod;

import java.util.function.Function;

public class VanillaPlusModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(VanillaPlusMod.MODID);
	public static final DeferredItem<Item> FLIP_FLOP;
	public static final DeferredItem<Item> FLIP_FLOP_ON;
	public static final DeferredItem<Item> AND_GATE_OFF;
	public static final DeferredItem<Item> AND_GATE_ON;
	public static final DeferredItem<Item> OR_GATE_OFF;
	public static final DeferredItem<Item> OR_GATE_ON;
	public static final DeferredItem<Item> XOR_GATE_OFF;
	public static final DeferredItem<Item> XOR_GATE_ON;
	public static final DeferredItem<Item> PIPE;
	public static final DeferredItem<Item> PIPE_SORTER;
	public static final DeferredItem<Item> PIPE_ROUTER;
	public static final DeferredItem<Item> SECUENCE_OFF;
	public static final DeferredItem<Item> SECUENCE_ON;
	public static final DeferredItem<Item> TIMER_OFF;
	public static final DeferredItem<Item> TIMER_WAITING;
	public static final DeferredItem<Item> TIMER_ON;
	public static final DeferredItem<Item> DISK_EDITOR;
	public static final DeferredItem<Item> EMPTY_DISK;
	public static final DeferredItem<Item> EDITED_DISK;
	public static final DeferredItem<Item> DISK_READER_OFF;
	public static final DeferredItem<Item> DISK_READER_ON;
	static {
		FLIP_FLOP = register("flip_flop", FlipFlopBlock.Item::new);
		FLIP_FLOP_ON = block(VanillaPlusModBlocks.FLIP_FLOP_ON);
		AND_GATE_OFF = register("and_gate_off", AndGateOffBlock.Item::new);
		AND_GATE_ON = block(VanillaPlusModBlocks.AND_GATE_ON);
		OR_GATE_OFF = register("or_gate_off", OrGateOffBlock.Item::new);
		OR_GATE_ON = register("or_gate_on", OrGateOnBlock.Item::new);
		XOR_GATE_OFF = register("xor_gate_off", XorGateOffBlock.Item::new);
		XOR_GATE_ON = register("xor_gate_on", XorGateOnBlock.Item::new);
		PIPE = block(VanillaPlusModBlocks.PIPE);
		PIPE_SORTER = block(VanillaPlusModBlocks.PIPE_SORTER);
		PIPE_ROUTER = block(VanillaPlusModBlocks.PIPE_ROUTER);
		SECUENCE_OFF = block(VanillaPlusModBlocks.SECUENCE_OFF);
		SECUENCE_ON = block(VanillaPlusModBlocks.SECUENCE_ON);
		TIMER_OFF = block(VanillaPlusModBlocks.TIMER_OFF);
		TIMER_WAITING = block(VanillaPlusModBlocks.TIMER_WAITING);
		TIMER_ON = block(VanillaPlusModBlocks.TIMER_ON);
		DISK_EDITOR = block(VanillaPlusModBlocks.DISK_EDITOR);
		EMPTY_DISK = register("empty_disk", EmptyDiskItem::new);
		EDITED_DISK = register("edited_disk", EditedDiskItem::new);
		DISK_READER_OFF = block(VanillaPlusModBlocks.DISK_READER_OFF);
		DISK_READER_ON = block(VanillaPlusModBlocks.DISK_READER_ON);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, Item.Properties::new);
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), () -> properties);
	}
}