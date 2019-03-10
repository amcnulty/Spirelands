package com.monkeystomp.spirelands.gamedata.saves;

import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.util.LocationManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  private JSONObject saveObject;
  private final JSONParser parser = new JSONParser();
  private String fileName;
  private static final SaveDataManager INSTANCE = new SaveDataManager();
  
  private SaveDataManager() {
    initSaveObject();
  }
  
  private void initSaveObject() {
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
   * Main save game method.
   */
  public void saveGame() throws IOException {
    setLocation();
    FileWriter file = new FileWriter(pathToSave + fileName);
    file.write(saveObject.toJSONString());
    file.flush();
  }
  
  private void setLocation() {
    Location currentLocation = LocationManager.getLocationManager().getCurrentLocation();
    JSONObject location = getLocation();
    location.put("Level Key", currentLocation.getLevelId());
    location.put("Level Name", currentLocation.getLevelName());
    location.put("X", currentLocation.getCoordinate().getX());
    location.put("Y", currentLocation.getCoordinate().getY());
    location.put("Direction", currentLocation.getCoordinate().getDirection());
  }

}
