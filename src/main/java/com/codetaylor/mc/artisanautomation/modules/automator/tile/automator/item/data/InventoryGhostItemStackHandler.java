package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;

public class InventoryGhostItemStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public InventoryGhostItemStackHandler() {

    super(26);
  }

  @Override
  public int getSlotLimit(int slot) {

    return 1;
  }
}
