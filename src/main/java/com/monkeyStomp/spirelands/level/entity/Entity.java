package com.monkeyStomp.spirelands.level.entity;

import com.monkeyStomp.spirelands.level.Level;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class Entity {
  
  protected int x,
                y;
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
  
  public void initLevel(Level level) {
    this.level = level;
  }
  
  public void update() {}
  
  public void render() {}
}
