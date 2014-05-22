package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBaseScanner extends TileEntity implements IInventory{
  private ItemStack[] inventory;
  
  public int explosionThreshold = 50;
  public int searchFor_ID = Block.getIdFromBlock(Blocks.iron_ore);

  @Override
  public void writeToNBT(NBTTagCompound par1) {
    super.writeToNBT(par1);
    par1.setInteger("explosionThreshold", explosionThreshold);
    par1.setInteger("searchFor_ID", searchFor_ID);
  }

  @Override
  public void readFromNBT(NBTTagCompound par1) {
    super.readFromNBT(par1);
    this.explosionThreshold = par1.getInteger("explosionThreshold");
    this.searchFor_ID = par1.getInteger("searchFor_ID");
  }

  public void setExplosionThreshold(int x) {
    this.explosionThreshold = x;
  }

  public void setSearchForBlock(Block b) {
    this.searchFor_ID = Block.getIdFromBlock(b);
  }

  public NBTTagCompound getNBTTagCompound() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("explosionThreshold", explosionThreshold);
    nbt.setInteger("searchFor_ID", searchFor_ID);
    return nbt;
  }

  /**
   * Write values from NBT to this bit of an awkward solution, but since we ain't got no pointers
   * making this more flexible is a pain, so I wrote it static for the time being
   * 
   * @param par1: NBTTagCompound
   */
  public void writeToParByNBT(NBTTagCompound par1) {
    this.explosionThreshold =
        (par1.hasKey("explosionThreshold")) ? par1.getInteger("explosionThreshold")
            : explosionThreshold;
    this.searchFor_ID =
        (par1.hasKey("searchFor_ID")) ? par1.getInteger("searchFor_ID") : searchFor_ID;
  }

  /**
   * CUSTOM GUI 
   */
  public int getSizeInventory() {
    return inventory.length;
  }

  public ItemStack getStackInSlot(int var1) {
    return inventory[var1];
  }

  public ItemStack decrStackSize(int slot, int count) {
    ItemStack itemstack = getStackInSlot(slot);

    if (itemstack != null) {
      if (itemstack.stackSize <= count) {
        setInventorySlotContents(slot, null);
      } else {
        itemstack = itemstack.splitStack(count);
        markDirty();
      }
    }
    return itemstack;
  }

  public ItemStack getStackInSlotOnClosing(int slot) {
    ItemStack itemstack = getStackInSlot(slot);
    setInventorySlotContents(slot, null);
    return itemstack;
  }

  public void setInventorySlotContents(int slot, ItemStack itemstack) {
    inventory[slot] = itemstack;

    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
      itemstack.stackSize = getInventoryStackLimit();
    }
    markDirty();
  }

  public String getInventoryName() {
    return "Scanner";
  }

  public boolean hasCustomInventoryName() {
    return false;
  }

  public int getInventoryStackLimit() {
    return 1;
  }

  public boolean isUseableByPlayer(EntityPlayer player) {
    return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
  }

  public void openInventory() {
    // TODO Auto-generated method stub
  }

  public void closeInventory() {
    // TODO Auto-generated method stub
  }

  public boolean isItemValidForSlot(int var1, ItemStack var2) {
    return true;
  }

}
