package com.otter_in_a_suit.MC.ChunkAnalyzerMod.Helper;

public class Vertex {
    public int x, y, z;
    public Vertex(int x, int y, int z){
    	this.x = x; this.y = y; this.z = z;
    }
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Vertex)) return false;
        Vertex v = (Vertex)o;
        return x == v.x && y == v.y && z == v.z;
    }

}