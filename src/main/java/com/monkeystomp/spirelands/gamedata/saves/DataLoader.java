package com.monkeystomp.spirelands.gamedata.saves;

import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.view.LevelView;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aaron Michael McNulty
 */
public class DataLoader {
  
  private JSONObject  json,
                      location,
                      characters;
  private int x, y, direction;
  private String levelKey;
  private final JSONParser parser = new JSONParser();
  /**
   * Gets the LevelView object needed for starting the game at the save point for the given file name.
   * @param fileName Name of the file which to use for loading game data.
   * @return LevelView object
   * @throws IOException
   * @throws ParseException 
   */
  public LevelView getLevelView(String fileName) throws IOException, ParseException {
    loadFile(fileName);
    location = (JSONObject) json.get(JSONUtil.LOCATION);
    characters = (JSONObject) json.get(JSONUtil.CHARACTERS);
    setSaveManager(fileName);
    setCharacterData();
    setInventoryData();
    levelKey = (String)location.get(JSONUtil.LEVEL_KEY);
    x = Math.toIntExact((long)location.get(JSONUtil.X));
    y = Math.toIntExact((long)location.get(JSONUtil.Y));
    direction = Math.toIntExact((long)location.get(JSONUtil.DIRECTION));
    return new LevelView(LevelFactory.createLevel(levelKey, new SpawnCoordinate(x, y, direction)));
  }
  
  private void loadFile(String fileName) throws IOException, ParseException {
    json = (JSONObject) parser.parse(new FileReader(new File("./saves/" + fileName)));
  }
  
  private void setSaveManager(String fileName) {
    SaveDataManager.getSaveDataManager().setSaveObject(json);
    SaveDataManager.getSaveDataManager().setFileName(fileName);
  }
  
  private void setCharacterData() {
    CharacterManager.getCharacterManager().setupCharactersDetails(characters);
  }
  
  private void setInventoryData() {
    JSONObject inventory = (JSONObject) json.get(JSONUtil.INVENTORY);
    InventoryManager.getInventoryManager().setInventoryData(inventory);
  }

}
