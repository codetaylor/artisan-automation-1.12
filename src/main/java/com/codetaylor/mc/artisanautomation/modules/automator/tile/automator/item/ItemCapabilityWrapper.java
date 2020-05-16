package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data.InventoryItemStackHandler;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data.OutputItemStackHandler;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data.InventoryGhostItemStackHandler;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.TileAutomator;
import com.codetaylor.mc.athenaeum.network.tile.data.TileDataEnum;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ItemCapabilityWrapper
    extends GhostItemInsertValidationItemHandlerWrapper {

  private final OutputItemStackHandler[] outputItemStackHandlers;
  private final List<TileDataEnum<TileAutomator.EnumOutputMode>> outputModes;

  public ItemCapabilityWrapper(
      InventoryItemStackHandler inventoryItemStackHandler,
      InventoryGhostItemStackHandler inventoryGhostItemStackHandler,
      OutputItemStackHandler[] outputItemStackHandlers,
      List<TileDataEnum<TileAutomator.EnumOutputMode>> outputModes,
      BooleanSupplier inventoryLocked
  ) {

    super(inventoryItemStackHandler, inventoryGhostItemStackHandler, inventoryLocked);
    this.outputItemStackHandlers = outputItemStackHandlers;
    this.outputModes = outputModes;
  }

  @Override
  public int getSlots() {

    return super.getSlots() + this.outputItemStackHandlers.length;
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {

    if (slot < super.getSlots()) {
      return super.getStackInSlot(slot);
    }

    return this.outputItemStackHandlers[slot - super.getSlots()].getStackInSlot(0);
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {

    if (slot < super.getSlots()) {
      return ItemStack.EMPTY;
    }

    int actualSlot = slot - super.getSlots();

    if (this.outputModes.get(actualSlot).get() != TileAutomator.EnumOutputMode.Manual) {
      return ItemStack.EMPTY;
    }

    return this.outputItemStackHandlers[actualSlot].extractItem(0, amount, simulate);
  }

  @Override
  public int getSlotLimit(int slot) {

    if (slot < super.getSlots()) {
      return super.getSlotLimit(slot);
    }

    return this.outputItemStackHandlers[slot - super.getSlots()].getSlotLimit(0);
  }
}
