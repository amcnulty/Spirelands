package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;

/**
 * Portal class is used to create a portal to another location. Portals can be use to move from one level to the next or from one scene to another within the same level.
 * @author Aaron Michael McNulty
 */
public class Portal extends Entity {
  
  private SpawnCoordinate coordinate;
  private String  levelKey,
                  sceneKey;
  private Bounds portalBounds;
  private boolean portalExitsLevel;
  /**
   * Creates a portal object of a certain size based on bounds object that links to another level at the specified spawn coordinate.
   * @param bounds The bounds of the portal.
   * @param coordinate The spawn coordinate where the player will appear in the new level.
   * @param levelKey The level key for the level factory to fetch the new level.
   */
  public Portal(Bounds bounds, SpawnCoordinate coordinate, String levelKey) {
    this.portalBounds = bounds;
    this.coordinate = coordinate;
    this.levelKey = levelKey;
    this.portalExitsLevel = true;
    setBounds();
  }
  /**
   * Creates a portal object of a certain size based on bounds object that links to another scene at the specified spawn coordinate.
   * @param bounds The bounds of the portal.
   * @param sceneKey The scene key to tell the level which scene to switch to.
   * @param coordinate The spawn coordinate where the player will appear in the new level.
   */
  public Portal(Bounds bounds, String sceneKey, SpawnCoordinate coordinate) {
    this.portalBounds = bounds;
    this.coordinate = coordinate;
    this.sceneKey = sceneKey;
    this.portalExitsLevel = false;
    setBounds();
  }
  
  private void setBounds() {
    bounds.add(portalBounds);
  }
  /**
   * Used to determine if this portal links to another level. If false the portal links to another scene.
   * @return True if portal is linked to another level. False if portal links to another scene on the same level.
   */
  public boolean portalExitsLevel() {
    return portalExitsLevel;
  }

  public String getSceneKey() {
    return sceneKey;
  }
  
  public SpawnCoordinate getSpawnCoordinate() {
    return coordinate;
  }
  /**
   * Enter the portal to change levels.
   */
  public void enterPortal() {
    level.getLevelChanger().change(LevelFactory.createLevel(levelKey, coordinate));
  }
}
