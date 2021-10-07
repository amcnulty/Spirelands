package com.monkeystomp.spirelands.level.util;

import com.monkeystomp.spirelands.gamedata.saves.SaveDataHydratable;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.level.Level;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.view.LevelView;
import com.monkeystomp.spirelands.view.ViewManager;
import org.json.simple.JSONObject;

/**
 * Location Manager class is used to store information about where the player is within a level and what level they are on. This information is used for game saves.
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class LocationManager implements SaveDataHydratable {
  /**
   * Slice of save object for this manager. JSON Name - LOCATION
   */
  private JSONObject location;
  
  private String levelKey;
  private int x, y, direction;
  private Location currentLocation;
  private static final LocationManager INSTANCE = new LocationManager();
  
  private LocationManager() {}
  
  public static LocationManager getLocationManager() {
    return INSTANCE;
  }

  public void setCurrentLocation(Location currentLocation) {
    this.currentLocation = currentLocation;
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }
  
  public void changeViewToLevel() {
    Level level = LevelFactory.createLevel(levelKey, new SpawnCoordinate(x, y, direction));
    LevelView levelView = new LevelView(level);
    ViewManager.getViewManager().changeView(levelView);
  }

  @Override
  public void hydrate(JSONObject json) {
    location = (JSONObject) json.get(JSONUtil.LOCATION);
    levelKey = (String)location.get(JSONUtil.LEVEL_KEY);
    x = Math.toIntExact((long)location.get(JSONUtil.X));
    y = Math.toIntExact((long)location.get(JSONUtil.Y));
    direction = Math.toIntExact((long)location.get(JSONUtil.DIRECTION));
    String levelName = (String)location.get(JSONUtil.LEVEL_NAME);
    setCurrentLocation(new Location(new SpawnCoordinate(x, y, direction), levelName, levelKey));
  }

  @Override
  public void populateSaveData(JSONObject saveObject) {
    saveObject.put(JSONUtil.LOCATION, location);
    JSONObject locationToSave = (JSONObject)saveObject.get(JSONUtil.LOCATION);
    locationToSave.put(JSONUtil.LEVEL_KEY, currentLocation.getLevelId());
    locationToSave.put(JSONUtil.LEVEL_NAME, currentLocation.getLevelName());
    locationToSave.put(JSONUtil.X, currentLocation.getCoordinate().getX());
    locationToSave.put(JSONUtil.Y, currentLocation.getCoordinate().getY());
    locationToSave.put(JSONUtil.DIRECTION, currentLocation.getCoordinate().getDirection());
  }
  
}
