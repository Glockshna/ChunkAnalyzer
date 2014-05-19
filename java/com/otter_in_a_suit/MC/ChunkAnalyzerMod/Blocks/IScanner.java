package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

public interface IScanner {
	public final static int LEVEL_BASE = 0;
	public final static int LEVEL_STONE = 1;
	public final static int LEVEL_IRON = 2;
	public final static int LEVEL_GOLD = 3;
	public abstract int getLevel();
}
