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
  private static int[]  pixels;
  private static int[][] lightMap;
  
  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    this.lightMap = new int[width * height][2];
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
  
  public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
    int renderY,
        renderX;
    for (int y = 0; y < sprite.getHeight(); y++) {
      renderY = yp + y;
      for (int x = 0; x < sprite.getWidth(); x++) {
        renderX = xp + x;
        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
        if (sprite.getPixels()[x + y * sprite.getWidth()] != 0) {
          pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
        }
      }
    }
  }

  // Temporary way of dealing with a bad spritesheet
  public void renderPlayer(int xp, int yp, Sprite sprite, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
    int renderY,
        renderX;
    for (int y = 0; y < sprite.getHeight(); y++) {
      renderY = yp + y;
      for (int x = 0; x < sprite.getWidth(); x++) {
        renderX = xp + x;
        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
        if (sprite.getPixels()[x + y * sprite.getWidth()] != 0xffff00ff) {
          pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
        }
      }
    }
  }
  
  public void renderTile(int xp, int yp, Sprite sprite) {
    xp -= xOffset;
    yp -= yOffset;
    int renderY,
        renderX;
    for (int y = 0; y < sprite.getHeight(); y++) {
      renderY = y + yp;
      for (int x = 0; x < sprite.getWidth(); x++) {
        renderX = x + xp;
        if (renderX < -sprite.getWidth() || renderX > width - 1 || renderY < 0 || renderY > height - 1) break;
        if (renderX < 0) renderX = 0;
        pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
      }
    }
  }

  public void fillLightMap(int color, int alpha) {
    for (int i = 0; i < lightMap.length; i++) {
      lightMap[i][0] = color;
      lightMap[i][1] = alpha;
    }
  }

  public void overlayLightMap() {
    for (int i = 0; i < pixels.length; i++) {
      int oldR = (pixels[i] & 0xff0000) >> 16;
      int oldG = (pixels[i] & 0xff00) >> 8;
      int oldB = pixels[i] & 0xff;

      int newR = (lightMap[i][0] & 0xff0000) >> 16;
      int newG = (lightMap[i][0] & 0xff00) >> 8;
      int newB = lightMap[i][0] & 0xff;

      int r = (int)(oldR * ((10 - (float)lightMap[i][1]) / 10) + newR * (((float)lightMap[i][1]) / 10));
      int g = (int)(oldG * ((10 - (float)lightMap[i][1]) / 10) + newG * (((float)lightMap[i][1]) / 10));
      int b = (int)(oldB * ((10 - (float)lightMap[i][1]) / 10) + newB * (((float)lightMap[i][1]) / 10));

      pixels[i] = (r << 16) | (g << 8) | b;
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
