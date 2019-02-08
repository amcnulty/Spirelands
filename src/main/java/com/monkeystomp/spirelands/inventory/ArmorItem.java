package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ArmorItem extends Item {
  
  private int physicalDefence,
              magicalDefence,
              speedPenalty;
  
  public ArmorItem(ItemBuilder builder) {
    super(builder.type(Item.ARMOR));
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

  public int getPhysicalDefence() {
    return physicalDefence;
  }

  public int getMagicalDefence() {
    return magicalDefence;
  }

  public int getSpeedPenalty() {
    return speedPenalty;
  }

}
