package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;

public class BucketItemStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public BucketItemStackHandler() {

    super(3);
  }

  @Override
  public int getSlotLimit(int slot) {

    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
  }
}
