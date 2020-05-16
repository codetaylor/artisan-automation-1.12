package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.ItemStack;

public class OutputItemStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public OutputItemStackHandler() {

    super(9);
  }

  public OutputItemStackHandler(OutputItemStackHandler toCopy) {

    super(toCopy.getSlots());

    for (int i = 0; i < toCopy.getSlots(); i++) {
      this.setStackInSlot(i, toCopy.getStackInSlot(i).copy());
    }
  }

  /**
   * Attempt to insert the given item stack into all slots in this handler
   * starting with slot 0.
   *
   * @param itemStack the stack to insert
   * @param simulate  simulate
   * @return the items that couldn't be inserted
   */
  public ItemStack insert(ItemStack itemStack, boolean simulate) {

    for (int i = 0; i < this.getSlots(); i++) {
      itemStack = this.insertItem(i, itemStack, simulate);

      if (itemStack.isEmpty()) {
        break;
      }
    }

    return itemStack;
  }

  /**
   * Loop through the handler's slots starting with the second slot. If
   * the slot isn't empty, remove the slot's stack and try to place the
   * removed stack into all slots up to and including the current slot
   * that was just emptied.
   */
  public void settleStacks() {

    for (int j = 1; j < this.getSlots(); j++) {
      ItemStack stackInSlot = this.getStackInSlot(j);

      if (!stackInSlot.isEmpty()) {
        int count = stackInSlot.getCount();
        stackInSlot = this.extractItem(j, count, false);

        for (int k = 0; k <= j; k++) {
          stackInSlot = this.insertItem(k, stackInSlot, false);

          if (stackInSlot.isEmpty()) {
            break;
          }
        }
      }
    }
  }
}
