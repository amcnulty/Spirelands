package com.monkeystomp.spirelands.level.entity.fixed;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;

/**
 * A example class to show how to build a structure on the overworld map.
 * @author Aaron Michael McNulty
 */
public class House extends Entity {
  
  private Sprite  houseTopSprite = new Sprite("./resources/objects/house_top.png"),
                  houseBottomSprite = new Sprite("./resources/objects/house_bottom.png"),
                  houseSprite = new Sprite("./resources/objects/house.png"),
                  doorOpenSprite = new Sprite("./resources/objects/house_door_open.png"); // 74 209
  private float opacity = 1f;
  private int[] touchBounds = new int[4],
                triangleBounds = new int[6];
  private int top,
              right,
              bottom,
              left;

  public House(int x, int y) {
    this.x = x;
    this.y = y;
    setBounds();
  }
  
  private void setBounds() {
    top = y - (houseTopSprite.getHeight() + houseBottomSprite.getHeight()) / 2;
    right = x + houseTopSprite.getWidth() / 2;
    bottom = y + (houseTopSprite.getHeight() + houseBottomSprite.getHeight()) / 2;
    left = x - houseTopSprite.getWidth() / 2;
    
    Bounds  quad1 = new Bounds(),
            quad2 = new Bounds(),
            quad3 = new Bounds();
    quad1.setQuadBounds(
      y - 5,
      right - 16,
      bottom - 18,
      left + 117
    );
    bounds.add(quad1);
    quad2.setQuadBounds(
      y - 5,
      left + 74,
      bottom - 18,
      left
    );
    bounds.add(quad2);
    quad3.setQuadBounds(
      y - 5,
      x + houseTopSprite.getWidth() / 2 - 16,
      top + 208,
      x - houseTopSprite.getWidth() / 2
    );
    bounds.add(quad3);
    // rectagular area below triangle
    touchBounds[0] = top + 95;
    touchBounds[1] = x + houseTopSprite.getWidth() / 2 - 16;
    touchBounds[2] = top + 200;
    touchBounds[3] = x - houseTopSprite.getWidth() / 2;
    // triangle area above rectangle
    triangleBounds[0] = left + 5;
    triangleBounds[1] = top + 95;
    triangleBounds[2] = left + 95;
    triangleBounds[3] = top + 5;
    triangleBounds[4] = left + 185;
    triangleBounds[5] = top + 95;
  }
  
  public void setOpacity(float opacity) {
    this.opacity = opacity;
  }
  
  private boolean playerWithinTouchBounds() {
    int playerX = level.getPlayer().getX(),
        playerY = level.getPlayer().getY();
    return ((playerY > touchBounds[0] &&
        playerX < touchBounds[1] &&
        playerY < touchBounds[2] &&
        playerX > touchBounds[3]) ||
        insideTriangle(
          triangleBounds[0],
          triangleBounds[1],
          triangleBounds[2],
          triangleBounds[3],
          triangleBounds[4],
          triangleBounds[5],
          playerX,
          playerY
        )
      );
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
  
  private boolean playerAtDoor() {
    int playerX = level.getPlayer().getX(),
        playerY = level.getPlayer().getY();
    return (
      playerY > bottom - 70 &&
      playerX > left + 73 &&
      playerY < bottom  - 15 &&
      playerX < left + 117
    );
  }
  
  @Override
  public void update() {
    if (playerWithinTouchBounds()) {
      opacity = .6f;
    }
    else opacity = 1;
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - houseSprite.getWidth() / 2, y - houseSprite.getHeight() / 2, houseSprite, opacity, true);
    if (playerAtDoor()) {
      screen.renderSprite(gl, left + 74, top + 209, doorOpenSprite, true);
    }
  }
}
