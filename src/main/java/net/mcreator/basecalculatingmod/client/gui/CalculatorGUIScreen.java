package net.mcreator.basecalculatingmod.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.mcreator.basecalculatingmod.world.inventory.CalculatorGUIMenu;
import net.mcreator.basecalculatingmod.network.CalculatorGUIButtonMessage;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModScreens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CalculatorGUIScreen extends AbstractContainerScreen<CalculatorGUIMenu> implements BaseCalculatingModModScreens.FabricScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox calcField;
	private Button button_calculate;

	public CalculatorGUIScreen(CalculatorGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		if (elementType == 0 && elementState instanceof String stringState) {
			if (name.equals("calcField"))
				calcField.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("base_calculating_mod:textures/screens/calculator_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		calcField.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		if (calcField.isFocused())
			return calcField.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String calcFieldValue = calcField.getValue();
		super.resize(minecraft, width, height);
		calcField.setValue(calcFieldValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		calcField = new EditBox(this.font, this.leftPos + 28, this.topPos + 20, 118, 18, Component.translatable("gui.base_calculating_mod.calculator_gui.calcField"));
		calcField.setMaxLength(8192);
		calcField.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "calcField", content, false);
		});
		calcField.setHint(Component.translatable("gui.base_calculating_mod.calculator_gui.calcField"));
		this.addWidget(this.calcField);
		button_calculate = Button.builder(Component.translatable("gui.base_calculating_mod.calculator_gui.button_calculate"), e -> {
			int x = CalculatorGUIScreen.this.x;
			int y = CalculatorGUIScreen.this.y;
			if (true) {
				ClientPlayNetworking.send(new CalculatorGUIButtonMessage(0, x, y, z));
				CalculatorGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 51, this.topPos + 52, 72, 20).build();
		this.addRenderableWidget(button_calculate);
	}
}