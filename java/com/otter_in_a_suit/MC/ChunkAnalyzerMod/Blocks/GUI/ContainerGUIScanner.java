package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GUI;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

class ContainerGUIScanner extends Container {
  private TileEntityBaseScanner tileentitiycontainer;

  public ContainerGUIScanner(InventoryPlayer invPlayer, TileEntityBaseScanner entity) {
    this.tileentitiycontainer = entity;
  }

  @Override
  public boolean canInteractWith(EntityPlayer player) {
    return tileentitiycontainer.isUseableByPlayer(player);
  }
}
