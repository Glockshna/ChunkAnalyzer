package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

public interface IScanner {
  public final static int LEVEL_BASE = 0;
  public final static int LEVEL_STONE = 3;
  public final static int LEVEL_IRON = 2;
  public final static int LEVEL_GOLD = 1;
  public final static int LEVEL_CHUNK_ANALYZER = 10;
  
  public final static float XP_BASE_CONSUMPTION = 1f;
  public final static float XP_BASE_MODIFIER = .3f;
  
  public abstract int getLevel();
  public abstract int getXPConsumtion();
}
