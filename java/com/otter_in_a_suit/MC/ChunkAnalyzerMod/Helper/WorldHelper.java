package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class WorldHelper {

	public static int getGroundLevelYAxsis(World world, int x, int z){
		return getGroundLevelYAxsis(world,x,-1,z);
	}
	public static int getGroundLevelYAxsis(World world, int x, int y, int z){
		y = (y < 0) ? 0 : y;
		System.out.println("canBlockSeeTheSky: "+y+" - "+world.canBlockSeeTheSky(x, y, z));
		while(!world.canBlockSeeTheSky(x, y, z) && y <= 1024){
			System.out.println(y);
			y++;
		}
		return y;
	}
	public static void chat(String msg){
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
	}
}
