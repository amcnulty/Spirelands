package com.monkeystomp.spirelands.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Inventory Manager class is a singleton class that is responsible for performing CRUD operations on the inventory in the game.
 * @author Aaron Michael McNulty
 */
public class InventoryManager {
  
  private int gold = 0;
  // A map of all items id to their inventory reference object.
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
   * Sets up all inventory data for the current game.
   * @param inventory The JSON data containing the inventory.
   */
  @SuppressWarnings("unchecked")
  public void setInventoryData(JSONObject inventory) {
    itemMap.clear();
    setGold(Math.toIntExact((long)(((JSONObject)inventory).get("gold"))));
    JSONArray refs = (JSONArray) inventory.get("refs");
    refs.forEach(ref -> {
      int id = Math.toIntExact((long)((JSONObject)ref).get("id"));
      int amount = Math.toIntExact((long)((JSONObject)ref).get("amount"));
      setInventoryReference(Item.ITEM_MAP.get(id), amount);
    });
  }
  /**
   * Adds an item to the inventory.
   * @param item The item to add to the inventory.
   */
  public void addToInventory(Item item) {
    if (itemMap.containsKey(item.getId())) itemMap.get(item.getId()).increaseAmount();
//    else itemMap.put(item.getId(), new InventoryReference(item, 1));
    else setInventoryReference(item, 1);
  }
  /**
   * <p>Adds an inventory reference entry for the given item set at the given amount.</p>
   * <p>Use this method for inventory creation on start up</p>
   * @param item Item record to add to inventory.
   * @param amount The amount of this item to add to inventory.
   */
  public void setInventoryReference(Item item, int amount) {
    itemMap.put(item.getId(), new InventoryReference(item, amount));
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
  public Map<Integer, InventoryReference> getItemsByType(String type) {
    return itemMap.entrySet().stream()
      .filter(map -> map.getValue().getItem().getType().equals(type))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  /**
   * Gets all the weapons of the given type. This is returned as a map.
   * <p>
   * Example Usage:
   * </p>
   * <pre>
   * {@code
   * Map<Integer, InventoryReference> swords = manager.getWeaponsByType(WeaponItem.SWORD);
   * }
   * </pre>
   * @param type The type of weapons to get.
   * @return The map of the weapons based on the given type.
   */
  public Map<Integer, InventoryReference> getWeaponsByType(String type) {
    Map<Integer, InventoryReference> weapons = itemMap.entrySet().stream()
      .filter(map -> map.getValue().getItem().getType().equals(Item.WEAPON))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return weapons.entrySet().stream()
      .filter(map -> ((WeaponItem) map.getValue().getItem()).getWeaponType().equals(type))
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

  public HashMap<Integer, InventoryReference> getItemMap() {
    return itemMap;
  }
  
}
