package com.monkeystomp.spirelands.inventory;

/**
 * Junk items are non-equipment items that can be used in crafting or simply just to be bought and sold.
 * @author Aaron Michael McNulty
 */
public class JunkItem extends Item {
  
  public JunkItem(ItemBuilder builder) {
    super(builder.type(Item.JUNK));
  }

  @Override
  public int getId() {
    return super.getId();
  }
}
