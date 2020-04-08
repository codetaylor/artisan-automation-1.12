package com.codetaylor.mc.artisanautomation.modules.automator.gui.element;

import com.codetaylor.mc.artisanautomation.modules.automator.gui.AutomatorContainer;
import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.Texture;
import com.codetaylor.mc.athenaeum.gui.element.GuiElementTextureRectangle;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class GuiElementToolCap
    extends GuiElementTextureRectangle {

  private final Supplier<AutomatorContainer.EnumState> currentState;
  private final AutomatorContainer.EnumState state;
  private final BooleanSupplier hasToolbox;

  public GuiElementToolCap(
      Supplier<AutomatorContainer.EnumState> currentState,
      AutomatorContainer.EnumState state,
      BooleanSupplier hasToolbox,
      GuiContainerBase guiBase,
      Texture[] textures,
      int elementX,
      int elementY
  ) {

    super(guiBase, textures, elementX, elementY, 16, 16);
    this.currentState = currentState;
    this.state = state;
    this.hasToolbox = hasToolbox;
  }

  @Override
  public boolean elementIsVisible(int mouseX, int mouseY) {

    return (!this.hasToolbox.getAsBoolean() && this.currentState.get() == this.state);
  }
}
