package com.monkeystomp.spirelands.inventory;

/**
 *
 * @author Aaron Michael McNulty
 */
public class InventoryReference {
  
  private int amount;
  private Item item;

  public InventoryReference(Item item, int amount) {
    this.item = item;
    this.amount = amount;
  }
  
  public void increaseAmount() {
    amount++;
  }
  
  public void decreaseAmount() {
    amount--;
  }
  
  public int getAmount() {
    return amount;
  }
  
  public Item getItem() {
    return item;
  }
}