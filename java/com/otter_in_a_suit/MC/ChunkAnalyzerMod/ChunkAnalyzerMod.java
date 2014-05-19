package com.otter_in_a_suit.MC.ChunkAnalyzerMod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GoldScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.IronScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.MarkerTorch;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.StoneScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Items.IronCage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ChunkAnalyzerMod.MODID, version = ChunkAnalyzerMod.VERSION)
public class ChunkAnalyzerMod
{
    public static final String MODID = "chunkanalyzermod";
    public static final String VERSION = "0.1";
    
    public static StoneScanner stoneScanner;
    public static IronScanner ironScanner;
    public static GoldScanner goldScanner;
    public static MarkerTorch markerTorch;
    public static IronCage ironCage;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	registerBlocks();
    	registerItems();
    	registerTileEntities();
    	registerRecepies();
    	
    	// TODO: texture
    }
    
    private void registerBlocks(){
    	stoneScanner = new StoneScanner();
    	ironScanner = new IronScanner();
    	goldScanner = new GoldScanner();
    	markerTorch = new MarkerTorch();
    	
    	GameRegistry.registerBlock(stoneScanner, stoneScanner.getUnlocalizedName().substring(5));    	
    	GameRegistry.registerBlock(ironScanner, ironScanner.getUnlocalizedName().substring(5));
    	GameRegistry.registerBlock(goldScanner, goldScanner.getUnlocalizedName().substring(5));
    	GameRegistry.registerBlock(markerTorch, markerTorch.getUnlocalizedName().substring(5));
    }
    
    private void registerItems(){
    	ironCage = new IronCage();
    	
    	GameRegistry.registerItem(ironCage, ironCage.getUnlocalizedName().substring(5));
    }
    
    private void registerTileEntities(){
    	GameRegistry.registerTileEntity(com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntityBaseScanner.class, "stringID");
    }
    
    private void registerRecepies(){
    	GameRegistry.addRecipe(new ItemStack(stoneScanner), 
    			"SIS", 
    			"IRI", 
    			"SIS",
    			//
    	        'S', new ItemStack(Blocks.stone_slab), 
    	        'I', new ItemStack(Items.iron_pickaxe),
    	        'R', new ItemStack(Items.redstone));
    
	GameRegistry.addRecipe(new ItemStack(ironScanner), 
			"SIS", 
			"IRI", 
			"SIS",
			//
	        'S', new ItemStack(Blocks.iron_bars), 
	        'I', new ItemStack(Items.iron_pickaxe),
	        'R', new ItemStack(stoneScanner));
	
	GameRegistry.addRecipe(new ItemStack(goldScanner), 
			"SIS", 
			"IRI", 
			"SIS",
			//
	        'S', new ItemStack(Items.gold_ingot), 
	        'I', new ItemStack(Items.golden_pickaxe),
	        'R', new ItemStack(ironScanner));
	
	GameRegistry.addRecipe(new ItemStack(ironCage), 
			"III", 
			"I I", 
			"III",
			//
	        'I', new ItemStack(Blocks.iron_bars,2));

	GameRegistry.addRecipe(new ItemStack(markerTorch, 2), 
			"T  ", 
			"T  ", 
			"   ",
			//
	        'T', new ItemStack(Blocks.torch));
    }
}
