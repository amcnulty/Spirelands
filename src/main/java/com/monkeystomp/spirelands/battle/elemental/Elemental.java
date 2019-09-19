package com.monkeystomp.spirelands.battle.elemental;

import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Elemental {
  
  /**
   * Fire elemental type.
   */
  public static final String FIRE = "Fire";
  /**
   * Ice elemental type.
   */
  public static final String ICE = "Ice";
  /**
   * Earth elemental type.
   */
  public static final String EARTH = "Earth";
  /**
   * Water elemental type.
   */
  public static final String WATER = "Water";
  /**
   * Electric elemental type.
   */
  public static final String ELECTRIC = "Electric";
  /**
   * Poison elemental type.
   */
  public static final String POISON = "Poison";
  /**
   * Undead elemental type.
   */
  public static final String UNDEAD = "Undead";
  /**
   * Holy elemental type.
   */
  public static final String HOLY = "Holy";
  
  private static JSONObject elementalTypes;
  private static final JSONUtil jsonUtil = new JSONUtil();
  private static final JSONParser parser = new JSONParser();
  
  static {
    try {
      elementalTypes = (JSONObject) parser.parse(new InputStreamReader(Class.class.getResourceAsStream("/gameData/elemental/elemental.json")));
    }
    catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
  
  public static double getAttackModifier(String attackElement, String defensiveElement) {
    if (attackElement != null && defensiveElement != null) {
      return jsonUtil.getNestedInt(elementalTypes, new String[]{attackElement, defensiveElement}) / 100.0;
    }
    else {
      return 1;
    }
  }

}
