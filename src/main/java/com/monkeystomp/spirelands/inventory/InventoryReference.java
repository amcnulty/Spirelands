package com.monkeystomp.spirelands.inventory;

/**
 * Inventory Reference class holds information about a specific item.
 * @author Aaron Michael McNulty
 */
public class InventoryReference {
  
  private int amount;
  private Item item;
  /**
   * Creates an InventoryReference object for the given item and amount.
   * @param item The item to creat the reference for.
   * @param amount The amount of the item to set.
   */
  public InventoryReference(Item item, int amount) {
    this.item = item;
    this.amount = amount;
  }
  /**
   * Increase the amount of this item by one.
   */
  public void increaseAmount() {
    amount++;
  }
  /**
   * Decrease the amount of this item by one.
   */
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
