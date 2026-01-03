package net.mcreator.basecalculatingmod.world.inventory;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.basecalculatingmod.network.ConverterBlockGUIButtonMessage;
import net.mcreator.basecalculatingmod.init.BaseCalculatingModModMenus;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class ConverterBlockGUIMenu extends AbstractContainerMenu implements BaseCalculatingModModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 4)
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

	public ConverterBlockGUIMenu(int id, Inventory inv) {
		this(id, inv, new SimpleContainer(0));
		this.x = (int) inv.player.getX();
		this.y = (int) inv.player.getY();
		this.z = (int) inv.player.getZ();
		access = ContainerLevelAccess.create(inv.player.level(), new BlockPos(x, y, z));
	}

	public ConverterBlockGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, new SimpleContainer(0), extraData);
	}

	public ConverterBlockGUIMenu(int id, Inventory inv, Container container, FriendlyByteBuf extraData) {
		this(id, inv, container);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
		}
		if (pos != null) {
			if (extraData.readableBytes() == 1) { // bound to item
				byte hand = extraData.readByte();
				ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();
				this.boundItem = itemstack;
				this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
				this.bound = true;
			}
		}
	}

	public ConverterBlockGUIMenu(int id, Inventory inv, Container container) {
		super(BaseCalculatingModModMenus.CONVERTER_BLOCK_GUI, id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.inventory = container;
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
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
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 0) {
				if (!this.moveItemStackTo(itemstack1, 0, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (boundItem != null && itemstack1 == boundItem) {
				return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(itemstack1, 0, 0, false)) {
				if (index < 0 + 27) {
					if (!this.moveItemStackTo(itemstack1, 0 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 0, 0 + 27, false))
						return ItemStack.EMPTY;
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Override
	public void clicked(int slotId, int button, ClickType clickType, Player player) {
		if (clickType == ClickType.SWAP && boundItem != null) {
			if (slotId >= 0 && slotId < this.slots.size()) {
				ItemStack slotItem = this.slots.get(slotId).getItem();
				ItemStack hotbarItem = player.getInventory().getItem(button);
				if (slotItem == boundItem || hotbarItem == boundItem) {
					return;
				}
			}
		}
		super.clicked(slotId, button, clickType, player);
	}

	@Override
	protected boolean moveItemStackTo(ItemStack itemstack, int i, int j, boolean bl) {
		int l;
		ItemStack itemstack2;
		Slot slot;
		boolean bl2 = false;
		int k = i;
		if (bl) {
			k = j - 1;
		}
		if (itemstack.isStackable()) {
			while (!itemstack.isEmpty() && (bl ? k >= i : k < j)) {
				slot = this.slots.get(k);
				itemstack2 = slot.getItem();
				if (!itemstack2.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, itemstack2)) {
					int m;
					l = itemstack2.getCount() + itemstack.getCount();
					if (l <= (m = slot.getMaxStackSize(itemstack2))) {
						itemstack.setCount(0);
						itemstack2.setCount(l);
						slot.set(itemstack2);
						bl2 = true;
					} else if (itemstack2.getCount() < m) {
						itemstack.shrink(m - itemstack2.getCount());
						itemstack2.setCount(m);
						slot.set(itemstack2);
						bl2 = true;
					}
				}
				if (bl) {
					--k;
					continue;
				}
				++k;
			}
		}
		if (!itemstack.isEmpty()) {
			k = bl ? j - 1 : i;
			while (bl ? k >= i : k < j) {
				slot = this.slots.get(k);
				itemstack2 = slot.getItem();
				if (itemstack2.isEmpty() && slot.mayPlace(itemstack)) {
					l = slot.getMaxStackSize(itemstack);
					slot.setByPlayer(itemstack.split(Math.min(itemstack.getCount(), l)));
					bl2 = true;
					break;
				}
				if (bl) {
					--k;
					continue;
				}
				++k;
			}
		}
		return bl2;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
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
		PayloadTypeRegistry.playC2S().register(ConverterBlockGUIButtonMessage.TYPE, ConverterBlockGUIButtonMessage.STREAM_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ConverterBlockGUIButtonMessage.TYPE, ConverterBlockGUIButtonMessage::handleData);
	}
}