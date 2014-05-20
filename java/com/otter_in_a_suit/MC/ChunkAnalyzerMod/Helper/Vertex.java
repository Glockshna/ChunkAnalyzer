package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

import net.minecraft.block.Block;

public class Vertex {
  public int x, y, z;
  public Block block;

  public Vertex(int x, int y, int z, Block block) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.block = block;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Vertex))
      return false;
    Vertex v = (Vertex) o;
    return x == v.x && y == v.y && z == v.z && block == v.block;
  }

}
