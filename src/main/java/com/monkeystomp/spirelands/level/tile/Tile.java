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
  public static final Tile VOID_TILE = new Tile(true, 0, 3);
  private static Texture texture;
  private static final File TILE_SHEET = new File("./resources/textures/sheets/smallTestSheet_stretch.png");
  private final int x, y;
  private final float PIXEL_UNITS;
  private final boolean SOLID;
  
  public Tile(boolean solid, int x, int y) {
    this.SOLID = solid;
    this.x = x;
    this.y = y;
    PIXEL_UNITS = 1 / (float)SpriteSheet.smallTestSheet.getWidth();
  }

  public float atlasX1() {
    return ((2 * PIXEL_UNITS) + (TILE_SIZE * PIXEL_UNITS)) * x + PIXEL_UNITS;
  }
  
  public float atlasX2() {
    return ((2 * PIXEL_UNITS) + (TILE_SIZE * PIXEL_UNITS)) * x + (PIXEL_UNITS * TILE_SIZE);
  }
  
  public float atlasY1() {
    return ((2 * PIXEL_UNITS) + (TILE_SIZE * PIXEL_UNITS)) * y + PIXEL_UNITS;
  }
  
  public float atlasY2() {
    return ((2 * PIXEL_UNITS) + (TILE_SIZE * PIXEL_UNITS)) * y + (PIXEL_UNITS * TILE_SIZE);
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
