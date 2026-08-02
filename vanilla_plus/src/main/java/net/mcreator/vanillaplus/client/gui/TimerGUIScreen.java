package net.mcreator.vanillaplus.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.mcreator.vanillaplus.world.inventory.TimerGUIMenu;
import net.mcreator.vanillaplus.procedures.TimerGUIStepProcedure;
import net.mcreator.vanillaplus.network.TimerGUISliderMessage;
import net.mcreator.vanillaplus.init.VanillaPlusModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class TimerGUIScreen extends AbstractContainerScreen<TimerGUIMenu> implements VanillaPlusModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private ExtendedSlider value;
	private static final Identifier BACKGROUND = Identifier.parse("vanilla_plus:textures/screens/timer_gui.png");

	public TimerGUIScreen(TimerGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 73);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 2 && elementState instanceof Number n) {
			if (name.equals("value"))
				value.setValue(n.doubleValue());
		}
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		int key = InputConstants.getKey(event).getValue();
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
		guiGraphics.text(this.font, Component.translatable("gui.vanilla_plus.timer_gui.label_steps"), 69, 5, -12829636, false);
		guiGraphics.text(this.font, TimerGUIStepProcedure.execute(world, x, y, z), 78, 50, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		value = new ExtendedSlider(this.leftPos + 60, this.topPos + 23, 50, 20, Component.translatable("gui.vanilla_plus.timer_gui.value_prefix"), Component.translatable("gui.vanilla_plus.timer_gui.value_suffix"), 0, 100, 1, 1, 0, true) {
			@Override
			protected void applyValue() {
				if (!menuStateUpdateActive)
					menu.sendMenuStateUpdate(entity, 2, "value", this.getValue(), false);
				ClientPacketDistributor.sendToServer(new TimerGUISliderMessage(0, x, y, z, this.getValue()));
				TimerGUISliderMessage.handleSliderAction(entity, 0, x, y, z, this.getValue());
			}
		};
		this.addRenderableWidget(value);
		if (!menuStateUpdateActive)
			menu.sendMenuStateUpdate(entity, 2, "value", value.getValue(), false);
	}
}