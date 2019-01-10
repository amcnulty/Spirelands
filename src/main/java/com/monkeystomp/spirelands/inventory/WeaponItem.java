package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WeaponItem extends Item {

  public WeaponItem(ItemBuilder builder) {
    super(builder.type(Item.WEAPON));
  }
  
}
