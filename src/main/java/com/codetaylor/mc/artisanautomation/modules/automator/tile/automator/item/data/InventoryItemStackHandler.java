package com.codetaylor.mc.artisanautomation.modules.automator.tile;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data.InventoryGhostItemStackHandler;
import com.codetaylor.mc.artisanworktables.lib.IBooleanSupplier;
import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.ItemStack;

public class InventoryItemStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  private final IBooleanSupplier isLocked;
  private final InventoryGhostItemStackHandler ghostItemStackHandler;

  /* package */ InventoryItemStackHandler(
      IBooleanSupplier isLocked,
      InventoryGhostItemStackHandler ghostItemStackHandler
  ) {

    super(26);
    this.isLocked = isLocked;
    this.ghostItemStackHandler = ghostItemStackHandler;
  }

  /* package */ InventoryItemStackHandler(InventoryItemStackHandler toCopy) {

    super(toCopy.getSlots());
    this.isLocked = toCopy.isLocked;
    this.ghostItemStackHandler = toCopy.ghostItemStackHandler;

    for (int i = 0; i < toCopy.getSlots(); i++) {
      this.setStackInSlot(i, toCopy.getStackInSlot(i).copy());
    }
  }

  @Override
  protected void onContentsChanged(int slot) {

    if (this.isLocked.get()) {
      ItemStack stackInSlot = this.getStackInSlot(slot);

      if (!stackInSlot.isEmpty()) {
        ItemStack copy = stackInSlot.copy();
        copy.setCount(1);
        this.ghostItemStackHandler.setStackInSlot(slot, copy);
      }
    }
    super.onContentsChanged(slot);
  }
}
