package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.level.tile.Tile;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The Screen class is used to render to the screen. The public methods of this class are used to render sprites, lightmaps, and lightmap entities.
 * @author Aaron Michael McNulty
 */
public class Screen {
  
  private static int  width,
                      height,
                      xOffset,
                      yOffset;
  private static float  scaleX,
                        scaleY;
  private int[] pixels,
                lightMap;
  private boolean[] lightMapPixelChecked;
  private BufferedImage lightMapImage;
  private Texture lightMapTex;
  private ArrayList<FontInfo> fontInfo = new ArrayList<>();
  private int lightMapColor = GameColors.LIGHTMAP_COLOR,
              count = 2;
  private final int R = (lightMapColor & 0xFF0000) >> 16,
                    G = (lightMapColor & 0xFF00) >> 8,
                    B = lightMapColor & 0xFF;
  private final float LIGHTMAP_R_VALUE = 0.07058823529411764705882352941176f,
                      LIGHTMAP_G_VALUE = 0.07058823529411764705882352941176f,
                      LIGHTMAP_B_VALUE = 0.07058823529411764705882352941176f;
  /**
   * Creates a Screen object of the given dimensions and scaling factors.
   * @param width The width of the drawing area in pixels.
   * @param height The height of the drawing are in pixels.
   * @param scaleX The horizontal scaling factor to size the final screen.
   * @param scaleY The vertical scaling factor to size the final screen.
   */
  public Screen(int width, int height, int scaleX, int scaleY) {
    this.width = width;
    this.height = height;
    this.scaleX = scaleX;
    this.scaleY = scaleY;
    this.pixels = new int[width * height];
    this.lightMap = new int[width * height];
    this.lightMapPixelChecked = new boolean[width * height];
    lightMapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    clearLightMap();
  }
  /**
   * Renders the title screen background.
   * @param gl Instance of the GL2 class.
   */
  public void renderTitleScreenBackground(GL2 gl) {
    gl.glColor4f(1, 0, 0, 1);
    gl.glBegin(GL2.GL_QUADS);
      gl.glVertex2f(0, 0);
      gl.glVertex2f(width, 0);
      gl.glVertex2f(width, height);
      gl.glVertex2f(0, height);
    gl.glEnd();
//    gl.glFlush();
  }
  /**
   * Renders a sprite to the screen at the given coordinates. If fixed is set to true the sprite will be rendered at a specific location on the map otherwise the coordinates will be in relation to the screen.
   * @param gl Instance of the GL2 class.
   * @param xp X position to start rendering of the sprite.
   * @param yp Y position to start rendering of the sprite.
   * @param sprite The sprite to be rendered.
   * @param fixed If true coordinates will be with respect to map. If false coordinates will be in respect to screen.
   */
  public void renderSprite(GL2 gl, int xp, int yp, Sprite sprite, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
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
//    gl.glFlush();
    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
  }
  /**
   * Renders a sprite with color blending effects. This is mostly used for lights that need to blend colors with their surroundings.
   * @param gl Instance of the GL2 class.
   * @param xp X position to start rendering of the sprite.
   * @param yp Y position to start rendering of the sprite.
   * @param sprite The sprite to be rendered.
   * @param fixed If true coordinates will be with respect to map. If false coordinates will be in respect to screen.
   */
  public void renderBlendedSprite(GL2 gl, int xp, int yp, Sprite sprite, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
    gl.glBlendFunc(GL2.GL_DST_COLOR, GL2.GL_ONE);
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
    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
  }
  /**
   * Renders a sprite to the screen at the given coordinates with a set alpha transparency. If fixed is set to true the sprite will be rendered at a specific location on the map otherwise the coordinates will be in relation to the screen.
   * @param gl Instance of the GL2 class.
   * @param xp X position to start rendering of the sprite.
   * @param yp Y position to start rendering of the sprite.
   * @param sprite The sprite to be rendered.
   * @param alpha The transparency to be applied over the whole sprite.
   * @param fixed If true coordinates will be with respect to map. If false coordinates will be in respect to screen.
   */
  public void renderSprite(GL2 gl, int xp, int yp, Sprite sprite, float alpha, boolean fixed) {
    if (fixed) {
      xp -= xOffset;
      yp -= yOffset;
    }
    gl.glBindTexture(GL2.GL_TEXTURE_2D, sprite.getTexture().getTextureObject());
    gl.glColor4f(1, 1, 1, alpha);
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
//    gl.glFlush();
    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
  }
  /**
   * Binds the tile texture. This is best called just before rendering all of the tiles so each tile render doesn't require rebinding with another resource.
   * @param gl Instance of the GL2 class.
   */
  public void bindTileTex(GL2 gl) {
    gl.glBindTexture(GL2.GL_TEXTURE_2D, Tile.getTexture().getTextureObject());
  }
  /**
   * Renders a set of tiles to the screen based on the tile data and coordinate location.
   * @param tileData Array list of tiles to be rendered.
   * @param xFloat Array list of x coordinate locations for each respective tile in the tileData parameter.
   * @param yFloat Array list of y coordinate locations for each respective tile in the tileData parameter.
   * @param gl Instance of the GL2 class.
   */
  public void renderTile(ArrayList<Tile> tileData, ArrayList<Float> xFloat, ArrayList<Float> yFloat, GL2 gl) {
    gl.glColor4f(1, 1, 1, 1);
    gl.glBegin(GL2.GL_QUADS);
      for (int i = 0; i < tileData.size(); i++) {
        gl.glTexCoord2f(tileData.get(i).atlasX1(), tileData.get(i).atlasY1());
        gl.glVertex2f(xFloat.get(i) - xOffset, yFloat.get(i) - yOffset);
        
        gl.glTexCoord2f(tileData.get(i).atlasX2(), tileData.get(i).atlasY1());
        gl.glVertex2f(xFloat.get(i) + Tile.TILE_SIZE - xOffset, yFloat.get(i) - yOffset);
        
        gl.glTexCoord2f(tileData.get(i).atlasX2(), tileData.get(i).atlasY2());
        gl.glVertex2f(xFloat.get(i) + Tile.TILE_SIZE - xOffset, yFloat.get(i) + Tile.TILE_SIZE - yOffset);
        
        gl.glTexCoord2f(tileData.get(i).atlasX1(), tileData.get(i).atlasY2());
        gl.glVertex2f(xFloat.get(i) - xOffset, yFloat.get(i) + Tile.TILE_SIZE - yOffset);
      }
    gl.glEnd();
    gl.glFlush();
  }
  /**
   * Renders a sprite to a location above the ground. This creates an effect of the tile moving across the screen faster than other tiles at ground level.
   * @param gl Instance of the GL2 class.
   * @param xp X position to start rendering of the sprite.
   * @param yp Y position to start rendering of the sprite.
   * @param sprite The sprite to be rendered.
   * @param alpha The transparency to be applied over the whole sprite.
   * @param fixed If true coordinates will be with respect to map. If false coordinates will be in respect to screen.
   */
  public void renderSpriteUpperLevel(GL2 gl, int xp, int yp, Sprite sprite, float alpha, boolean fixed) {
    if (fixed) {
      xp -= xOffset * 1.2;
      yp -= yOffset * 1.2;
    }
    gl.glBindTexture(GL2.GL_TEXTURE_2D, sprite.getTexture().getTextureObject());
    gl.glColor4f(1, 1, 1, alpha);
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
//    gl.glFlush();
    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
  }
  /**
   * Renders a sprite as a light map entity to the screen. This is used when using a subtractive lightmap.
   * @param gl Instance of the GL2 class.
   * @param x X position to start rendering of the sprite.
   * @param y Y position to start rendering of the sprite.
   * @param sprite The sprite to be rendered.
   */
  public void renderLightMapEntity(GL2 gl, int x, int y, Sprite sprite) {
    x -= xOffset;
    y -= yOffset;
    int renderX,
        renderY;
    for (int yp = 0; yp < sprite.getHeight(); yp++) {
      renderY = y + yp;
      for (int xp = 0; xp < sprite.getWidth(); xp++) {
        renderX = x + xp;
        if (renderX < 0 || renderX > width -1 || renderY < 0 || renderY > height -1) continue;
        if (lightMapPixelChecked[renderX + renderY * width]) {
          lightMap[renderX + renderY * width] = blendAlphas(lightMap[renderX + renderY * width], sprite.getPixels()[xp + yp * sprite.getWidth()]);
        }
        else {
          lightMap[renderX + renderY * width] = sprite.getPixels()[xp + yp * sprite.getWidth()];
          lightMapPixelChecked[renderX + renderY * width] = true;
        }
      }
    }
  }
  /**
   * Renders a subtractive light map to the screen. This should be used sparingly as it has performance implications.
   * @param gl Instance of the GL2 class.
   * @param alpha The transparency to be applied over the whole light map.
   */
  public void renderLightMap(GL2 gl, float alpha) {
    if (count % 2 == 0) {
      lightMapImage.setRGB(0, 0, width, height, lightMap, 0, width);
      lightMapTex = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), lightMapImage, false);
      count = 0;
    }
    count++;
    gl.glBindTexture(GL2.GL_TEXTURE_2D, lightMapTex.getTextureObject());
    gl.glColor4f(1, 1, 1, alpha);
    gl.glBegin(GL2.GL_QUADS);
      gl.glTexCoord2f(0, 0);
      gl.glVertex2f(0, 0);
      
      gl.glTexCoord2f(1, 0);
      gl.glVertex2f(width, 0);
      
      gl.glTexCoord2f(1, 1);
      gl.glVertex2f(width, height);
      
      gl.glTexCoord2f(0, 1);
      gl.glVertex2f(0, height);
    gl.glEnd();
