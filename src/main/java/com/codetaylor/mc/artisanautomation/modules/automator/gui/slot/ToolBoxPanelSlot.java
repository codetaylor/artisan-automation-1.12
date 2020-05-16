package com.codetaylor.mc.artisanautomation.modules.automator.gui.slot;

import com.codetaylor.mc.artisanautomation.modules.automator.gui.AutomatorContainer;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.automator.item.data.ToolUpgradeStackHandler;
import com.codetaylor.mc.athenaeum.util.StackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Supplier;

public class ToolBoxPanelSlot
    extends PanelSlot {

  private final ToolUpgradeStackHandler toolUpgradeStackHandler;
  private final BlockPos pos;
  private final EntityPlayer player;

  public ToolBoxPanelSlot(
      Supplier<AutomatorContainer.EnumState> currentState,
      AutomatorContainer.EnumState state,
      IItemHandler itemHandler,
      ToolUpgradeStackHandler toolUpgradeStackHandler,
      BlockPos pos,
      EntityPlayer player,
      int index,
      int xPosition,
      int yPosition
  ) {

    super(currentState, state, itemHandler, index, xPosition, yPosition);
    this.toolUpgradeStackHandler = toolUpgradeStackHandler;
    this.pos = pos;
    this.player = player;
  }

  @Override
  public void onSlotChanged() {

    super.onSlotChanged();

    if (this.getItemHandler().getStackInSlot(0).isEmpty()) {

      for (int i = 0; i < this.toolUpgradeStackHandler.getSlots(); i++) {
        ItemStack itemStack = this.toolUpgradeStackHandler.extractItem(i, 1, this.player.world.isRemote);
        StackHelper.addToInventoryOrSpawn(this.player.world, this.player, itemStack, this.pos, 1, false, true);
      }
    }
  }
}
