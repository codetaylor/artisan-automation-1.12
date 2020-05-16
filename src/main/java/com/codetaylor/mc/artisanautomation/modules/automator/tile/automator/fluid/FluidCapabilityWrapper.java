package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.fluid;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.fluid.data.FluidHandler;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluidCapabilityWrapper
    implements IFluidHandler {

  private final FluidHandler[] fluidHandler;

  private IFluidTankProperties[] tankProperties;

  public FluidCapabilityWrapper(
      FluidHandler[] fluidHandler
  ) {

    this.fluidHandler = fluidHandler;
  }

  @Override
  public IFluidTankProperties[] getTankProperties() {

    if (this.tankProperties == null) {
      List<IFluidTankProperties> list = new ArrayList<>();

      for (FluidHandler handler : this.fluidHandler) {
        list.addAll(Arrays.asList(handler.getTankProperties()));
      }
      this.tankProperties = list.toArray(new IFluidTankProperties[0]);
    }

    return this.tankProperties;
  }

  @Override
  public int fill(FluidStack resource, boolean doFill) {

    FluidStack copy = resource.copy();
    int total = copy.amount;

    for (int i = 0; i < this.fluidHandler.length; i++) {
      int filled = this.fluidHandler[i].fill(copy, doFill);
      copy.amount -= filled;

      if (copy.amount <= 0) {
        return total;
      }
    }

    return total - copy.amount;
  }

  @Nullable
  @Override
  public FluidStack drain(FluidStack resource, boolean doDrain) {

    FluidStack toDrain = resource.copy();
    int totalAmountDrained = 0;

    for (int i = 0; i < this.fluidHandler.length; i++) {
      FluidStack drained = this.fluidHandler[i].drain(toDrain, doDrain);
      totalAmountDrained += (drained != null) ? drained.amount : 0;
      toDrain.amount -= (drained != null) ? drained.amount : 0;

      if (toDrain.amount <= 0) {
        break;
      }
    }

    return new FluidStack(resource, totalAmountDrained);
  }

  @Nullable
  @Override
  public FluidStack drain(int maxDrain, boolean doDrain) {

    if (maxDrain <= 0) {
      return null;
    }

    FluidStack result = null;
    int remainingDrain = maxDrain;

    for (int i = 0; i < this.fluidHandler.length; i++) {
      FluidStack drained = this.fluidHandler[i].drain(remainingDrain, false);

      if (drained == null) {
        continue;
      }

      remainingDrain -= drained.amount;

      if (result == null) {
        result = this.fluidHandler[i].drain(drained.amount, doDrain);

      } else {

        if (result.isFluidEqual(drained)) {
          this.fluidHandler[i].drain(drained.amount, doDrain);
          result.amount += drained.amount;
        }
      }

      if (remainingDrain <= 0) {
        break;
      }
    }

    return result;
  }
}
