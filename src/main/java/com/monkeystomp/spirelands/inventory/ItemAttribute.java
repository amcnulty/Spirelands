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
