package com.monkeystomp.spirelands.inventory;

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
  private final String  label,
                        value;
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
