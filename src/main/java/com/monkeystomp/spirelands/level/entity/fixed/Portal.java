package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.level.LevelFactory;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.tile.Tile;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Portal extends Entity {
  
  private SpawnCoordinate coordinate;
  private String levelKey;
  
  public Portal(int x, int y, SpawnCoordinate coordinate, String levelKey) {
    this.x = x;
    this.y = y;
    this.coordinate = coordinate;
    this.levelKey = levelKey;
  }
  
  public boolean portalHere(int xp, int yp) {
    return (xp > x - 1 && xp < x + Tile.TILE_SIZE + 1 && yp > y - 1 && yp < y + Tile.TILE_SIZE + 1);
  }
  
  public void enterPortal() {
    level.getLevelChanger().change(LevelFactory.createLevel(levelKey, coordinate));
  }
}