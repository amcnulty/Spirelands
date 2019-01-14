package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WeaponItem extends Item {
  
  private int attackPower;

  public WeaponItem(ItemBuilder builder) {
    super(builder.type(Item.WEAPON));
  }
  
  public void setAttackPower(int power) {
    attributes.add(new ItemAttribute(ItemAttribute.ATTACK_POWER, power));
    this.attackPower = power;
  }
  
  public int getAttackPower() {
    return this.attackPower;
  }
  
}
