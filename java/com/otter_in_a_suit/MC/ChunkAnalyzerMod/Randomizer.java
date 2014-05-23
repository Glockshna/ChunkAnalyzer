package com.otter_in_a_suit.MC.ChunkAnalyzerMod;

public class Randomizer {

  public static int EXPLOSION_THRESHOLD_BASE = 50;

  public final static int EXPLOSION_THRESHOLD_BASE_C = 50;
  public static final int LEVEL_EXPL_DES = 0;
  public static final int LEVEL_EXPL_PRES = 1;
  public static final int LEVEL_PRESERVE = 2;


  public int getExplosion_threshold() {
    return EXPLOSION_THRESHOLD_BASE;
  }

  public int calculateExplosionLevel(int threshold) {
    EXPLOSION_THRESHOLD_BASE = threshold;
    int ran = randomWithRange(0, 100);
    System.out.println("Thres: " + EXPLOSION_THRESHOLD_BASE + ", random: " + ran);
    if (ran >= EXPLOSION_THRESHOLD_BASE) {
      if (ran >= EXPLOSION_THRESHOLD_BASE * 1.5f)
        return LEVEL_EXPL_DES;
      else
        return LEVEL_EXPL_PRES;
    } else {
      return LEVEL_PRESERVE;
    }
  }

  private int randomWithRange(int min, int max) {
    int range = (max - min) + 1;
    return (int) (Math.random() * range) + min;
  }
}
