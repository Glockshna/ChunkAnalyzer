package com.otter_in_a_suit.MC.ChunkAnalyzerMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CreativeInv extends CreativeTabs {

	public static CreativeInv _instance = new CreativeInv();
	
	private CreativeInv() {
		super("Scanner Mod");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Blocks.iron_ore);
	}

}
