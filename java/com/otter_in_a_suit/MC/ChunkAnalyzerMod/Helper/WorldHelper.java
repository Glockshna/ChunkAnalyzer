package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntityBaseScanner;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

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
  // TODO: TEST
  public static int getGroundLevelYAxsis_i(World world, int x, int y, int z) {
    System.out.println("getGroundLevelYAxsis_i " + x + " " + y + " " + z);
    y = (y < 0) ? 0 : y;
    int i = 1;
    int blockAirCount = 0;
    while (i <= 256 && blockAirCount >= AIR_THRESHOLD_SKY) {
      y++;
      if (world.getBlock(x, y, z) == Blocks.air) {
        System.out.println("world.getBlock(x, y, z) == Blocks.air: "
            + (world.getBlock(x, y, z) == Blocks.air) + " , y: " + y);
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
      Item parItem, String displayName) {
    System.out.println("dropBlockAsItem");

    // super.dropBlockAsItem(p_149642_1_, p_149642_2_, p_149642_3_, p_149642_4_, p_149642_5_);
    // TODO: TEST
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

  public static boolean dropBlockAsItemWithTileEntity(World world, int x, int y, int z, Item parItem) {
    return dropBlockAsItemWithTileEntity(world, x, y, z, parItem, null);
  }
}
