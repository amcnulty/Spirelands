package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.input.ICallback;
import java.util.ArrayList;


/**
 *
 * @author Aaron Michael McNulty
 */
public class EquipmentItem extends Item {
  
  private final ArrayList<ICallback> actions = new ArrayList<>();
  private boolean consumable;
  
  public EquipmentItem(ItemBuilder builder) {
    super(builder.type(EQUIPMENT));
  }
  
  public void setHealingPoints(int points) {
    actions.add(() -> getCharacter().increaseHealth(points));
    attributes.add(new ItemAttribute(ItemAttribute.HEALTH_RESTORE, points));
    consumable = true;
  }
  
  public void setManaRestorePoints(int points) {
    actions.add(() -> getCharacter().increaseMana(points));
    attributes.add(new ItemAttribute(ItemAttribute.MANA_RESTORE, points));
    consumable = true;
  }

  @Override
  public ArrayList<ItemAttribute> getAttributes() {
    return attributes;
  }
  /**
   * The general method for using an equipment item. If item is consumable it will be consumed. If it is has another purpose it will be used for that.
   */
  public void useItem() {
    if (consumable) consumeItem();
  }
 
  public void consumeItem() {
    for (ICallback action: actions) {
      action.execute();
    }
    INVENTORY_MANAGER.removeFromInventory(this);
  }
  
}
