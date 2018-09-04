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
    library.put(0xFF57D357, new ArrayList<>(Arrays.asList(0, 0, SpriteSheet.testSheet, false)));
    library.put(0xFFC8814F, new ArrayList<>(Arrays.asList(1, 0, SpriteSheet.testSheet, false)));
    library.put(0xFFF3E191, new ArrayList<>(Arrays.asList(0, 1, SpriteSheet.testSheet, false)));
    library.put(0xFF00E6E6, new ArrayList<>(Arrays.asList(1, 1, SpriteSheet.testSheet, true)));
  }

}