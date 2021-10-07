package com.monkeystomp.spirelands.gamedata.saves;

import com.monkeystomp.spirelands.level.util.LocationManager;
import org.json.simple.JSONObject;

/**
 * Main data loader class which is in charge of making requests to load save data.
 * @author Aaron Michael McNulty
 */
public class DataLoader {
  
  private JSONObject json;
  private final SaveLoadTransformer saveLoadTransformer = new SaveLoadTransformer();
  private static final SaveDataManager SAVE_DATA_MANAGER = SaveDataManager.getSaveDataManager();
  
  public void loadGame(SaveGameSlot slot) throws Exception {
    json = getSaveDataForSlot(slot);
    for (SaveDataHydratable manager: SAVE_DATA_MANAGER.getSaveDataManagers()) {
      manager.hydrate(json);
    }
    SAVE_DATA_MANAGER.setSlot(slot);
    LocationManager.getLocationManager().changeViewToLevel();
  }
  
  public JSONObject getSaveDataForSlot(SaveGameSlot slot) throws Exception {
    return saveLoadTransformer.load(slot);
  }

}
