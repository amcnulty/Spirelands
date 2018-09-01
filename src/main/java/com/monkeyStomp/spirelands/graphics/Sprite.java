package com.monkeystomp.spirelands.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Sprite {
  
  public static HashMap<Integer, ArrayList<Object>> tileData = new HashMap<>();
  
  
  private int width,
              height,
              x,
              y;
  private int[] pixels;
  private SpriteSheet sheet;
  
  public Sprite(int size, int x, int y, SpriteSheet sheet) {
    this.width = size;
    this.height = size;
    this.pixels = new int[width * height];
    this.sheet = sheet;
    this.x = x * width;
    this.y = y * height;
    cutSpriteFromSheet();
  }
  
  public Sprite(int width, int height, int color) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
  }
  
  private void cutSpriteFromSheet() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[x + y * width] = sheet.getPixels()[(x + this.x) + (y + this.y) * sheet.getWidth()];
      }
    }
  }
  
  public static void setTileData() {
    tileData.put(0xFF57D357, new ArrayList<>(Arrays.asList(SpriteSheet.testSheet, 0, 0)));
    tileData.put(0xFFC8814F, new ArrayList<>(Arrays.asList(SpriteSheet.testSheet, 0, 1)));
    tileData.put(0xFFF3E191, new ArrayList<>(Arrays.asList(SpriteSheet.testSheet, 1, 0)));
    tileData.put(0xFF00E6E6, new ArrayList<>(Arrays.asList(SpriteSheet.testSheet, 1, 1)));
  }
  
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
  
  public int[] getPixels() {
    return pixels;
  }
}
