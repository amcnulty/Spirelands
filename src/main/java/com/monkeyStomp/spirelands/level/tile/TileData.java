package com.monkeystomp.spirelands.level.tile;

import com.monkeystomp.spirelands.graphics.SpriteSheet;
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
      library.put(0xFF57D357, new ArrayList<>(Arrays.asList(0, 0, SpriteSheet.smallTestSheet, false)));
      // Flower
      library.put(0xFFF1F905, new ArrayList<>(Arrays.asList(2, 0, SpriteSheet.smallTestSheet, false)));
      // Rock
      library.put(0xFF807854, new ArrayList<>(Arrays.asList(2, 1, SpriteSheet.smallTestSheet, true)));
    // Dirt
      // Plain
      library.put(0xFFC8814F, new ArrayList<>(Arrays.asList(1, 0, SpriteSheet.smallTestSheet, false)));
      // Rock
      library.put(0xFF6D433C, new ArrayList<>(Arrays.asList(2, 2, SpriteSheet.smallTestSheet, true)));
    // Sand
    library.put(0xFFF3E191, new ArrayList<>(Arrays.asList(0, 1, SpriteSheet.smallTestSheet, false)));
    // Water
    library.put(0xFF00E6E6, new ArrayList<>(Arrays.asList(1, 1, SpriteSheet.smallTestSheet, true)));
    // Cobblestone
    library.put(0xFF9B9B91, new ArrayList<>(Arrays.asList(0, 2, SpriteSheet.smallTestSheet, false)));
    // Grey Brick
    library.put(0xFF5B6A72, new ArrayList<>(Arrays.asList(1, 2, SpriteSheet.smallTestSheet, true)));
  }

}