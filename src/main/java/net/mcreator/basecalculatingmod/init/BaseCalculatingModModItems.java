/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.basecalculatingmod.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import java.util.function.Function;

public class BaseCalculatingModModItems {
	public static Item CONVERTER_BLOCK;
	public static Item CALCULATOR_BLOCK;
	public static Item NUMBER_CODER;

	public static void load() {
		CONVERTER_BLOCK = block(BaseCalculatingModModBlocks.CONVERTER_BLOCK, "converter_block");
		CALCULATOR_BLOCK = block(BaseCalculatingModModBlocks.CALCULATOR_BLOCK, "calculator_block");
		NUMBER_CODER = block(BaseCalculatingModModBlocks.NUMBER_CODER, "number_coder");
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> I register(String name, Function<Item.Properties, ? extends I> supplier) {
		return (I) Items.registerItem(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BaseCalculatingModMod.MODID, name)), (Function<Item.Properties, Item>) supplier);
	}

	private static Item block(Block block, String name) {
		return block(block, name, new Item.Properties());
	}

	private static Item block(Block block, String name, Item.Properties properties) {
		return Items.registerItem(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BaseCalculatingModMod.MODID, name)), prop -> new BlockItem(block, prop), properties);
	}
}