package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.artisanworktables.modules.worktables.block.BlockBase;
import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class TableItemStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public TableItemStackHandler() {

    super(1);
  }

  @Override
  public int getSlotLimit(int slot) {

    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    if (!(stack.getItem() instanceof ItemBlock)) {
      return false;
    }

    Block block = ((ItemBlock) stack.getItem()).getBlock();
    return (block instanceof BlockBase);
  }
}
