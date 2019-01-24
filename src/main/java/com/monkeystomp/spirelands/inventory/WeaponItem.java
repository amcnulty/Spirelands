package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WeaponItem extends Item {
  
  private int attackPower,
              magicPower;

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
  
  public int getAttackPower() {
    return attackPower;
  }

  public int getMagicPower() {
    return magicPower;
  }
  
}
