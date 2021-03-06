package com.monkeystomp.spirelands.level.entity.bounds;

/**
 * Bounds class is used to set bounds information for solid entites and portals.
 * @author Aaron Michael McNulty
 */
public class Bounds {
  
  private int top,
              right,
              bottom,
              left,
              x1, y1,
              x2, y2,
              x3, y3;
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
  /**
   * Creates rectangular bounds for this object.
   * @param top Top bounding y pixel coordinate.
   * @param right Right bounding x pixel coordinate.
   * @param bottom Bottom bounding y pixel coordinate.
   * @param left Left bounding x pixel coordinate.
   */
  public void setQuadBounds(int top, int right, int bottom, int left) {
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
    this.quadSet = true;
    this.triSet = false;
  }
  /**
   * Creates triangular bounds for this object.
   * @param x1 X coordinate for p1 of the triangle.
   * @param y1 Y coordinate for p1 of the triangle.
   * @param x2 X coordinate for p2 of the triangle.
   * @param y2 Y coordinate for p2 of the triangle.
   * @param x3 X coordinate for p3 of the triangle.
   * @param y3 Y coordinate for p3 of the triangle.
   */
  public void setTriBounds(int x1, int y1, int x2, int y2, int x3, int y3) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.x3 = x3;
    this.y3 = y3;
    this.triSet = true;
    this.quadSet = false;
  }
  /**
   * Checks if a given point is inside of the set triangle bounds.
   * @param x X coordinate of point to check.
   * @param y Y coordinate of point to check.
   * @return True if given point is inside triangle bounds, otherwise returns false.
   */
  public boolean insideBounds(int x, int y) {
    if (quadSet) return (y >= top && x <= right && y <= bottom && x >= left);
    else if (triSet) return insideTriangle(x1, y1, x2, y2, x3, y3, x, y);
    return false;
  }
  
  private float area(int x1, int y1, int x2, int y2, int x3, int y3) {
    return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2);
  }
  
  private boolean insideTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int x, int y) {
    float A = area(x1, y1, x2, y2, x3, y3);
    float A1 = area(x, y, x2, y2, x3, y3);
    float A2 = area(x1, y1, x, y, x3, y3);
    float A3 = area(x1, y1, x2, y2, x, y);
    return (A == A1 + A2 + A3);
  }

}
