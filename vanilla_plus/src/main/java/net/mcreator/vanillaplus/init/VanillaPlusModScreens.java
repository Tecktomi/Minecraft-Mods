/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.vanillaplus.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.vanillaplus.client.gui.TimerGUIScreen;
import net.mcreator.vanillaplus.client.gui.SecuenceGUIScreen;
import net.mcreator.vanillaplus.client.gui.PipeGuiScreen;

@EventBusSubscriber(Dist.CLIENT)
public class VanillaPlusModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(VanillaPlusModMenus.PIPE_GUI.get(), PipeGuiScreen::new);
		event.register(VanillaPlusModMenus.SECUENCE_GUI.get(), SecuenceGUIScreen::new);
		event.register(VanillaPlusModMenus.TIMER_GUI.get(), TimerGUIScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}