package com.codetaylor.mc.artisanautomation.modules.automator.gui.slot;

import com.codetaylor.mc.artisanautomation.modules.automator.gui.AutomatorContainer;
import net.minecraftforge.items.IItemHandler;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ToolUpgradePanelSlot
    extends PanelSlot {

  private final BooleanSupplier hasToolbox;

  public ToolUpgradePanelSlot(
      Supplier<AutomatorContainer.EnumState> currentState,
      AutomatorContainer.EnumState state,
      BooleanSupplier hasToolbox,
      IItemHandler itemHandler,
      int index,
      int xPosition,
      int yPosition
  ) {

    super(currentState, state, itemHandler, index, xPosition, yPosition);
    this.hasToolbox = hasToolbox;
  }

  @Override
  public boolean isEnabled() {

    return this.hasToolbox.getAsBoolean() && super.isEnabled();
  }
}
