package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.ChunkAnalyzerMod;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.CreativeInv;
import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntities.TileEntityMarker;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// BlockSkull
public class MarkerTorch extends BlockContainer {

  public MarkerTorch() {
    super(Material.glass);
    this.setBlockTextureName("chunkanalyzermod:Marker");
    // this.setLightOpacity(100);
    this.setBlockName("Marker torch");
    this.setCreativeTab(CreativeInv._instance);
    this.setHardness(0.0F);
    this.setLightLevel(0.9375f);
    this.setStepSound(soundTypeCloth);
    this.setBlockBounds(0.0f, 0.0f, 0.0f, .25f, 1.0f, .25f);
    this.setLightOpacity(1);
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

  @Override
  public int quantityDropped(Random p_149745_1_) {
    return 0;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  public TileEntity createNewTileEntity(World var1) {
    return new TileEntityMarker();

  }

  public TileEntity createNewTileEntity(World var1, int var2) {
    return new TileEntityMarker();
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
    return AxisAlignedBB.getAABBPool().getAABB(0, 0, 0, 0, 0, 0);
  }

  @SideOnly(Side.CLIENT)
  /**
   * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
   * Transparency
   */
  public int getRenderBlockPass() {
    return 1;
  }

}
