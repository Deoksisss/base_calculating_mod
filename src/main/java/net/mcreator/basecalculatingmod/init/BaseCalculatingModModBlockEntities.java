/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.basecalculatingmod.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.mcreator.basecalculatingmod.block.entity.ConverterBlockBlockEntity;
import net.mcreator.basecalculatingmod.block.entity.CalculatorBlockBlockEntity;
import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public class BaseCalculatingModModBlockEntities {
	public static BlockEntityType<ConverterBlockBlockEntity> CONVERTER_BLOCK;
	public static BlockEntityType<CalculatorBlockBlockEntity> CALCULATOR_BLOCK;

	public static void load() {
		CONVERTER_BLOCK = register("converter_block", BaseCalculatingModModBlocks.CONVERTER_BLOCK, ConverterBlockBlockEntity::new);
		CALCULATOR_BLOCK = register("calculator_block", BaseCalculatingModModBlocks.CALCULATOR_BLOCK, CalculatorBlockBlockEntity::new);
	}

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> BlockEntityType<T> register(String registryname, Block block, FabricBlockEntityTypeBuilder.Factory<? extends T> supplier) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(BaseCalculatingModMod.MODID, registryname), FabricBlockEntityTypeBuilder.<T>create(supplier, block).build());
	}
}