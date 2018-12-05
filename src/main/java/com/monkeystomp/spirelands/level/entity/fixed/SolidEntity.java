package com.monkeystomp.spirelands.level.entity.fixed;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SolidEntity extends Entity {
  
  private Sprite sprite;

  public SolidEntity(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
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
