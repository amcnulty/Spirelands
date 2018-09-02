package com.monkeyStomp.spirelands.level.entity.mob;


import com.monkeyStomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Mob extends Entity {

  private boolean walking = false;
  protected int direction = 0;
  
  protected void move(int xDir, int yDir) {
    if (xDir != 0 && yDir != 0) {
      move(xDir, 0);
      move(0, yDir);
      return;
    }
    
    if (xDir > 0)       direction = 1;
    else if (xDir < 0)  direction = 3;
    if (yDir > 0)       direction = 2;
    else if (yDir < 0)  direction = 0;
    
    if (!collision(xDir, yDir)) {
      x += xDir;
      y += yDir;
      walking = true;
    }
    else walking = false;
  }
  
  protected boolean collision(int xDir, int yDir) {
    for (int c = 0; c < 4; c++) {
      int xt = ((x + xDir) + c % 2 * 48 - 24) >> 6;
      int nx = (x + xDir) + c % 2 * 48 - 24;
      int yt = ((y + yDir) + c / 2 * 66 - 4) >> 6;
      int ny = (y + yDir) + c / 2 * 66 - 4;
      if (level.getTile(xt, yt).isSolid()) return true;
    }
    return false;
  }
}