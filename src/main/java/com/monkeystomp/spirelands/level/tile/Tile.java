package com.monkeystomp.spirelands.level.tile;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Tile {
  
  public static final int TILE_SIZE = 16;
  public static final Tile VOID_TILE = new Tile(Sprite.VOID_SPRITE, true, -1, -1);
  private static Texture texture;
  private int x, y;
  private float pixelUnits;
  
  private Sprite sprite;
  private boolean solid;
  
  public Tile(Sprite sprite, boolean solid, int x, int y) {
    this.sprite = sprite;
    this.solid = solid;
    this.x = x;
    this.y = y;
    pixelUnits = 1 / (float)SpriteSheet.smallTestSheet.getWidth();
  }
  
  public Sprite getSprite() {
    return sprite;
  }
  
  public float atlasX1() {
    return ((2 * pixelUnits) + (TILE_SIZE * pixelUnits)) * x + pixelUnits;
//    return pixelUnits + ((x * 18) * pixelUnits);
  }
  
  public float atlasX2() {
    return ((2 * pixelUnits) + (TILE_SIZE * pixelUnits)) * x + (pixelUnits * TILE_SIZE);
//    return (TILE_SIZE * pixelUnits) + (x * 18) * pixelUnits;
  }
  
  public float atlasY1() {
    return ((2 * pixelUnits) + (TILE_SIZE * pixelUnits)) * y + pixelUnits;
//    return pixelUnits + ((y * 18) * pixelUnits);
  }
  
  public float atlasY2() {
    return ((2 * pixelUnits) + (TILE_SIZE * pixelUnits)) * y + (pixelUnits * TILE_SIZE);
//    return (TILE_SIZE * pixelUnits) + (y * 18) * pixelUnits;
  }
  
  public boolean isSolid() {
    return solid;
  }
  
  private static void setTexture() {
    try {
      texture = AWTTextureIO.newTexture(GLProfile.getGL2GL3(), ImageIO.read(new File("./resources/textures/sheets/smallTestSheet_stretch.png")), false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static Texture getTexture() {
    if (texture == null) setTexture();
    return texture;
  }
  
  public void render(int x, int y, Screen screen, GL2 gl) {
//    screen.renderTile(gl, x << 4, y << 4, sprite);
  }
}