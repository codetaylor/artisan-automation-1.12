package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.artisanautomation.modules.automator.item.ItemUpgrade;
import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ToolUpgradeStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public ToolUpgradeStackHandler() {

    super(6);
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    return ItemUpgrade.isToolUpgrade(stack);
  }
}
