package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;

public class WorldHelper {

  public static int AIR_THRESHOLD_SKY = 15;

  public static int getGroundLevelYAxsis(World world, int x, int z) {
    return getGroundLevelYAxsis(world, x, 0, z);
  }

  @Deprecated
  public static int getGroundLevelYAxsis(World world, int x, int y, int z) {
    y = (y < 0) ? 0 : y;
    System.out.println("canBlockSeeTheSky: " + y + " - " + world.canBlockSeeTheSky(x, y, z));
    while (!world.canBlockSeeTheSky(x, y, z) && y <= 1024) {
      System.out.println(y);
      y++;
    }
    return y;
  }


  // TODO: some fancy traytracing ?
  public static int getGroundLevelYAxsis_i(World world, int x, int y, int z) {
    y = (y < 0) ? 0 : y;
    int i = 1;
    int blockAirCount = 0;
    while (i <= 256 && blockAirCount >= AIR_THRESHOLD_SKY) {
      y++;
      if (world.getBlock(x, y, z) == Blocks.air) {
        blockAirCount++;
      }
      i++;
    }
    return y;
  }

  public static int getGroundLevelYAxsis_i(World world, int x, int z) {
    return getGroundLevelYAxsis_i(world, x, 0, z);
  }

  public static void chat(String msg) {
    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
  }

  /**
   * Drop a block as item w/ the TileEntity stored w/in it No clue if this is required
   * 
   * @param world
   * @param x
   * @param y
   * @param z
   * @param parItem
   * @param displayName: Can be null, should contain displayed nbts (e.g. "Level: 10")
   * @return
   */
  public static boolean dropBlockAsItemWithTileEntity(World world, int x, int y, int z,
      Block parItem, String displayName) {
    System.out.println("dropBlockAsItem");

    try {
      TileEntityBaseScanner tile =
          TileEntityHelper.getTileEntityBaseScannerFromCoords(world, x, y, z);
      ItemStack itemstack = new ItemStack(parItem, 1);
      itemstack.setTagCompound(tile.getNBTTagCompound());
      if (displayName != null)
        itemstack.setStackDisplayName(displayName);
      Entity item = new EntityItem(world, x, y, z, itemstack);
      world.spawnEntityInWorld(item);
      return true;
    } catch (Exception e) {
      System.out.println("NO entity dropped!");
      e.printStackTrace();
      return false;
    }
  }

  public static boolean dropBlockAsItemWithTileEntity(World world, int x, int y, int z,
      Block parItem) {
    return dropBlockAsItemWithTileEntity(world, x, y, z, parItem, null);
  }
}
