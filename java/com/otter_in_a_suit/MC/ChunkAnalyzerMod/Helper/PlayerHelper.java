package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerHelper {
  @Deprecated
  public static int InventoryContainsItem(EntityPlayer player, Item item) {
    int count = 0;
    if (player != null) {
      ItemStack[] inventory = player.inventory.mainInventory;
      if (inventory != null && inventory.length > 0) {
        for (ItemStack itemstack : inventory) {
          if (itemstack != null && itemstack.getItem() != null && itemstack.getItem() == item)
            count++;
        }
      }
    }
    return count;
  }

  /**
   * Based on private func_146029_c
   * 
   * @param player
   * @param item
   * @return
   */
  public static int InventoryContainsItem_i(EntityPlayer player, Item item) {
    int count = 0;
    if (player != null) {
      for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
        if (player.inventory.mainInventory[i] != null
            && player.inventory.mainInventory[i].getItem() == item) {
          count += player.inventory.mainInventory[i].stackSize;
        }
      }
    }
    return count;
  }

  public static float getXPCostByLevel(int currentLevel) {
    if (currentLevel <= 15) {
      return (currentLevel * 17);
    } else if (currentLevel >= 16 && currentLevel <= 31) {
      return (float) ((Math.pow(1.5 * currentLevel, 2) - 29.5 * currentLevel + 360));
    } else if (currentLevel >= 32) {
      return (float) ((Math.pow(3.5 * currentLevel, 2) - 151.5 * currentLevel + 2220));
    }
    return 0f;
  }

  /*
   * TODO: finish
   */
  @Deprecated
  public static float getRequiredLevel(int currentlevel, int xp) {
    float xp_cur = getXPCostByLevel(currentlevel);
    return xp_cur;
  }

  public static int expCost(int currentLevel) {
    if (currentLevel >= 30) {
      return 62 + (currentLevel - 30) * 7;
    } else if (currentLevel >= 15) {
      return 17 + (currentLevel - 15) * 3;
    } else {
      return 17;
    }
  }
}
