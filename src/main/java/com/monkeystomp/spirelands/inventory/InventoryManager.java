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
  
  private final HashMap<Integer, InventoryReference> ITEM_MAP = new HashMap<>();
  
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
    if (ITEM_MAP.containsKey(item.getId())) ITEM_MAP.get(item.getId()).increaseAmount();
    else ITEM_MAP.put(item.getId(), new InventoryReference(item, 1));
  }
  /**
   * Removes an item from the inventory.
   * @param item The item to be removed from the inventory.
   */
  public void removeFromInventory(Item item) {
    ITEM_MAP.get(item.getId()).decreaseAmount();
    if (ITEM_MAP.get(item.getId()).getAmount() == 0) ITEM_MAP.remove(item.getId());
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
    return ITEM_MAP.entrySet().stream()
      .filter(map -> map.getValue().getItem().getType()== type)
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  /**
   * Gets the amount of an item in the current inventory.
   * @param id The id of the item to get the amount of.
   * @return The amount of the item of the given id.
   */
  public int getAmountById(int id) {
    if (ITEM_MAP.containsKey(id)) return ITEM_MAP.get(id).getAmount();
    return 0;
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
