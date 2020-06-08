package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.elemental.Elemental;
import java.util.HashMap;

/**
 * This class is used to hold the label and value of an attribute.
 * Attributes are displayed on the battle move detail page. Predefined labels are held as static variables.
 * @author Aaron Michael McNulty
 */
public class BattleMoveAttribute {
  
  /**
   * Element type used for offensive moves.
   */
  public static final String ELEMENT = "Element";
  /**
   * Type of move. Used for Physical, Magical.
   */
  public static final String TYPE = "Type";
  /**
   * Action of move. Used for Offensive, Defensive, Buff.
   */
  public static final String ACTION = "Action";
  /**
   * Power level of the move.
   */
  public static final String POWER_LEVEL = "Power Level";
  /**
   * Mana Required for the move.
   */
  public static final String MANA_REQUIRED = "Mana";
  /**
   * Accuracy percentage of the move.
   */
  public static final String ACCURACY = "Accuracy %";
  /**
   * Ranged label for if move is ranged.
   */
  public static final String RANGED = "Ranged";
  /**
   * Multi-target label for if move is multi-target.
   */
  public static final String MULTI_TARGET = "Multi-Target";
  /**
   * Attack buff modifier percentage.
   */
  public static final String ATTACK_BUFF = "Attack Increase";
  /**
   * Defense buff modifier percentage.
   */
  public static final String DEFENSE_BUFF = "Defense Increase";
  /**
   * Intellect buff modifier percentage.
   */
  public static final String INTELLECT_BUFF = "Intellect Increase";
  /**
   * Spirit buff modifier percentage.
   */
  public static final String SPIRIT_BUFF = "Spirit Increase";
  /**
   * Buff time label.
   */
  public static final String BUFF_TIME = "Buff Time";
  
  
  // Elemental effects
  
  // Fire element resistance.
  private static final String FIRE_RESIST = "Fire Resistance";
  // Ice element resistance.
  private static final String ICE_RESIST = "Ice Resistance";
  // Electric element resistance.
  private static final String ELECTRIC_RESIST = "Electric Resistance";
  // Earth element resistance.
  private static final String EARTH_RESIST = "Earth Resistance";
  // Water element resistance.
  private static final String WATER_RESIST = "Water Resistance";
  // Poison element resistance.
  private static final String POISON_RESIST = "Poison Resistance";
  // Holy element resistance.
  private static final String HOLY_RESIST = "Holy Resistance";
  // Undead element resistance.
  private static final String UNDEAD_RESIST = "Undead Resistance";
  
  public static final HashMap<String, String> ELEMENTAL_EFFECT_MAP = new HashMap<>();
  
  static {
    ELEMENTAL_EFFECT_MAP.put(Elemental.FIRE, FIRE_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.ICE, ICE_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.ELECTRIC, ELECTRIC_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.EARTH, EARTH_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.WATER, WATER_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.POISON, POISON_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.HOLY, HOLY_RESIST);
    ELEMENTAL_EFFECT_MAP.put(Elemental.UNDEAD, UNDEAD_RESIST);
  }
  
  private final String  label,
                        value;
  
  public BattleMoveAttribute(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }
  
}
