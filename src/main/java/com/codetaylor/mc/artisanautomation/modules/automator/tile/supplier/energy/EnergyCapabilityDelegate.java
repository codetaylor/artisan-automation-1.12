package com.codetaylor.mc.artisanautomation.modules.automator.tile.supplier.energy;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.EnergyStorageAdapter;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class EnergyCapabilityDelegate
    extends EnergyStorageAdapter {

  private IEnergyStorage energyStorage;

  public EnergyCapabilityDelegate setEnergyStorage(@Nullable IEnergyStorage energyStorage) {

    this.energyStorage = energyStorage;
    return this;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {

    if (this.energyStorage == null) {
      return 0;
    }

    return this.energyStorage.receiveEnergy(
        maxReceive,
        simulate
    );
  }

  @Override
  public int getEnergyStored() {

    if (this.energyStorage == null) {
      return 0;
    }

    return this.energyStorage.getEnergyStored();
  }

  @Override
  public int getMaxEnergyStored() {

    if (this.energyStorage == null) {
      return 0;
    }

    return this.energyStorage.getMaxEnergyStored();
  }

  @Override
  public boolean canReceive() {

    return (this.energyStorage != null);
  }
}
