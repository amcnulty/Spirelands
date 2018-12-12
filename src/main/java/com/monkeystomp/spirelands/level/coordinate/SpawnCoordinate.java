package com.monkeystomp.spirelands.level.coordinate;

/**
 * Spawn Coordinate class is used to hold coordinate information for where to place the player on levels.
 * @author Aaron Michael McNulty
 */
public class SpawnCoordinate {
   
  private int x,
              y,
              direction;
  /**
   * Creates a new SpawnCoordinate object with the given x, y, and directional information.
   * @param x The x pixel coordinate to set.
   * @param y The y pixel coordinate to set.
   * @param direction The direction the player will face at the coordinate.
   */
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
