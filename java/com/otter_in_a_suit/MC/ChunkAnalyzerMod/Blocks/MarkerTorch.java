package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.Random;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.CreativeInv;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;

import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.item.Item;

public class MarkerTorch extends BlockRedstoneTorch{

	public MarkerTorch(){
		super(false);
		this.setBlockName("Marker torch");
		this.setCreativeTab(CreativeInv._instance);
		this.setHardness(0.0F);
		this.setLightLevel(0.9375f/2);
		this.setStepSound(soundTypeWood);
		this.setBlockTextureName("torch_on");
	}
	
	@Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ChunkAnalyzerMod.markerTorch);
    }
}
