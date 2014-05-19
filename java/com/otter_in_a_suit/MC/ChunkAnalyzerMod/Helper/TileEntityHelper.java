package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.otter_in_a_suit.MC.ChunkAnalyzerMod.Blocks.TileEntityBaseScanner;

public class TileEntityHelper {
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}

	public static Object deserialize(byte[] data) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	public static TileEntityBaseScanner getTileEntityBaseScannerFromCoords(World world, int x, int y, int z) throws Exception{
		TileEntity genericTile = world.getTileEntity(x, y, z);
		if (genericTile != null && genericTile instanceof TileEntityBaseScanner) {
			TileEntityBaseScanner tile = (TileEntityBaseScanner) genericTile;
			
			return tile;
		} else throw new Exception("No TileEntityBaseScanner found!");
	}
}
