/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.vanillaplus.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.mcreator.vanillaplus.block.*;
import net.mcreator.vanillaplus.VanillaPlusMod;

import java.util.function.Function;

public class VanillaPlusModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(VanillaPlusMod.MODID);
	public static final DeferredBlock<Block> FLIP_FLOP;
	public static final DeferredBlock<Block> FLIP_FLOP_ON;
	public static final DeferredBlock<Block> AND_GATE_OFF;
	public static final DeferredBlock<Block> AND_GATE_ON;
	public static final DeferredBlock<Block> OR_GATE_OFF;
	public static final DeferredBlock<Block> OR_GATE_ON;
	public static final DeferredBlock<Block> XOR_GATE_OFF;
	public static final DeferredBlock<Block> XOR_GATE_ON;
	public static final DeferredBlock<Block> PIPE;
	public static final DeferredBlock<Block> PIPE_SORTER;
	public static final DeferredBlock<Block> PIPE_ROUTER;
	public static final DeferredBlock<Block> SECUENCE_OFF;
	public static final DeferredBlock<Block> SECUENCE_ON;
	public static final DeferredBlock<Block> TIMER_OFF;
	public static final DeferredBlock<Block> TIMER_WAITING;
	public static final DeferredBlock<Block> TIMER_ON;
	public static final DeferredBlock<Block> DRILL;
	static {
		FLIP_FLOP = register("flip_flop", FlipFlopBlock::new);
		FLIP_FLOP_ON = register("flip_flop_on", FlipFlopOnBlock::new);
		AND_GATE_OFF = register("and_gate_off", AndGateOffBlock::new);
		AND_GATE_ON = register("and_gate_on", AndGateOnBlock::new);
		OR_GATE_OFF = register("or_gate_off", OrGateOffBlock::new);
		OR_GATE_ON = register("or_gate_on", OrGateOnBlock::new);
		XOR_GATE_OFF = register("xor_gate_off", XorGateOffBlock::new);
		XOR_GATE_ON = register("xor_gate_on", XorGateOnBlock::new);
		PIPE = register("pipe", PipeBlock::new);
		PIPE_SORTER = register("pipe_sorter", PipeSorterBlock::new);
		PIPE_ROUTER = register("pipe_router", PipeRouterBlock::new);
		SECUENCE_OFF = register("secuence_off", SecuenceOffBlock::new);
		SECUENCE_ON = register("secuence_on", SecuenceOnBlock::new);
		TIMER_OFF = register("timer_off", TimerOffBlock::new);
		TIMER_WAITING = register("timer_waiting", TimerWaitingBlock::new);
		TIMER_ON = register("timer_on", TimerOnBlock::new);
		DRILL = register("drill", DrillBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}
}