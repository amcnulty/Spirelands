package com.monkeystomp.spirelands.level.wall;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.SolidEntity;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Wall extends Entity {
  
  private ArrayList<SolidEntity> wall = new ArrayList<>();
  private int startingX,
              startingY,
              length;
  private boolean interior,
                  leftCorner,
                  rightCorner,
                  hasWallFront;
  
  public void createHorizontalWall(WallConfig config) {
    this.startingX = config.getStartingX();
    this.startingY = config.getStartingY();
    this.length = config.getLength();
    this.interior = config.isInterior();
    this.leftCorner = config.isLeftCorner();
    this.rightCorner = config.isRightCorner();
    if (leftCorner) {
      SolidEntity wallCorner = new SolidEntity(startingX - Sprite.WALL_DRYWALL_CORNER.getWidth(), startingY);
      wallCorner.setSprite(Sprite.WALLTOP_DRYWALL_CORNER);
      wall.add(wallCorner);
    }
    for (int i = 0; i < length; i++) {
      SolidEntity wallSection = new SolidEntity(startingX + (Sprite.WALL_DRYWALL.getWidth() * i), startingY);
      int num = i;
      if (IntStream.of(config.getWindows()).anyMatch((value) -> value == num) && i != 0) {
        wallSection.setSprite(interior ? Sprite.WALL_DRYWALL_WINDOW : Sprite.WALL_STONE_WINDOW);
      }
      else wallSection.setSprite((interior) ? Sprite.WALL_DRYWALL : Sprite.WALL_STONE);
      wall.add(wallSection);
    }
    if (rightCorner) {
      SolidEntity wallCorner = new SolidEntity(startingX + Sprite.WALL_DRYWALL.getWidth() * length, startingY);
      wallCorner.setSprite(Sprite.WALLTOP_DRYWALL_CORNER);
      wall.add(wallCorner);
    }
    setHorizontalBounds();
    setHorizontalOverlap();
  }
  
  private void setHorizontalBounds() {
    Bounds quadBounds = new Bounds();
    quadBounds.setQuadBounds(
      startingY + 25,
      startingX + Sprite.WALL_DRYWALL.getWidth() * length,
      startingY + Sprite.WALL_DRYWALL.getHeight() - 4,
      startingX
    );
    bounds.add(quadBounds);
  }

  private void setHorizontalOverlap() {
    this.overlapY = startingY + 20;
  }
  
  public void createVerticalWall(WallConfig config) {
    this.startingX = config.getStartingX();
    this.startingY = config.getStartingY();
    this.interior = config.isInterior();
    this.length = config.getLength();
    this.hasWallFront = config.hasWallFront();
    for (int i = 0; i < length; i++) {
      SolidEntity wallSection = new SolidEntity(startingX, startingY + Sprite.WALLTOP_DRYWALL_VERT.getHeight() * i);
      wallSection.setSprite(Sprite.WALLTOP_DRYWALL_VERT);
      wall.add(wallSection);
    }
    if (config.hasWallFront()) {
      SolidEntity wallSection = new SolidEntity(startingX, startingY + Sprite.WALLTOP_DRYWALL_VERT.getHeight() * length);
      wallSection.setSprite(interior ? Sprite.WALL_DRYWALL_CORNER : Sprite.WALL_STONE_CORNER);
      wall.add(wallSection);
    }
    setVerticalBounds();
    setVerticalOverlap();
  }
  
  private void setVerticalBounds() {
    Bounds quad = new Bounds();
    quad.setQuadBounds(
      startingY + 17,
      startingX + Sprite.WALLTOP_DRYWALL_VERT.getWidth(),
      startingY + Sprite.WALLTOP_DRYWALL_VERT.getHeight() * length + (hasWallFront ? Sprite.WALL_DRYWALL_CORNER.getHeight() - 4 : 0),
      startingX
    );
    bounds.add(quad);
  }
  
  private void setVerticalOverlap() {
    this.overlapY = startingY + Sprite.WALLTOP_DRYWALL_VERT.getHeight() * length + 11;
  }
  
  @Override
  public int getOverlapY() {
    return overlapY;
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    for (int i = 0; i < wall.size(); i++) {
      wall.get(i).render(screen, gl);
    }
  }
}
