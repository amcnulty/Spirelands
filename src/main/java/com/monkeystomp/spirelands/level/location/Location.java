package com.monkeystomp.spirelands.level.location;

import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;

/**
 * Location describes a coordinate, level name, and level id.
 * @author Aaron Michael McNulty
 */
public class Location {
  
  private final SpawnCoordinate coordinate;
  private final String levelName,
                       levelId;

  public Location(SpawnCoordinate coordinate, String levelName, String levelId) {
    this.coordinate = coordinate;
    this.levelName = levelName;
    this.levelId = levelId;
  }

  public SpawnCoordinate getCoordinate() {
    return coordinate;
  }

  public String getLevelName() {
    return levelName;
  }

  public String getLevelId() {
    return levelId;
  }
  
}
