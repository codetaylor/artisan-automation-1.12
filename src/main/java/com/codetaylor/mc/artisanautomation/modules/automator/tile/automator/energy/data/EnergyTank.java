package com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.energy.data;

import com.codetaylor.mc.athenaeum.inventory.ObservableEnergyStorage;
import com.codetaylor.mc.athenaeum.network.tile.spi.ITileDataEnergyStorage;

public class EnergyTank
    extends ObservableEnergyStorage
    implements ITileDataEnergyStorage {

  public EnergyTank(int capacity, int maxReceive, int maxExtract) {

    super(capacity, maxReceive, maxExtract);
  }

  public void setCapacity(int capacity) {

    this.capacity = capacity;

    if (this.energy > capacity) {
      this.extractEnergy(this.energy - capacity, false);
    }
  }
}
