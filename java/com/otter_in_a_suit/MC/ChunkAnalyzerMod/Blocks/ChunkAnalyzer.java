package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
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
  public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_,
      int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_,
      float p_149727_8_, float p_149727_9_) {
    if (p_149727_1_.isRemote)
      return false;
    int x = p_149727_2_;
    int y = p_149727_3_;
    int z = p_149727_4_;

    if (ChunkAnalyzerMod.useXPForScanner) {
      if (p_149727_5_.experienceTotal >= getXPConsumtion()) {
        p_149727_5_.experienceTotal -= getXPConsumtion();
        handleResult(scan(p_149727_1_, p_149727_5_, x, y, z, this));
      } else
        WorldHelper.chat("Insufficient levels for scan!");
    }
    return false;
  }

  private void handleResult(ArrayList<Vertex> result) {
    if (result != null && result.size() > 0) {
      for (Vertex v : result) {
        System.out.println(v.y);
      }
    }
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
