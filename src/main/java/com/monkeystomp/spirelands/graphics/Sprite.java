package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.monkeystomp.spirelands.gui.styles.GameColors;
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
              rawWidth,
              rawHeight,
              x,
              y;
  private int[] pixels;
  private SpriteSheet sheet;
  private Texture texture;
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!          Particles             !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * Dust particle for floating particles on level.
   */
  public static final Sprite DUST = new Sprite(2, 2, GameColors.WHITE);
  /**
   * Ember particle for floating particles on level.
   */
  public static final Sprite EMBER = new Sprite(2, 2, GameColors.EMBER_PARTICLE_COLOR);
  /**
   * Gold pieces for chest opening effect.
   */
  public static final Sprite GOLD = new Sprite(2, 2, GameColors.GOLD_PARTICLE_COLOR);
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!            Objects             !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * The gold indicator icon.
   */
  public static final Sprite GOLD_INDICATOR = new Sprite(new Sprite("./resources/gui/gold_indicator.png"), 50.0);
  /**
   * The ability points indicator icon.
   */
  public static final Sprite ABILITY_POINTS_INDICATOR = new Sprite(new Sprite("./resources/gui/ability_points_indicator.png"), 12);
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
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!        Level Transition        !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * The transition sprite that is the size of the entire screen.
   */
  public static final Sprite TRANSITION = new Sprite(Screen.getWidth(), Screen.getHeight(), GameColors.BLACK);
  /**
   *          !!################################!!
   *          !!                                !!
   *          !!              GUI               !!
   *          !!                                !!
   *          !!################################!!
   */
  /**
   * The background and border for the game menu.
   */
  public static final Sprite GAME_MENU_BACKGROUND = new Sprite("./resources/gui/game_menu_background.png");
  /**
   * The right arrow that appears next to the play buttons when active.
   */
  public static final Sprite GAME_MENU_RIGHT_ARROW = new Sprite("./resources/gui/arrow.png");
  /**
   * The hand cursor.
   */
  public static final Sprite HAND_CURSOR = new Sprite(new Sprite("./resources/gui/hand_cursor.png"), 16);
  /**
   * The white cursor.
   */
  public static final Sprite WHITE_CURSOR = new Sprite(new Sprite("./resources/gui/white_cursor.png"), 16);
  /**
   * The fantasy cursor.
   */
  public static final Sprite FANTASY_CURSOR = new Sprite(new Sprite("./resources/gui/fantasy_cursor.png"), 16);
  /**
   * Lock icon
   */
  public static final Sprite LOCK = new Sprite(new Sprite("./resources/gui/lock.png"), 9);
  /**
   * Creates a Sprite object of equal width and height from a specific point cut from a sprite sheet.
   * @param rawSize The width and height in pixels of the sprite to cut from the given sprite sheet. This is the size of the sprite on the sheet.
   * @param renderSize The size to render this sprite at. This will set the width and height of the sprite.
   * @param x The x coordinate of the sprite on the sprite sheet in sprite precision.
   * @param y The y coordinate of the sprite on the sprite sheet in sprite precision.
   * @param sheet The sprite sheet to cut the sprite from.
   */
  public Sprite(int rawSize, int renderSize, int x, int y, SpriteSheet sheet) {
    this.width = renderSize;
    this.height = renderSize;
    this.rawWidth = rawSize;
    this.rawHeight = rawSize;
    this.pixels = new int[rawWidth * rawHeight];
    this.sheet = sheet;
    this.x = x * rawWidth;
    this.y = y * rawHeight;
    cutSpriteFromSheet();
  }
  /**
   * Creates a Sprite object of the given width and height and color.
   * @param width The width in pixels of the sprite.
   * @param height The height in pixels of the sprite.
   * @param color The hexadecimal color value of the sprite.
   */
  public Sprite(int width, int height, int color) {
    this.width = this.rawWidth = width;
    this.height = this.rawHeight = height;
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
    this.width = this.rawWidth = width;
    this.height = this.rawHeight = height;
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
      this.width = this.rawWidth = image.getWidth();
      this.height = this.rawHeight = image.getHeight();
      this.pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * Creates a sprite that is scaled up or down to match the render size. This will create a square sprite.
   * @param path The path to the image resource to create the new sprite from.
   * @param renderSize The width and height in pixels this sprite should render at.
   */
  public Sprite(String path, int renderSize) {
    try {
      BufferedImage image = ImageIO.read(new File(path));
      this.width = renderSize;
      this.height = renderSize;
      this.rawWidth = image.getWidth();
      this.rawHeight = image.getHeight();
      this.pixels = new int[rawWidth * rawHeight];
      image.getRGB(0, 0, rawWidth, rawHeight, pixels, 0, rawWidth);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public Sprite(String path, int renderWidth, int renderHeight) {
    try {
      BufferedImage image = ImageIO.read(new File(path));
      this.width = renderWidth;
      this.height = renderHeight;
      this.rawWidth = image.getWidth();
      this.rawHeight = image.getHeight();
      this.pixels = new int[rawWidth * rawHeight];
      image.getRGB(0, 0, rawWidth, rawHeight, pixels, 0, rawWidth);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * Creates a scaled sprite from an existing one based on a percentage value.
   * @param original The original sprite to create the new sprite from.
   * @param percentage Percentage to scale new image range from 1.0 and up
   */
  public Sprite(Sprite original, double percentage) {
    this.width = (int)(original.getWidth() * (percentage / 100.0));
    this.height = (int)(original.getHeight() * (percentage / 100.0));
    this.rawWidth = original.getWidth();
    this.rawHeight = original.getHeight();
    this.pixels = original.getPixels();
  }
  /**
   * Creates a scaled sprite set to a specific render size. This is used for square sprites.
   * @param original The original sprite to create the new sprite from.
   * @param renderSize Pixel size to render image at.
   */
  public Sprite(Sprite original, int renderSize) {
    this.width = renderSize;
    this.height = renderSize;
    this.rawWidth = original.getWidth();
    this.rawHeight = original.getHeight();
    this.pixels = original.getPixels();
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
    BufferedImage image = new BufferedImage(rawWidth, rawHeight, BufferedImage.TYPE_INT_ARGB);
    image.setRGB(0, 0, rawWidth, rawHeight, pixels, 0, rawWidth);
    texture = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), image, false);
  }
  
  private void cutSpriteFromSheet() {
    for (int y = 0; y < rawHeight; y++) {
      for (int x = 0; x < rawWidth; x++) {
        pixels[x + y * rawWidth] = sheet.getPixels()[(x + this.x) + (y + this.y) * sheet.getWidth()];
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
