package com.codetaylor.mc.artisanautomation.modules.automator.gui.slot;

import com.codetaylor.mc.artisanautomation.modules.automator.gui.AutomatorContainer;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Supplier;

public class InventorySlot
    extends PanelSlot {

  private final int index;

  public InventorySlot(
      Supplier<AutomatorContainer.EnumState> currentState,
      AutomatorContainer.EnumState state,
      IItemHandler itemHandler,
      int index,
      int xPosition,
      int yPosition
  ) {

    super(currentState, state, itemHandler, index, xPosition, yPosition);
    this.index = index;
  }

  public int getIndex() {

    return this.index;
  }
}
