package com.monkeystomp.spirelands.graphics;

import com.monkeystomp.spirelands.level.tile.Tile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Sprite {
  
  private int width,
              height,
              x,
              y;
  private int[] pixels;
  private SpriteSheet sheet;
  
  public static final Sprite VOID_SPRITE = new Sprite(Tile.TILE_SIZE, Tile.TILE_SIZE, 0x161616);
  
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
  
  public Sprite(int[] pixels, int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = pixels[i];
    }
  }
  
  public Sprite(String path) {
    try {
      BufferedImage image = ImageIO.read(new File(path));
      this.width = image.getWidth();
      this.height = image.getHeight();
      this.pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void cutSpriteFromSheet() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[x + y * width] = sheet.getPixels()[(x + this.x) + (y + this.y) * sheet.getWidth()];
      }
    }
  }
  
  public static Sprite[] split(SpriteSheet sheet, int spriteSize) {
    Sprite[] sprites = new Sprite[(sheet.getWidth() * sheet.getHeight()) / (spriteSize * spriteSize)];
    int[] pixels = new int[spriteSize * spriteSize];
    int currentIndex = 0;
    for (int yp = 0; yp < sheet.getHeight() / spriteSize; yp++) {
      for (int xp = 0; xp < sheet.getWidth() / spriteSize; xp++) {
        for (int y = 0; y < spriteSize; y++) {
          for (int x = 0; x < spriteSize; x++) {
            pixels[x + y * spriteSize] = sheet.getPixels()[(x + xp * spriteSize) + (y + yp * spriteSize) * sheet.getWidth()];
          }
        }
        sprites[currentIndex++] = new Sprite(pixels, spriteSize, spriteSize);
      }
    }
    return sprites;
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