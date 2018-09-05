package com.monkeystomp.spirelands.level.tile;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Tile {
  
  public static final int TILE_SIZE = 16;
  public static final Tile VOID_TILE = new Tile(Sprite.VOID_SPRITE, true);
  
  private Sprite sprite;
  private boolean solid;
  
  public Tile(Sprite sprite, boolean solid) {
    this.sprite = sprite;
    this.solid = solid;
  }
  
  public Sprite getSprite() {
    return sprite;
  }
  
  public boolean isSolid() {
    return solid;
  }
  
  public void render(int x, int y, Screen screen) {
    screen.renderTile(x << 4, y << 4, sprite);
  }
}
