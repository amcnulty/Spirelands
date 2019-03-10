package com.monkeystomp.spirelands.level.util;

import com.monkeystomp.spirelands.level.HouseLevel;
import com.monkeystomp.spirelands.level.HouseLevelUpstairs;
import com.monkeystomp.spirelands.level.Level;
import com.monkeystomp.spirelands.level.SpawnLevel;
import com.monkeystomp.spirelands.level.TestLevel;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class LevelFactory {
  
  // How to create a factory pattern for a constructor that takes no parameters.
  
//  private static HashMap<String, Supplier<Level>> levelsMap = new HashMap<>();
//  
//  static {
//    levelsMap.put("SPAWN_LEVEL", SpawnLevel::new);
//    levelsMap.put("TEST_LEVEL", TestLevel::new);
//  }
//  
//  public static Level createLevel(String key) {
//    return levelsMap.get(key).get();
//  }
  
  // How to create a factory pattern for a constructor that takes multiple parameters.
  
//  private static Map<String, Factory<String, String, Level>> factoryMap;
//  
//  static {
//    factoryMap.put("SPAWN_LEVEL", SpawnLevel::new);
//    factoryMap.put("TEST_LEVEL", TestLevel::new);
//  }
//  
//  public static Level createLevel(String key, String first, String second) {
//    return factoryMap.get(key).create(first, second);
//  }
//  
//  @FunctionalInterface
//  private interface Factory<T, R, S> {
//    S create(T obj1, R obj2);
//  }
  
  private static final HashMap<String, Factory<Level, SpawnCoordinate>> FACTORY_MAP = new HashMap<>();
  
  static {
    FACTORY_MAP.put(SpawnLevel.LEVEL_ID, SpawnLevel::new);
    FACTORY_MAP.put(TestLevel.LEVEL_ID, TestLevel::new);
    FACTORY_MAP.put(HouseLevel.LEVEL_ID, HouseLevel::new);
    FACTORY_MAP.put(HouseLevelUpstairs.LEVEL_ID, HouseLevelUpstairs::new);
  }
  
  public static Level createLevel(String key, SpawnCoordinate coordinate) {
    return FACTORY_MAP.get(key).create(coordinate);
  }
  
  private interface Factory<T, R> {
    T create(R obj1);
  }
}