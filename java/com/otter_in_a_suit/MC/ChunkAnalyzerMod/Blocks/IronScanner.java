package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

public class IronScanner extends StoneScanner implements IScanner{
	public IronScanner() {
		this.setBlockName("Iron Scanner");
		this.setBlockTextureName("chunkanalyzermod:IronScanner");
	}
	public int getLevel() {
		return LEVEL_IRON;
	}
}
