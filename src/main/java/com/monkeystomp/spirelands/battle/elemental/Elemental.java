package com.monkeystomp.spirelands.battle.elemental;

import com.monkeystomp.spirelands.battle.enemy.Enemy;
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
  /**
   * Weakness of 25% higher damage.
   */
  public static final int WEAK = 125;
  /**
   * Weakness of 50% higher damage.
   */
  public static final int VERY_WEAK = 150;
  /**
   * Resistance of 25% less damage.
   */
  public static final int RESISTANT = 75;
  /**
   * Resistance of 50% less damage.
   */
  public static final int VERY_RESISTANT = 50;
  /**
   * Five percent value.
   */
  public static final int FIVE_PERCENT = 105;
  /**
   * Ten percent value.
   */
  public static final int TEN_PERCENT = 110;
  /**
   * Fifteen percent value.
   */
  public static final int FIFTEEN_PERCENT = 115;
  /**
   * Twenty percent value.
   */
  public static final int TWENTY_PERCENT = 120;
  
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
  
  public static double getModifierForEnemyElement(String attackElement, Enemy target) {
    if (target.effectOfElement(attackElement) != 1) return target.effectOfElement(attackElement) / 100.0;
    else if (attackElement != null) {
      return jsonUtil.getNestedInt(elementalTypes, new String[]{attackElement, target.getElement()}) / 100.0;
    }
    else {
      return 1;
    }
  }

}
