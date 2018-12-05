package com.monkeystomp.spirelands.level.tile;

import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TileData {
  
  private boolean solid;
  private int x, y;
  
  public static HashMap<Integer, TileData> library = new HashMap<>();
  
  static {
    // Grass
      // Plain
      library.put(0xFF57D357, new TileData(false, 0, 0));
      // Flower
      library.put(0xFFF1F905, new TileData(false, 2, 0));
      // Rock
      library.put(0xFF807854, new TileData(true, 2, 1));
    // Dirt
      // Plain
      library.put(0xFFC8814F, new TileData(false, 1, 0));
      // Rock
      library.put(0xFF6D433C, new TileData(true, 2, 2));
    // Sand
    library.put(0xFFF3E191, new TileData(false, 0, 1));
    // Water
    library.put(0xFF00E6E6, new TileData(true, 1, 1));
    // Cobblestone
    library.put(0xFF9B9B91, new TileData(false, 0, 2));
    // Grey Brick
    library.put(0xFF5B6A72, new TileData(true, 1, 2));
    // Indoor
    library.put(0xFF633C10, new TileData(false, 3, 0));
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

}
