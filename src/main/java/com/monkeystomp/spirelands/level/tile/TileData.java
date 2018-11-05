package com.monkeystomp.spirelands.level.tile;

import com.monkeystomp.spirelands.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TileData {
  
  public static HashMap<Integer, ArrayList<Object>> library = new HashMap<>();
  
  static {
    // Grass
      // Plain
      library.put(0xFF57D357, new ArrayList<>(Arrays.asList(Sprite.GRASS, false, 0, 0)));
      // Flower
      library.put(0xFFF1F905, new ArrayList<>(Arrays.asList(Sprite.FLOWER, false, 2, 0)));
      // Rock
      library.put(0xFF807854, new ArrayList<>(Arrays.asList(Sprite.GRASS_ROCK, true, 2, 1)));
    // Dirt
      // Plain
      library.put(0xFFC8814F, new ArrayList<>(Arrays.asList(Sprite.DIRT, false, 1, 0)));
      // Rock
      library.put(0xFF6D433C, new ArrayList<>(Arrays.asList(Sprite.DIRT_ROCK, true, 2, 2)));
    // Sand
    library.put(0xFFF3E191, new ArrayList<>(Arrays.asList(Sprite.SAND, false, 0, 1)));
    // Water
    library.put(0xFF00E6E6, new ArrayList<>(Arrays.asList(Sprite.WATER, true, 1, 1)));
    // Cobblestone
    library.put(0xFF9B9B91, new ArrayList<>(Arrays.asList(Sprite.COBBLESTONE, false, 0, 2)));
    // Grey Brick
    library.put(0xFF5B6A72, new ArrayList<>(Arrays.asList(Sprite.BRICK, true, 1, 2)));
  }

}