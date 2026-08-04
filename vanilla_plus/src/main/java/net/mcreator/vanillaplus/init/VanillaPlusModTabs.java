/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.vanillaplus.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.mcreator.vanillaplus.VanillaPlusMod;

@EventBusSubscriber
public class VanillaPlusModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VanillaPlusMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			tabData.accept(VanillaPlusModBlocks.FLIP_FLOP.get().asItem());
			tabData.accept(VanillaPlusModBlocks.AND_GATE_OFF.get().asItem());
			tabData.accept(VanillaPlusModBlocks.OR_GATE_OFF.get().asItem());
			tabData.accept(VanillaPlusModBlocks.XOR_GATE_OFF.get().asItem());
			tabData.accept(VanillaPlusModBlocks.PIPE.get().asItem());
			tabData.accept(VanillaPlusModBlocks.PIPE_SORTER.get().asItem());
			tabData.accept(VanillaPlusModBlocks.PIPE_ROUTER.get().asItem());
			tabData.accept(VanillaPlusModBlocks.SECUENCE_OFF.get().asItem());
			tabData.accept(VanillaPlusModBlocks.TIMER_OFF.get().asItem());
			tabData.accept(VanillaPlusModBlocks.DISK_EDITOR.get().asItem());
			tabData.accept(VanillaPlusModItems.EMPTY_DISK.get());
			tabData.accept(VanillaPlusModBlocks.DISK_READER_OFF.get().asItem());
			tabData.accept(VanillaPlusModBlocks.ANTENA_EMISORA.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			tabData.accept(VanillaPlusModBlocks.PIPE.get().asItem());
			tabData.accept(VanillaPlusModBlocks.PIPE_SORTER.get().asItem());
		}
	}
}