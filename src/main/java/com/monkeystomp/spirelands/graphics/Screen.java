package com.monkeystomp.spirelands.graphics;

import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Screen {
  
  private static int  width,
                      height,
                      xOffset,
                      yOffset;
  private static double scaleX,
                        scaleY;
  private static int[]  pixels;
  private static int[][] lightMap;
  private ArrayList<FontInfo> fontInfo = new ArrayList<>();
  
  public Screen(int width, int height, double scaleX, double scaleY) {
    this.width = width;
    this.height = height;
    this.scaleX = scaleX;
    this.scaleY = scaleY;
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

  public void renderSprite(int xp, int yp, Sprite sprite, int alpha, boolean fixed) {
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
          pixels[renderX + renderY * width] = blend(
            pixels[renderX + renderY * width],
            sprite.getPixels()[x + y * sprite.getWidth()],
            alpha
          );
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
      pixels[i] = blend(pixels[i], lightMap[i][0], lightMap[i][1]);
    }
  }

  private int blend(int background, int foreground, int alpha) {
    int oldR = (background & 0xff0000) >> 16;
    int oldG = (background & 0xff00) >> 8;
    int oldB = background & 0xff;

    int newR = (foreground & 0xff0000) >> 16;
    int newG = (foreground & 0xff00) >> 8;
    int newB = foreground & 0xff;

    int r = (int)(oldR * ((10 - (float)alpha) / 10) + newR * (((float)alpha) / 10));
    int g = (int)(oldG * ((10 - (float)alpha) / 10) + newG * (((float)alpha) / 10));
    int b = (int)(oldB * ((10 - (float)alpha) / 10) + newB * (((float)alpha) / 10));

    return (r << 16) | (g << 8) | b;
  }
  
  public void addText(FontInfo info) {
    fontInfo.add(info);
  }
  
  public void renderFonts(Graphics graphics) {
    for (int i = 0; i < fontInfo.size(); i++) {
      graphics.setFont(fontInfo.get(i).getFont());
      graphics.setColor(fontInfo.get(i).getColor());
      int verticalAdjustment = graphics.getFontMetrics().getAscent() / 2;
      graphics.drawString(fontInfo.get(i).getText(), (int)(fontInfo.get(i).getX() * scaleX), (int)(fontInfo.get(i).getY() * scaleY) + verticalAdjustment);
    }
    fontInfo.clear();
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
  
  public static double getScaleX() {
    return scaleX;
  }
  
  public static double getScaleY() {
    return scaleY;
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
