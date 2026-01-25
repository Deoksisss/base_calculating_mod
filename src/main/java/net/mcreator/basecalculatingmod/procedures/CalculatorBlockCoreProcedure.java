package net.mcreator.basecalculatingmod.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;

import net.mcreator.basecalculatingmod.init.BaseCalculatingModModMenus;

import static BaseCalculatorLogic.Calculator.calculate;

public class CalculatorBlockCoreProcedure {
	public static boolean eventResult = true;

	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		String expression = "";
		StringBuilder out = new StringBuilder();
		expression = (entity instanceof Player _entity0 && _entity0.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "calcField", "") : "";
		try {
			if (entity instanceof Player _player && _player.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu) {
				_menu.sendMenuStateUpdate(_player, 0, "calcField", calculate(expression, out), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (world instanceof ServerLevel _level) {
			_level.getServer().getPlayerList().broadcastSystemMessage(Component.literal(out.toString()), false);
		}
		out.setLength(0);
	}
}