package net.mcreator.basecalculatingmod.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.basecalculatingmod.init.BaseCalculatingModModMenus;

import static BaseCalculatorLogic.Calculator.calculate;

public class CalculatorBlockCoreProcedure {
	public static boolean eventResult = true;

	public static void execute(Entity entity) {
		if (entity == null)
			return;
		String expression = "";

		try {
			expression = (entity instanceof Player _entity0 && _entity0.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "calcField", "") : "";
			if (entity instanceof Player _player && _player.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu) {
				_menu.sendMenuStateUpdate(_player, 0, "calcField", calculate(expression), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}