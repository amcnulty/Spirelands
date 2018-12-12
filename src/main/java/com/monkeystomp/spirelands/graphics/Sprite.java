package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Sprite class is used for creating sprites for the game. Sprites can be defined in a number of ways to add flexibility.
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
  //Particles
  /**
   * Dust particle for floating particles on level.
   */
  public static final Sprite DUST = new Sprite(2, 2, 0xFFFFFFFF);
  /**
   * Ember particle for floating particles on level.
   */
  public static final Sprite EMBER = new Sprite(2, 2, 0xFFF05E1B);
  /**
   * Gold pieces for chest opening effect.
   */
  public static final Sprite GOLD = new Sprite(2, 2, 0xFFFCC201);
  // Objects
  /**
   * Wooden stairs that turn left at the end
   */
  public static final Sprite STAIRS_WOOD_TURN_LEFT = new Sprite("./resources/objects/stairs_wood_turn_left.png");
  /**
   * Wooden stairs that go down and to the right.
   */
  public static final Sprite STAIRS_WOOD_DOWN_RIGHT = new Sprite("./resources/objects/stairs_wood_down_right.png");
  // Walls
  /**
   * Stone wall section with top piece.
   */
  public static final Sprite WALL_STONE = new Sprite("./resources/textures/building/wall_stone.png");
  /**
   * Stone wall section with window and top piece.
   */
  public static final Sprite WALL_STONE_WINDOW = new Sprite("./resources/textures/building/wall_stone_window.png");
  /**
   * Stone wall corner section with corner top piece.
   */
  public static final Sprite WALL_STONE_CORNER = new Sprite("./resources/textures/building/wall_stone_corner.png");
  /**
   * Drywall wall section with top piece.
   */
  public static final Sprite WALL_DRYWALL = new Sprite("./resources/textures/building/wall_drywall.png");
  /**
   * Drywall wall section with window and top piece.
   */
  public static final Sprite WALL_DRYWALL_WINDOW = new Sprite("./resources/textures/building/wall_drywall_window.png");
  /**
   * Drywall wall corner section with corner top piece.
   */
  public static final Sprite WALL_DRYWALL_CORNER = new Sprite("./resources/textures/building/wall_drywall_corner.png");
  /**
   * Top piece for walls that runs vertically.
   */
  public static final Sprite WALLTOP_DRYWALL_VERT = new Sprite("./resources/textures/building/walltop_drywall_vert.png");
  /**
   * Top piece for corners of walls.
   */
  public static final Sprite WALLTOP_DRYWALL_CORNER = new Sprite("./resources/textures/building/walltop_drywall_corner.png");
  // Level Transition
  /**
   * The transition sprite that is the size of the entire screen.
   */
  public static final Sprite TRANSITION = new Sprite(Screen.getWidth(), Screen.getHeight(), 0xFF000000);
  // GUI
  /**
   * The background and border for the game menu.
   */
  public static final Sprite GAME_MENU_BACKGROUND = new Sprite("./resources/gui/game_menu_background.png");
  /**
   * Creates a Sprite object of equal width and height from a specific point cut from a sprite sheet.
   * @param size The width and height in pixels of the sprite to cut from the given sprite sheet.
   * @param x The x coordinate of the sprite on the sprite sheet in sprite precision.
   * @param y The y coordinate of the sprite on the sprite sheet in sprite precision.
   * @param sheet The sprite sheet to cut the sprite from.
   */
  public Sprite(int size, int x, int y, SpriteSheet sheet) {
    this.width = size;
    this.height = size;
    this.pixels = new int[width * height];
    this.sheet = sheet;
    this.x = x * width;
    this.y = y * height;
    cutSpriteFromSheet();
  }
  /**
   * Creates a Sprite object of the given width and height and color.
   * @param width The width in pixels of the sprite.
   * @param height The height in pixels of the sprite.
   * @param color The hexadecimal color value of the sprite.
   */
  public Sprite(int width, int height, int color) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
  }
  /**
   * Creates a Sprite object from an array of pixels with the given width and height.
   * @param pixels An array of pixels containing hexadecimal color values.
   * @param width The width of the sprite.
   * @param height The height of the sprite.
   */
  public Sprite(int[] pixels, int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = pixels[i];
    }
  }
  /**
   * Creates a Sprite object from the given path.
   * @param path The path to the image resource to create new sprite from.
   */
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
  /**
   * Creates an array of Sprites from a sprite sheet.
   * @param sheet The sheet to generate sprites from.
   * @param spriteSize The size of the sprites on the sprite sheet. This refers to the width and height.
   */
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
