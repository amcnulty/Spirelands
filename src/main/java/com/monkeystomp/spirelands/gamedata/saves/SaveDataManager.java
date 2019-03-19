package com.monkeystomp.spirelands.gamedata.saves;

import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class SaveDataManager {
  
  private final String pathToSave = "./saves/";
  private JSONObject  saveObject,
                      levels;
  private final JSONParser parser = new JSONParser();
  private String fileName;
  private static final SaveDataManager INSTANCE = new SaveDataManager();
  
  public void initSaveObject() {
    try {
      saveObject = (JSONObject) parser.parse(new FileReader(getClass().getResource("/gameData/saveData/newGame.json").getFile()));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
  
  public static SaveDataManager getSaveDataManager() {
    return INSTANCE;
  }
  
  public void setSaveObject(JSONObject saveObject) {
    this.saveObject = saveObject;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public JSONObject getSaveObject() {
    return saveObject;
  }
  /**
   * Gets the characters data portion of the current save object.
   * @return JSONObject of character information.
   */
  public JSONObject getCharacters() {
    return (JSONObject)saveObject.get(JSONUtil.CHARACTERS);
  }
  /**
   * Gets the location data portion of the current save object.
   * @return JSONObject of location data.
   */
  public JSONObject getLocation() {
    return (JSONObject)saveObject.get(JSONUtil.LOCATION);
  }
  /**
   * Gets the chests data portion of the current save object
   * @param levelKey Level which the chests data is location
   * @return Array of booleans that represent if the chest has been opened.
   */
  public boolean[] getChests(String levelKey) {
    levels = (JSONObject) saveObject.get(JSONUtil.LEVELS);
    JSONObject myLevel = (JSONObject) levels.get(levelKey);
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
   * @param levelKey Key to the level which chest data will be set.
   * @param data Array of boolean values indicating if chest at that index has been opened.
   */
  public void setChests(String levelKey, boolean[] data) {
    JSONObject level = (JSONObject) levels.get(levelKey);
    if (level == null) {
      levels.put(levelKey, new JSONObject());
      level = (JSONObject) levels.get(levelKey);
    }
    JSONArray array = new JSONArray();
    for (int i = 0; i < data.length; i++) {
      array.add(data[i]);
    }
    level.put(JSONUtil.CHESTS, array);
  }
  /**
   * Main save game method.
   */
  public void saveGame() throws IOException {
    saveLocation();
    saveStats();
    saveInventory();
    FileWriter file = new FileWriter(pathToSave + fileName);
    file.write(saveObject.toJSONString());
    file.flush();
  }
  
  private void saveLocation() {
    Location currentLocation = LocationManager.getLocationManager().getCurrentLocation();
    JSONObject location = getLocation();
    location.put(JSONUtil.LEVEL_KEY, currentLocation.getLevelId());
    location.put(JSONUtil.LEVEL_NAME, currentLocation.getLevelName());
    location.put(JSONUtil.X, currentLocation.getCoordinate().getX());
    location.put(JSONUtil.Y, currentLocation.getCoordinate().getY());
    location.put(JSONUtil.DIRECTION, currentLocation.getCoordinate().getDirection());
  }
  
  private void saveStats() {
    JSONObject characters = (JSONObject) saveObject.get(JSONUtil.CHARACTERS);
    ArrayList<Character> gameCharacters = CharacterManager.getCharacterManager().getCharacters();
    Set<?> keys = characters.keySet();
    keys.forEach(key -> {
      JSONObject character = (JSONObject) characters.get(key);
      JSONObject stats = (JSONObject) character.get(JSONUtil.STATS);
      JSONObject equipment = (JSONObject) character.get(JSONUtil.EQUIPMENT);
      gameCharacters.forEach(gameCharacter -> {
        if (gameCharacter.getId().equals((String)character.get(JSONUtil.ID))) {
          stats.put(JSONUtil.LEVEL_STAT, gameCharacter.getLevel());
          stats.put(JSONUtil.EXPERIENCE, gameCharacter.getExperience());
          stats.put(JSONUtil.HEALTH, gameCharacter.getHealth());
          stats.put(JSONUtil.HEALTH_MAX, gameCharacter.getHealthMax());
          stats.put(JSONUtil.MANA, gameCharacter.getMana());
          stats.put(JSONUtil.MANA_MAX, gameCharacter.getManaMax());
          stats.put(JSONUtil.STRENGTH, gameCharacter.getStrength());
          stats.put(JSONUtil.INTELLECT, gameCharacter.getIntellect());
          stats.put(JSONUtil.DEFENSE, gameCharacter.getDefense());
          stats.put(JSONUtil.SPIRIT, gameCharacter.getSpirit());
          stats.put(JSONUtil.SPEED, gameCharacter.getSpeed());
          stats.put(JSONUtil.LUCK, gameCharacter.getLuck());
          if (gameCharacter.getEquippedWeapon() != null)
            equipment.put(JSONUtil.WEAPON, gameCharacter.getEquippedWeapon().getId());
          if (gameCharacter.getEquippedHelmet() != null)
            equipment.put(JSONUtil.HELMET, gameCharacter.getEquippedHelmet().getId());
          if (gameCharacter.getEquippedChestplate() != null)
            equipment.put(JSONUtil.CHESTPLATE, gameCharacter.getEquippedChestplate().getId());
          if (gameCharacter.getEquippedShield() != null)
            equipment.put(JSONUtil.SHIELD, gameCharacter.getEquippedShield().getId());
          if (gameCharacter.getEquippedBoots() != null)
            equipment.put(JSONUtil.BOOTS, gameCharacter.getEquippedBoots().getId());
        }
      });
    });
  }

  private void saveInventory() {
    JSONObject inventory = (JSONObject) saveObject.get(JSONUtil.INVENTORY);
    inventory.put(JSONUtil.GOLD, InventoryManager.getInventoryManager().getGold());
    JSONArray refs = (JSONArray) inventory.get(JSONUtil.REFS);
    refs.clear();
    HashMap<Integer, InventoryReference> inventoryMap = InventoryManager.getInventoryManager().getItemMap();
    inventoryMap.forEach((id, ref) -> {
      JSONObject item = new JSONObject();
      item.put(JSONUtil.AMOUNT, ref.getAmount());
      item.put(JSONUtil.ID, id);
      refs.add(item);
    });
  }
  
}
