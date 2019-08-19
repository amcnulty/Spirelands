package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleItem extends Item {

  public BattleItem(ItemBuilder builder) {
    super(builder.type(Item.BATTLE));
  }

}
