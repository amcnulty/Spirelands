package com.monkeystomp.spirelands.level.entity.mob;


import com.monkeystomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Mob extends Entity {

  protected boolean walking = false;
  protected int direction = 2,
                stepIndex = 0;
  protected int[] moveBounds = new int[4];
  
  protected void move(int xDir, int yDir, int[] bounds) {
    if (xDir != 0 && yDir != 0) {
      move(xDir, 0, bounds);
      move(0, yDir, bounds);
      return;
    }
    else if (xDir == 0 && yDir == 0) return;
    
    if (xDir > 0)       direction = 1;
    else if (xDir < 0)  direction = 3;
    if (yDir > 0)       direction = 2;
    else if (yDir < 0)  direction = 0;
    
    if (!collision(xDir, yDir, bounds)) {
      x += xDir;
      y += yDir;
      walking = true;
    }
  }
  
  protected boolean collision(int xDir, int yDir, int[] bounds) {
    return !(!checkTiles(xDir, yDir, bounds) && !checkEntities(xDir, yDir, bounds));
  }
  
  private boolean checkTiles(int xDir, int yDir, int[] bounds) {
    // Check edges
    if (yDir < 0 && level.getTile(x >> 4, ((y + yDir) - bounds[0]) >> 4).isSolid()) return true;
    if (xDir > 0 && level.getTile(((x + xDir) + bounds[1]) >> 4, y >> 4).isSolid()) return true;
    if (yDir > 0 && level.getTile(x >> 4, ((y + yDir) + bounds[2]) >>4).isSolid()) return true;
    if (xDir < 0 && level.getTile(((x + xDir) - bounds[3]) >> 4, y >> 4).isSolid()) return true;
    // Check corner pins
    for (int c = 0; c < 4; c++) {
      int xt = ((x + xDir) + c % 2 * (bounds[1] + bounds[3]) - bounds[3]) >> 4;
      int nx = (x + xDir) + c % 2 * (bounds[1] + bounds[3]) - bounds[3];
      int yt = ((y + yDir) + c / 2 * (bounds[2] + bounds[0]) - bounds[0]) >> 4;
      int ny = (y + yDir) + c / 2 * (bounds[2] + bounds[0]) - bounds[0];
      if (level.getTile(xt, yt).isSolid()) return true;
    }
    return false;
  }
  
  private boolean checkEntities(int xDir, int yDir, int[] bounds) {
    for (int i = 0; i < level.getSolidEntities().size(); i++) {
      // Make sure entity is not running into itself
      if (!level.getSolidEntities().get(i).equals(this)) {
        if (yDir < 0 && level.getSolidEntities().get(i).entityHere(x, (y + yDir) - bounds[0])) return true;
        if (xDir > 0 && level.getSolidEntities().get(i).entityHere((x + xDir) + bounds[1], y)) return true;
        if (yDir > 0 && level.getSolidEntities().get(i).entityHere(x, (y + yDir) + bounds[2])) return true;
        if (xDir < 0 && level.getSolidEntities().get(i).entityHere((x + xDir) - bounds[3], y)) return true;
        // Check corner pins
        for (int c = 0; c < 4; c++) {
          if (level.getSolidEntities().get(i).entityHere((x + xDir) + c % 2 * (bounds[1] + bounds[3]) - bounds[3], (y + yDir) + c / 2 * (bounds[2] + bounds[0]) - bounds[0])) return true;
        }
      }
    }
    return false;
  }

  public int getDirection() {
    return direction;
  }
}