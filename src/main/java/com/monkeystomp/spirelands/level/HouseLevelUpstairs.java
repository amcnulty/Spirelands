package com.monkeystomp.spirelands.level;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.lightmap.CustomLightMap;
import com.monkeystomp.spirelands.level.lightmap.LightMapType;

/**
 *
 * @author Aaron Michael McNulty
 */
public class HouseLevelUpstairs extends Level {
  
  /**
   * Id used for save and load game.
   */
  public static final String LEVEL_ID = "HOUSE_LEVEL_UPSTAIRS";
  private final String LEVEL_NAME = "Villager's Home";
  private final String BITMAP_PATH = "./resources/textures/worlds/houseLevel.png";
  public static SpawnCoordinate entrance = new SpawnCoordinate(148, 208, 0);
  
  public HouseLevelUpstairs(SpawnCoordinate spawnCoordinate) {
    this.spawnCoordinate = spawnCoordinate;
    this.levelName = LEVEL_NAME;
    this.levelId = LEVEL_ID;
    loadLevel(BITMAP_PATH);
  }
  
  @Override
  protected void addPortals() {
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(48, 225, 80, 220);
    portals.add(new Portal(bounds, HouseLevel.FIRST_FLOOR_ENTRANCE, "HOUSE_LEVEL"));
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addSolidEntities() {
    
  }
    
  @Override
  protected void finalLevelSetup() {
    lightMap.enableLightMap(LightMapType.CUSTOM);
    lightMap.setCustomLightMap(new CustomLightMap(new Sprite("./resources/textures/worlds/houseLevelLightMap.png")));
  }

}
