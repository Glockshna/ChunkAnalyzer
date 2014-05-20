package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.Random;

import net.minecraft.block.BlockSkull;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.CreativeInv;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
// BlockSkull
public class MarkerTorch extends BlockSkull {

  public MarkerTorch() {
    super();
    this.setBlockName("Marker torch");
    this.setCreativeTab(CreativeInv._instance);
    this.setHardness(0.0F);
    this.setLightLevel(0.9375f / 2);
    this.setStepSound(soundTypeCloth);
    this.setBlockTextureName("chunkanalyzermod:Marker");
  }

  @Override
  public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
    return Item.getItemFromBlock(ChunkAnalyzerMod.markerTorch);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
    return Item.getItemFromBlock(ChunkAnalyzerMod.markerTorch);
  }

  @Override
  public boolean renderAsNormalBlock() {
    return true;
  }
  
  public int quantityDropped(Random p_149745_1_)
  {
      return 0;
  }

}
