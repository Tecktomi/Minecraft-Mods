/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.vanillaplus.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.mcreator.vanillaplus.world.inventory.TimerGUIMenu;
import net.mcreator.vanillaplus.world.inventory.SecuenceGUIMenu;
import net.mcreator.vanillaplus.world.inventory.PipeGuiMenu;
import net.mcreator.vanillaplus.world.inventory.DiskReaderGUIMenu;
import net.mcreator.vanillaplus.world.inventory.DiskEditorGUIMenu;
import net.mcreator.vanillaplus.network.MenuStateUpdateMessage;
import net.mcreator.vanillaplus.VanillaPlusMod;

import java.util.Map;

public class VanillaPlusModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, VanillaPlusMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<PipeGuiMenu>> PIPE_GUI = REGISTRY.register("pipe_gui", () -> IMenuTypeExtension.create(PipeGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<SecuenceGUIMenu>> SECUENCE_GUI = REGISTRY.register("secuence_gui", () -> IMenuTypeExtension.create(SecuenceGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<TimerGUIMenu>> TIMER_GUI = REGISTRY.register("timer_gui", () -> IMenuTypeExtension.create(TimerGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DiskEditorGUIMenu>> DISK_EDITOR_GUI = REGISTRY.register("disk_editor_gui", () -> IMenuTypeExtension.create(DiskEditorGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<DiskReaderGUIMenu>> DISK_READER_GUI = REGISTRY.register("disk_reader_gui", () -> IMenuTypeExtension.create(DiskReaderGUIMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide()) {
				if (Minecraft.getInstance().screen instanceof VanillaPlusModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}