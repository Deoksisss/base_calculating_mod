/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.basecalculatingmod.init;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.mcreator.basecalculatingmod.block.NumberCoderBlock;
import net.mcreator.basecalculatingmod.block.ConverterBlockBlock;
import net.mcreator.basecalculatingmod.block.CalculatorBlockBlock;
import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import java.util.function.Function;

public class BaseCalculatingModModBlocks {
	public static Block CONVERTER_BLOCK;
	public static Block CALCULATOR_BLOCK;
	public static Block NUMBER_CODER;

	public static void load() {
		CONVERTER_BLOCK = register("converter_block", ConverterBlockBlock::new);
		CALCULATOR_BLOCK = register("calculator_block", CalculatorBlockBlock::new);
		NUMBER_CODER = register("number_coder", NumberCoderBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> B register(String name, Function<BlockBehaviour.Properties, B> supplier) {
		return (B) Blocks.register(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BaseCalculatingModMod.MODID, name)), (Function<BlockBehaviour.Properties, Block>) supplier, BlockBehaviour.Properties.of());
	}
}