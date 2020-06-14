package com.monkeystomp.spirelands.level.tile;

import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Tile {
  
  public static final int TILE_SIZE = 16;
  public static final Tile VOID_TILE = new Tile(true, 7, 1);
  private static Texture texture;
  private static final File TILE_SHEET = new File("./resources/textures/sheets/high-res-sheet.png");
  private final int x, y;
  private final int tileRawSize = 64;
  private final float PIXEL_UNITS_X;
  private final float PIXEL_UNITS_Y;
  private final boolean SOLID;
  
  public Tile(boolean solid, int x, int y) {
    this.SOLID = solid;
    this.x = x;
    this.y = y;
    PIXEL_UNITS_X = 1 / (float)SpriteSheet.highResSheet.getWidth();
    PIXEL_UNITS_Y = 1 / (float)SpriteSheet.highResSheet.getHeight();
  }

  public float atlasX1() {
    return ((2 * PIXEL_UNITS_X) + (tileRawSize * PIXEL_UNITS_X)) * x + PIXEL_UNITS_X;
  }
  
  public float atlasX2() {
    return ((2 * PIXEL_UNITS_X) + (tileRawSize * PIXEL_UNITS_X)) * x + (PIXEL_UNITS_X * tileRawSize);
  }
  
  public float atlasY1() {
    return ((2 * PIXEL_UNITS_Y) + (tileRawSize * PIXEL_UNITS_Y)) * y + PIXEL_UNITS_Y;
  }
  
  public float atlasY2() {
    return ((2 * PIXEL_UNITS_Y) + (tileRawSize * PIXEL_UNITS_Y)) * y + (PIXEL_UNITS_Y * tileRawSize);
  }
  
  public boolean isSolid() {
    return SOLID;
  }
  
  private static void setTexture() {
    try {
      texture = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), ImageIO.read(TILE_SHEET), false);
    }
    catch (GLException | IOException e) {
      e.printStackTrace();
    }
  }
  
  public static Texture getTexture() {
    if (texture == null) setTexture();
    return texture;
  }
}
