package com.monkeystomp.spirelands.gamedata.saves;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Used to make the specific calls to the storage system to read and write save data then transform it into JSON for the application to consume.
 * @author Aaron Michael McNulty
 */
public class SaveLoadTransformer {
  
  private final String saveGameBasePath = "./saves/";
  private final JSONParser parser = new JSONParser();
  private final HashMap<SaveGameSlot, String> slotPathMap = new HashMap<>();
  
  public SaveLoadTransformer() {
    slotPathMap.put(SaveGameSlot.SLOT1, "slot1.json");
    slotPathMap.put(SaveGameSlot.SLOT2, "slot2.json");
    slotPathMap.put(SaveGameSlot.SLOT3, "slot3.json");
  }
  /**
   * Loads the game data for the given slot from whatever storage system is in use and returns it as a JSONObject for the application to consume.
   * @param slot Slot for which game data to load.
   * @return JSONObject containing save game information.
   * @throws Exception 
   */
  public JSONObject load(SaveGameSlot slot) throws Exception {
    if (!slot.equals(SaveGameSlot.NEW_GAME)) {
      return (JSONObject) parser.parse(new FileReader(new File(saveGameBasePath + slotPathMap.get(slot))));
    }
    return (JSONObject) parser.parse(new InputStreamReader(getClass().getResourceAsStream("/gameData/saveData/newGame.json")));
  }
  /**
   * Saves the given JSONObject to the storage system at the specific location for the given slot.
   * @param saveObject JSONObject with the data to be saved.
   * @param slot Slot for where game data is to be saved to.
   * @throws Exception 
   */
  public void save(JSONObject saveObject, SaveGameSlot slot) throws Exception {
    FileWriter file = new FileWriter(saveGameBasePath + slotPathMap.get(slot));
    file.write(saveObject.toJSONString());
    file.flush();
  }
  
}
