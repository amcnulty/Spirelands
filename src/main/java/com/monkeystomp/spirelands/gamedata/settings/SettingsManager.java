package com.monkeystomp.spirelands.gamedata.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class SettingsManager {
  
  private JSONObject  json,
                      resolution,
                      cursor;
  private final JSONParser parser = new JSONParser();
  private boolean customCursor,
                  fullScreen;
  private String pathToCursor;
  private int width,
              height;
  private float musicVolume,
                sfxVolume;
  
  private static final SettingsManager instance = new SettingsManager();
  
  public static SettingsManager getSettingsManager() {
    return instance;
  }
  
  private SettingsManager() {
    loadSettings();
    fullScreen = (boolean)json.get("fullScreen");
    resolution = (JSONObject)json.get("resolution");
    width = Math.toIntExact((long)resolution.get("width"));
    height = Math.toIntExact((long)resolution.get("height"));
    cursor = (JSONObject)json.get("cursor");
    customCursor = (boolean)cursor.get("custom");
    pathToCursor = (String)cursor.get("path");
    musicVolume = (long)json.get("musicVolume") / 100f;
    sfxVolume = (long)json.get("soundEffectVolume") / 100f;
  }
  
  private void loadSettings() {
    try {
      json = (JSONObject) parser.parse(new FileReader(new File("./settings/settings.json")));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void saveSettings() {
    try (FileWriter file = new FileWriter("./settings/settings.json")) {
      file.write(json.toJSONString());
      file.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public String getPathToCursor() {
    return pathToCursor;
  }

  public void setPathToCursor(String pathToCursor) {
    this.pathToCursor = pathToCursor;
    cursor.put("path", pathToCursor);
  }

  public boolean isCustomCursor() {
    return customCursor;
  }

  public void setCustomCursor(boolean customCursor) {
    this.customCursor = customCursor;
    cursor.put("custom", customCursor);
  }

  public boolean isFullScreen() {
    return fullScreen;
  }

  public void setFullScreen(boolean fullScreen) {
    this.fullScreen = fullScreen;
    json.put("fullScreen", fullScreen);
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
    resolution.put("width", width);
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
    resolution.put("height", height);
  }

  public float getMusicVolume() {
    return musicVolume;
  }

  public void setMusicVolume(float musicVolume) {
    this.musicVolume = musicVolume;
    json.put("musicVolume", (int)(musicVolume * 100));
  }

  public float getSfxVolume() {
    return sfxVolume;
  }

  public void setSfxVolume(float sfxVolume) {
    this.sfxVolume = sfxVolume;
    json.put("soundEffectVolume", (int)(sfxVolume * 100));
  }

}
