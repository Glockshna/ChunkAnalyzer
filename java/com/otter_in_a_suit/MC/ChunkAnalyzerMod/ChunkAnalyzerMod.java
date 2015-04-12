/*
 *  LICENSING: This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.otter_in_a_suit.MC.ChunkAnalyzerMod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GoldScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.IronScanner;
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
 *  TODO: Comment code for readability (In progress)
 *  TODO: Remove marker torches completely (Done)
 *  TODO: Remove explosion chance (Done)
 *  TODO: Make item placed in scanner a ghost item instead of a physical item
 *  TODO: Remove tiers (In progress)
 *  TODO: Stop requiring shift to enter the interface
 *  TODO: Change activation to require Redstone signal instead of right click (Power also??)
 *  TODO: Change ore scanning and interface to and scan for all ores in the OreDict
 *  TODO: Change report to be more vague Eg:
 *  
 *  Prospecting Report:
 *  The device seems to register a < very dense | dense | minor | trace > deposit of <the most abundant material type> in the scanning area.
 *  
 *  TODO: Add noise to the readings (Inaccuracy) 
 *  TODO: Add upgrades that allow for specific ore type scanning(?)
 *  
 *  Down the road stuff:
 *  
 *  1. Make the machines appear to use some sort of seismic scanning method by having some sort of shock wave emanate from the machine on the ground EG: http://bit.ly/1ynsS9S
 *  2. Cooler looking model
 *  
 *  BUGS:
 *  1. Shift clicking a stack into the interface eats all but one of the stack (Fixed, increased stack limit to 64 as a band aid)
 *  
 */
@Mod(modid = ChunkAnalyzerMod.MODID, version = ChunkAnalyzerMod.VERSION)
public class ChunkAnalyzerMod {
  public static ChunkAnalyzerMod _instance;

  public static final String MODID = "chunkanalyzermod";
  public static final String VERSION = "0.5b";

  public static WoodenScanner woodenScanner;
  public static IronScanner ironScanner;
  public static GoldScanner goldScanner;
  public static IronCage ironCage;

  public static boolean scannerDebug = false;
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


    GameRegistry.registerBlock(woodenScanner, woodenScanner.getUnlocalizedName().substring(5));
    GameRegistry.registerBlock(ironScanner, ironScanner.getUnlocalizedName().substring(5));
    GameRegistry.registerBlock(goldScanner, goldScanner.getUnlocalizedName().substring(5));
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
