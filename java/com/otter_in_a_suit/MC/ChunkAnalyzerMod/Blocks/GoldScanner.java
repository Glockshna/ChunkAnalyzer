package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GoldScanner extends BaseScanner implements IScanner {
  public GoldScanner() {
    this.setBlockName("Gold Scanner");
    this.setBlockTextureName("chunkanalyzermod:GoldScanner");
  }

  @Override
  public int getLevel() {
    return LEVEL_GOLD;
  }

}