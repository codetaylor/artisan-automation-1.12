package com.codetaylor.mc.artisanautomation.modules.automator.tile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class GhostItemValidationItemHandlerWrapper
    implements IItemHandler {

  private final IItemHandler handler;
  private final IItemHandler ghostHandler;
  private final BooleanSupplier inventoryLocked;

  public GhostItemValidationItemHandlerWrapper(IItemHandler handler, IItemHandler ghostHandler, BooleanSupplier inventoryLocked) {

    this.handler = handler;
    this.ghostHandler = ghostHandler;
    this.inventoryLocked = inventoryLocked;
  }

  @Override
  public int getSlots() {

    return this.handler.getSlots();
  }

  @Nonnull
  @Override
  public ItemStack getStackInSlot(int slot) {

    return this.handler.getStackInSlot(slot);
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {

    if (slot >= this.handler.getSlots()) {
      return stack;
    }

    if (this.inventoryLocked.getAsBoolean()) {
      ItemStack ghostStack = this.ghostHandler.getStackInSlot(slot);

      if (ghostStack.getItem() != stack.getItem()) {
        // items aren't equal
        return stack;

      } else if (ghostStack.getMetadata() != OreDictionary.WILDCARD_VALUE
          && ghostStack.getMetadata() != stack.getMetadata()) {
        // ghost stack doesn't have wildcard and metas don't match
        return stack;

      } else if (ghostStack.hasTagCompound()
          && !ItemStack.areItemStackTagsEqual(ghostStack, stack)) {
        // ghost stack has tag, tags aren't equal
        return stack;
      }
    }

    return this.handler.insertItem(slot, stack, simulate);
  }

  @Nonnull
  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {

    return this.handler.extractItem(slot, amount, simulate);
  }

  @Override
  public int getSlotLimit(int slot) {

    return this.handler.getSlotLimit(slot);
  }
}
