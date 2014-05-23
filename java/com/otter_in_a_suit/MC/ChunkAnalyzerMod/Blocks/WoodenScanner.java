package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

public class WoodenScanner extends BaseScanner implements IScanner {
  public WoodenScanner() {
    this.setBlockName("Wooden Scanner");
    this.setBlockTextureName("chunkanalyzermod:WoodScanner");
  }

  @Override
  public int getLevel() {
    return LEVEL_WOOD;
  }
}
