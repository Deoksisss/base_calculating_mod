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

import net.mcreator.basecalculatingmod.world.inventory.ConverterBlockGUIMenu;
import net.mcreator.basecalculatingmod.network.ConverterBlockGUIButtonMessage;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModScreens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ConverterBlockGUIScreen extends AbstractContainerScreen<ConverterBlockGUIMenu> implements BaseCalculatingModModScreens.FabricScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private EditBox converterTextField;
	private Button button_convert;
	private BaseCalculatingModModScreens.ExtendedSlider converterFirstBaseSlider;
	private BaseCalculatingModModScreens.ExtendedSlider converterSecondBaseSlider;

	public ConverterBlockGUIScreen(ConverterBlockGUIMenu container, Inventory inventory, Component text) {
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
			if (name.equals("converterTextField"))
				converterTextField.setValue(stringState);
		}
		if (elementType == 2 && elementState instanceof Number n) {
			if (name.equals("converterFirstBaseSlider"))
				converterFirstBaseSlider.setValue(n.doubleValue());
			else if (name.equals("converterSecondBaseSlider"))
				converterSecondBaseSlider.setValue(n.doubleValue());
		}
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("base_calculating_mod:textures/screens/converter_block_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		converterTextField.render(guiGraphics, mouseX, mouseY, partialTicks);
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
		if (converterTextField.isFocused())
			return converterTextField.keyPressed(key, b, c);
		return super.keyPressed(key, b, c);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		return (this.getFocused() != null && this.isDragging() && button == 0) ? this.getFocused().mouseDragged(mouseX, mouseY, button, dragX, dragY) : super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		String converterTextFieldValue = converterTextField.getValue();
		super.resize(minecraft, width, height);
		converterTextField.setValue(converterTextFieldValue);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		converterTextField = new EditBox(this.font, this.leftPos + 7, this.topPos + 9, 118, 18, Component.translatable("gui.base_calculating_mod.converter_block_gui.converterTextField"));
		converterTextField.setMaxLength(8192);
		converterTextField.setResponder(content -> {
			if (!menuStateUpdateActive)
				menu.sendMenuStateUpdate(entity, 0, "converterTextField", content, false);
		});
		converterTextField.setHint(Component.translatable("gui.base_calculating_mod.converter_block_gui.converterTextField"));
		this.addWidget(this.converterTextField);
		button_convert = Button.builder(Component.translatable("gui.base_calculating_mod.converter_block_gui.button_convert"), e -> {
			int x = ConverterBlockGUIScreen.this.x;
			int y = ConverterBlockGUIScreen.this.y;
			if (true) {
				ClientPlayNetworking.send(new ConverterBlockGUIButtonMessage(0, x, y, z));
				ConverterBlockGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 107, this.topPos + 56, 61, 20).build();
		this.addRenderableWidget(button_convert);
		converterFirstBaseSlider = new BaseCalculatingModModScreens.ExtendedSlider(this.leftPos + 7, this.topPos + 31, 46, 20, Component.translatable("gui.base_calculating_mod.converter_block_gui.converterFirstBaseSlider_prefix"),
				Component.translatable("gui.base_calculating_mod.converter_block_gui.converterFirstBaseSlider_suffix"), 2, 60, 10, 1, 0, true) {
			@Override
			protected void applyValue() {
				if (!menuStateUpdateActive)
					menu.sendMenuStateUpdate(entity, 2, "converterFirstBaseSlider", this.getValue(), false);
			}
		};
		this.addRenderableWidget(converterFirstBaseSlider);
		if (!menuStateUpdateActive)
			menu.sendMenuStateUpdate(entity, 2, "converterFirstBaseSlider", converterFirstBaseSlider.getValue(), false);
		converterSecondBaseSlider = new BaseCalculatingModModScreens.ExtendedSlider(this.leftPos + 7, this.topPos + 56, 46, 20, Component.translatable("gui.base_calculating_mod.converter_block_gui.converterSecondBaseSlider_prefix"),
				Component.translatable("gui.base_calculating_mod.converter_block_gui.converterSecondBaseSlider_suffix"), 2, 60, 2, 1, 0, true) {
			@Override
			protected void applyValue() {
				if (!menuStateUpdateActive)
					menu.sendMenuStateUpdate(entity, 2, "converterSecondBaseSlider", this.getValue(), false);
			}
		};
		this.addRenderableWidget(converterSecondBaseSlider);
		if (!menuStateUpdateActive)
			menu.sendMenuStateUpdate(entity, 2, "converterSecondBaseSlider", converterSecondBaseSlider.getValue(), false);
	}
}