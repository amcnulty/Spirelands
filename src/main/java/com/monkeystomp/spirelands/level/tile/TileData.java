package com.monkeystomp.spirelands.level.tile;

import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TileData {
  
  private boolean solid;
  private int x, y;
  
  public static final HashMap<String, TileData> LIBRARY = new HashMap<>();
  
  static {
    // Marching Squares
      // Grass-dirt
      LIBRARY.put(createKey(0xFF57D357, 0xFF57D357, 0xFFC8814F, 0xFF57D357), new TileData(false, 0, 0));
      LIBRARY.put(createKey(0xFF57D357, 0xFF57D357, 0xFF57D357, 0xFFC8814F), new TileData(false, 1, 0));
      LIBRARY.put(createKey(0xFF57D357, 0xFF57D357, 0xFFC8814F, 0xFFC8814F), new TileData(false, 2, 0));
      LIBRARY.put(createKey(0xFF57D357, 0xFFC8814F, 0xFF57D357, 0xFF57D357), new TileData(false, 3, 0));
      LIBRARY.put(createKey(0xFF57D357, 0xFFC8814F, 0xFFC8814F, 0xFF57D357), new TileData(false, 4, 0));
      LIBRARY.put(createKey(0xFF57D357, 0xFFC8814F, 0xFF57D357, 0xFFC8814F), new TileData(false, 5, 0));
      LIBRARY.put(createKey(0xFF57D357, 0xFFC8814F, 0xFFC8814F, 0xFFC8814F), new TileData(false, 6, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFF57D357, 0xFF57D357, 0xFF57D357), new TileData(false, 7, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFF57D357, 0xFFC8814F, 0xFF57D357), new TileData(false, 8, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFF57D357, 0xFF57D357, 0xFFC8814F), new TileData(false, 9, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFF57D357, 0xFFC8814F, 0xFFC8814F), new TileData(false, 10, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFFC8814F, 0xFF57D357, 0xFF57D357), new TileData(false, 11, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFFC8814F, 0xFFC8814F, 0xFF57D357), new TileData(false, 12, 0));
      LIBRARY.put(createKey(0xFFC8814F, 0xFFC8814F, 0xFF57D357, 0xFFC8814F), new TileData(false, 13, 0));
    
    // Grass
      // Plain
      LIBRARY.put(createKey(0xFF57D357, 0xFF57D357, 0xFF57D357, 0xFF57D357), new TileData(false, 0, 1));
      // Flower
      LIBRARY.put(createKey(0xFFF1F905, 0xFFF1F905, 0xFFF1F905, 0xFFF1F905), new TileData(false, 2, 1));
    // Dirt
      // Plain
      LIBRARY.put(createKey(0xFFC8814F, 0xFFC8814F, 0xFFC8814F, 0xFFC8814F), new TileData(false, 1, 1));
    // Sand
    LIBRARY.put(createKey(0xFFF3E191, 0xFFF3E191, 0xFFF3E191, 0xFFF3E191), new TileData(false, 3, 1));
    // Water
    LIBRARY.put(createKey(0xFF00E6E6, 0xFF00E6E6, 0xFF00E6E6, 0xFF00E6E6), new TileData(false, 4, 1));
    // Cobblestone
    LIBRARY.put(createKey(0xFF9B9B91, 0xFF9B9B91, 0xFF9B9B91, 0xFF9B9B91), new TileData(false, 5, 1));
    // Indoor
    LIBRARY.put(createKey(0xFF633C10, 0xFF633C10, 0xFF633C10, 0xFF633C10), new TileData(false, 6, 1));
  }
  
  public TileData(boolean solid, int x, int y) {
    this.solid = solid;
    this.x = x;
    this.y = y;
  }

  public boolean isSolid() {
    return solid;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
  
  private static String createKey(int color1, int color2, int color3, int color4) {
    return String.valueOf(color1) + String.valueOf(color2) + String.valueOf(color3) + String.valueOf(color4);
  }

}
