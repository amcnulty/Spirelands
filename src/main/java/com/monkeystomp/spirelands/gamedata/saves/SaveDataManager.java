package com.monkeystomp.spirelands.gamedata.saves;

import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.character.Character;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    return (JSONObject)saveObject.get("Characters");
  }
  /**
   * Gets the location data portion of the current save object.
   * @return JSONObject of location data.
   */
  public JSONObject getLocation() {
    return (JSONObject)saveObject.get("Location");
  }
  /**
   * Gets the chests data portion of the current save object
   * @param levelKey Level which the chests data is location
   * @return Array of booleans that represent if the chest has been opened.
   */
  public boolean[] getChests(String levelKey) {
    levels = (JSONObject) saveObject.get("Levels");
    JSONObject myLevel = (JSONObject) levels.get(levelKey);
    if (myLevel == null) return null;
    else {
      JSONArray array = (JSONArray) myLevel.get("chests");
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
    level.put("chests", array);
  }
  /**
   * Main save game method.
   */
  public void saveGame() throws IOException {
    saveLocation();
    saveStats();
    FileWriter file = new FileWriter(pathToSave + fileName);
    file.write(saveObject.toJSONString());
    file.flush();
  }
  
  private void saveLocation() {
    Location currentLocation = LocationManager.getLocationManager().getCurrentLocation();
    JSONObject location = getLocation();
    location.put("Level Key", currentLocation.getLevelId());
    location.put("Level Name", currentLocation.getLevelName());
    location.put("X", currentLocation.getCoordinate().getX());
    location.put("Y", currentLocation.getCoordinate().getY());
    location.put("Direction", currentLocation.getCoordinate().getDirection());
  }
  
  private void saveStats() {
    JSONObject characters = (JSONObject) saveObject.get("Characters");
    ArrayList<Character> gameCharacters = CharacterManager.getCharacterManager().getCharacters();
    Set<?> keys = characters.keySet();
    keys.forEach(key -> {
      JSONObject character = (JSONObject) characters.get(key);
      JSONObject stats = (JSONObject) character.get("stats");
      gameCharacters.forEach(gameCharacter -> {
        if (gameCharacter.getId().equals((String)character.get("id"))) {
          stats.put("level", gameCharacter.getLevel());
          stats.put("experience", gameCharacter.getExperience());
          stats.put("health", gameCharacter.getHealth());
          stats.put("healthMax", gameCharacter.getHealthMax());
          stats.put("mana", gameCharacter.getMana());
          stats.put("manaMax", gameCharacter.getManaMax());
          stats.put("strength", gameCharacter.getStrength());
          stats.put("intellect", gameCharacter.getIntellect());
          stats.put("defense", gameCharacter.getDefense());
          stats.put("spirit", gameCharacter.getSpirit());
          stats.put("speed", gameCharacter.getSpeed());
          stats.put("luck", gameCharacter.getLuck());
                  
        }
      });
    });
  }

}
