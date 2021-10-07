package com.monkeystomp.spirelands.gamedata.saves;

import org.json.simple.JSONObject;

/**
 * Describes any singleton class that is aware of save data that will be hydrated on load.
 * Save data will be requested on save.
 * @author Aaron Michael McNulty
 */
public interface SaveDataHydratable {
  /**
   * Called when save game data is being loaded. Use this to hydrate all relevant information stored in the save object.
   * @param json Save object with data that was loaded.
   */
  public void hydrate(JSONObject json);
  
  /**
   * Called when game is being saved. Return a mutated copy of the given JSON with the additions of relevant save information from this class.
   * @param saveObject Save object to be populated with data to save for this class.
   */
  public void populateSaveData(JSONObject saveObject);
}
