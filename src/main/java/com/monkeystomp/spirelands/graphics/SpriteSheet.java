package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Sprite sheets are used as a collection of sprites that can be used across the application.
 * @author Aaron Michael McNulty
 */
public class SpriteSheet {
  private String path;
  private int width,
              height;
  private int[] pixels;
  private Texture texture;
  private BufferedImage image;
  /**
   * A test sheet that contains tile sprites.
   */
  public static SpriteSheet smallTestSheet = new SpriteSheet("./resources/textures/sheets/smallTestSheet.png");
  /**
   * A high resolution sheet that contains tile spirtes.
   */
  public static SpriteSheet highResSheet = new SpriteSheet("./resources/textures/sheets/high-res-sheet.png");
  /**
   * A collection of treasure chests of various styles and states.
   */
  public static SpriteSheet chestSheet = new SpriteSheet("./resources/textures/sheets/chest_sheet.png");
  /**
   * A collection of game items sprites.
   */
  public static SpriteSheet itemsSheet = new SpriteSheet("./resources/textures/sheets/items_sheet.png");
  /**
   * A collection of game items sprites in 32 x 32 size.
   */
  public static SpriteSheet itemsSheet_32x32 = new SpriteSheet("./resources/textures/sheets/32x32_items_sheet.png");
  /**
   * A collection of weapon item sprites.
   */
  public static SpriteSheet weaponsSheet = new SpriteSheet("./resources/textures/sheets/weapons_sheet.png");
  /**
   * A collection of gui elements for the settings screen.
   */
  public static SpriteSheet settingsGuiSheet = new SpriteSheet("./resources/gui/settings_controls.png");
  /**
   * Creates a SpriteSheet of the given path.
   * @param path - Path to the sprite sheet resource.
   */
  public SpriteSheet(String path) {
    this.path = path;
    load();
  }
  
  private void load() {
    try {
      image = ImageIO.read(new File(path));
      width = image.getWidth();
      height = image.getHeight();
      pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
      
    }
    catch (IOException e) {
      System.out.println("Failed to load file at: " + path);
      e.printStackTrace();
    }
  }
  
  public Texture getTexture() {
    if (texture == null) texture = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), image, false);
    return texture;
  }
  
  public int[] getPixels() {
    return pixels;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
}
