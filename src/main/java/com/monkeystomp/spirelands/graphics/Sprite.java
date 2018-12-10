package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
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
  
  public static final Sprite  // Particles
                              DUST = new Sprite(2, 2, 0xFFFFFFFF),
                              EMBER = new Sprite(2, 2, 0xFFF05E1B),
                              GOLD = new Sprite(2, 2, 0xFFFCC201),
                              // Objects
                              STAIRS_WOOD_TURN_LEFT = new Sprite("./resources/objects/stairs_wood_turn_left.png"),
                              // Walls
                              WALL_STONE = new Sprite("./resources/textures/building/wall_stone.png"),
                              WALL_STONE_WINDOW = new Sprite("./resources/textures/building/wall_stone_window.png"),
                              WALL_STONE_CORNER = new Sprite("./resources/textures/building/wall_stone_corner.png"),
                              WALL_DRYWALL = new Sprite("./resources/textures/building/wall_drywall.png"),
                              WALL_DRYWALL_WINDOW = new Sprite("./resources/textures/building/wall_drywall_window.png"),
                              WALL_DRYWALL_CORNER = new Sprite("./resources/textures/building/wall_drywall_corner.png"),
                              WALLTOP_DRYWALL_VERT = new Sprite("./resources/textures/building/walltop_drywall_vert.png"),
                              WALLTOP_DRYWALL_CORNER = new Sprite("./resources/textures/building/walltop_drywall_corner.png"),
                              // Level Transition
                              TRANSITION = new Sprite(Screen.getWidth(), Screen.getHeight(), 0xFF000000),
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
    texture = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), image, false);
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
