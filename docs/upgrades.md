# Upgrades

The Mechanical Artisan allows any item to act as an upgrade so long as the item has the correct NBT data.

All upgrade NBT data is nested inside of a root `ArtisanWorktables` tag.

## Machine Upgrades

![upgrade-items](img/upgrade-items.png)

The mod comes packaged with several pre-made items that don't do anything by themselves, but can be used as upgrades if you add the correct NBT to them. 

All machine upgrade tags are nested inside of a root `Upgrade` tag.

Any number of the upgrade tags below can be combined into any upgrade.

### Speed

The `Speed` tag will increase the machine's operating speed using the given additive percentile modifier.

Range: `[0, -)`

```
<artisanautomation:upgrade_speed>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            Speed: 0.25, 
            EnergyUsage: -0.25
        }
    }
})
```

### EnergyUsage

The `EnergyUsage` tag will increase or decrease the energy used when crafting a recipe using the given additive percentile modifier.

Range: `[-1, -)`

```
<artisanautomation:upgrade_speed>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            Speed: 0.25, 
            EnergyUsage: -0.25
        }
    }
})
```

### FluidCapacity

The `FluidCapacity` tag will increase or decrease the fluid capacity of the tanks using the given additive percentile modifier.

Range: `[-1, -)`

```
<artisanautomation:upgrade_fluid_capacity>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            FluidCapacity: 0.25
        }
    }
})
```

### EnergyCapacity

The `EnergyCapacity` tag will increase or decrease the energy capacity of the machine using the given additive percentile modifier.

Range: `[-1, -)`

```
<artisanautomation:upgrade_energy_capacity>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            EnergyCapacity: 0.25
        }
    }
})
```

### AutoExportItems

The `AutoExportItems` tag will allow recipe output slots to be toggled for auto-export.

Values: `true` or `false`

```
<artisanautomation:upgrade_auto_export_items>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            AutoExportItems: true
        }
    }
})
```

### AutoImportItems

The `AutoImportItems` tag will automatically move items into the machine's inventory from any inventory adjacent to any connected Power Converter block.

Values: `true` or `false`

```
<artisanautomation:upgrade_auto_import_items>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            AutoImportItems: true
        }
    }
})
```

!!! note
    The mod is packaged with an `Auto-Import / Export Items` item that you could use to add both the `AutoImportItems` and the `AutoExportItems` tag. See the example below.

```
<artisanautomation:upgrade_auto_import_export_items>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            AutoImportItems: true,
            AutoExportItems: true
        }
    }
})
```

### AutoImportFluids

The `AutoImportFluids` tag will automatically drain fluids into the machine's tanks from any fluid tank adjacent to any connected Power Converter block.

Values: `true` or `false`

```
<artisanautomation:upgrade_auto_import_fluids>.withTag({
    ArtisanWorktables: {
        Upgrade: {
            AutoImportFluids: true
        }
    }
})
```

## Tool Repair Upgrades

![upgrade-repair](img/upgrade-repair.png)

The mod comes packaged with one pre-made tool repair upgrade item that doesn't do anything by itself, but can be used as an upgrade if you add the correct NBT to it.

All tool repair upgrade tags are nested inside of a root `ToolUpgrade` tag.

Any number of the tool repair upgrade tags below can be combined into any upgrade.

### DurabilityRepaired

The `DurabilityRepaired` tag controls what percentage of a tool's max durability is repaired by consuming one repair material.

Range: `[0, 1]`

```
<artisanautomation:upgrade_tool_repair>.withTag({
    ArtisanWorktables: {
        ToolUpgrade: {
            DurabilityRepaired: 0.5, 
            EnergyUsage: 0.5
        }
    }
})
```

### EnergyUsage

The `EnergyUsage` tag will increase or decrease the energy used when repairing a tool using the given additive percentile modifier.

By default, the machine will consume 1 RF per 1 durability repaired.

Range: `[-1, -)`

```
<artisanautomation:upgrade_tool_repair>.withTag({
    ArtisanWorktables: {
        ToolUpgrade: {
            DurabilityRepaired: 0.5, 
            EnergyUsage: 0.5
        }
    }
})
```