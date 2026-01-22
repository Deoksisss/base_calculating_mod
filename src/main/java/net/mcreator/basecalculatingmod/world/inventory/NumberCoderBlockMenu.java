package net.mcreator.basecalculatingmod.world.inventory;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.basecalculatingmod.network.NumberCoderBlockButtonMessage;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModMenus;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class NumberCoderBlockMenu extends AbstractContainerMenu implements BaseCalculatingModModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 5)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private final Container inventory;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private ItemStack boundItem = null;

	public NumberCoderBlockMenu(int id, Inventory inv) {
		this(id, inv, new SimpleContainer(0));
		this.x = (int) inv.player.getX();
		this.y = (int) inv.player.getY();
		this.z = (int) inv.player.getZ();
		access = ContainerLevelAccess.create(inv.player.level(), new BlockPos(x, y, z));
	}

	public NumberCoderBlockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, new SimpleContainer(0), extraData);
	}

	public NumberCoderBlockMenu(int id, Inventory inv, Container container, FriendlyByteBuf extraData) {
		this(id, inv, container);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
		}
	}

	public NumberCoderBlockMenu(int id, Inventory inv, Container container) {
		super(BaseCalculatingModModMenus.NUMBER_CODER_BLOCK, id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.inventory = container;
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.bound) {
			if (this.boundItemMatcher != null)
				return this.boundItemMatcher.get();
		}
		return this.inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}

	public static void screenInit() {
		PayloadTypeRegistry.playC2S().register(NumberCoderBlockButtonMessage.TYPE, NumberCoderBlockButtonMessage.STREAM_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(NumberCoderBlockButtonMessage.TYPE, NumberCoderBlockButtonMessage::handleData);
	}
}