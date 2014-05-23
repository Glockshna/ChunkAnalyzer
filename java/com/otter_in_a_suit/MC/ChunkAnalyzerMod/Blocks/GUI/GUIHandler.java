package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.GUI;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityBaseScanner;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
  
  public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity entity = world.getTileEntity(x, y, z);
    switch (id) {
      case 0:
        if (entity != null && entity instanceof TileEntityBaseScanner) {
          return new ContainerGUIScanner(player.inventory, (TileEntityBaseScanner) entity);
        } else {
          return null;
        }
      default:
        return null;
    }
  }

  public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity entity = world.getTileEntity(x, y, z);

    switch (id) {
      case 0:
        if (entity != null && entity instanceof TileEntityBaseScanner) {
          return new GUIScanner(player.inventory, (TileEntityBaseScanner) entity);
        } else {
          return null;
        }
      default:
        return null;
    }
  }
}
