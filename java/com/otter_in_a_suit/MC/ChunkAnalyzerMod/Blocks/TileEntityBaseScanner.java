package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBaseScanner extends TileEntity {
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
   * Write values from NBT to this
   * bit of an awkward solution, but since we ain't got no pointers making this more flexible is a pain, so I wrote it static for the time being
   * @param par1: NBTTagCompound
   */
  public void writeToParByNBT(NBTTagCompound par1){
    this.explosionThreshold = (par1.hasKey("explosionThreshold")) ? par1.getInteger("explosionThreshold") : explosionThreshold;
    this.searchFor_ID = (par1.hasKey("searchFor_ID")) ? par1.getInteger("searchFor_ID") : searchFor_ID;
  }

  /************************
   * Sending Tile Entity Data From Server to Client
   *********************** 
   * public Packet getDescriptionPacket() { NBTTagCompound nbtTag = new NBTTagCompound();
   * this.writeToNBT(nbtTag); return new Packet132TileEntityData(this.xCoord, this.yCoord,
   * this.zCoord, 1, nbtTag); }
   * 
   * public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
   * readFromNBT(packet.customParam1); }
   */
}
