package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

public class GoldScanner extends IronScanner implements IScanner{
	public GoldScanner() {
		this.setBlockName("Gold Scanner");
	}
	
	public int getLevel() {
		return LEVEL_GOLD;
	}
}