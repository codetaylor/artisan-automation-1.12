package com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier.delegate;

import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class FluidCapabilityDelegate
    extends FluidHandlerDelegate {

  public void setFluidHandler(@Nullable IFluidHandler iFluidHandler) {

    this.fluidHandler = iFluidHandler;
  }
}
