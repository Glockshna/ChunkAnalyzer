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
/**
 * Chunk Analyzer Mod by 
 * @author otter-in-a-suit
 * @see https://www.otter-in-a-suit.com
 * Current version as of 2014-05-23
 * For Minecraft 1.7.2
 */

/* Global ToDo:
 * TODO: Chunk analyzer 
 * TODO: getGroundLevelYAxsis_i 
 * TODO: clean up the code, properly implement helper, maybe create an own helper-jar...
 * -> https://image-store.slidesharecdn.com/8237aa56-e138-11e3-8a7e-22000ab82dd9-large.jpeg 
 * TODO: Remote Terminal 
 * TODO: mobile scanner
 */
@Mod(modid = ChunkAnalyzerMod.MODID, version = ChunkAnalyzerMod.VERSION)
public class ChunkAnalyzerMod {
  public static ChunkAnalyzerMod _instance;

  public static final String MODID = "chunkanalyzermod";
  public static final String VERSION = "0.5b";

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
    registerBlocks();
    registerItems();
    registerTileEntities();
    registerRecepies();

    // GUI
    GUIHandler = new GUIHandler();
    cpw.mods.fml.common.network.NetworkRegistry.INSTANCE.registerGuiHandler(this, GUIHandler);
    
    // Models
    // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMarker.class, new
    // TileEntityMarkerRenderer());
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
        'S', new ItemStack(Blocks.wooden_slab), 'I', new ItemStack(Items.wooden_pickaxe), 'R',
        new ItemStack(Items.redstone));

    GameRegistry.addRecipe(new ItemStack(ironScanner), "SIS", "IRI", "SIS",
        //
        'S', new ItemStack(Items.iron_ingot), 'I', new ItemStack(Items.iron_pickaxe), 'R',
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
