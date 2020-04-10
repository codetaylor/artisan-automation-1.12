package com.codetaylor.mc.artisanautomation.modules.automator.gui;

import com.codetaylor.mc.artisanautomation.modules.automator.gui.slot.*;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.TileAutomator;
import com.codetaylor.mc.athenaeum.gui.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AutomatorContainer
    extends ContainerBase {

  private final World world;
  private final TileAutomator tile;

  public enum EnumState {
    Gear(0), Pattern(1), Inventory(2), Fluid(3), Tool(4);

    private final int index;

    EnumState(int index) {

      this.index = index;
    }

    public int getIndex() {

      return this.index;
    }

    public static EnumState fromIndex(int index) {

      for (EnumState value : EnumState.values()) {
        if (value.index == index) {
          return value;
        }
      }
      throw new IllegalArgumentException("Unknown index: " + index);
    }
  }

  private EnumState state;

  private final int slotIndexPlayerInventoryStart;
  private final int slotIndexPlayerInventoryEnd;
  private final int slotIndexPlayerHotbarStart;
  private final int slotIndexPlayerHotbarEnd;
  private final int slotIndexTable;
  private final int slotIndexUpgradeStart;
  private final int slotIndexUpgradeEnd;
  private final int slotIndexPatternStart;
  private final int slotIndexPatternEnd;
  private final int slotIndexOutputStart;
  private final int slotIndexOutputEnd;
  private final int slotIndexInventoryStart;
  private final int slotIndexInventoryEnd;
  private final int slotIndexBucketStart;
  private final int slotIndexBucketEnd;
  private final int slotIndexToolStart;
  private final int slotIndexToolEnd;
  private final int slotIndexToolbox;
  private final int slotIndexToolUpgradeStart;
  private final int slotIndexToolUpgradeEnd;

  public AutomatorContainer(
      InventoryPlayer inventoryPlayer,
      World world,
      TileAutomator tile
  ) {

    super(inventoryPlayer);
    this.world = world;
    this.tile = tile;

    this.state = EnumState.Gear;

    this.slotIndexPlayerInventoryStart = this.nextSlotIndex;
    this.containerPlayerInventoryAdd();
    this.slotIndexPlayerInventoryEnd = this.nextSlotIndex - 1;

    this.slotIndexPlayerHotbarStart = this.nextSlotIndex;
    this.containerPlayerHotbarAdd();
    this.slotIndexPlayerHotbarEnd = this.nextSlotIndex - 1;

    this.slotIndexTable = this.nextSlotIndex;
    this.containerSlotAdd(new TableSlot(
        () -> this.state, EnumState.Gear,
        this.tile.getTableItemStackHandler(), 0, 26, 56
    ));

    this.slotIndexUpgradeStart = this.nextSlotIndex;
    for (int i = 0; i < 5; i++) {
      this.containerSlotAdd(new PanelSlot(
          () -> this.state, EnumState.Gear,
          this.tile.getUpgradeItemStackHandler(), i, 71 + (i * 18), 65
      ));
    }
    this.slotIndexUpgradeEnd = this.nextSlotIndex - 1;

    this.slotIndexPatternStart = this.nextSlotIndex;
    for (int i = 0; i < 9; i++) {
      this.containerSlotAdd(new PanelSlot(
          () -> this.state, EnumState.Pattern,
          this.tile.getPatternItemStackHandler(), i, 8 + (i * 18), 38
      ));
    }
    this.slotIndexPatternEnd = this.nextSlotIndex - 1;

    this.slotIndexOutputStart = this.nextSlotIndex;
    for (int i = 0; i < 9; i++) {
      this.containerSlotAdd(new OutputPanelSlot(
          () -> this.state, EnumState.Pattern,
          this.tile.getOutputItemStackHandler(i), 0, 8 + (i * 18), 38 + 18
      ));
    }
    this.slotIndexOutputEnd = this.nextSlotIndex - 1;

    this.slotIndexInventoryStart = this.nextSlotIndex;
    for (int i = 0; i < 26; i++) {
      int x = i % 9;
      int y = i / 9;
      this.containerSlotAdd(new InventorySlot(
          () -> this.state, EnumState.Inventory,
          this.tile.getInventoryItemStackHandler(), i, 8 + (x * 18), 38 + (y * 18)
      ));
    }
    this.slotIndexInventoryEnd = this.nextSlotIndex - 1;

    this.slotIndexBucketStart = this.nextSlotIndex;
    for (int i = 0; i < 3; i++) {
      this.containerSlotAdd(new PanelSlot(
          () -> this.state, EnumState.Fluid,
          this.tile.getBucketItemStackHandler(), i, 8, 38 + (i * 18)
      ));
    }
    this.slotIndexBucketEnd = this.nextSlotIndex - 1;

    this.slotIndexToolStart = this.nextSlotIndex;
    for (int i = 0; i < 6; i++) {
      this.containerSlotAdd(new PanelSlot(
          () -> this.state, EnumState.Tool,
          this.tile.getToolStackHandler(), i, 8 + (i * 18), 38
      ));
    }

    for (int i = 6; i < 12; i++) {
      this.containerSlotAdd(new PanelSlot(
          () -> this.state, EnumState.Tool,
          this.tile.getToolStackHandler(), i, 8 + ((i - 6) * 18), 38 + (2 * 18)
      ));
    }
    this.slotIndexToolEnd = this.nextSlotIndex - 1;

    this.slotIndexToolbox = this.nextSlotIndex;
    this.containerSlotAdd(new ToolBoxPanelSlot(
        () -> this.state, EnumState.Tool,
        this.tile.getToolboxStackHandler(),
        this.tile.getToolUpgradeStackHandler(),
        this.tile.getPos(),
        inventoryPlayer.player, 0, 8 + 7 * 18, 56
    ));

    this.slotIndexToolUpgradeStart = this.nextSlotIndex;
    for (int i = 0; i < 6; i++) {
      this.containerSlotAdd(new ToolUpgradePanelSlot(
          () -> this.state, EnumState.Tool,
          () -> !this.tile.getToolboxStackHandler().getStackInSlot(0).isEmpty(),
          this.tile.getToolUpgradeStackHandler(), i, 8 + i * 18, 56
      ));
    }
    this.slotIndexToolUpgradeEnd = this.nextSlotIndex - 1;
  }

  // ---------------------------------------------------------------------------
  // - Slot Index Check
  // ---------------------------------------------------------------------------

  private boolean isSlotIndexPlayerInventory(int slotIndex) {

    return slotIndex >= this.slotIndexPlayerInventoryStart && slotIndex <= this.slotIndexPlayerInventoryEnd;
  }

  private boolean isSlotIndexPlayerHotbar(int slotIndex) {

    return slotIndex >= this.slotIndexPlayerHotbarStart && slotIndex <= this.slotIndexPlayerHotbarEnd;
  }

  private boolean isSlotIndexTable(int slotIndex) {

    return slotIndex == this.slotIndexTable;
  }

  private boolean isSlotIndexUpgrade(int slotIndex) {

    return slotIndex >= this.slotIndexUpgradeStart && slotIndex <= this.slotIndexUpgradeEnd;
  }

  private boolean isSlotIndexPattern(int slotIndex) {

    return slotIndex >= this.slotIndexPatternStart && slotIndex <= this.slotIndexPatternEnd;
  }

  private boolean isSlotIndexOutput(int slotIndex) {

    return slotIndex >= this.slotIndexOutputStart && slotIndex <= this.slotIndexOutputEnd;
  }

  private boolean isSlotIndexInventory(int slotIndex) {

    return slotIndex >= this.slotIndexInventoryStart && slotIndex <= this.slotIndexInventoryEnd;
  }

  private boolean isSlotIndexBucket(int slotIndex) {

    return slotIndex >= this.slotIndexBucketStart && slotIndex <= this.slotIndexBucketEnd;
  }

  private boolean isSlotIndexTool(int slotIndex) {

    return slotIndex >= this.slotIndexToolStart && slotIndex <= this.slotIndexToolEnd;
  }

  private boolean isslotIndexToolbox(int slotIndex) {

    return slotIndex == this.slotIndexToolbox;
  }

  private boolean isSlotIndexToolUpgrade(int slotIndex) {

    return slotIndex >= this.slotIndexToolUpgradeStart && slotIndex <= this.slotIndexToolUpgradeEnd;
  }

  // ---------------------------------------------------------------------------
  // - Slot Merge
  // ---------------------------------------------------------------------------

  private boolean mergePlayerInventory(ItemStack itemStack, boolean reverse) {

    return this.mergeItemStack(itemStack, this.slotIndexPlayerInventoryStart, this.slotIndexPlayerInventoryEnd + 1, reverse);
  }

  private boolean mergePlayerHotbar(ItemStack itemStack, boolean reverse) {

    return this.mergeItemStack(itemStack, this.slotIndexPlayerHotbarStart, this.slotIndexPlayerHotbarEnd + 1, reverse);
  }

  private boolean mergeTable(ItemStack itemStack) {

    return this.state == EnumState.Gear
        && this.mergeItemStack(itemStack, this.slotIndexTable, this.slotIndexTable + 1, false);
  }

  private boolean mergeUpgrade(ItemStack itemStack) {

    return this.state == EnumState.Gear
        && this.mergeItemStack(itemStack, this.slotIndexUpgradeStart, this.slotIndexUpgradeEnd + 1, false);
  }

  private boolean mergePattern(ItemStack itemStack) {

    return this.state == EnumState.Pattern
        && this.mergeItemStack(itemStack, this.slotIndexPatternStart, this.slotIndexPatternEnd + 1, false);
  }

  private boolean mergeInventory(ItemStack itemStack) {

    return this.state == EnumState.Inventory
        && this.mergeItemStack(itemStack, this.slotIndexInventoryStart, this.slotIndexInventoryEnd + 1, false);
  }

  private boolean mergeBucket(ItemStack itemStack) {

    return this.state == EnumState.Fluid
        && this.mergeItemStack(itemStack, this.slotIndexBucketStart, this.slotIndexBucketEnd + 1, false);
  }

  private boolean mergeTool(ItemStack itemStack) {

    return this.state == EnumState.Tool
        && this.mergeItemStack(itemStack, this.slotIndexToolStart, this.slotIndexToolEnd + 1, false);
  }

  private boolean mergeToolbox(ItemStack itemStack) {

    return this.state == EnumState.Tool
        && this.mergeItemStack(itemStack, this.slotIndexToolbox, this.slotIndexToolbox + 1, false);
  }

  private boolean mergeToolUpgrade(ItemStack itemStack) {

    return this.state == EnumState.Tool
        && !this.tile.getToolboxStackHandler().getStackInSlot(0).isEmpty()
        && this.mergeItemStack(itemStack, this.slotIndexToolUpgradeStart, this.slotIndexToolUpgradeEnd + 1, false);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

    ItemStack itemStackCopy = ItemStack.EMPTY;
    Slot slot = this.inventorySlots.get(slotIndex);

    if (slot != null && slot.getHasStack()) {
      ItemStack itemStack = slot.getStack();
      itemStackCopy = itemStack.copy();

      if (this.isSlotIndexPlayerInventory(slotIndex)) {

        if (!this.mergeIntoDevice(itemStack)
            && !this.mergePlayerHotbar(itemStack, false)) {
          return ItemStack.EMPTY;
        }

      } else if (this.isSlotIndexPlayerHotbar(slotIndex)) {

        if (!this.mergeIntoDevice(itemStack)
            && !this.mergePlayerInventory(itemStack, false)) {
          return ItemStack.EMPTY;
        }

      } else {

        if (!this.mergePlayerHotbar(itemStack, false)
            && !this.mergePlayerInventory(itemStack, false)) {
          return ItemStack.EMPTY;
        }
      }

      if (itemStack.isEmpty()) {
        slot.putStack(ItemStack.EMPTY);

      } else {
        slot.onSlotChanged();
      }

      if (itemStack.getCount() == itemStackCopy.getCount()) {
        return ItemStack.EMPTY;
      }
    }

    return itemStackCopy;
  }

  private boolean mergeIntoDevice(ItemStack itemStack) {

    return this.mergeTable(itemStack)
        || this.mergeUpgrade(itemStack)
        || this.mergePattern(itemStack)
        || this.mergeInventory(itemStack)
        || this.mergeBucket(itemStack)
        || this.mergeTool(itemStack)
        || this.mergeToolbox(itemStack)
        || this.mergeToolUpgrade(itemStack);
  }

  // ---------------------------------------------------------------------------

  @Override
  protected int containerInventoryPositionGetY() {

    return 108;
  }

  @Override
  protected int containerHotbarPositionGetY() {

    return 166;
  }

  public boolean setState(EnumState state) {

    if (this.state != state) {
      this.state = state;
      return true;
    }

    return false;
  }

  public EnumState getState() {

    return this.state;
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {

    return player.getDistanceSq((double) this.tile.getPos().getX() + 0.5D, (double) this.tile.getPos().getY() + 0.5D, (double) this.tile.getPos().getZ() + 0.5D) <= 64.0D;
  }
}
