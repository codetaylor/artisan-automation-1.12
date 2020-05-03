package com.codetaylor.mc.artisanautomation.modules.automator.event;

import com.codetaylor.mc.artisanautomation.modules.automator.TooltipUtil;
import com.codetaylor.mc.artisanautomation.modules.automator.item.ItemUpgrade;
import com.codetaylor.mc.artisanautomation.modules.automator.reference.UpgradeTags;
import com.codetaylor.mc.athenaeum.util.TooltipHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class TooltipEventHandler {

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void on(ItemTooltipEvent event) {

    ItemStack itemStack = event.getItemStack();
    NBTTagCompound upgradeTag = ItemUpgrade.getUpgradeTag(itemStack);
    boolean isToolUpgrade = false;

    if (upgradeTag == null) {
      upgradeTag = ItemUpgrade.getToolUpgradeTag(itemStack);

      if (upgradeTag == null) {
        return;
      }

      isToolUpgrade = true;
    }

    if (upgradeTag.getSize() == 0) {
      return;
    }

    List<String> tooltip = event.getToolTip();

    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {

      if (isToolUpgrade) {
        this.addToolUpgradeTooltip(upgradeTag, tooltip);

      } else {
        this.addUpgradeTooltip(upgradeTag, tooltip);
      }

    } else {
      tooltip.add(TooltipHelper.tooltipHoldShiftStringGet());
    }
  }

  private void addToolUpgradeTooltip(NBTTagCompound upgradeTag, List<String> tooltip) {

    if (upgradeTag.hasKey(UpgradeTags.TAG_TOOL_UPGRADE_DURABILITY_REPAIRED)) {
      int durabilityRepaired = (int) (upgradeTag.getFloat(UpgradeTags.TAG_TOOL_UPGRADE_DURABILITY_REPAIRED) * 100);

      if (durabilityRepaired > 0) {
        tooltip.add(TextFormatting.GRAY + TooltipUtil.getDurabilityRepairedString(durabilityRepaired));
      }
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_ENERGY_USAGE)) {
      int energyUsageModifier = (int) (upgradeTag.getFloat(UpgradeTags.TAG_UPGRADE_ENERGY_USAGE) * 100);

      if (energyUsageModifier != 0) {
        tooltip.add(TextFormatting.GRAY + TooltipUtil.getEnergyUsageString(energyUsageModifier, true));
      }
    }
  }

  private void addUpgradeTooltip(NBTTagCompound upgradeTag, List<String> tooltip) {

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_SPEED)) {
      int speedModifier = (int) (upgradeTag.getFloat(UpgradeTags.TAG_UPGRADE_SPEED) * 100);

      if (speedModifier != 0) {
        tooltip.add(TextFormatting.GRAY + TooltipUtil.getSpeedString(speedModifier, true));
      }
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_ENERGY_USAGE)) {
      int energyUsageModifier = (int) (upgradeTag.getFloat(UpgradeTags.TAG_UPGRADE_ENERGY_USAGE) * 100);

      if (energyUsageModifier != 0) {
        tooltip.add(TextFormatting.GRAY + TooltipUtil.getEnergyUsageString(energyUsageModifier, true));
      }
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_FLUID_CAPACITY)) {
      int fluidCapacityModifier = (int) (upgradeTag.getFloat(UpgradeTags.TAG_UPGRADE_FLUID_CAPACITY) * 100);

      if (fluidCapacityModifier != 0) {
        tooltip.add(TextFormatting.GRAY + TooltipUtil.getFluidCapacityString(fluidCapacityModifier, true));
      }
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_ENERGY_CAPACITY)) {
      int energyCapacityModifier = (int) (upgradeTag.getFloat(UpgradeTags.TAG_UPGRADE_ENERGY_CAPACITY) * 100);

      if (energyCapacityModifier != 0) {
        tooltip.add(TextFormatting.GRAY + TooltipUtil.getEnergyCapacityString(energyCapacityModifier, true));
      }
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_AUTO_IMPORT_ITEMS)) {
      boolean autoImportItems = upgradeTag.getBoolean(UpgradeTags.TAG_UPGRADE_AUTO_IMPORT_ITEMS);
      tooltip.add(TextFormatting.GRAY + TooltipUtil.getAutoImportItemsString(autoImportItems));
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_AUTO_EXPORT_ITEMS)) {
      boolean autoExportItems = upgradeTag.getBoolean(UpgradeTags.TAG_UPGRADE_AUTO_EXPORT_ITEMS);
      tooltip.add(TextFormatting.GRAY + TooltipUtil.getAutoExportItemsString(autoExportItems));
    }

    if (upgradeTag.hasKey(UpgradeTags.TAG_UPGRADE_AUTO_IMPORT_FLUIDS)) {
      boolean autoImportFluids = upgradeTag.getBoolean(UpgradeTags.TAG_UPGRADE_AUTO_IMPORT_FLUIDS);
      tooltip.add(TextFormatting.GRAY + TooltipUtil.getAutoImportFluidsString(autoImportFluids));
    }
  }

}
