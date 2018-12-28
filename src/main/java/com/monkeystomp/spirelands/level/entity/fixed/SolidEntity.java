package com.monkeystomp.spirelands.level.entity.fixed;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;

/**
 * Solid entities are objects on the map that have some sort of location and bounding properties and a sprite image.
 * @author Aaron Michael McNulty
 */
public class SolidEntity extends Entity {
  
  private Sprite sprite;
  /**
   * Creates a SolidEntity object at the given coordinate.
   * @param x X coordinate of entity.
   * @param y Y coordinate of entity.
   */
  public SolidEntity(int x, int y) {
    this.x = x;
    this.y = y;
  }
  /**
   * Adds a bounds object to this entity.
   * @param newBounds The bounds object to set to this entity.
   */
  public void addBounds(Bounds newBounds) {
    bounds.add(newBounds);
  }
  
  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, sprite, true);
  }
  
}
