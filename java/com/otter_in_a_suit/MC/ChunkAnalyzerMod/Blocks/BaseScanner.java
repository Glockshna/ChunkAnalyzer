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
   * @see net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World, int, int, int,
   * net.minecraft.entity.player.EntityPlayer, int, float, float, float)
   */
  @Override
  public boolean onBlockActivated(World world, int p_149727_2_, int p_149727_3_, int p_149727_4_,
      EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    if (world.isRemote)
      return false;
    int x = p_149727_2_;
    int y = p_149727_3_;
    int z = p_149727_4_;

    if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      FMLNetworkHandler.openGui(player, ChunkAnalyzerMod._instance, 0, world, x, y, z);
      return true;
    }

    // Set searchFor-block
    Block searchFor = Blocks.iron_ore;
    try {
      TileEntityBaseScanner tile =
          TileEntityHelper.getTileEntityBaseScannerFromCoords(world, x, y, z);
      if (tile != null) {
        /*
         * ItemStack itemInUse = player.getHeldItem(); // store the current item if (itemInUse !=
         * null && itemInUse.stackSize > 0) { searchFor =
         * Block.getBlockFromItem(itemInUse.getItem()); tile.searchFor_ID =
         * Block.getIdFromBlock(searchFor); } else {
         */
        // if no item held in hand or no block, use stored tile
        searchFor = tile.getStoredBlock();
        // }
        if (searchFor == null || searchFor == Blocks.air) {
          WorldHelper.chat("Couldn't read or store a (stored) block!");
          return false;
        }
        System.out.println("Search for: " + searchFor);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    search(world, player, x, y, z, searchFor, this, true, false);

    return false;
  }


  protected ArrayList<Vertex> scan(World p_149727_1_, EntityPlayer player, int x, int y, int z,
      BaseScanner caller) {
    return this.search(p_149727_1_, player, x, y, z, null, caller, false, false);
  }

  protected void resetChunkFromMarkers(World p_149727_1_, EntityPlayer player, int x, int y, int z) {
    System.out.println("resetChunkFromMarkers");
    this.search(p_149727_1_, player, x, y, z, null, this, false, true);
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
   * @param resetOnly: use resetChunkFromMarkers()
   * @return
   */
  protected ArrayList<Vertex> search(World world, EntityPlayer player, int x, int y, int z,
      Block searchFor, BaseScanner caller, boolean placeMarkers, boolean resetOnly) {
    boolean debugPlacer = false;
    if (searchFor == null || caller instanceof ChunkAnalyzer)
      placeMarkers = false;

    System.out.println("click from " + caller);
    int x_mod = x % 16;
    int x1_border = x;
    int c = 0;
    while (x_mod != 0 && c <= 16) {
      x1_border++;
      c++;
      x_mod = (x1_border) % 16;
      if (debugPlacer)
        world.setBlock(x1_border, y, z, new MarkerTorch());
    }

    int z_mod = z % 16;
    int z1_border = z;
    c = 0;
    while (z_mod != 0 && c <= 16) {
      z1_border++;
      c++;
      z_mod = (z1_border) % 16;
      if (debugPlacer)
        world.setBlock(x1_border, y, z1_border, new MarkerTorch());
    }

    int z2_border = z1_border - 16;
    int x2_border = x1_border - 16;
    if (debugPlacer) {
      world.setBlock(x1_border, y, z2_border, Blocks.redstone_block);
      world.setBlock(x2_border, y, z1_border, Blocks.redstone_block);
      world.setBlock(x2_border, y, z2_border, Blocks.redstone_block);
      world.setBlock(x1_border, y, z1_border, Blocks.redstone_block);
    }
    // long size = 0;
    int count = 0;
    int accountableCount = 0;
    boolean kBlocked = true;
    ArrayList<Vertex> findings = new ArrayList<Vertex>();
    ArrayList<Vertex> torchPositions = new ArrayList<Vertex>();
    Chunk chunk = world.getChunkFromBlockCoords(x, z);
    int chunkX = chunk.xPosition * 16;
    int chunkZ = chunk.zPosition * 16;
    for (int i = 0; i < 16; ++i) {
      for (int j = 0; j < 16; ++j) {
        kBlocked = false;
        for (int k = 0; k < 256; ++k) {
          Block block = world.getBlock(chunkX + i, k, chunkZ + j);
          if (block != Blocks.air) {
            // ++size;
          }
          if (block == ChunkAnalyzerMod.markerTorch) {
            torchPositions.add(new Vertex((chunkX + i), k, (chunkZ + j), block));
          }
          if (!resetOnly) {
            // just analyze
            if (searchFor == null) {
              findings.add(new Vertex(chunkX + i, k, chunkZ + j, block));
            } else if (block == searchFor) {
              findings.add(new Vertex(chunkX + i, k, chunkZ + j, block));
              count++;
              if (!kBlocked) {
                accountableCount++; // we only need the figure for
                // blocks that aren't stacked in
                // the y-axis
                kBlocked = true;
              }
            }
          }

        }
      }
    }

    boolean sufficientLevels = false;
    if (ChunkAnalyzerMod.useXPForScanner && !player.capabilities.isCreativeMode) {
      sufficientLevels = player.experienceTotal >= (count * getXPConsumtion());
      if (sufficientLevels) {
        player.addExperience(-1 * (count * getXPConsumtion()));
      } else {
        WorldHelper.chat("Insufficient XP for scan! You need at least "
            + (count * getXPConsumtion() - player.experienceTotal) + " more!");
      }
    }
    sufficientLevels = (player.capabilities.isCreativeMode) ? true : sufficientLevels;
    // Delete markers
    System.out.println("Torches: " + torchPositions.size() + " w " + resetOnly);
    if (resetOnly || placeMarkers || (ChunkAnalyzerMod.useXPForScanner && !sufficientLevels)) {
      for (Vertex v : torchPositions) {
        world.setBlockToAir(v.x, v.y, v.z);
      }
    }

    if (placeMarkers && sufficientLevels)
      renderMarkerAndSendMSG(findings, world, player, x, y, z, searchFor, caller, count,
          accountableCount);

    return findings;
  }

  protected void renderMarkerAndSendMSG(ArrayList<Vertex> findings, World p_149727_1_,
      EntityPlayer player, int x, int y, int z, Block searchFor, BaseScanner caller, int count,
      int accountableCount) {
    // distinguish the tier
    // check if the player has sufficient torches
    // the branching is a nightmare, but i plan on changing this in the
    // future
    // (2014-05-19)

    // enable player needs torches
    boolean playerNeedsTorches = false;

    String msg = "";
    boolean renderTorches = false, renderOne = false;
    Item marker = Item.getItemFromBlock(ChunkAnalyzerMod.markerTorch);
    int markerTorchCount =
        (playerNeedsTorches) ? PlayerHelper.InventoryContainsItem_i(player, marker) : findings
            .size() + 1;

    if (!findings.isEmpty()) {
      if (caller.getLevel() == LEVEL_BASE) {
        // do nuthin b/c abstract. if this happens, bad news
      } else if (caller.getLevel() == LEVEL_WOOD) {
        msg = "Found at least one " + searchFor.getUnlocalizedName().substring(5) + "!";
        renderOne = true;
      }
      // Gold & iron are essential equal right now, to be changed in the
      // future
      else if (caller.getLevel() == LEVEL_IRON || caller.getLevel() == LEVEL_GOLD) {
        msg = "Found " + count + " " + searchFor.getUnlocalizedName().substring(5) + "!";
        renderTorches = true;
      }

      // Render
      int ySky = y;
      if (renderTorches) {
        for (int cc = 0; cc < markerTorchCount && cc < findings.size(); cc++) {
          Vertex v = findings.get(cc);
          // TODO getGroundLevelYAxsis
          ySky = WorldHelper.getGroundLevelYAxsis_i(p_149727_1_, v.z, y, v.z);
          /*
           * if (p_149727_1_.isAirBlock(v.x, ySky - 1, v.z)) { p_149727_1_.setBlock(v.x, ySky - 1,
           * v.z, Blocks.cobblestone); }
           */

          // dont replace the scanner by a torch
          if (p_149727_1_.getBlock(v.x, ySky, v.z) != ChunkAnalyzerMod.markerTorch
              && p_149727_1_.getBlock(v.x, ySky, v.z) != this) {
            p_149727_1_.spawnEntityInWorld(new EntityItem(p_149727_1_, x, y, z, new ItemStack(
                p_149727_1_.getBlock(v.x, ySky, v.z))));
            p_149727_1_.setBlock(v.x, ySky, v.z, ChunkAnalyzerMod.markerTorch);
            if (!player.capabilities.isCreativeMode && playerNeedsTorches)
              player.inventory.consumeInventoryItem(marker);
          }

        }
      } else if (renderOne && markerTorchCount >= 1) {
        Vertex v = findings.get(0);
        ySky = WorldHelper.getGroundLevelYAxsis_i(p_149727_1_, v.z, y, v.z);

        /*
         * if (p_149727_1_.isAirBlock(v.x, ySky - 1, v.z)) { p_149727_1_.setBlock(v.x, ySky - 1,
         * v.z, Blocks.cobblestone); }
         */
        if (p_149727_1_.getBlock(v.x, ySky, v.z) != this) {
          p_149727_1_.setBlock(v.x, ySky, v.z, ChunkAnalyzerMod.markerTorch);
          System.out.println("Torch at " + v.x + ", " + ySky + ", " + v.z);
        }

        if (!player.capabilities.isCreativeMode && playerNeedsTorches)
          player.inventory.consumeInventoryItem(marker);
      }

      if (markerTorchCount <= 0) {
        msg = msg.replace("!", ", but you have no marker torches!");
      } else if (markerTorchCount < accountableCount) {
        msg = msg.replace("!", ", but you haven't sufficient marker torches!");
      }

    } else {
      msg = "Found none!";
    }
    WorldHelper.chat(msg);
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
  public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
    if (world.isRemote)
      return false;

    getAndSaveExplosionThreshold(world, x, y, z);
    int isExplode = _randomizer.calculateExplosionLevel(explosion_threshold);

    try {
      TileEntityBaseScanner tile =
          TileEntityHelper.getTileEntityBaseScannerFromCoords(world, x, y, z);
      ItemStack storedBlockAsItem = tile.getStackInSlotOnClosing(0);

      if (isExplode == Randomizer.LEVEL_EXPL_DES) {
        triggerExplosion(world, x, y, z);
      } else if (isExplode == Randomizer.LEVEL_EXPL_PRES || isExplode == Randomizer.LEVEL_PRESERVE) {
        System.out.println("no boom =(");
        this.dropBlockAsItem(world, x, y, z);
        if (storedBlockAsItem != null) {
          Entity item = new EntityItem(world, x, y, z, storedBlockAsItem);
          world.spawnEntityInWorld(item);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    resetChunkFromMarkers(world, player, x, y, z);
    return world.setBlockToAir(x, y, z);
  }

  /************************
   * OVERRIDEN FROM SUPER W/O MAJOR ADJUSTMENTS
   ************************/
  @Override
  public void breakBlock(World world, int x, int y, int z,
      Block block, int p_149749_6_) {
    super.breakBlock(world, x, y, z, block, p_149749_6_);
    // p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
  }

  protected void dropBlockAsItem(World world, int x, int y, int z) {
    this.dropBlockAsItem(world, x, y, z, null);
  }

  @Override
  protected void dropBlockAsItem(World world, int x, int y,
      int z, ItemStack itemStack) {
    try {
      TileEntityBaseScanner tile =
          TileEntityHelper.getTileEntityBaseScannerFromCoords(world, x,
              y, z);
      String display = "";

      if (tile.getNBTTagCompound().getInteger("explosionThreshold") != Randomizer.EXPLOSION_THRESHOLD_BASE_C)
        display += "Reenforced ";
      display += "Scanner";
      display +=
          ", Target: "
              + tile.getStoredBlock()
                  .getUnlocalizedName().substring(5);

      WorldHelper.dropBlockAsItemWithTileEntity(world, x, y, z,
          this, display);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z,
      EntityLivingBase p_149689_5_, ItemStack itemStack) {
    if (world.isRemote)
      return;

    try {
      NBTTagCompound nbt = itemStack.getTagCompound();
      if (nbt != null) {
        TileEntityBaseScanner tile = new TileEntityBaseScanner();
        // tile.writeToNBT(nbt);
        tile.writeToParByNBT(nbt);
        System.out.println(tile.explosionThreshold);
        world.setTileEntity(x, y, z, tile);
      } else {
        System.out.println("No NBT");
      }
    } catch (Exception e) {
      System.out.println("NO entity dropped!");
      e.printStackTrace();
    }

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

  @Override
  public boolean hasTileEntity(int metadata) {
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

  public abstract int getLevel();

  private void triggerExplosion(World world, int x, int y, int z) {
    float strength = 2.0f; // 4.0 = TNT
    world.createExplosion(null, x, y, z, strength, true);
  }

  public int getXPConsumtion() {
    if (getLevel() == LEVEL_GOLD)
      return 0; // free stuff, yay!
    return (int) Math.ceil((XP_BASE_CONSUMPTION * getLevel() * XP_BASE_MODIFIER));
  }
}
