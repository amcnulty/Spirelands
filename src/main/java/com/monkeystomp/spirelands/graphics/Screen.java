package com.monkeystomp.spirelands.graphics;

import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import java.awt.Graphics;
import java.util.ArrayList;

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
  private ArrayList<ArrayList<Object>> lightMapEntities = new ArrayList<>();
  
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
  
  public void renderTransparentSprite(int xp, int yp, Sprite sprite, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
    int renderY,
        renderX,
        alpha;
    for (int y = 0; y < sprite.getHeight(); y++) {
      renderY = yp + y;
      for (int x = 0; x < sprite.getWidth(); x++) {
        renderX = xp + x;
        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
          if ((sprite.getPixels()[x + y * sprite.getWidth()] >> 24) < 0) {
            alpha = (int)(((sprite.getPixels()[x + y * sprite.getWidth()] >> 24) + 256) / 25.5);
          }
          else alpha = (int)((sprite.getPixels()[x + y * sprite.getWidth()] >> 24) / 25.5);
          pixels[renderX + renderY * width] = blend(
            pixels[renderX + renderY * width],
            sprite.getPixels()[x + y * sprite.getWidth()],
            alpha
          );
      }
    }
  }

  public void overlayLightMap() {
    renderLightMapEntities();
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = blend(pixels[i], lightMap[i][0], lightMap[i][1]);
    }
  }
  
  public void addLightMapEntity(int x, int y, Sprite sprite) {
    ArrayList<Object> newList = new ArrayList<>();
    newList.add(x);
    newList.add(y);
    newList.add(sprite);
    lightMapEntities.add(newList);
  }
  
  private void renderLightMapEntities() {
    int xp,
        yp,
        renderX,
        renderY,
        currentAlpha,
        subtractiveAlpha;
    for (int i = 0; i < lightMapEntities.size(); i++) {
      xp = (int)lightMapEntities.get(i).get(0) - xOffset;
      yp = (int)lightMapEntities.get(i).get(1) - yOffset;
      Sprite mySprite = (Sprite)lightMapEntities.get(i).get(2);
//      System.out.println((int)(((mySprite.getPixels()[64 + 64 * mySprite.getWidth()] >> 24) + 256) / 25.5));
      for (int y = 0; y < mySprite.getHeight(); y++) {
        renderY = yp + y;
        for (int x = 0; x < mySprite.getWidth(); x++) {
          renderX = xp + x;
          if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
          // Subtract the alpha of the sprite by the current value of the lightMap alpha and
          // don't let alpha be less than 0.
          currentAlpha = lightMap[renderX + renderY * width][1];
//          System.out.println("\n Current Alpha: " + currentAlpha);
//          System.out.println(x + y * mySprite.getWidth());
//          System.out.println(mySprite.getPixels()[x + y * mySprite.getWidth()]);
//          System.out.println(mySprite.getPixels()[x + y * mySprite.getWidth()] >> 24);
          if ((mySprite.getPixels()[x + y * mySprite.getWidth()] >> 24) < 0) {
            subtractiveAlpha = (int)(((mySprite.getPixels()[x + y * mySprite.getWidth()] >> 24) + 256) / 25.5);
//            System.out.println(subtractiveAlpha);
          }
          else {
            subtractiveAlpha = (int)((mySprite.getPixels()[x + y * mySprite.getWidth()] >> 24) / 25.5);
          }
//          System.out.println("Subtractive Alpha: " + subtractiveAlpha);
          currentAlpha -= subtractiveAlpha;
          if (currentAlpha < 0) currentAlpha = 0;
//          System.out.println("New Value For Current Alpha: " + currentAlpha);
          lightMap[renderX + renderY * width][1] = currentAlpha;
        }
      }
    }
    lightMapEntities.clear();
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