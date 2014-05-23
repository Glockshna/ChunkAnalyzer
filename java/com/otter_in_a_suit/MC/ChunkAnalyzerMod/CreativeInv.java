package com.otter_in_a_suit.MC.ChunkAnalyzerMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeInv extends CreativeTabs {

  public static CreativeInv _instance = new CreativeInv();

  private CreativeInv() {
    super("Scanner Mod");
  }

  @Override
  @SideOnly(Side.CLIENT)
  public Item getTabIconItem() {
    return Item.getItemFromBlock(Blocks.gold_ore);
  }

}
