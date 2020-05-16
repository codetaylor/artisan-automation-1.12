package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.artisanworktables.modules.toolbox.ModuleToolbox;
import com.codetaylor.mc.artisanworktables.modules.toolbox.ModuleToolboxConfig;
import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ToolboxStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public ToolboxStackHandler() {

    super(1);
  }

  @Override
  public int getSlotLimit(int slot) {

    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    return ModuleToolboxConfig.ENABLE_MODULE
        && ModuleToolboxConfig.MECHANICAL_TOOLBOX.ENABLED
        && stack.getItem() == Item.getItemFromBlock(ModuleToolbox.Blocks.MECHANICAL_TOOLBOX);
  }
}