//    gl.glFlush();
    clearLightMap();
  }
  /**
   * Renders a blended light map to the screen of the preset light map color.
   * @param gl Instance of the GL2 class.
   * @param alpha The transparency to be applied over the whole light map.
   */
  public void renderBlendedLightMap(GL2 gl, float alpha) {
    gl.glColor4f(LIGHTMAP_R_VALUE, LIGHTMAP_G_VALUE, LIGHTMAP_B_VALUE, alpha);
    gl.glBegin(GL2.GL_QUADS);
      gl.glVertex2f(0, 0);
      gl.glVertex2f(width, 0);
      gl.glVertex2f(width, height);
      gl.glVertex2f(0, height);
    gl.glEnd();
  }
  /**
   * Clears the subtractive light map.
   */
  private void clearLightMap() {
    for (int i = 0; i < lightMap.length; i++) {
      lightMap[i] = lightMapColor;
      lightMapPixelChecked[i] = false;
    }
  }
  
  private int blendAlphas(int background, int newColor) {
    int newAlpha, backgroundAlpha;
    if ((newColor & 0xFF000000 ) >> 24 < 0) {
      newAlpha = ((newColor & 0xFF000000 ) >> 24) + 256;
    }
    else {
      newAlpha = (newColor & 0xFF000000 ) >> 24;
    }
    if ((background & 0xFF000000 ) >> 24 < 0) {
      backgroundAlpha = ((background & 0xFF000000 ) >> 24) + 256;
    }
    else {
      backgroundAlpha = (background & 0xFF000000 ) >> 24;
    }
    backgroundAlpha -= 255 - newAlpha;
    if (backgroundAlpha < 0) backgroundAlpha = 0;
    return (backgroundAlpha << 24) | (R << 16) | (G << 8) | B;
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
  /**
   * Adds font info to the array of font info to be rendered.
   * @param info FontInfo object to be added to the que to be rendered.
   * @deprecated This feature should not be used as there is a new method for rendering fonts called renderFonts().
   * @see #renderFonts(FontInfo info)
   */
  public void addText(FontInfo info) {
    fontInfo.add(info);
  }
  /**
   * Renders fonts to the screen based on their FontInfo configuration object.
   * @param info FontInfo configuration object that holds information regarding the text to be rendered.
   */
  public void renderFonts(FontInfo info) {
    TextRenderer textRenderer = info.getTextRenderer();
    Rectangle2D bounds = textRenderer.getBounds("M");
    int textHeight = (int)(bounds.getHeight()),
        vertAdjustment = (int)(textHeight / 2);
    textRenderer.setColor(info.getColor());
    textRenderer.beginRendering((int)(width * scaleX), (int)(height * scaleY));
    textRenderer.draw(info.getText(), (int)(info.getX() * scaleX), (int)(height * scaleY - (info.getY() * scaleY + vertAdjustment)));
    textRenderer.endRendering();
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
  
  public static float getScaleX() {
    return scaleX;
  }
  
  public static void setScaleX(float scaleX) {
    Screen.scaleX = scaleX;
  }
  
  public static float getScaleY() {
    return scaleY;
  }
  
  public static void setScaleY(float scaleY) {
    Screen.scaleY = scaleY;
  }
  
  public static int getWidth() {
    return width;
  }
  
  public static int getHeight() {
    return height;
  }
  
  public int[] getPixels() {
    return pixels;
  }
}
