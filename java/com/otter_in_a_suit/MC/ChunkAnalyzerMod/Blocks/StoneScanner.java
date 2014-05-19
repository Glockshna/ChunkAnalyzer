package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

public class StoneScanner extends BaseScanner implements IScanner{
	public StoneScanner() {
		this.setBlockName("Stone Scanner");
	}
	
	public int getLevel() {
		return LEVEL_STONE;
	}
}
