package com.codetaylor.mc.artisanautomation.modules.automator.client;

import com.codetaylor.mc.artisanautomation.modules.automator.tile.TileAutomator;
import com.codetaylor.mc.athenaeum.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class TESRAutomator
    extends TileEntitySpecialRenderer<TileAutomator> {

  @Override
  public void render(TileAutomator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

    if (MinecraftForgeClient.getRenderPass() != 0) {
      return;
    }

    ItemStack itemStack = te.getTableItemStackHandler().getStackInSlot(0);

    if (itemStack.isEmpty()) {
      return;
    }
    Minecraft minecraft = Minecraft.getMinecraft();
    RenderItem renderItem = minecraft.getRenderItem();

    GlStateManager.pushMatrix();
    GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
    GlStateManager.scale(0.5, 0.5, 0.5);

    net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
    IBakedModel model = renderItem.getItemModelWithOverrides(itemStack, null, null);
    RenderHelper.renderItemModel(itemStack, model, ItemCameraTransforms.TransformType.NONE, false, false);

    GlStateManager.popMatrix();
  }
}
