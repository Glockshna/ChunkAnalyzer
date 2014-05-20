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
}
