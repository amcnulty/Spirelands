package com.monkeystomp.spirelands.level.util;

import com.monkeystomp.spirelands.level.location.Location;

/**
 * Location Manager class is used to store information about where the player is within a level and what level they are on. This information is used for game saves.
 * @author Aaron Michael McNulty
 */
public class LocationManager {
  
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
  
}
