package com.monkeystomp.spirelands.inventory;

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
  
  @Override
  public String getTypeAsString(int type) {
    if (weaponType != null) return weaponType;
    else return TYPE_MAP.get(getType());
  }
  
}
