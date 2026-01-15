/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.basecalculatingmod.init;

import net.mcreator.basecalculatingmod.block.CalculatorBlockBlock;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class BaseCalculatingModModBlocksRenderers {
	public static void clientLoad() {
		CalculatorBlockBlock.registerRenderLayer();
		NumberCoderBlock.registerRenderLayer();
	}
	// Start of user code block custom block renderers
	// End of user code block custom block renderers
}