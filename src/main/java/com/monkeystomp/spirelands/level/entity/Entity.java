package com.monkeystomp.spirelands.level.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.level.Level;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class Entity {
  
  protected int x,
                y,
                overlapY;
  protected ArrayList<Bounds> bounds = new ArrayList<>();
  protected Level level;
  private boolean removed = false;
  
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

  public int getOverlapY() {
    return y;
  }

  public void interact() {}
  
  public boolean entityHere(int xp, int yp) {
    for (Bounds bound: bounds) {
      if (bound.insideBounds(xp, yp)) return true;
    }
    return false;
  }
  
  public void initLevel(Level level) {
    this.level = level;
  }
  
  public void update() {}
  
  public void render(Screen screen, GL2 gl) {}
}
