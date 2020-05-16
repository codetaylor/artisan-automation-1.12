package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data;

import com.codetaylor.mc.artisanworktables.api.ArtisanAPI;
import com.codetaylor.mc.athenaeum.inventory.ObservableStackHandler;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataItemStackHandler;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ToolStackHandler
    extends ObservableStackHandler
    implements ITileDataItemStackHandler {

  public ToolStackHandler() {

    super(12);
  }

  @Override
  public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    return ArtisanAPI.containsRecipeWithTool(stack);
  }
}
