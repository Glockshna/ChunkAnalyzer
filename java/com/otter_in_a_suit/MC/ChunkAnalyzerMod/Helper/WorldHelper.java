package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class WorldHelper {
	
	public static int AIR_THRESHOLD_SKY = 15;

	public static int getGroundLevelYAxsis(World world, int x, int z){
		return getGroundLevelYAxsis(world,x,0,z);
	}
	
	@Deprecated
	public static int getGroundLevelYAxsis(World world, int x, int y, int z){
		y = (y < 0) ? 0 : y;
		System.out.println("canBlockSeeTheSky: "+y+" - "+world.canBlockSeeTheSky(x, y, z));
		while(!world.canBlockSeeTheSky(x, y, z) && y <= 1024){
			System.out.println(y);
			y++;
		}
		return y;
	}
	
	
	// TODO: some fancy traytracing ? 
	// TODO: TEST
	public static int getGroundLevelYAxsis_i(World world, int x, int y, int z){
		y = (y < 0) ? 0 : y;
		int i = 1;
		int blockAirCount = 0;		
		while (i <= 256 && blockAirCount >= AIR_THRESHOLD_SKY){
			y++;
			if (world.getBlock(x, y, z) == Blocks.air) {
				blockAirCount++;
			}
			i++;
		}
		return y;
	}
	public static int getGroundLevelYAxsis_i(World world, int x, int z){
		return getGroundLevelYAxsis_i(world, x, 0, z);
	}
	public static void chat(String msg){
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
	}
}
