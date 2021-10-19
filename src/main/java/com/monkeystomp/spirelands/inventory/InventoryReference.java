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
   * @param item The item to create the reference for.
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
  /**
   * Increase the amount by the given quantity.
   * @param quantity Amount to increase inventory by.
   */
  public void increaseByQuantity(int quantity) {
    amount += quantity;
  }
  /**
   * Decrease the amount by the given quantity.
   * @param quantity Amount to reduce inventory by.
   */
  public void decreaseByQuantity(int quantity) {
    amount -= quantity;
  }
  
  public int getAmount() {
    return amount;
  }
  
  public Item getItem() {
    return item;
  }
}
