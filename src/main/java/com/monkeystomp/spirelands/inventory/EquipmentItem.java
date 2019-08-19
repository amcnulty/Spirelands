package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.character.Character;
import java.io.File;
import java.util.ArrayList;


/**
 *
 * @author Aaron Michael McNulty
 */
public class EquipmentItem extends Item {
  
  private final ArrayList<ICallback> actions = new ArrayList<>();
  private boolean consumable,
                  equippable = false;
                  
  private final SoundEffects sfx = new SoundEffects();
  private File useSound;
  
  public EquipmentItem(ItemBuilder builder) {
    super(builder.type(EQUIPMENT));
  }
  
  public void setUseItemSound(File soundEffect) {
    this.useSound = soundEffect;
  }
  
  public void setHealingPoints(int points) {
    actions.add(() -> getStatModel().increaseHealth(points));
    attributes.add(new ItemAttribute(ItemAttribute.HEALTH_RESTORE, points));
    consumable = true;
  }
  
  public void setManaRestorePoints(int points) {
    actions.add(() -> getStatModel().increaseMana(points));
    attributes.add(new ItemAttribute(ItemAttribute.MANA_RESTORE, points));
    consumable = true;
  }

  public void setStrengthUp(int level) {
    actions.add(() -> getStatModel().increaseStrength(level));
    attributes.add(new ItemAttribute(ItemAttribute.STRENGTH_UP, level));
    consumable = true;
  }
  
  public void setDefenseUp(int level) {
    actions.add(() -> getStatModel().increaseDefense(level));
    attributes.add(new ItemAttribute(ItemAttribute.DEFENSE_UP, level));
    consumable = true;
  }
  
  public void setIntellectUp(int level) {
    actions.add(() -> getStatModel().increaseIntellect(level));
    attributes.add(new ItemAttribute(ItemAttribute.INTELLECT_UP, level));
    consumable = true;
  }
  
  public void setSpiritUp(int level) {
    actions.add(() -> getStatModel().increaseSpirit(level));
    attributes.add(new ItemAttribute(ItemAttribute.SPIRIT_UP, level));
    consumable = true;
  }
  
  public void setSpeedUp(int level) {
    actions.add(() -> getStatModel().increaseSpeed(level));
    attributes.add(new ItemAttribute(ItemAttribute.SPEED_UP, level));
    consumable = true;
  }
  
  public void setLuckUp(int level) {
    actions.add(() -> getStatModel().increaseLuck(level));
    attributes.add(new ItemAttribute(ItemAttribute.LUCK_UP, level));
    consumable = true;
  }
  
  public void setLevelUp(int level) {
    actions.add(() -> ((Character)getStatModel()).increaseLevel(level));
    attributes.add(new ItemAttribute(ItemAttribute.LEVEL_UP, level));
    consumable = true;
  }
  /**
   * The general method for using an equipment item. If item is consumable it will be consumed. If it is has another purpose it will be used for that.
   */
  public void useItem() {
    if (consumable && INVENTORY_MANAGER.getInventoryReferenceById(getId()) != null) consumeItem();
  }
 
  private void consumeItem() {
    if (useSound != null) sfx.playSoundEffect(useSound);
    for (ICallback action: actions) {
      action.execute();
    }
    INVENTORY_MANAGER.removeFromInventory(this);
  }

  public boolean isEquippable() {
    return equippable;
  }

  public void setEquippable(boolean equippable) {
    this.equippable = equippable;
  }
  
  @Override
  public int getId() {
    return super.getId();
  }
  
}
