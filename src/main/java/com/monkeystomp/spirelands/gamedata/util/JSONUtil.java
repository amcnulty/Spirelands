package com.monkeystomp.spirelands.gamedata.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Aaron Michael McNulty
 */
public class JSONUtil {
  /**
   * Key used for ids
   */
//  public static final String ID = "bHJncPLq94";
  public static final String ID = "ID";
  /**
   * Key used for the main characters object.
   */
//  public static final String CHARACTERS = "awNbSE58uK";
  public static final String CHARACTERS = "CHARACTERS";
  /**
   * Key for Luke character.
   */
//  public static final String LUKE = "na2rRC7tcp";
  public static final String LUKE = "LUKE";
  /**
   * Key for Sara character.
   */
//  public static final String SARA = "0LUWKjxGG7";
  public static final String SARA = "SARA";
  /**
   * Key for William character.
   */
//  public static final String WILLIAM = "aRbmU7JFgB";
  public static final String WILLIAM = "WILLIAM";
  /**
   * Key for Aaron character.
   */
  public static final String AARON = "AARON";
  /**
   * Key for Miley character.
   */
  public static final String MILEY = "MILEY";
  /**
   * Key for Justin character.
   */
  public static final String JUSTIN = "JUSTIN";
  /**
   * Key for party info object on each character.
   */
//  public static final String PARTY_INFO = "588Z6sPUkF";
  public static final String PARTY_INFO = "PARTY_INFO";
  /**
   * Boolean value for in party info for each character.
   */
//  public static final String IN_PARTY = "TNhGlhRMpi";
  public static final String IN_PARTY = "IN_PARTY";
  /**
   * Boolean value for if character is available to be added to the party.
   */
  public static final String AVAILABLE = "AVAILABLE";
  /**
   * Number value for position in party.
   */
//  public static final String PARTY_POSITION = "L2MzPWN5OQ";
  public static final String PARTY_POSITION = "PARTY_POSITION";
  /**
   * Key for stats object on each character.
   */
//  public static final String STATS = "apU4KX7T8e";
  public static final String STATS = "STATS";
  /**
   * Number value for intellect stat.
   */
//  public static final String INTELLECT = "V7x9C8oRTm";
  public static final String INTELLECT = "INTELLECT";
  /**
   * Number value for luck stat.
   */
//  public static final String LUCK = "kLwwSDho64";
  public static final String LUCK = "LUCK";
  /**
   * Number value for mana stat.
   */
//  public static final String MANA = "vODbbb8Alx";
  public static final String MANA = "MANA";
  /**
   * Number value for strength stat.
   */
//  public static final String STRENGTH = "yZpYl62ZxQ";
  public static final String STRENGTH = "STRENGTH";
  /**
   * Number value for level stat.
   */
//  public static final String LEVEL_STAT = "DN6CO1Rsu8";
  public static final String LEVEL_STAT = "LEVEL_STAT";
  /**
   * Number value for mana max stat.
   */
//  public static final String MANA_MAX = "NBIHRUJHHr";
  public static final String MANA_MAX = "MANA_MAX";
  /**
   * Number value for defense stat.
   */
//  public static final String DEFENSE = "R1Jgagop66";
  public static final String DEFENSE = "DEFENSE";
  /**
   * Number value for health max stat.
   */
//  public static final String HEALTH_MAX = "uqWyiIdj8r";
  public static final String HEALTH_MAX = "HEALTH_MAX";
  /**
   * Number value for spirit stat.
   */
//  public static final String SPIRIT = "0qv84towQw";
  public static final String SPIRIT = "SPIRIT";
  /**
   * Number value for health stat.
   */
//  public static final String HEALTH = "JSb14I8V1n";
  public static final String HEALTH = "HEALTH";
  /**
   * Number value for experience stat.
   */
//  public static final String EXPERIENCE = "M43HMFzkSv";
  public static final String EXPERIENCE = "EXPERIENCE";
  /**
   * Number value for speed stat.
   */
//  public static final String SPEED = "eNAP1loLmD";
  public static final String SPEED = "SPEED";
  /**
   * Key for equipment object on each character.
   */
//  public static final String EQUIPMENT = "8FRT4q5CKl";
  public static final String EQUIPMENT = "EQUIPMENT";
  /**
   * Number value for id of equipped weapon.
   */
//  public static final String WEAPON = "AMgclefCmS";
  public static final String WEAPON = "WEAPON";
  /**
   * Number value for id of equipped helmet.
   */
//  public static final String HELMET = "yoyHNJXgPn";
  public static final String HELMET = "HELMET";
  /**
   * Number value for id of equipped chestplate.
   */
//  public static final String CHESTPLATE = "MHyXq4noGV";
  public static final String CHESTPLATE = "CHESTPLATE";
  /**
   * Number value for id of equipped shield.
   */
//  public static final String SHIELD = "xnLt6pp1Yy";
  public static final String SHIELD = "SHIELD";
  /**
   * Number value for id of equipped boots.
   */
//  public static final String BOOTS = "T4gs8cvhf3";
  public static final String BOOTS = "BOOTS";
  /**
   * The ability slots object for each character.
   */
  public static final String ABILITY_SLOTS = "AbilitySlots";
  /**
   * The type used for ability slots.
   */
  public static final String TYPE = "type";
  /**
   * The level used for ability slots.
   */
  public static final String LEVEL = "level";
  /**
   * Equipped move for ability slots.
   */
  public static final String MOVE = "move";
  /**
   * Equipped item moves for ability slots.
   */
  public static final String ITEM_MOVES = "itemMoves";
  /**
   * Key for main levels object.
   */
//  public static final String LEVELS = "DHlVpJMNLN";
  public static final String LEVELS = "LEVELS";
  /**
   * Key for JSONArray object of boolean values on each level.
   */
//  public static final String CHESTS = "thAQqOM7oV";
  public static final String CHESTS = "CHESTS";
  /**
   * Key for main location object.
   */
//  public static final String LOCATION = "4VPmsx452s";
  public static final String LOCATION = "LOCATION";
  /**
   * String value for level key representing the level the save was made on.
   */
//  public static final String LEVEL_KEY = "43Djs2IJ3f";
  public static final String LEVEL_KEY = "LEVEL_KEY";
  /**
   * String value for the display name of the level that save was made on.
   */
//  public static final String LEVEL_NAME = "BKOJJqghnB";
  public static final String LEVEL_NAME = "LEVEL_NAME";
  /**
   * Number value for the x coordinate the player is at in the level.
   */
//  public static final String X = "BDqLEP7Yas";
  public static final String X = "X";
  /**
   * Number value for the y coordinate the player is at in the level.
   */
//  public static final String Y = "7updRAspaV";
  public static final String Y = "Y";
  /**
   * Number value for the direction the player is facing in the level.
   */
//  public static final String DIRECTION = "1ozadLPvm2";
  public static final String DIRECTION = "DIRECTION";
  /**
   * Key for the main inventory object.
   */
//  public static final String INVENTORY = "5sI67QIas5";
  public static final String INVENTORY = "INVENTORY";
  /**
   * Number value for the gold amount in the player's inventory.
   */
//  public static final String GOLD = "STlGH2brLY";
  public static final String GOLD = "GOLD";
  /**
   * Number value for the ability points in the player's inventory.
   */
  public static final String ABILITY_POINTS = "abilityPoints";
  /**
   * Key for the JSONArray object for all the inventory references.
   */
//  public static final String REFS = "gBmuNMUWDb";
  public static final String REFS = "REFS";
  /**
   * Number value for the amount within each inventory reference.
   */
//  public static final String AMOUNT = "DoQd94zuo4";
  public static final String AMOUNT = "AMOUNT";
  /**
   * Key for the array of battle moves in the inventory.
   */
  public static final String BATTLE_MOVES = "battleMoves";
  /**
   * Key for the main crafting object.
   */
  public static final String CRAFTING = "CRAFTING";
  /**
   * Key for current crafting level.
   */
  public static final String CRAFTING_LEVEL = "CRAFTING_LEVEL";
  /**
   * Key for crafting experience.
   */
  public static final String CRAFTING_EXP = "CRAFTING_EXP";
  /**
   * Key for array of discovered recipes.
   */
  public static final String DISCOVERED_RECIPES = "DISCOVERED_RECIPES";
  
  public JSONObject getNestedObject(JSONObject parent, String[] keys) {
    JSONObject object = parent;
    for (int i = 0; i < keys.length; i++) {
      object = (JSONObject) object.get(keys[i]);
    }
    return object;
  }
  
  public JSONArray getNestedArray (JSONObject parent, String[] keys) {
    JSONObject object = parent;
    JSONArray returnValue = new JSONArray();
    for (int i = 0; i < keys.length; i++) {
      if (i == keys.length - 1) returnValue = (JSONArray)object.get(keys[i]);
      else object = (JSONObject) object.get(keys[i]);
    }
    return returnValue;
  }
  
  public String getString(JSONObject parent, String key) {
    return parent.get(key).toString();
  }
  
  public String getNestedString(JSONObject parent, String[] keys) {
    JSONObject object = parent;
    for (int i = 0; i < keys.length - 1; i++) {
      object = (JSONObject) object.get(keys[i]);
    }
    return object.get(keys[keys.length - 1]).toString();
  }
  
  public int getInt(JSONObject parent, String key) {
    return Math.toIntExact((long)parent.get(key));
  }
  
  public boolean getBoolean(JSONObject parent, String key) {
    return (boolean)parent.get(key);
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
