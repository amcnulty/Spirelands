package com.monkeystomp.spirelands.level.tile;

import com.monkeystomp.spirelands.graphics.SpriteSheet;
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
      library.put(0xFF57D357, new ArrayList<>(Arrays.asList(Sprite.GRASS, false)));
      // Flower
      library.put(0xFFF1F905, new ArrayList<>(Arrays.asList(Sprite.FLOWER, false)));
      // Rock
      library.put(0xFF807854, new ArrayList<>(Arrays.asList(Sprite.GRASS_ROCK, true)));
    // Dirt
      // Plain
      library.put(0xFFC8814F, new ArrayList<>(Arrays.asList(Sprite.DIRT, false)));
      // Rock
      library.put(0xFF6D433C, new ArrayList<>(Arrays.asList(Sprite.DIRT_ROCK, true)));
    // Sand
    library.put(0xFFF3E191, new ArrayList<>(Arrays.asList(Sprite.SAND, false)));
    // Water
    library.put(0xFF00E6E6, new ArrayList<>(Arrays.asList(Sprite.WATER, true)));
    // Cobblestone
    library.put(0xFF9B9B91, new ArrayList<>(Arrays.asList(Sprite.COBBLESTONE, false)));
    // Grey Brick
    library.put(0xFF5B6A72, new ArrayList<>(Arrays.asList(Sprite.BRICK, true)));
  }

}