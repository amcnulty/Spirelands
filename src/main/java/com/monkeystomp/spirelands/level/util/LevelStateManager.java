package com.monkeystomp.spirelands.level.util;

import com.monkeystomp.spirelands.gamedata.saves.SaveDataHydratable;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Singleton class to track state information and save data for all levels.
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class LevelStateManager implements SaveDataHydratable {
  /**
   * Slice of save object for this manager. JSON Name - LEVELS
   */
  private JSONObject levels;
  
  private static final LevelStateManager INSTANCE = new LevelStateManager();
  
  private LevelStateManager() {}
  
  public static LevelStateManager getLevelStateManager() {
    return INSTANCE;
  }
  
  /**
   * Gets the chests data portion of the current save object
   * @param levelId Level which the chests data is location
   * @return Array of booleans that represent if the chest has been opened.
   */
  public boolean[] getChests(String levelId) {
    JSONObject myLevel = (JSONObject) levels.get(levelId);
    if (myLevel == null) return null;
    else {
      JSONArray array = (JSONArray) myLevel.get(JSONUtil.CHESTS);
      boolean[] values = new boolean[array.size()];
      for (int i = 0; i < values.length; i++) {
        values[i] = (boolean)array.get(i);
      }
      return values;
    }
  }
  /**
   * Saves the state of the chests on the level that matches the given level key to the given data.
   * @param levelId Key to the level which chest data will be set.
   * @param data Array of boolean values indicating if chest at that index has been opened.
   */
  public void setChests(String levelId, boolean[] data) {
    JSONObject level = (JSONObject) levels.get(levelId);
    if (level == null) {
      levels.put(levelId, new JSONObject());
      level = (JSONObject) levels.get(levelId);
    }
    JSONArray array = new JSONArray();
    for (int i = 0; i < data.length; i++) {
      array.add(data[i]);
    }
    level.put(JSONUtil.CHESTS, array);
  }

  @Override
  public void hydrate(JSONObject json) {
    levels = (JSONObject) json.get(JSONUtil.LEVELS);
  }

  @Override
  public void populateSaveData(JSONObject saveObject) {
    saveObject.put(JSONUtil.LEVELS, levels);
  }

}
