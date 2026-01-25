package net.mcreator.basecalculatingmod.network;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.basecalculatingmod.procedures.CalculatorBlockCoreProcedure;
import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record CalculatorGUIButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<CalculatorGUIButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BaseCalculatingModMod.MODID, "calculator_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CalculatorGUIButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, CalculatorGUIButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new CalculatorGUIButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<CalculatorGUIButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final CalculatorGUIButtonMessage message, final ServerPlayNetworking.Context context) {
		context.server().execute(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z));
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			CalculatorBlockCoreProcedure.execute(world, entity);
		}
	}
}