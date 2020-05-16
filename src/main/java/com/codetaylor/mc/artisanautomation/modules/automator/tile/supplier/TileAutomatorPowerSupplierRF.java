package com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.ITileAutomatorBlock;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier.energy.EnergyCapabilityDelegate;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier.fluid.FluidCapabilityDelegate;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier.item.ItemCapabilityDelegate;
import com.codetaylor.mc.athenaeum.spi.TileEntityBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileAutomatorPowerSupplierRF
    extends TileEntityBase
    implements ITileAutomatorPowerSupplier {

  private final EnergyCapabilityDelegate energyCapabilityDelegate;
  private final ItemCapabilityDelegate itemCapabilityDelegate;
  private final FluidCapabilityDelegate fluidCapabilityDelegate;

  public TileAutomatorPowerSupplierRF() {

    this.energyCapabilityDelegate = new EnergyCapabilityDelegate();
    this.itemCapabilityDelegate = new ItemCapabilityDelegate();
    this.fluidCapabilityDelegate = new FluidCapabilityDelegate();
  }

  // --------------------------------------------------------------------------
  // - Accessors
  // --------------------------------------------------------------------------

  @Override
  public boolean isPowered() {

    TileEntity tileEntity = this.world.getTileEntity(this.pos.up());

    if (tileEntity instanceof ITileAutomatorBlock) {
      return ((ITileAutomatorBlock) tileEntity).isPowered();
    }

    return false;
  }

  // --------------------------------------------------------------------------
  // - Update
  // --------------------------------------------------------------------------

  public void neighborChanged() {

    this.updateCapabilityDelegates();
  }

  private void updateCapabilityDelegates() {

    TileEntity tileEntity = this.world.getTileEntity(this.pos.up());

    if (tileEntity instanceof ITileAutomatorBlock) {
      this.energyCapabilityDelegate.setEnergyStorage(
          tileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.DOWN)
      );
      this.itemCapabilityDelegate.setItemHandler(
          tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
      );
      this.fluidCapabilityDelegate.setFluidHandler(
          tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.DOWN)
      );

    } else {
      this.energyCapabilityDelegate.setEnergyStorage(null);
      this.itemCapabilityDelegate.setItemHandler(null);
      this.fluidCapabilityDelegate.setFluidHandler(null);
    }
  }

  // --------------------------------------------------------------------------
  // - Capabilities
  // --------------------------------------------------------------------------

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {

    return (capability == CapabilityEnergy.ENERGY
        || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
        || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {

    if (capability == CapabilityEnergy.ENERGY) {
      this.updateCapabilityDelegates();
      //noinspection unchecked
      return (T) this.energyCapabilityDelegate;

    } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      this.updateCapabilityDelegates();
      //noinspection unchecked
      return (T) this.itemCapabilityDelegate;

    } else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      this.updateCapabilityDelegates();
      //noinspection unchecked
      return (T) this.fluidCapabilityDelegate;
    }

    return null;
  }

}
