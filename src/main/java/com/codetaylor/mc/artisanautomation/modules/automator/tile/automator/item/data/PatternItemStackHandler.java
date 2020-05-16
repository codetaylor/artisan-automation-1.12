package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.artisanworktables.modules.worktables.item.ItemDesignPattern;
import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class PatternItemStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public PatternItemStackHandler() {

    super(9);
  }

  @Override
  public int getSlotLimit(int slot) {

    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    Item item = stack.getItem();

    return (item instanceof ItemDesignPattern)
        && (((ItemDesignPattern) item).hasRecipe(stack));
  }
}
