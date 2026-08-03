/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.vanillaplus.init;

import net.neoforged.neoforge.transfer.item.WorldlyContainerWrapper;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.vanillaplus.block.entity.*;
import net.mcreator.vanillaplus.VanillaPlusMod;

@EventBusSubscriber
public class VanillaPlusModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, VanillaPlusMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PipeBlockEntity>> PIPE = register("pipe", VanillaPlusModBlocks.PIPE, PipeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PipeSorterBlockEntity>> PIPE_SORTER = register("pipe_sorter", VanillaPlusModBlocks.PIPE_SORTER, PipeSorterBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PipeRouterBlockEntity>> PIPE_ROUTER = register("pipe_router", VanillaPlusModBlocks.PIPE_ROUTER, PipeRouterBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SecuenceOffBlockEntity>> SECUENCE_OFF = register("secuence_off", VanillaPlusModBlocks.SECUENCE_OFF, SecuenceOffBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SecuenceOnBlockEntity>> SECUENCE_ON = register("secuence_on", VanillaPlusModBlocks.SECUENCE_ON, SecuenceOnBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TimerOffBlockEntity>> TIMER_OFF = register("timer_off", VanillaPlusModBlocks.TIMER_OFF, TimerOffBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TimerWaitingBlockEntity>> TIMER_WAITING = register("timer_waiting", VanillaPlusModBlocks.TIMER_WAITING, TimerWaitingBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TimerOnBlockEntity>> TIMER_ON = register("timer_on", VanillaPlusModBlocks.TIMER_ON, TimerOnBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiskEditorBlockEntity>> DISK_EDITOR = register("disk_editor", VanillaPlusModBlocks.DISK_EDITOR, DiskEditorBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiskReaderOffBlockEntity>> DISK_READER_OFF = register("disk_reader_off", VanillaPlusModBlocks.DISK_READER_OFF, DiskReaderOffBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiskReaderOnBlockEntity>> DISK_READER_ON = register("disk_reader_on", VanillaPlusModBlocks.DISK_READER_ON, DiskReaderOnBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.Item.BLOCK, PIPE.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, PIPE_SORTER.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, PIPE_ROUTER.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SECUENCE_OFF.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, SECUENCE_ON.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TIMER_OFF.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TIMER_WAITING.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, TIMER_ON.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, DISK_EDITOR.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, DISK_READER_OFF.get(), WorldlyContainerWrapper::new);
		event.registerBlockEntity(Capabilities.Item.BLOCK, DISK_READER_ON.get(), WorldlyContainerWrapper::new);
	}
}