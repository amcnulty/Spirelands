package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.battle.elemental.Elemental;
import java.util.HashMap;

/**
 * Items Attributes are the effects of using or equipping an item. This class is used to hold the label and value of an attribute. Predefined labels are held as static variables.
 * @author Aaron Michael McNulty
 */
public class ItemAttribute {
  /**
   * Health restoring attribute. Used for healing equipment items.
   */
  public static final String HEALTH_RESTORE = "Health Restore";
  /**
   * Mana restoring attribute. Used for healing equipment items.
   */
  public static final String MANA_RESTORE = "Mana Restore";
  /**
   * Attack power of a weapon. Used on offensive weapons that modify a characters total attack stat.
   */
  public static final String ATTACK_POWER = "Attack Power";
  /**
   * Magic power of a weapon. Used on offensive weapons that modify a characters total magic attack stat.
   */
  public static final String MAGIC_POWER = "Magic Power";
  /**
   * Physical defense of armor. Used on armor to protect against physical attacks.
   */
  public static final String PHYSICAL_DEFENSE = "Physical Defense";
  /**
   * Magic defense of armor. Used on armor to protect against magic attacks.
   */
  public static final String MAGIC_DEFENSE = "Magic Defense";
  /**
   * Speed penalty of item. Reduces the wearer's speed stat.
   */
  public static final String SPEED_PENALTY = "Speed Penalty";
  /**
   * Strength stat increase. Used for strength up items.
   */
  public static final String STRENGTH_UP = "Stregth Increase";
  /**
   * Defense stat increase. Used for defense up items.
   */
  public static final String DEFENSE_UP = "Defense Increase";
  /**
   * Intellect stat increase. Used for magic up items.
   */
  public static final String INTELLECT_UP = "Intellect Increase";
  /**
   * Spirit stat increase. Used for spirit up items.
   */
  public static final String SPIRIT_UP = "Increase Spirit";
  /**
   * Speed stat increase. Used for speed up items.
   */
  public static final String SPEED_UP = "Speed Increase";
  /**
   * Level increase. Used for level up items.
   */
  public static final String LEVEL_UP = "Level Increase";
  /**
   * Luck stat increase. Used for luck up items.
   */
  public static final String LUCK_UP = "Luck Increase";
  /**
   * Fire damage amount. Used for fire type battle items.
   */
  public static final String FIRE_DAMAGE = "Fire Damage";
  /**
   * Fire bonus percentage. Used for weapons items.
   */
  public static final String FIRE_BONUS = "Fire Bonus %";
  /**
   * Fire resistance percentage. Used for armor items.
   */
  public static final String FIRE_RESISTANCE = "Fire Resistance %";
  /**
   * Ice bonus percentage. Used for weapons items.
   */
  public static final String ICE_BONUS = "Ice Bonus %";
  /**
   * Ice resistance percentage. Used for armor items.
   */
  public static final String ICE_RESISTANCE = "Ice Resistance %";
  /**
   * Earth bonus percentage. Used for weapons items.
   */
  public static final String EARTH_BONUS = "Earth Bonus %";
  /**
   * Earth resistance percentage. Used for armor items.
   */
  public static final String EARTH_RESISTANCE = "Earth Resistance %";
  /**
   * Water bonus percentage. Used for weapons items.
   */
  public static final String WATER_BONUS = "Water Bonus %";
  /**
   * Water resistance percentage. Used for armor items.
   */
  public static final String WATER_RESISTANCE = "Water Resistance %";
  /**
   * Electric bonus percentage. Used for weapons items.
   */
  public static final String ELECTRIC_BONUS = "Electric Bonus %";
  /**
   * Electric resistance percentage. Used for armor items.
   */
  public static final String ELECTRIC_RESISTANCE = "Electric Resistance %";
  /**
   * Poison bonus percentage. Used for weapons items.
   */
  public static final String POISON_BONUS = "Poison Bonus %";
  /**
   * Poison resistance percentage. Used for armor items.
   */
  public static final String POISON_RESISTANCE = "Poison Resistance %";
  /**
   * Undead bonus percentage. Used for weapons items.
   */
  public static final String UNDEAD_BONUS = "Undead Bonus %";
  /**
   * Undead resistance percentage. Used for armor items.
   */
  public static final String UNDEAD_RESISTANCE = "Undead Resistance %";
  /**
   * Holy bonus percentage. Used for weapons items.
   */
  public static final String HOLY_BONUS = "Holy Bonus %";
  /**
   * Holy resistance percentage. Used for armor items.
   */
  public static final String HOLY_RESISTANCE = "Holy Resistance %";
  
  private final String  label,
                        value;
  public static final HashMap<String, String> WEAPON_ELEMENT_MAP = new HashMap<>(),
                                              ARMOR_ELEMENT_MAP = new HashMap<>();
  
  static {
    WEAPON_ELEMENT_MAP.put(Elemental.FIRE, FIRE_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.ICE, ICE_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.EARTH, EARTH_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.WATER, WATER_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.ELECTRIC, ELECTRIC_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.POISON, POISON_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.UNDEAD, UNDEAD_BONUS);
    WEAPON_ELEMENT_MAP.put(Elemental.HOLY, HOLY_BONUS);
    
    ARMOR_ELEMENT_MAP.put(Elemental.FIRE, FIRE_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.ICE, ICE_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.EARTH, EARTH_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.WATER, WATER_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.ELECTRIC, ELECTRIC_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.POISON, POISON_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.UNDEAD, UNDEAD_RESISTANCE);
    ARMOR_ELEMENT_MAP.put(Elemental.HOLY, HOLY_RESISTANCE);
  }
  /**
   * Creates a new ItemAttribute object with the given label and value.
   * @param label The label for the attribute to be displayed.
   * @param value The value of the attribute to be displayed.
   */
  public ItemAttribute(String label, Object value) {
    this.label = label;
    this.value = String.valueOf(value);
  }

  public String getLabel() {
    return label;
  }

  public String getValue() {
    return value;
  }

}
