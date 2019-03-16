package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpecialItem extends Item {
  
  public SpecialItem(ItemBuilder builder) {
    super(builder.type(Item.SPECIAL));
  }
  
  @Override
  public int getId() {
    return super.getId();
  }
  
}
