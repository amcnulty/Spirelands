package com.monkeystomp.spirelands.gamedata.util;

import org.json.simple.JSONObject;

/**
 *
 * @author Aaron Michael McNulty
 */
public class JSONUtil {
  /**
   * Key used for ids
   */
  public static final String ID = "bHJncPLq94";
  /**
   * Key used for the main characters object.
   */
  public static final String CHARACTERS = "awNbSE58uK";
  /**
   * Key for Luke character.
   */
  public static final String LUKE = "na2rRC7tcp";
  /**
   * Key for Sara character.
   */
  public static final String SARA = "0LUWKjxGG7";
  /**
   * Key for William character.
   */
  public static final String WILLIAM = "aRbmU7JFgB";
  /**
   * Key for party info object on each character.
   */
  public static final String PARTY_INFO = "588Z6sPUkF";
  /**
   * Boolean value for in party info for each character.
   */
  public static final String IN_PARTY = "TNhGlhRMpi";
  /**
   * Number value for position in party.
   */
  public static final String PARTY_POSITION = "L2MzPWN5OQ";
  /**
   * Key for stats object on each character.
   */
  public static final String STATS = "apU4KX7T8e";
  /**
   * Number value for intellect stat.
   */
  public static final String INTELLECT = "V7x9C8oRTm";
  /**
   * Number value for luck stat.
   */
  public static final String LUCK = "kLwwSDho64";
  /**
   * Number value for mana stat.
   */
  public static final String MANA = "vODbbb8Alx";
  /**
   * Number value for strength stat.
   */
  public static final String STRENGTH = "yZpYl62ZxQ";
  /**
   * Number value for level stat.
   */
  public static final String LEVEL_STAT = "DN6CO1Rsu8";
  /**
   * Number value for mana max stat.
   */
  public static final String MANA_MAX = "NBIHRUJHHr";
  /**
   * Number value for defense stat.
   */
  public static final String DEFENSE = "R1Jgagop66";
  /**
   * Number value for health max stat.
   */
  public static final String HEALTH_MAX = "uqWyiIdj8r";
  /**
   * Number value for spirit stat.
   */
  public static final String SPIRIT = "0qv84towQw";
  /**
   * Number value for health stat.
   */
  public static final String HEALTH = "JSb14I8V1n";
  /**
   * Number value for experience stat.
   */
  public static final String EXPERIENCE = "M43HMFzkSv";
  /**
   * Number value for speed stat.
   */
  public static final String SPEED = "eNAP1loLmD";
  /**
   * Key for equipment object on each character.
   */
  public static final String EQUIPMENT = "8FRT4q5CKl";
  /**
   * Number value for id of equipped weapon.
   */
  public static final String WEAPON = "AMgclefCmS";
  /**
   * Number value for id of equipped helmet.
   */
  public static final String HELMET = "yoyHNJXgPn";
  /**
   * Number value for id of equipped chestplate.
   */
  public static final String CHESTPLATE = "MHyXq4noGV";
  /**
   * Number value for id of equipped shield.
   */
  public static final String SHIELD = "xnLt6pp1Yy";
  /**
   * Number value for id of equipped boots.
   */
  public static final String BOOTS = "T4gs8cvhf3";
  /**
   * Key for main levels object.
   */
  public static final String LEVELS = "DHlVpJMNLN";
  /**
   * Key for JSONArray object of boolean values on each level.
   */
  public static final String CHESTS = "thAQqOM7oV";
  /**
   * Key for main location object.
   */
  public static final String LOCATION = "4VPmsx452s";
  /**
   * String value for level key representing the level the save was made on.
   */
  public static final String LEVEL_KEY = "43Djs2IJ3f";
  /**
   * String value for the display name of the level that save was made on.
   */
  public static final String LEVEL_NAME = "BKOJJqghnB";
  /**
   * Number value for the x coordinate the player is at in the level.
   */
  public static final String X = "BDqLEP7Yas";
  /**
   * Number value for the y coordinate the player is at in the level.
   */
  public static final String Y = "7updRAspaV";
  /**
   * Number value for the direction the player is facing in the level.
   */
  public static final String DIRECTION = "1ozadLPvm2";
  /**
   * String value for the id of the party leader.
   */
  public static final String PARTY_LEADER = "dyYJ9VY0sF";
  /**
   * Key for the main inventory object.
   */
  public static final String INVENTORY = "5sI67QIas5";
  /**
   * Number value for the gold amount in the player's inventory.
   */
  public static final String GOLD = "STlGH2brLY";
  /**
   * Key for the JSONArray object for all the inventory references.
   */
  public static final String REFS = "gBmuNMUWDb";
  /**
   * Number value for the amount within each inventory reference.
   */
  public static final String AMOUNT = "DoQd94zuo4";
  
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
