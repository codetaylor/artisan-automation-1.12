package com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier.item;

import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class ItemCapabilityDelegate
    extends ItemHandlerDelegate {

  public void setItemHandler(@Nullable IItemHandler iItemHandler) {

    this.itemHandler = iItemHandler;
  }
}
