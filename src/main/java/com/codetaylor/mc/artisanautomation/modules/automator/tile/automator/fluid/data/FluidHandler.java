package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.fluid.data;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.TileAutomator;
import com.codetaylor.mc.artisanworktables.lib.IBooleanSupplier;
import com.codetaylor.mc.athenaeum.inventory.ObservableFluidTank;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataFluidTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FluidHandler
    extends ObservableFluidTank
    implements ITileDataFluidTank {

  private FluidStack memoryStack;

  private final IBooleanSupplier locked;
  private final Supplier<TileAutomator.EnumFluidMode> mode;

  public FluidHandler(
      int capacity,
      IBooleanSupplier locked,
      Supplier<TileAutomator.EnumFluidMode> mode
  ) {

    super(capacity);
    this.locked = locked;
    this.mode = mode;
  }

  public FluidStack getMemoryStack() {

    return this.memoryStack;
  }

  public boolean isLocked() {

    return this.locked.get();
  }

  public boolean updateMemory() {

    if (this.locked.get()) {
      // update the handler's memory
      FluidStack fluid = this.getFluid();

      if (fluid != null) {
        this.memoryStack = fluid.copy();
        return true;
      }
    }

    // clear the handler's memory
    return this.clearMemory();
  }

  private boolean clearMemory() {

    if (this.memoryStack != null) {
      this.memoryStack = null;
      return true;
    }

    return false;
  }

  public boolean clearAll() {

    FluidStack drained = super.drainInternal(this.getFluidAmount(), true);
    boolean memoryCleared = this.clearMemory();
    return (drained != null && drained.amount > 0) || memoryCleared;
  }

  @Override
  public FluidTank readFromNBT(NBTTagCompound nbt) {

    super.readFromNBT(nbt);

    if (nbt.hasKey("memoryStack")) {
      NBTTagCompound memoryStackTag = nbt.getCompoundTag("memoryStack");
      this.memoryStack = FluidStack.loadFluidStackFromNBT(memoryStackTag);

    } else {
      this.memoryStack = null;
    }
    return this;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

    super.writeToNBT(nbt);

    if (this.memoryStack != null) {
      nbt.setTag("memoryStack", this.memoryStack.writeToNBT(new NBTTagCompound()));
    }
    return nbt;
  }

  @Override
  public int fillInternal(FluidStack resource, boolean doFill) {

    /*
    Do nothing if the tank's mode is not set to fill.
    If the tank is locked and the input fluid does not match the remembered
    fluid, do nothing.
    If the tank is unlocked and it was actually filled, set the remembered
    fluid to the input fluid.
     */

    if (this.mode.get() != TileAutomator.EnumFluidMode.Fill) {
      return 0;
    }

    if (this.locked.get()
        && !resource.isFluidEqual(this.memoryStack)) {
      return 0;
    }

    int filled = super.fillInternal(resource, doFill);

    if (doFill
        && !this.locked.get()
        && filled > 0) {
      this.memoryStack = resource.copy();
    }

    return filled;
  }

  @Nullable
  @Override
  public FluidStack drainInternal(int maxDrain, boolean doDrain) {

    /*
    Do nothing if the tank's mode is not set to drain.
    If the tank is unlocked and it was actually emptied by this drain,
    clear the remembered fluid.
     */

    if (this.mode.get() != TileAutomator.EnumFluidMode.Drain) {
      return null;
    }

    FluidStack fluidStack = super.drainInternal(maxDrain, doDrain);

    if (doDrain
        && !this.locked.get()
        && fluidStack != null
        && fluidStack.amount > 0
        && this.getFluidAmount() == 0) {
      this.memoryStack = null;
    }

    return fluidStack;
  }

  @Override
  public void setCapacity(int capacity) {

    this.capacity = capacity;

    if (this.fluid != null
        && this.fluid.amount > capacity) {
      this.forceDrain(this.fluid.amount - capacity);
    }
  }

  public FluidStack forceDrain(int maxDrain) {

    FluidStack fluidStack = super.drainInternal(maxDrain, true);

    if (!this.locked.get()
        && fluidStack != null
        && fluidStack.amount > 0
        && this.getFluidAmount() == 0) {
      this.memoryStack = null;
    }

    return fluidStack;
  }
}
