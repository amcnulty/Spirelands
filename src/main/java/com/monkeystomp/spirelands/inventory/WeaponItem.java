package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WeaponItem extends Item {
  
  /**
   * Sword weapon type
   */
  public static final String SWORD = "Sword";
  /**
   * Bow weapon type
   */
  public static final String BOW = "Bow";
  /**
   * Dagger weapon type
   */
  public static final String DAGGER = "Dagger";
  /**
   * Staff weapon type
   */
  public static final String STAFF = "Staff";
  /**
   * Battle Axe weapon type
   */
  public static final String BATTLE_AXE = "Battle Axe";
  /**
   * Wand weapon type
   */
  public static final String WAND = "Wand";
  /**
   * Blunt type weapon
   */
  public static final String BLUNT = "Blunt";
  
  private int attackPower,
              magicPower;
  private String weaponType;
  private final ArrayList<ElementalEffect> elementalEffects = new ArrayList<>();
  private static final HashMap<String, File> EQUIP_SOUND_MAP = new HashMap<>();
  static {
    EQUIP_SOUND_MAP.put(SWORD, SoundEffects.EQUIP_SWORD);
    EQUIP_SOUND_MAP.put(BOW, SoundEffects.EQUIP_BOW);
    EQUIP_SOUND_MAP.put(DAGGER, SoundEffects.EQUIP_DAGGER);
    EQUIP_SOUND_MAP.put(STAFF, SoundEffects.EQUIP_STAFF);
    EQUIP_SOUND_MAP.put(BATTLE_AXE, SoundEffects.EQUIP_BATTLE_AXE);
    EQUIP_SOUND_MAP.put(WAND, SoundEffects.EQUIP_ARMOR);
    EQUIP_SOUND_MAP.put(BLUNT, SoundEffects.EQUIP_BLUNT);
  }

  public WeaponItem(ItemBuilder builder) {
    super(builder.type(Item.WEAPON));
  }
  
  public void setAttackPower(int power) {
    attributes.add(new ItemAttribute(ItemAttribute.ATTACK_POWER, power));
    attackPower = power;
  }
  
  public void setMagicAttackPower(int power) {
    attributes.add(new ItemAttribute(ItemAttribute.MAGIC_POWER, power));
    magicPower = power;
  }
  
  public void elementalEffect(ElementalEffect effect) {
    elementalEffects.add(effect);
    attributes.add(new ItemAttribute(ItemAttribute.WEAPON_ELEMENT_MAP.get(effect.getElement()), effect.getPercentage() - 100));
  }
  
  public void setWeaponType(String type) {
    this.weaponType = type;
  }
  
  public String getWeaponType() {
    return weaponType;
  }
  
  public int getAttackPower() {
    return attackPower;
  }

  public int getMagicPower() {
    return magicPower;
  }

  public ArrayList<ElementalEffect> getElementalEffects() {
    return elementalEffects;
  }
  
  public File getEquipSound() {
    return EQUIP_SOUND_MAP.get(weaponType);
  }
  
  @Override
  public int getId() {
    return super.getId();
  }
  
}
