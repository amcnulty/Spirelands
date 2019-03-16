package com.monkeystomp.spirelands.gamedata.util;

import org.json.simple.JSONObject;

/**
 *
 * @author Aaron Michael McNulty
 */
public class JSONUtil {
  
  public JSONObject getNestedObject(JSONObject parent, String[] keys) {
    JSONObject object = parent;
    for (int i = 0; i < keys.length; i++) {
      object = (JSONObject) object.get(keys[i]);
    }
    return object;
  }
  
  public String getNestedString(JSONObject parent, String[] keys) {
    JSONObject object = parent;
    for (int i = 0; i < keys.length - 1; i++) {
      object = (JSONObject) object.get(keys[i]);
    }
    return object.get(keys[keys.length - 1]).toString();
  }
  
  public int getNestedInt(JSONObject parent, String[] keys) {
    JSONObject object = parent;
    for (int i = 0; i < keys.length - 1; i++) {
      object = (JSONObject) object.get(keys[i]);
    }
    return Math.toIntExact((long)object.get(keys[keys.length - 1]));
  }
  
  public boolean getNestedBoolean(JSONObject parent, String[] keys) {
    JSONObject object = parent;
    for (int i = 0; i < keys.length - 1; i++) {
      object = (JSONObject) object.get(keys[i]);
    }
    return (boolean)object.get(keys[keys.length - 1]);
  }

}
