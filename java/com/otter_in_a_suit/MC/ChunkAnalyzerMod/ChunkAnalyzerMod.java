package com.otter_in_a_suit.MC.ChunkAnalyzerMod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GoldScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.IronScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.MarkerTorch;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.WoodenScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GUI.GUIHandler;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Items.IronCage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ChunkAnalyzerMod.MODID, version = ChunkAnalyzerMod.VERSION)
public class ChunkAnalyzerMod {
  public static ChunkAnalyzerMod _instance;
  
  public static final String MODID = "chunkanalyzermod";
  public static final String VERSION = "0.3b";

  public static WoodenScanner woodenScanner;
  public static IronScanner ironScanner;
  public static GoldScanner goldScanner;
  public static MarkerTorch markerTorch;
  public static IronCage ironCage;

  public static boolean useXPForScanner;
  public static Configuration cfg;

  public static GUIHandler GUIHandler;
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    _instance = this;
    cfg = new Configuration(event.getSuggestedConfigurationFile());
    cfg.load();
    useXPForScanner =
        cfg.get(Configuration.CATEGORY_GENERAL, "useXPForScanner", true).getBoolean(true);
    cfg.save();
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    // Models
    // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new
    // TileEntityMarkerRenderer());

    registerBlocks();
    registerItems();
    registerTileEntities();
    registerRecepies();
    
    // GUI
    GUIHandler = new GUIHandler();
    cpw.mods.fml.common.network.NetworkRegistry.INSTANCE.registerGuiHandler(this,GUIHandler);
  }

  private void registerBlocks() {
    woodenScanner = new WoodenScanner();
    ironScanner = new IronScanner();
    goldScanner = new GoldScanner();
    markerTorch = new MarkerTorch();

    GameRegistry.registerBlock(woodenScanner, woodenScanner.getUnlocalizedName().substring(5));
    GameRegistry.registerBlock(ironScanner, ironScanner.getUnlocalizedName().substring(5));
    GameRegistry.registerBlock(goldScanner, goldScanner.getUnlocalizedName().substring(5));
    GameRegistry.registerBlock(markerTorch, markerTorch.getUnlocalizedName().substring(5));
  }

  private void registerItems() {
    ironCage = new IronCage();

    GameRegistry.registerItem(ironCage, ironCage.getUnlocalizedName().substring(5));
  }

  private void registerTileEntities() {
    GameRegistry.registerTileEntity(
        com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner.class,
        "stringID");

  }

  private void registerRecepies() {
    GameRegistry.addRecipe(new ItemStack(woodenScanner), "SIS", "IRI", "SIS",
        //
        'S', new ItemStack(Blocks.stone_slab), 'I', new ItemStack(Items.iron_pickaxe), 'R',
        new ItemStack(Items.redstone));

    GameRegistry.addRecipe(new ItemStack(ironScanner), "SIS", "IRI", "SIS",
        //
        'S', new ItemStack(Blocks.iron_bars), 'I', new ItemStack(Items.iron_pickaxe), 'R',
        new ItemStack(woodenScanner));

    GameRegistry.addRecipe(new ItemStack(goldScanner), "SIS", "IRI", "SIS",
        //
        'S', new ItemStack(Items.gold_ingot), 'I', new ItemStack(Items.golden_pickaxe), 'R',
        new ItemStack(ironScanner));

    GameRegistry.addRecipe(new ItemStack(ironCage), "III", "I I", "III",
    //
        'I', new ItemStack(Blocks.iron_bars, 2));
  }
}
