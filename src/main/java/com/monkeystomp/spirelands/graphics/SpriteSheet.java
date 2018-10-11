package com.monkeystomp.spirelands.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpriteSheet {
  private String path;
  private int width,
              height;
  private int[] pixels;
  
  public static SpriteSheet smallTestSheet = new SpriteSheet("./resources/textures/sheets/smallTestSheet.png");
  public static SpriteSheet chestSheet = new SpriteSheet("./resources/textures/sheets/chest_sheet.png");
  public static SpriteSheet itemsSheet = new SpriteSheet("./resources/textures/sheets/items_sheet.png");
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
      BufferedImage image = ImageIO.read(new File(path));
      width = image.getWidth();
      height = image.getHeight();
      pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
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
