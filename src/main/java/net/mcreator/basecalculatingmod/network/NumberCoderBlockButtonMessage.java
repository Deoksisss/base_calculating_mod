package net.mcreator.basecalculatingmod.network;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.basecalculatingmod.procedures.ReverseCoderProcedure;
import net.mcreator.basecalculatingmod.procedures.DirectCoderProcedure;
import net.mcreator.basecalculatingmod.procedures.AdditionalCoderProcedure;
import net.mcreator.basecalculatingmod.BaseCalculatingModMod;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record NumberCoderBlockButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<NumberCoderBlockButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BaseCalculatingModMod.MODID, "number_coder_block_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, NumberCoderBlockButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, NumberCoderBlockButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new NumberCoderBlockButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<NumberCoderBlockButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final NumberCoderBlockButtonMessage message, final ServerPlayNetworking.Context context) {
		context.server().execute(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z));
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			DirectCoderProcedure.execute(world, entity);
		}
		if (buttonID == 1) {

			ReverseCoderProcedure.execute(world, entity);
		}
		if (buttonID == 2) {

			AdditionalCoderProcedure.execute(world, entity);
		}
	}
}