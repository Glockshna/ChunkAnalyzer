package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

/**
 * TODO: One Block torch
 * TODO: save tile
 * TODO: CFG
 * TODO: Chunk analyzer
 */
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.CreativeInv;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Randomizer;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.PlayerHelper;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.TileEntityHelper;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.Vertex;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.WorldHelper;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Items.IronCage;

public abstract class BaseScanner extends BlockContainer implements IScanner {

	/*************************
	 * VARIABLES & CONSTRUCTORS
	 ************************/
	protected int quantityDropped = 0;
	private int explosion_threshold = 50;
	private static Randomizer _randomizer = new Randomizer();

	public BaseScanner() {
		super(Material.rock);
		this.setHardness(3F)
				.setResistance(5F)
				.setBlockName("BaseScanner")
				.setBlockTextureName("chunkanalyzermod:IronScanner")
				.setCreativeTab(CreativeInv._instance);
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
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_,
			int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		if (p_149727_1_.isRemote)
			return false;
		int x = p_149727_2_;
		int y = p_149727_3_;
		int z = p_149727_4_;

		// Set searchFor-block
		Block searchFor = Blocks.diamond_ore;
		try {
			TileEntityBaseScanner tile = TileEntityHelper.getTileEntityBaseScannerFromCoords(p_149727_1_, x, y, z);
			if(tile != null) {
				ItemStack itemInUse = p_149727_5_.getHeldItem();
				// store the current item
				if(itemInUse != null && itemInUse.stackSize > 0){
					searchFor = Block.getBlockFromItem(itemInUse.getItem());
					tile.searchFor_ID = Block.getIdFromBlock(searchFor);
				}else{
					// if no item held in hand or no block, use stored tile
					searchFor = Block.getBlockById(tile.searchFor_ID);
				}
				System.out.println("Stored "+Block.getBlockById(tile.searchFor_ID));
				if (searchFor == null) 
					throw new Exception(""); // yeah...
				System.out.println("Search for: " + searchFor);
			}
		} catch (Exception e) {
			WorldHelper.chat("Couldn't read or store a (stored) block, l/f diamond-ore instead!");
		}

		search(p_149727_1_, p_149727_5_, x, y, z, searchFor, this);

		return false;
	}

