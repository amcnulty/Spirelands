package com.monkeyStomp.spirelands.level.tile;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Tile {
  
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
    screen.renderTile(x << 6, y << 6, sprite);
  }
}
