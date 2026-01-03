package net.mcreator.basecalculatingmod.procedures;

import BaseCalculatorLogic.NumberWithBase;
import BaseConverterLogic.Converter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.basecalculatingmod.init.BaseCalculatingModModMenus;

public class ConverterBlockCoreProcedureProcedure {
	public static boolean eventResult = true;

	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double inputBase = 0;
		double outputBase = 0;
		String inputNumber = "";
		String outputNumber = "";
		inputNumber = (entity instanceof Player _entity0 && _entity0.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu0) ? _menu0.getMenuState(0, "converterTextField", "") : "";
		inputBase = (entity instanceof Player _entity1 && _entity1.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu1) ? _menu1.getMenuState(2, "converterFirstBaseSlider", 0.0) : 0.0;
		outputBase = (entity instanceof Player _entity2 && _entity2.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu2) ? _menu2.getMenuState(2, "converterSecondBaseSlider", 0.0) : 0.0;
		if (entity instanceof Player _player && _player.containerMenu instanceof BaseCalculatingModModMenus.MenuAccessor _menu) {
			outputNumber = Converter.convert(inputNumber, (int) inputBase, (int) outputBase);
			_menu.sendMenuStateUpdate(_player, 0, "converterTextField", outputNumber, true);
		}
	}
}