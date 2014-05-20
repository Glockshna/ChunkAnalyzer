package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.ArrayList;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper.Vertex;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ChunkAnalyzer extends BaseScanner implements IScanner {
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

   handleResult(scan(p_149727_1_, p_149727_5_, x, y, z, this));
   
    return false;
  }
  
  private void handleResult(ArrayList<Vertex> result){
    if(result != null && result.size() > 0){
      // TODO: implement TODO: use exp or similar
      for(Vertex v : result){
        
      }
    }
  }

  @Override
  public int getLevel() {
    return LEVEL_CHUNK;
  }
}