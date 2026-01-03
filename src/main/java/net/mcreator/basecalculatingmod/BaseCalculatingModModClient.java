package net.mcreator.basecalculatingmod;

import net.mcreator.basecalculatingmod.init.BaseCalculatingModModScreens;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModMenus;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModBlocksRenderers;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class BaseCalculatingModModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		BaseCalculatingModModBlocksRenderers.clientLoad();
		BaseCalculatingModModScreens.clientLoad();
		BaseCalculatingModModMenus.clientLoad();
		// Start of user code block mod init
		// End of user code block mod init
	}
	// Start of user code block mod methods
	// End of user code block mod methods
}