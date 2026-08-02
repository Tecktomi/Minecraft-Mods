package net.mcreator.vanillaplus.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class SecuenceGUITextProcedure {
	public static String execute(LevelAccessor world, double x, double y, double z) {
		double iter = 0;
		double iter_max = 0;
		String output = "";
		iter_max = getBlockNBTNumber(world, BlockPos.containing(x, y, z), "state_max");
		iter = 0;
		output = "";
		while (iter < iter_max) {
			if (iter == getBlockNBTNumber(world, BlockPos.containing(x, y, z), "state")) {
				output = output + ">";
			}
			if (getBlockNBTLogic(world, BlockPos.containing(x, y, z), ("state_" + iter))) {
				output = output + "1";
			} else {
				output = output + "0";
			}
			iter = iter + 1;
		}
		return output;
	}

	private static double getBlockNBTNumber(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getDoubleOr(tag, 0);
		return -1;
	}

	private static boolean getBlockNBTLogic(LevelAccessor world, BlockPos pos, String tag) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity != null)
			return blockEntity.getPersistentData().getBooleanOr(tag, false);
		return false;
	}
}