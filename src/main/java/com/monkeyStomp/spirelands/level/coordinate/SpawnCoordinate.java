package com.monkeyStomp.spirelands.level.coordinate;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnCoordinate {
   
  private int x,
              y,
              direction;
  
  public SpawnCoordinate(int x, int y, int direction) {
    this.x = x;
    this.y = y;
    this.direction = direction;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getDirection() {
    return direction;
  }
}
