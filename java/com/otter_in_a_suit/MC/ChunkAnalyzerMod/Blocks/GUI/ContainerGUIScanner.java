package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GUI;

/*
 * http://www.minecraftforum.net/topic/1959857-162-advanced-minecraft-forge-modding-tutorial-2-inventories/
 */
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class ContainerGUIScanner extends Container {
  private TileEntityBaseScanner tileentitiycontainer;

  public ContainerGUIScanner(InventoryPlayer invPlayer, TileEntityBaseScanner entity) {
    this.tileentitiycontainer = entity;
    
    // Render player's inventory
    for(int x = 0; x < 9; x++) {
      this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
    }
    for(int y = 0; y < 3; y++) {
      for(int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
      }
    }
    
    // Render GUI-Slot(s)
    // single slot @ 79|34, 18px wide
     this.addSlotToContainer(new Slot(entity, 0, 80, 35));
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return tileentitiycontainer.isUseableByPlayer(player);
  }
  
  /*
   * Prevent crash from shift-click
   * (non-Javadoc)
   * @see net.minecraft.inventory.Container#transferStackInSlot(net.minecraft.entity.player.EntityPlayer, int)
   */
  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int i) {
    Slot slot = getSlot(i);

    if(slot != null && slot.getHasStack()) {
          ItemStack itemstack = slot.getStack();
          ItemStack result = itemstack.copy();

          if(i >= 36) {
            if(!mergeItemStack(itemstack, 0, 36, false)) {
                  return null;
            }
          } else if(!mergeItemStack(itemstack, 36, 36 + tileentitiycontainer.getSizeInventory(), false)) {
            return null;
          }

          if(itemstack.stackSize == 0) {
            slot.putStack(null);
          } else {
            slot.onSlotChanged();
          }
          slot.onPickupFromSlot(player, itemstack); 
          return result;
    }
    return null;
  }
}
