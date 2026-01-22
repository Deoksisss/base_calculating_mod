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

import net.mcreator.basecalculatingmod.world.inventory.NumberCoderBlockMenu;
import net.mcreator.basecalculatingmod.network.NumberCoderBlockButtonMessage;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModScreens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class NumberCoderBlockScreen extends AbstractContainerScreen<NumberCoderBlockMenu> implements BaseCalculatingModModScreens.FabricScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox coderField;
	private Button button_to_direct;
	private Button button_to_reverse;
	private Button button_to_additional;

	public NumberCoderBlockScreen(NumberCoderBlockMenu container, Inventory inventory, Component text) {
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
			if (name.equals("coderField"))
				coderField.setValue(stringState);
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("base_calculating_mod:textures/screens/number_coder_block.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		coderField.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (coderField.isFocused())
			return coderField.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String coderFieldValue = coderField.getValue();
		super.resize(minecraft, width, height);
		coderField.setValue(coderFieldValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.base_calculating_mod.number_coder_block.label_cpureprodector_3000_2026"), 94, 148, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		coderField = new EditBox(this.font, this.leftPos + 28, this.topPos + 13, 118, 18, Component.translatable("gui.base_calculating_mod.number_coder_block.coderField"));
		coderField.setMaxLength(8192);
		coderField.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "coderField", content, false);
		});
		coderField.setHint(Component.translatable("gui.base_calculating_mod.number_coder_block.coderField"));
		this.addWidget(this.coderField);
		button_to_direct = Button.builder(Component.translatable("gui.base_calculating_mod.number_coder_block.button_to_direct"), e -> {
			int x = NumberCoderBlockScreen.this.x;
			int y = NumberCoderBlockScreen.this.y;
			if (true) {
				ClientPlayNetworking.send(new NumberCoderBlockButtonMessage(0, x, y, z));
				NumberCoderBlockButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 28, this.topPos + 43, 72, 20).build();
		this.addRenderableWidget(button_to_direct);
		button_to_reverse = Button.builder(Component.translatable("gui.base_calculating_mod.number_coder_block.button_to_reverse"), e -> {
			int x = NumberCoderBlockScreen.this.x;
			int y = NumberCoderBlockScreen.this.y;
			if (true) {
				ClientPlayNetworking.send(new NumberCoderBlockButtonMessage(1, x, y, z));
				NumberCoderBlockButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 28, this.topPos + 74, 77, 20).build();
		this.addRenderableWidget(button_to_reverse);
		button_to_additional = Button.builder(Component.translatable("gui.base_calculating_mod.number_coder_block.button_to_additional"), e -> {
			int x = NumberCoderBlockScreen.this.x;
			int y = NumberCoderBlockScreen.this.y;
			if (true) {
				ClientPlayNetworking.send(new NumberCoderBlockButtonMessage(2, x, y, z));
				NumberCoderBlockButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 27, this.topPos + 105, 93, 20).build();
		this.addRenderableWidget(button_to_additional);
	}
}