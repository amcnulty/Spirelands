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

  public void setStrengthUp(int level) {
    actions.add(() -> getCharacter().increaseStrength(level));
    attributes.add(new ItemAttribute(ItemAttribute.STRENGTH_UP, level));
    consumable = true;
  }
  
  public void setDefenseUp(int level) {
    actions.add(() -> getCharacter().increaseDefense(level));
    attributes.add(new ItemAttribute(ItemAttribute.DEFENSE_UP, level));
    consumable = true;
  }
  
  public void setIntellectUp(int level) {
    actions.add(() -> getCharacter().increaseIntellect(level));
    attributes.add(new ItemAttribute(ItemAttribute.INTELLECT_UP, level));
    consumable = true;
  }
  
  public void setSpiritUp(int level) {
    actions.add(() -> getCharacter().increaseSpirit(level));
    attributes.add(new ItemAttribute(ItemAttribute.SPIRIT_UP, level));
    consumable = true;
  }
  
  public void setSpeedUp(int level) {
    actions.add(() -> getCharacter().increaseSpeed(level));
    attributes.add(new ItemAttribute(ItemAttribute.SPEED_UP, level));
    consumable = true;
  }
  
  public void setLuckUp(int level) {
    actions.add(() -> getCharacter().increaseLuck(level));
    attributes.add(new ItemAttribute(ItemAttribute.LUCK_UP, level));
    consumable = true;
  }
  
  public void setLevelUp(int level) {
    actions.add(() -> getCharacter().increaseLevel(level));
    attributes.add(new ItemAttribute(ItemAttribute.LEVEL_UP, level));
    consumable = true;
  }
  /**
   * The general method for using an equipment item. If item is consumable it will be consumed. If it is has another purpose it will be used for that.
   */
  public void useItem() {
    if (consumable) consumeItem();
  }
 
  private void consumeItem() {
    for (ICallback action: actions) {
      action.execute();
    }
    INVENTORY_MANAGER.removeFromInventory(this);
  }
  
}
