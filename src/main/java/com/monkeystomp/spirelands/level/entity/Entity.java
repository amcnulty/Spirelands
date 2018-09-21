package com.monkeystomp.spirelands.level.entity;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.level.Level;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class Entity {
  
  protected int x,
                y;
  protected int[] bounds = new int[4];
  protected Level level;
  private boolean removed;
  
  public void remove() {
    removed = true;
  }
  
  public boolean isRemoved() {
    return removed;
  }
  
  public int getX() {
    return x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public void interact() {}
  
  public boolean entityHere(int xp, int yp) {
    return (xp > bounds[3] && xp < bounds[1] + 1 && yp > bounds[0] && yp < bounds[2]);
  }
  
  public void initLevel(Level level) {
    this.level = level;
  }
  
  public void update() {}
  
  public void render(Screen screen) {}
}