	protected void search(World p_149727_1_, EntityPlayer player, int x, int y,
			int z, Block searchFor, BaseScanner caller) {
		boolean debugPlacer = false;

		System.out.println("click from " + caller);
		int x_mod = x % 16;
		int x1_border = x;
		int c = 0;
		while (x_mod != 0 && c <= 16) {
			x1_border++;
			c++;
			x_mod = (x1_border) % 16;
			if (debugPlacer)
				p_149727_1_.setBlock(x1_border, y, z, new MarkerTorch());
		}

		int z_mod = z % 16;
		int z1_border = z;
		c = 0;
		while (z_mod != 0 && c <= 16) {
			z1_border++;
			c++;
			z_mod = (z1_border) % 16;
			if (debugPlacer)
				p_149727_1_
						.setBlock(x1_border, y, z1_border, new MarkerTorch());
		}

		int z2_border = z1_border - 16;
		int x2_border = x1_border - 16;
		if (debugPlacer) {
			p_149727_1_
					.setBlock(x1_border, y, z2_border, Blocks.redstone_block);
			p_149727_1_.setBlock(x2_border, y, z1_border, Blocks.lapis_block);
			p_149727_1_.setBlock(x2_border, y, z2_border, Blocks.gold_block);
			p_149727_1_.setBlock(x1_border, y, z1_border, Blocks.diamond_block);
		}
		long size = 0;
		int count = 0;
		int accountableCount = 0;
		boolean kBlocked = true;
		ArrayList<Vertex> findings = new ArrayList<Vertex>();
		Chunk chunk = p_149727_1_.getChunkFromBlockCoords(x, z);
		int chunkX = chunk.xPosition * 16;
		int chunkZ = chunk.zPosition * 16;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				kBlocked = false;
				for (int k = 0; k < 256; ++k) {
					Block block = p_149727_1_.getBlock(chunkX + i, k, chunkZ
							+ j);
					if (block != Blocks.air) {
						++size;
					}
					if (block == searchFor) {
						findings.add(new Vertex(chunkX + i, k, chunkZ + j));					
						count++;
						if(!kBlocked) {
							accountableCount++; // we only need the figure for blocks that aren't stacked in the y-axis
							kBlocked = true;
						}
					}
				}
			}
		}
		System.out.println("accountableCount "+accountableCount);
		// distinguish the tier
		// check if the player has sufficient torches
		// the branching is a nightmare, but i plan on changing this in the
		// future
		// (2014-05-19)
		String msg = "";
		boolean renderTorches = false, renderOne = false;
		Item marker = Item.getItemFromBlock(ChunkAnalyzerMod.markerTorch);
		int markerTorchCount = PlayerHelper.InventoryContainsItem_i(player,
				marker);

		if (!findings.isEmpty()) {
			if (caller.getLevel() == LEVEL_BASE) {
				// do nuthin b/c abstract. if this happens, bad news
			} else if (caller.getLevel() == LEVEL_STONE) {
				msg = "Found at least one " + searchFor.getUnlocalizedName()
						+ "!";
				renderOne = true;
			}
			// Gold & iron are essential equal right now, to be changed in the
			// future
			else if (caller.getLevel() == LEVEL_IRON
					|| caller.getLevel() == LEVEL_GOLD) {
				msg = "Found " + count + " " + searchFor.getUnlocalizedName()
						+ "!";
				renderTorches = true;
			}

			// Render
			int ySky = y;
			if (renderTorches) {
				System.out.println("Findings: "+ findings.size());
				System.out.println("Torches: "+markerTorchCount);
				for (int cc = 0; cc < markerTorchCount && cc < findings.size(); cc++) {
					Vertex v = findings.get(cc);
					ySky = WorldHelper.getGroundLevelYAxsis_i(p_149727_1_, v.z, y, v.z); // TODO getGroundLevelYAxsis
					if(p_149727_1_.isAirBlock(v.x, ySky-1, v.z)) p_149727_1_.setBlock(v.x, ySky-1, v.z, Blocks.cobblestone);
					
					if(p_149727_1_.getBlock(v.x, ySky, v.z) != ChunkAnalyzerMod.markerTorch) {
						p_149727_1_.setBlock(v.x, ySky, v.z, ChunkAnalyzerMod.markerTorch);
						System.out.println("Torch at "+v.x+", "+ySky+", "+ v.z);
						
						if (!player.capabilities.isCreativeMode)
							player.inventory.consumeInventoryItem(marker);
					}
					
				}
			} else if (renderOne && markerTorchCount >= 1) {
				System.out.println("XXX "+markerTorchCount);
				Vertex v = findings.get(0);
				ySky = WorldHelper.getGroundLevelYAxsis_i(p_149727_1_, v.z, y, v.z);
				if(p_149727_1_.isAirBlock(v.x, ySky-1, v.z)) p_149727_1_.setBlock(v.x, ySky-1, v.z, Blocks.cobblestone);
				p_149727_1_.setBlock(v.x, ySky, v.z, ChunkAnalyzerMod.markerTorch);
				System.out.println("Torch at "+v.x+", "+ySky+", "+ v.z);
				if (!player.capabilities.isCreativeMode)
					player.inventory.consumeInventoryItem(marker);
			}
			
			if (markerTorchCount <= 0) {
				msg = msg.replace("!", ", but you have no marker torches!");
			} else if (markerTorchCount < accountableCount) {
				msg = msg.replace("!",
						", but you haven't sufficient marker torches!");
			}

		} else {
			msg = "Found none!";
		}
		

		// TODO: transparent marker
		WorldHelper.chat(msg);

		System.out.println("SIZE: " + size);
	}

	/*
	 * and the block is intact at time of call. (non-Javadoc)
	 * 
	 * @see net.minecraft.block.Block#removedByPlayer(net.minecraft.world.World,
	 * net.minecraft.entity.player.EntityPlayer, int, int, int)
	 * 
	 * @param world The current world
	 * 
	 * @param player The player damaging the block, may be null
	 * 
	 * @param x X Position
	 * 
	 * @param y Y position
	 * 
	 * @param z Z position
	 * 
	 * @return True if the block is actually destroyed.
	 */
	// harvestBlock
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x,
			int y, int z) {
		if (world.isRemote)
			return false;

		getAndSaveExplosionThreshold(world, x, y, z);
		int isExplode = _randomizer
				.calculateExplosionLevel(explosion_threshold);
		
		if (isExplode == Randomizer.LEVEL_EXPL_DES) {
			triggerExplosion(world, x, y, z);
		} else if (isExplode == Randomizer.LEVEL_EXPL_PRES
				&& !player.capabilities.isCreativeMode) {
			player.inventory.addItemStackToInventory(new ItemStack(this, 1));
			if (explosion_threshold > 50) {
				player.inventory.addItemStackToInventory(new ItemStack(
						new IronCage(), 1));
			}
		} else {
			System.out.println("no boom =(");
			if (!player.capabilities.isCreativeMode) {
				player.inventory
						.addItemStackToInventory(new ItemStack(this, 1));
				if (explosion_threshold > 50) {
					player.inventory.addItemStackToInventory(new ItemStack(
							new IronCage(), 1));
				}
			}
		}

		return world.setBlockToAir(x, y, z);
	}

	/************************
	 * OVERRIDEN FROM SUPER W/O MAJOR ADJUSTMENTS
	 ************************/
	@Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_){   	
        //super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        //p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
		// TODO: TEST
		try {
			TileEntityBaseScanner tile = (TileEntityBaseScanner) TileEntityHelper.getTileEntityBaseScannerFromCoords(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
			ItemStack itemstack = new ItemStack(this,1);
			itemstack.setTagCompound(tile.getNBTTagCompound());
			Entity item = new EntityItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, itemstack);
			p_149749_1_.spawnEntityInWorld(item);
		} catch (Exception e) {
			System.out.println("NO entity dropped!");
			e.printStackTrace();
		}
    }
	
	@Override
	protected void dropBlockAsItem(World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_){
		super.dropBlockAsItem(p_149642_1_, p_149642_2_, p_149642_3_, p_149642_4_, p_149642_5_);
	}
	
	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return quantityDropped;
	}
	
	/************************
	 * EASE OF USE / NON-EXTERNALIZED HELPER
	 ************************/
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityBaseScanner();
	}
	
	public boolean hasTileEntity(int metadata)
	{
	    return true;
	}

	private int getAndSaveExplosionThreshold(World world, int x, int y, int z) {
		TileEntity genericTile = world.getTileEntity(x, y, z);
		if (genericTile != null && genericTile instanceof TileEntityBaseScanner) {
			TileEntityBaseScanner tile = (TileEntityBaseScanner) genericTile;
			explosion_threshold = tile.explosionThreshold;
			return explosion_threshold;
		} else {
			System.out.println("WARNING: explosionThreshold is 0!");
			return 0;
		}
	}

	public int getLevel() {
		return LEVEL_BASE;
	}
	
	private void triggerExplosion(World world, int x, int y, int z) {
		float strength = 2.0f; // 4.0 = TNT
		world.createExplosion(null, x, y, z, strength, true);
	}
}
