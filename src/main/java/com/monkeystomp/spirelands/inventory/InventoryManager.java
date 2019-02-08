package com.monkeystomp.spirelands.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Inventory Manager class is a singleton class that is responsible for performing CRUD operations on the inventory in the game.
 * @author Aaron Michael McNulty
 */
public class InventoryManager {
  
  private int gold = 0;
  
  private final HashMap<Integer, InventoryReference> itemMap = new HashMap<>();
  
  private static final InventoryManager INSTANCE = new InventoryManager();

  private InventoryManager() {}
  /**
   * Gets the singleton instance of the InventoryManager class.
   * @return The singleton instance of this class.
   */
  public static InventoryManager getInventoryManager() {
    return INSTANCE;
  }
  /**
   * Adds an item to the inventory.
   * @param item The item to add to the inventory.
   */
  public void addToInventory(Item item) {
    if (itemMap.containsKey(item.getId())) itemMap.get(item.getId()).increaseAmount();
    else itemMap.put(item.getId(), new InventoryReference(item, 1));
  }
  /**
   * Removes an item from the inventory.
   * @param item The item to be removed from the inventory.
   */
  public void removeFromInventory(Item item) {
    itemMap.get(item.getId()).decreaseAmount();
    if (itemMap.get(item.getId()).getAmount() == 0) itemMap.remove(item.getId());
  }
  /**
   * Gets all the items by a type. This is returned as a map.
   * <p>
   * Example Usage:
   * </p>
   * <pre>
   * {@code
   * Map<Integer, InventoryReference> items = manager.getItemsByType(Item.ARMOR);
   * }
   * </pre>
   * @param type The type of item to fetch results for.
   * @return The map of items based on the given type.
   */
  public Map<Integer, InventoryReference> getItemsByType(int type) {
    return itemMap.entrySet().stream()
      .filter(map -> map.getValue().getItem().getType()== type)
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  /**
   * Gets the amount of an item in the current inventory.
   * @param id The id of the item to get the amount of.
   * @return The amount of the item of the given id.
   */
  public int getAmountById(int id) {
    if (itemMap.containsKey(id)) return itemMap.get(id).getAmount();
    return 0;
  }
  /**
   * Gets the inventory reference for the given item id.
   * @param id Item id to get reference for.
   * @return The InventoryReference object associated with the given item id.
   */
  public InventoryReference getInventoryReferenceById(int id) {
    return itemMap.get(id);
  }
  
  public void setGold(int amount) {
    this.gold = amount;
  }
  
  public int getGold() {
    return gold;
  }
  /**
   * Adds gold to the supply of gold in the inventory.
   * @param amount The amount of gold to add to the inventory.
   */
  public void addGold(int amount) {
    gold += amount;
  }
}
