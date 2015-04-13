package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import org.lwjgl.input.Keyboard;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.CreativeInv;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Randomizer;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.PlayerHelper;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.TileEntityHelper;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.Vertex;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.WorldHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public abstract class BaseScanner extends BlockContainer implements IScanner {

	/*************************
	 * VARIABLES & CONSTRUCTORS
	 ************************/
	private boolean debug = ChunkAnalyzerMod.scannerBaseDebug;
	protected int quantityDropped = 0;
	private int explosion_threshold = 50;
	private static Randomizer _randomizer = new Randomizer();
	
	public BaseScanner() {
		super(Material.rock);
		this.setHardness(3F);
		this.setResistance(5F);
		this.setBlockName("BaseScanner");
		this.setBlockTextureName("chunkanalyzermod:IronScanner");
		this.setCreativeTab(CreativeInv._instance);
	}

	/************************
	 * MAIN FUNCTIONS
	 ************************/
	/*
	 * on right click (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World,
	 * int, int, int, net.minecraft.entity.player.EntityPlayer, int, float,
	 * float, float)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return false;


		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			FMLNetworkHandler.openGui(player, ChunkAnalyzerMod._instance, 0,
					world, x, y, z);
			return true;
		}

		// Set searchFor-block
		Block searchFor = Blocks.iron_ore;
		try {
			TileEntityBaseScanner tile = TileEntityHelper
					.getTileEntityBaseScannerFromCoords(world, x, y, z);
			if (tile != null) {
				/*
				 * ItemStack itemInUse = player.getHeldItem(); // store the
				 * current item if (itemInUse != null && itemInUse.stackSize >
				 * 0) { searchFor = Block.getBlockFromItem(itemInUse.getItem());
				 * tile.searchFor_ID = Block.getIdFromBlock(searchFor); } else {
				 */
				// if no item held in hand or no block, use stored tile
				searchFor = tile.getStoredBlock();
				// }
				if (searchFor == null || searchFor == Blocks.air) {
					WorldHelper
							.chat("Couldn't read or store a (stored) block!");
					return false;
				}
				if(debug)
					System.out.println("Search for: " + searchFor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		search(world, player, x, y, z, searchFor, this, false);

		return false;
	}

	protected int scan(World world, EntityPlayer player,
			int x, int y, int z, BaseScanner caller) {
		return this.search(world, player, x, y, z, null, caller,
				false);
	}

	protected void resetChunkFromMarkers(World p_149727_1_,
			EntityPlayer player, int x, int y, int z) {
		System.out.println("resetChunkFromMarkers");
		this.search(p_149727_1_, player, x, y, z, null, this, true);
	}

	/**
	 * 
	 * @param world
	 * @param player
	 * @param x
	 * @param y
	 * @param z
	 * @param searchFor
	 * @param caller
	 * @param placeMarkers
	 * @param resetOnly
	 *            : use resetChunkFromMarkers()
	 * @return
	 */
	protected int search(World world, EntityPlayer player, int x, int y, int z, Block searchFor, BaseScanner caller, boolean resetOnly) {
		if (searchFor == null || caller instanceof ChunkAnalyzer)
		
		//Figure out chunk boundries//
		if(debug)
			System.out.println("click from " + caller);
		
		int x_mod = x % 16;
		int x1_border = x;
		int c = 0;
		while (x_mod != 0 && c <= 16) {
			x1_border++;
			c++;
			x_mod = (x1_border) % 16;
		}
		int z_mod = z % 16;
		int z1_border = z;
		c = 0;
		while (z_mod != 0 && c <= 16) {
			z1_border++;
			c++;
			z_mod = (z1_border) % 16;
		}
		int z2_border = z1_border - 16;
		int x2_border = x1_border - 16;
		
		if (debug) {
			world.setBlock(x1_border, y, z2_border, Blocks.redstone_block);
			world.setBlock(x2_border, y, z1_border, Blocks.redstone_block);
			world.setBlock(x2_border, y, z2_border, Blocks.redstone_block);
			world.setBlock(x1_border, y, z1_border, Blocks.redstone_block);
		}
		
		
		// long size = 0;
		int count = 0;
		int accountableCount = 0;
		boolean kBlocked = true;
		//ArrayList<Vertex> findings = new ArrayList<Vertex>();
		ArrayList<Vertex> torchPositions = new ArrayList<Vertex>();
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		int chunkX = chunk.xPosition * 16;
		int chunkZ = chunk.zPosition * 16;
		
		//Search for the stuff//
		
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				kBlocked = false;
				for (int k = 0; k < 256; ++k) {
					Block block = world.getBlock(chunkX + i, k, chunkZ + j);
					if (!resetOnly) {
						if (block == searchFor) 
							count++;
					}
				}
			}
		}
		
		//Check player experience against experience required//
		
		boolean sufficientLevels = false;
		if (ChunkAnalyzerMod.useXPForScanner
				&& !player.capabilities.isCreativeMode) {
			sufficientLevels = player.experienceTotal >= (count * getXPConsumtion());
			if (sufficientLevels) {
				player.addExperience(-1 * (count * getXPConsumtion()));
			} else {
				WorldHelper.chat("Insufficient XP for scan! You need at least "
						+ (count * getXPConsumtion() - player.experienceTotal)
						+ " more!");
			}
		}
		sufficientLevels = (player.capabilities.isCreativeMode) ? true
				: sufficientLevels;
		
		//If we have enough levels return the findings//
		
		if (sufficientLevels)
			reportFindings(world, player, x, y, z, searchFor, caller, count, accountableCount);
		return count;
	}

	protected void reportFindings(World world, EntityPlayer player, int x, int y, int z,
			Block searchFor, BaseScanner caller, int count, int accountableCount) {
		// distinguish the tier
		// check if the player has sufficient torches
		// the branching is a nightmare, but i plan on changing this in the
		// future
		// (2014-05-19)
		
		String msg1 = "Prospecting Report";
		String msg2 = "";
		
		//Check that there are in fact some ores that match the scan type//
		if(debug){
			System.out.println("Count = " + count);
			System.out.println("Looking for " + searchFor.getLocalizedName());
		}
		if (count > 0) {
			

			if(!ChunkAnalyzerMod.reportRaw){
				
				//Determine what density to report//
				if (count >= ChunkAnalyzerMod.veryDense) {
					msg2 = "There appears to be a very dense concentration of " + searchFor.getLocalizedName() + ".";
					
				} else if (count >= ChunkAnalyzerMod.dense) {
					msg2 = "There appears to be a dense concentration of " + searchFor.getLocalizedName() + ".";
				} else if (count >= ChunkAnalyzerMod.minor){
					msg2 = "There appears to be a minor concentration of " + searchFor.getLocalizedName() + ".";
					
				} else if (count >= ChunkAnalyzerMod.trace) {
					msg2 = "There appear to be trace amounts of " + searchFor.getLocalizedName() + ".";
				} else if (count < ChunkAnalyzerMod.trace) {
					msg2 = "There doesn't appear to be enough " + searchFor.getLocalizedName() + " to bother with.";
				} 
			} else {
				msg2 = "Detected " + count + " " + searchFor.getLocalizedName() + ".";
			}

		}
		else {
			msg2 = "There wasn't enough " + searchFor.getLocalizedName() + " for the scanner to report anything.";
		}
		//Report it//
		WorldHelper.chat(msg1);
		WorldHelper.chat(msg2);
	}

	/************************
	 * EASE OF USE / NON-EXTERNALIZED HELPER
	 ************************/
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBaseScanner();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

//	private int getAndSaveExplosionThreshold(World world, int x, int y, int z) {
//		TileEntity genericTile = world.getTileEntity(x, y, z);
//		if (genericTile != null && genericTile instanceof TileEntityBaseScanner) {
//			TileEntityBaseScanner tile = (TileEntityBaseScanner) genericTile;
//			explosion_threshold = tile.explosionThreshold;
//			return explosion_threshold;
//		} else {
//			System.out.println("WARNING: explosionThreshold is 0!");
//			return 0;
//		}
//	}
//
//	public abstract int getLevel();
//
//	private void triggerExplosion(World world, int x, int y, int z) {
//		float strength = 2.0f; // 4.0 = TNT
//		world.createExplosion(null, x, y, z, strength, true);
//	}

	public int getXPConsumtion() {
		if (getLevel() == LEVEL_GOLD)
			return 0; // free stuff, yay!
		return (int) Math
				.ceil((XP_BASE_CONSUMPTION * getLevel() * XP_BASE_MODIFIER));
	}
}
