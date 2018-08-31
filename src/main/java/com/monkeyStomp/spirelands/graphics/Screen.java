package com.monkeystomp.spirelands.graphics;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Screen {
  
  private static int  width,
                      height,
                      xOffset,
                      yOffset;
  private static int[] pixels;
  
  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
  }
  
  /**
   * Clears the pixel array.
   */
  public void clear() {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = 0;
    }
  }
  
  public void renderColor(int color) {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
  }
  
  public void renderSprite(int xp, int yp, Sprite sprite) {
    int renderY,
        renderX;
    for (int y = 0; y < sprite.getHeight(); y++) {
      renderY = yp + y;
      for (int x = 0; x < sprite.getWidth(); x++) {
        renderX = xp + x;
        pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
      }
    }
  }
  
  /**
   * Sets the offset variables.
   * @param xOffset The horizontal offset in pixels.
   * @param yOffset The vertical offset in pixels.
   */
  public void setOffset(int xOffset, int yOffset) {
      this.xOffset = xOffset;
      this.yOffset = yOffset;
  }
  
  public static int getWidth() {
    return width;
  }
  
  public static int getHeight() {
    return height;
  }
  
  public static int[] getPixels() {
    return pixels;
  }

}
