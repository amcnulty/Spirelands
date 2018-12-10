package com.monkeystomp.spirelands.level.wall;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WallConfig {
  
  private int startingX;
  private int startingY;
  private int length;
  private boolean interior;
  private boolean leftCorner = true;
  private boolean rightCorner = true;
  private boolean hasWallFront = true;
  private int[] windowIndex;

  public int getStartingX() {
    return startingX;
  }

  public void setStartingX(int startingX) {
    this.startingX = startingX;
  }

  public int getStartingY() {
    return startingY;
  }

  public void setStartingY(int startingY) {
    this.startingY = startingY;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
    this.windowIndex = new int[length];
  }

  public boolean isInterior() {
    return interior;
  }

  public void setInterior(boolean interior) {
    this.interior = interior;
  }

  public boolean isLeftCorner() {
    return leftCorner;
  }

  public void setLeftCorner(boolean leftCorner) {
    this.leftCorner = leftCorner;
  }

  public boolean isRightCorner() {
    return rightCorner;
  }

  public void setRightCorner(boolean rightCorner) {
    this.rightCorner = rightCorner;
  }

  public boolean hasWallFront() {
    return hasWallFront;
  }

  public void setHasWallFront(boolean hasWallFront) {
    this.hasWallFront = hasWallFront;
  }
  
  public void setWindows(int[] windowIndex) {
    this.windowIndex = windowIndex;
  }
  
  public int[] getWindows() {
    return windowIndex;
  }

}
