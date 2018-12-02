package com.monkeystomp.spirelands.level.entity.bounds;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Bounds {
  
  private int top,
              right,
              bottom,
              left;
  private boolean quadSet = false,
                  triSet = false;

  public int getTop() {
    return top;
  }

  public int getRight() {
    return right;
  }

  public int getBottom() {
    return bottom;
  }

  public int getLeft() {
    return left;
  }
  
  public void setQuadBounds(int top, int right, int bottom, int left) {
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
    this.quadSet = true;
  }
  
  public boolean insideBounds(int x, int y) {
    if (quadSet) return (y > top && x < right && y < bottom && x > left);
    else if (triSet) return false;
    return false;
  }

}
