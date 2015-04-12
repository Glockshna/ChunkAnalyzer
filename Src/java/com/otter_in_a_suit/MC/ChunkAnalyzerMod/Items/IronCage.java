package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.CreativeInv;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.TileEntityHelper;

/**
 * Reduces the explosion-chance by -50%
 */
public class IronCage extends Item {
  public IronCage() {
    this.setCreativeTab(CreativeInv._instance);
    this.setUnlocalizedName("IronCage");
    this.setTextureName("chunkanalyzermod:IronCage");
  }

  /**
   * Callback for item usage. If the item does something special on right clicking, he will have one
   * of those. Return True if something happen and false if it don't. This is for ITEMS, not BLOCKS
   * (only true experience can be ensured when there's a shitload of useless parameters none knows
   * jack shit about)
   */
  @Override
  public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World,
      int par4, int par5, int par6, int par7, float par8, float par9, float par10) {

    return false;
  }

  /**
   * This is called when the item is used, before the block is activated.
   * 
   * @param stack The Item Stack
   * @param player The Player that used the item
   * @param world The Current World
   * @param x Target X Position
   * @param y Target Y Position
   * @param z Target Z Position
   * @param side The side of the target hit
   * @return Return true to prevent any further processing.
   */
  @Override
  public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y,
      int z, int side, float hitX, float hitY, float hitZ) {
    if (player.worldObj.isRemote)
      return false;

    // reduce explosion level
    try {
      TileEntityBaseScanner tile =
          TileEntityHelper.getTileEntityBaseScannerFromCoords(world, x, y, z);
      tile.explosionThreshold = reduceExplosionLevel(tile.explosionThreshold, 1.5f);
      if (!player.capabilities.isCreativeMode)
        player.inventory.consumeInventoryItem(this);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  private int reduceExplosionLevel(int explosion_threshold, float by) {
    explosion_threshold = (int) ((by > 100.0f) ? 0.00001f : explosion_threshold * by);
    System.out.println("explosion_threshold: " + explosion_threshold);
    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
        "Reduced the explosion-chance by 50%!"));
    return explosion_threshold;
  }

  /*
   * MovingObjectPosition lastPosition = player.rayTrace(100, 1.0F);
   * System.out.println("Pos: "+lastPosition.blockX + " " + lastPosition.blockY+ " " +
   * lastPosition.blockZ); Bock block = world.getBlock(lastPosition.blockX, lastPosition.blockY,
   * lastPosition.blockZ); Block block = world.getBlock(x, y, z); //
   * System.out.println(block.getUnlocalizedName()); if (block != null && block != Blocks.air &&
   * block instanceof BaseScanner) { BaseScanner scanner = (BaseScanner) block;
   * player.inventory.consumeInventoryItem(this); }
   */
}
