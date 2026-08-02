package net.mcreator.vanillaplus.client.gui;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.mcreator.vanillaplus.world.inventory.SecuenceGUIMenu;
import net.mcreator.vanillaplus.procedures.SecuenceGUITextProcedure;
import net.mcreator.vanillaplus.procedures.SecuenceGUIPopConditionProcedure;
import net.mcreator.vanillaplus.network.SecuenceGUIButtonMessage;
import net.mcreator.vanillaplus.init.VanillaPlusModScreens;

import com.mojang.blaze3d.platform.InputConstants;

public class SecuenceGUIScreen extends AbstractContainerScreen<SecuenceGUIMenu> implements VanillaPlusModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_push_0;
	private Button button_push_1;
	private Button button_pop;
	private static final Identifier BACKGROUND = Identifier.parse("vanilla_plus:textures/screens/secuence_gui.png");

	public SecuenceGUIScreen(SecuenceGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 176, 166);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
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
		guiGraphics.text(this.font, SecuenceGUITextProcedure.execute(world, x, y, z), 6, 61, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_push_0 = Button.builder(Component.translatable("gui.vanilla_plus.secuence_gui.button_push_0"), e -> {
			int x = SecuenceGUIScreen.this.x;
			int y = SecuenceGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new SecuenceGUIButtonMessage(0, x, y, z));
				SecuenceGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 7, 55, 20).build();
		this.addRenderableWidget(button_push_0);
		button_push_1 = Button.builder(Component.translatable("gui.vanilla_plus.secuence_gui.button_push_1"), e -> {
			int x = SecuenceGUIScreen.this.x;
			int y = SecuenceGUIScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new SecuenceGUIButtonMessage(1, x, y, z));
				SecuenceGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 34, 55, 20).build();
		this.addRenderableWidget(button_push_1);
		button_pop = Button.builder(Component.translatable("gui.vanilla_plus.secuence_gui.button_pop"), e -> {
			int x = SecuenceGUIScreen.this.x;
			int y = SecuenceGUIScreen.this.y;
			if (SecuenceGUIPopConditionProcedure.execute(world, x, y, z)) {
				ClientPacketDistributor.sendToServer(new SecuenceGUIButtonMessage(2, x, y, z));
				SecuenceGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 123, this.topPos + 7, 40, 20).build();
		this.addRenderableWidget(button_pop);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.button_pop.visible = SecuenceGUIPopConditionProcedure.execute(world, x, y, z);
	}
}