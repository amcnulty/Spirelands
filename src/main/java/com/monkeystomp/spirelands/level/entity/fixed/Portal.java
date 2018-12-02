package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Portal extends Entity {
  
  private SpawnCoordinate coordinate;
  private String levelKey;
  private Bounds portalBounds;
  
  public Portal(Bounds bounds, SpawnCoordinate coordinate, String levelKey) {
    this.portalBounds = bounds;
    this.coordinate = coordinate;
    this.levelKey = levelKey;
    setBounds();
  }
  
  private void setBounds() {
    bounds.add(portalBounds);
  }
  
  public void enterPortal() {
    level.getLevelChanger().change(LevelFactory.createLevel(levelKey, coordinate));
  }
}
