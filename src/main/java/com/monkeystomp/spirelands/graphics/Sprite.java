package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
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
  private Texture texture;
  
  public static final Sprite  // Tiles
                              VOID_SPRITE = new Sprite(Tile.TILE_SIZE, Tile.TILE_SIZE, 0xFF161616),
                              GRASS = new Sprite(Tile.TILE_SIZE, 0, 0, SpriteSheet.smallTestSheet),
                              DIRT = new Sprite(Tile.TILE_SIZE, 1, 0, SpriteSheet.smallTestSheet),
                              FLOWER = new Sprite(Tile.TILE_SIZE, 2, 0, SpriteSheet.smallTestSheet),
                              SAND = new Sprite(Tile.TILE_SIZE, 0, 1, SpriteSheet.smallTestSheet),
                              WATER = new Sprite(Tile.TILE_SIZE, 1, 1, SpriteSheet.smallTestSheet),
                              GRASS_ROCK = new Sprite(Tile.TILE_SIZE, 2, 1, SpriteSheet.smallTestSheet),
                              COBBLESTONE = new Sprite(Tile.TILE_SIZE, 0, 2, SpriteSheet.smallTestSheet),
                              BRICK = new Sprite(Tile.TILE_SIZE, 1, 2, SpriteSheet.smallTestSheet),
                              DIRT_ROCK = new Sprite(Tile.TILE_SIZE, 2, 2, SpriteSheet.smallTestSheet),
                              // Particles
                              DUST = new Sprite(2, 2, 0xFFFFFFFF),
                              EMBER = new Sprite(2, 2, 0xFFF05E1B),
                              GOLD = new Sprite(2, 2, 0xFFFCC201),
                              // GUI
                              GAME_MENU_BACKGROUND = new Sprite("./resources/gui/game_menu_background.png");
  
  public Sprite(int size, int x, int y, SpriteSheet sheet) {
    this.width = size;
    this.height = size;
    this.pixels = new int[width * height];
    this.sheet = sheet;
    this.x = x * width;
    this.y = y * height;
    cutSpriteFromSheet();
//    setTexture();
  }
  
  public Sprite(int width, int height, int color) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
//    setTexture();
  }
  
  public Sprite(int[] pixels, int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = pixels[i];
    }
//    setTexture();
  }
  
  public Sprite(String path) {
    try {
      BufferedImage image = ImageIO.read(new File(path));
      this.width = image.getWidth();
      this.height = image.getHeight();
      this.pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
//      setTexture();
    }
    catch (IOException e) {
      e.printStackTrace();
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
  
  private void setTexture() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    image.setRGB(0, 0, width, height, pixels, 0, width);
    texture = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), image, true);
  }
  
  private void cutSpriteFromSheet() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[x + y * width] = sheet.getPixels()[(x + this.x) + (y + this.y) * sheet.getWidth()];
      }
    }
  }
  
  public Texture getTexture() {
    if (texture == null) setTexture();
    return texture;
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