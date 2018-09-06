package com.monkeystomp.spirelands.level.entity.mob;


import com.monkeystomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Mob extends Entity {

  protected boolean walking = false;
  protected int direction = 2;
  
  protected void move(int xDir, int yDir, int[] bounds) {
    if (xDir != 0 && yDir != 0) {
      move(xDir, 0, bounds);
      move(0, yDir, bounds);
      return;
    }
    
    if (xDir > 0)       direction = 1;
    else if (xDir < 0)  direction = 3;
    if (yDir > 0)       direction = 2;
    else if (yDir < 0)  direction = 0;
    
    if (!collision(xDir, yDir, bounds)) {
      x += xDir;
      y += yDir;
      walking = true;
    }
    else walking = false;
  }
  
  protected boolean collision(int xDir, int yDir, int[] bounds) {
    for (int c = 0; c < 4; c++) {
      int xt = ((x + xDir) + c % 2 * (bounds[1] + bounds[3]) - bounds[3]) >> 4;
      int nx = (x + xDir) + c % 2 * (bounds[1] + bounds[3]) - bounds[3];
      int yt = ((y + yDir) + c / 2 * (bounds[2] + bounds[0]) - bounds[0]) >> 4;
      int ny = (y + yDir) + c / 2 * (bounds[2] + bounds[0]) - bounds[0];
      if (level.getTile(xt, yt).isSolid()) return true;
    }
    return false;
  }
}