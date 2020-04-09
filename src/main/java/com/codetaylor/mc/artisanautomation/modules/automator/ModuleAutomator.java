package com.codetaylor.mc.artisanautomation.modules.automator;

import com.codetaylor.mc.artisanautomation.ModArtisanAutomation;
import com.codetaylor.mc.artisanautomation.modules.automator.block.BlockAutomator;
import com.codetaylor.mc.artisanautomation.modules.automator.block.BlockAutomatorPowerRF;
import com.codetaylor.mc.artisanautomation.modules.automator.client.TESRAutomator;
import com.codetaylor.mc.artisanautomation.modules.automator.event.TooltipEventHandler;
import com.codetaylor.mc.artisanautomation.modules.automator.item.ItemUpgrade;
import com.codetaylor.mc.artisanautomation.modules.automator.network.*;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.TileAutomator;
import com.codetaylor.mc.artisanautomation.modules.automator.tile.TileAutomatorPowerSupplierRF;
import com.codetaylor.mc.artisanworktables.ModArtisanWorktables;
import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.network.IPacketRegistry;
import com.codetaylor.mc.athenaeum.network.IPacketService;
import com.codetaylor.mc.athenaeum.network.tile.ITileDataService;
import com.codetaylor.mc.athenaeum.network.tile.SCPacketTileData;
import com.codetaylor.mc.athenaeum.packer.PackAPI;
import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ModuleAutomator
    extends ModuleBase {

  public static final String MOD_NAME = ModArtisanAutomation.NAME;
  public static final String MOD_ID = ModArtisanAutomation.MOD_ID;
  public static final CreativeTabs CREATIVE_TAB = ModArtisanWorktables.CREATIVE_TAB;
  public static final Logger LOG = LogManager.getLogger(MOD_ID);

  @GameRegistry.ObjectHolder(ModuleAutomator.MOD_ID)
  public static class Blocks {

    @GameRegistry.ObjectHolder(BlockAutomator.NAME)
    public static final BlockAutomator AUTOMATOR;

    @GameRegistry.ObjectHolder(BlockAutomatorPowerRF.NAME)
    public static final BlockAutomatorPowerRF AUTOMATOR_POWER_RF;

    static {
      AUTOMATOR = null;
      AUTOMATOR_POWER_RF = null;
    }
  }

  @GameRegistry.ObjectHolder(ModuleAutomator.MOD_ID)
  public static class Items {

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_SPEED)
    public static final ItemUpgrade UPGRADE_SPEED;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_FLUID_CAPACITY)
    public static final ItemUpgrade UPGRADE_FLUID_CAPACITY;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_ENERGY_CAPACITY)
    public static final ItemUpgrade UPGRADE_ENERGY_CAPACITY;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_AUTO_EXPORT_ITEMS)
    public static final ItemUpgrade UPGRADE_AUTO_EXPORT_ITEMS;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_AUTO_IMPORT_ITEMS)
    public static final ItemUpgrade UPGRADE_AUTO_IMPORT_ITEMS;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_AUTO_IMPORT_EXPORT_ITEMS)
    public static final ItemUpgrade UPGRADE_AUTO_IMPORT_EXPORT_ITEMS;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_AUTO_IMPORT_FLUIDS)
    public static final ItemUpgrade UPGRADE_AUTO_IMPORT_FLUIDS;

    @GameRegistry.ObjectHolder(ItemUpgrade.NAME_TOOL_REPAIR)
    public static final ItemUpgrade UPGRADE_TOOL_REPAIR;

    static {
      UPGRADE_SPEED = null;
      UPGRADE_FLUID_CAPACITY = null;
      UPGRADE_ENERGY_CAPACITY = null;
      UPGRADE_AUTO_EXPORT_ITEMS = null;
      UPGRADE_AUTO_IMPORT_ITEMS = null;
      UPGRADE_AUTO_IMPORT_EXPORT_ITEMS = null;
      UPGRADE_AUTO_IMPORT_FLUIDS = null;
      UPGRADE_TOOL_REPAIR = null;
    }
  }

  public static IPacketService PACKET_SERVICE;
  public static ITileDataService TILE_DATA_SERVICE;

  public ModuleAutomator() {

    super(0, MOD_ID);

    this.setRegistry(new Registry(MOD_ID, CREATIVE_TAB));
    this.enableAutoRegistry();

    PACKET_SERVICE = this.enableNetwork();
    TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);

    MinecraftForge.EVENT_BUS.register(new TooltipEventHandler());
  }

  @Override
  public void onNetworkRegister(IPacketRegistry registry) {

    registry.register(
        CSPacketAutomatorTabStateChange.class,
        CSPacketAutomatorTabStateChange.class,
        Side.SERVER
    );

    registry.register(
        SCPacketAutomatorTabStateChange.class,
        SCPacketAutomatorTabStateChange.class,
        Side.CLIENT
    );

    registry.register(
        CSPacketAutomatorOutputModeChange.class,
        CSPacketAutomatorOutputModeChange.class,
        Side.SERVER
    );

    registry.register(
        CSPacketAutomatorClearInventoryGhostSlot.class,
        CSPacketAutomatorClearInventoryGhostSlot.class,
        Side.SERVER
    );

    registry.register(
        CSPacketAutomatorInventoryLockModeChange.class,
        CSPacketAutomatorInventoryLockModeChange.class,
        Side.SERVER
    );

    registry.register(
        CSPacketAutomatorFluidLockModeChange.class,
        CSPacketAutomatorFluidLockModeChange.class,
        Side.SERVER
    );

    registry.register(
        CSPacketAutomatorFluidModeChange.class,
        CSPacketAutomatorFluidModeChange.class,
        Side.SERVER
    );

    registry.register(
        CSPacketAutomatorFluidDestroy.class,
        CSPacketAutomatorFluidDestroy.class,
        Side.SERVER
    );

    registry.register(
        SCPacketTileData.class,
        SCPacketTileData.class,
        Side.CLIENT
    );
  }

  @Override
  public void onRegister(Registry registry) {

    registry.registerBlockWithItem(new BlockAutomator(), BlockAutomator.NAME);
    registry.registerBlockWithItem(new BlockAutomatorPowerRF(), BlockAutomatorPowerRF.NAME);

    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_SPEED);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_FLUID_CAPACITY);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_ENERGY_CAPACITY);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_AUTO_EXPORT_ITEMS);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_AUTO_IMPORT_ITEMS);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_AUTO_IMPORT_EXPORT_ITEMS);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_AUTO_IMPORT_FLUIDS);
    registry.registerItem(new ItemUpgrade(), ItemUpgrade.NAME_TOOL_REPAIR);

    //noinspection unchecked
    this.registerTileEntities(
        registry,
        TileAutomator.class,
        TileAutomatorPowerSupplierRF.class
    );
  }

  @Override
  public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onClientPreInitializationEvent(event);

    String resourcePath = "textures/gui/atlas/packed.json";
    ResourceLocation resourceLocation = new ResourceLocation(ModuleAutomator.MOD_ID, resourcePath);

    PackAPI.register(resourceLocation, () -> {
      try {
        Minecraft minecraft = Minecraft.getMinecraft();
        IResourceManager resourceManager = minecraft.getResourceManager();
        IResource resource = resourceManager.getResource(resourceLocation);
        return Optional.of(resource.getInputStream());

      } catch (Exception e) {
        LOG.error("Error loading packed atlas data: " + resourceLocation, e);
      }
      return Optional.empty();
    });
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void onClientRegister(Registry registry) {

    registry.registerClientModelRegistrationStrategy(() -> {
      ModelRegistrationHelper.registerBlockItemModels(
          Blocks.AUTOMATOR,
          Blocks.AUTOMATOR_POWER_RF
      );

      ModelRegistrationHelper.registerItemModels(
          Items.UPGRADE_SPEED,
          Items.UPGRADE_FLUID_CAPACITY,
          Items.UPGRADE_ENERGY_CAPACITY,
          Items.UPGRADE_AUTO_EXPORT_ITEMS,
          Items.UPGRADE_AUTO_IMPORT_ITEMS,
          Items.UPGRADE_AUTO_IMPORT_EXPORT_ITEMS,
          Items.UPGRADE_AUTO_IMPORT_FLUIDS,
          Items.UPGRADE_TOOL_REPAIR
      );

      ClientRegistry.bindTileEntitySpecialRenderer(TileAutomator.class, new TESRAutomator());
    });
  }

  @SuppressWarnings("unchecked")
  public void registerTileEntities(Registry registry, Class<? extends TileEntity>... tileEntityClasses) {

    for (Class<? extends TileEntity> tileEntityClass : tileEntityClasses) {
      this.registerTileEntity(registry, tileEntityClass);
    }
  }

  public void registerTileEntity(Registry registry, Class<? extends TileEntity> tileEntityClass) {

    registry.registerTileEntityRegistrationStrategy(() -> GameRegistry.registerTileEntity(
        tileEntityClass,
        new ResourceLocation(registry.getModId(), "tile." + tileEntityClass.getSimpleName())
    ));
  }
}
