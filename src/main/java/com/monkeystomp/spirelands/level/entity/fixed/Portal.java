package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.level.util.LevelFactory;
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
    setBounds();
  }
  
  private void setBounds() {
    bounds[0] = y - 1;
    bounds[1] = x + Tile.TILE_SIZE;
    bounds[2] = y + Tile.TILE_SIZE;
    bounds[3] = x - 1;
  }
  
  public void enterPortal() {
    level.getLevelChanger().change(LevelFactory.createLevel(levelKey, coordinate));
  }
}