package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GL2;
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
  private static int[] lightMap;
  private ArrayList<FontInfo> fontInfo = new ArrayList<>();
  private int lightMapColor = 0,
              lightMapAlpha = 0;
  
  public Screen(int width, int height, double scaleX, double scaleY) {
    this.width = width;
    this.height = height;
    this.scaleX = scaleX;
    this.scaleY = scaleY;
    this.pixels = new int[width * height];
    this.lightMap = new int[width * height];
  }
  
  public void renderTitleScreenBackground(GL2 gl) {
    gl.glColor4f(1, 0, 0, 1);
    gl.glBegin(GL2.GL_QUADS);
      gl.glVertex2f(0, 0);
      gl.glVertex2f(width, 0);
      gl.glVertex2f(width, height);
      gl.glVertex2f(0, height);
    gl.glEnd();
    gl.glFlush();
  }
  
  public void renderSprite(GL2 gl, int xp, int yp, Sprite sprite, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
    int renderY,
        renderX;
    gl.glBindTexture(GL2.GL_TEXTURE_2D, sprite.getTexture().getTextureObject());
    gl.glColor4f(1, 1, 1, 1);
    gl.glBegin(GL2.GL_QUADS);
      gl.glTexCoord2f(0, 0);
      gl.glVertex2f(xp, yp);
      
      gl.glTexCoord2f(1, 0);
      gl.glVertex2f(xp + sprite.getWidth(), yp);
      
      gl.glTexCoord2f(1, 1);
      gl.glVertex2f(xp + sprite.getWidth(), yp + sprite.getHeight());
      
      gl.glTexCoord2f(0, 1);
      gl.glVertex2f(xp, yp + sprite.getHeight());
    gl.glEnd();
    gl.glFlush();
    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
  }
  
  
  
  /**
   * Clears the pixel array.
   */
  public void clear() {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = 0;
      lightMap[i] = -1;
    }
  }
  
  public void renderColor(int color) {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
  }
  
//  public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed, boolean blended) {
//    if (fixed) {
//      xp -= xOffset;
//      yp -= yOffset;
//    }
//    int renderY,
//        renderX;
//    for (int y = 0; y < sprite.getHeight(); y++) {
//      renderY = yp + y;
//      for (int x = 0; x < sprite.getWidth(); x++) {
//        renderX = xp + x;
//        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
//        if (sprite.getPixels()[x + y * sprite.getWidth()] != 0) {
//          if (blended) {
//            if (lightMap[renderX + renderY * width] != -1) {
//              pixels[renderX + renderY * width] = blend(
//                sprite.getPixels()[x + y * sprite.getWidth()],
//                lightMapColor,
//                lightMap[renderX + renderY * width]
//              );
//            }
//            else {
//              pixels[renderX + renderY * width] = blend(
//                sprite.getPixels()[x + y * sprite.getWidth()],
//                lightMapColor,
//                lightMapAlpha
//              );
//            }
//          }
//          else pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
//        }
//      }
//    }
//  }

  public void renderSprite(int xp, int yp, Sprite sprite, int alpha, boolean fixed, boolean blended) {
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
        if (sprite.getPixels()[x + y * sprite.getWidth()] != 0 && blended) {
          pixels[renderX + renderY * width] = blend(
            pixels[renderX + renderY * width],
            sprite.getPixels()[x + y * sprite.getWidth()],
            alpha
          );
        }
        else pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
      }
    }
  }
  
  public void renderSpriteUpperLevel(int xp, int yp, Sprite sprite, int alpha, boolean fixed, boolean blended) {
    if (fixed) {
      xp -= xOffset * 1.2;
      yp -= yOffset * 1.2;
    }
    int renderY,
        renderX;
    for (int y = 0; y < sprite.getHeight(); y++) {
      renderY = yp + y;
      for (int x = 0; x < sprite.getWidth(); x++) {
        renderX = xp + x;
        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
        if (sprite.getPixels()[x + y * sprite.getWidth()] != 0 && blended) {
          pixels[renderX + renderY * width] = blend(
            pixels[renderX + renderY * width],
            sprite.getPixels()[x + y * sprite.getWidth()],
            alpha
          );
        }
        else pixels[renderX + renderY * width] = sprite.getPixels()[x + y * sprite.getWidth()];
      }
    }
  }
    
  public void renderCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed, boolean blended) {
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
          if (blended) {
            if (lightMap[renderX + renderY * width] != -1) {
              pixels[renderX + renderY * width] = blend(
                (color == 0) ? sprite.getPixels()[x + y * sprite.getWidth()] : color,
                lightMapColor,
                lightMap[renderX + renderY * width]
              );
            }
            else {
              pixels[renderX + renderY * width] = blend(
                (color == 0) ? sprite.getPixels()[x + y * sprite.getWidth()]: color,
                lightMapColor,
                lightMapAlpha
              );
            }
          }
          else pixels[renderX + renderY * width] = (color == 0) ? sprite.getPixels()[x + y * sprite.getWidth()] : color;
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
        if (lightMap[renderX + renderY * width] != -1) {
          pixels[renderX + renderY * width] = blend(
            sprite.getPixels()[x + y * sprite.getWidth()],
            lightMapColor,
            lightMap[renderX + renderY * width]
          );
        }
        else {
          pixels[renderX + renderY * width] = blend(
            sprite.getPixels()[x + y * sprite.getWidth()],
            lightMapColor,
            lightMapAlpha
          );
        }
      }
    }
  }

  public void setLightMap(int color, int alpha) {
    lightMapColor = color;
    lightMapAlpha = alpha;
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
  
  public void renderLightMapEntity(int x, int y, Sprite mySprite) {
    int xp = x - xOffset,
        yp = y - yOffset,
        renderX,
        renderY,
        currentAlpha,
        subtractiveAlpha;
    for (int spriteY = 0; spriteY < mySprite.getHeight(); spriteY++) {
      renderY = yp + spriteY;
      for (int spriteX = 0; spriteX < mySprite.getWidth(); spriteX++) {
        renderX = xp + spriteX;
        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
        // Subtract the alpha of the sprite by the current value of the lightMap alpha and
        // don't let alpha be less than 0.
        currentAlpha = lightMapAlpha;
        if ((mySprite.getPixels()[spriteX + spriteY * mySprite.getWidth()] >> 24) < 0) {
          subtractiveAlpha = (int)(((mySprite.getPixels()[spriteX + spriteY * mySprite.getWidth()] >> 24) + 256) / 25.5);
        }
        else {
          subtractiveAlpha = (int)((mySprite.getPixels()[spriteX + spriteY * mySprite.getWidth()] >> 24) / 25.5);
        }
        if (subtractiveAlpha > 0) {
          if (lightMap[renderX + renderY * width] != -1) currentAlpha = lightMap[renderX + renderY * width];
          currentAlpha -= subtractiveAlpha;
          if (currentAlpha < 0) currentAlpha = 0;
          lightMap[renderX + renderY * width] = currentAlpha;
        }
      }
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