package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.Vertex;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.WorldHelper;

public class ChunkAnalyzer extends BaseScanner implements IScanner {
  
  @Deprecated
  public ChunkAnalyzer() {
    this.setBlockName("Chunk analyzer");
    this.setBlockTextureName("chunkanalyzermod:GoldScanner");
  }

  /************************
   * MAIN FUNCTIONS
   ************************/

  @Override
  public boolean onBlockActivated(World world, int x, int y,
      int z, EntityPlayer player, int p_149727_6_, float p_149727_7_,
      float p_149727_8_, float p_149727_9_) {
    if (world.isRemote)
      return false;
//    int x = cX;
//    int y = cY;
//    int z = cZ;

    if (ChunkAnalyzerMod.useXPForScanner) {
      if (player.experienceTotal >= getXPConsumtion()) {
        player.experienceTotal -= getXPConsumtion();
      } else
        WorldHelper.chat("Insufficient levels for scan!");
    }
    return false;
  }


  @Override
  public int getLevel() {
    return LEVEL_CHUNK_ANALYZER;
  }

  @Override
  public int getXPConsumtion() {
    // TODO Auto-generated method stub
    return 0;
  }
}
