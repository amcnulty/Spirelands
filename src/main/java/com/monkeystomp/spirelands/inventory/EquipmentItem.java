package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.input.ICallback;
import java.util.ArrayList;


/**
 *
 * @author Aaron Michael McNulty
 */
public class EquipmentItem extends Item {
  
  private final ArrayList<ICallback> actions = new ArrayList<>();
  
  public EquipmentItem(ItemBuilder builder) {
    super(builder.type(EQUIPMENT));
  }
  
  public void setHealingPoints(int points) {
    actions.add(() -> getCharacter().increaseHealth(points));
    attributes.add(new ItemAttribute(ItemAttribute.HEALTH_RESTORE, points));
  }
  
  public void setManaRestorePoints(int points) {
    actions.add(() -> getCharacter().increaseMana(points));
    attributes.add(new ItemAttribute(ItemAttribute.MANA_RESTORE, points));
  }

  @Override
  public ArrayList<ItemAttribute> getAttributes() {
    return attributes;
  }
  
  @Override
  public void useItem() {
    for (ICallback action: actions) {
      action.execute();
    }
    INVENTORY_MANAGER.removeFromInventory(this);
  }
  
}
