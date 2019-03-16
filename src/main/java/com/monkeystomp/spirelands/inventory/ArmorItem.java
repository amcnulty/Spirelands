package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.audio.SoundEffects;
import java.io.File;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ArmorItem extends Item {
  /**
   * Helmet type armor.
   */
  public static final String HELMET = "Helmet";
  /**
   * 'Chestplate' type armor.
   */
  public static final String CHESTPLATE = "Chestplate";
  /**
   * Shield type armor.
   */
  public static final String SHIELD = "Shield";
  /**
   * Boot type armor.
   */
  public static final String BOOTS = "Boots";
  
  private final SoundEffects sfx = new SoundEffects();
  private final File  equipSound = SoundEffects.EQUIP_ARMOR,
                      unequipSound = SoundEffects.UNEQUIP_ARMOR;
  private int physicalDefence,
              magicalDefence,
              speedPenalty;
  private String armorType;
  
  public ArmorItem(ItemBuilder builder) {
    super(builder.type(Item.ARMOR));
  }
  /**
   * Plays the equip sound for this item.
   */
  public void playEquipSound() {
    if (equipSound != null) {
      sfx.playSoundEffect(equipSound);
    }
  }
  /**
   * Plays the unequip sound for this item.
   */
  public void playUnequipSound() {
    if (unequipSound != null) {
      sfx.playSoundEffect(unequipSound);
    }
  }
  
  public void setPhysicalDefense(int power) {
    attributes.add(new ItemAttribute(ItemAttribute.PHYSICAL_DEFENSE, power));
    this.physicalDefence = power;
  }
  
  public void setMagicalDefense(int power) {
    attributes.add(new ItemAttribute(ItemAttribute.MAGIC_DEFENSE, power));
    this.magicalDefence = power;
  }
  
  public void setSpeedPenalty(int power) {
    attributes.add(new ItemAttribute(ItemAttribute.SPEED_PENALTY, power));
    this.speedPenalty = power;
  }

  public int getPhysicalDefense() {
    return physicalDefence;
  }

  public int getMagicalDefense() {
    return magicalDefence;
  }

  public int getSpeedPenalty() {
    return speedPenalty;
  }

  public String getArmorType() {
    return armorType;
  }

  public void setArmorType(String armorType) {
    this.armorType = armorType;
  }
  
  @Override
  public int getId() {
    return super.getId();
  }

}
