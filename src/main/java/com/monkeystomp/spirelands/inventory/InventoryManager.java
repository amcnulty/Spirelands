package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataHydratable;
import com.monkeystomp.spirelands.gamedata.util.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Inventory Manager class is a singleton class that is responsible for performing CRUD operations on the inventory in the game.
 * @author Aaron Michael McNulty
 */
@SuppressWarnings("unchecked")
public class InventoryManager implements SaveDataHydratable {
  /**
   * Slice of save object for this manager. JSON Name - INVENTORY
   */
  private JSONObject inventory;
  
  private final JSONUtil jsonUtil = new JSONUtil();
  private int gold = 0;
  private int abilityPoints = 0;
  // A map of all items id to their inventory reference object.
  private final HashMap<Integer, InventoryReference> itemMap = new HashMap<>();
  // A map of all ids to battle moves in the inventory.
  private final HashMap<Integer, BattleMove> battleMoveMap = new HashMap<>();
  
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
   */
  @SuppressWarnings("unchecked")
  private void setInventoryData() {
    itemMap.clear();
    setGold(jsonUtil.getNestedInt(inventory, new String[]{JSONUtil.GOLD}));
    setAbilityPoints(jsonUtil.getInt(inventory, JSONUtil.ABILITY_POINTS));
    JSONArray refs = (JSONArray) inventory.get(JSONUtil.REFS);
    refs.forEach(ref -> {
      int id = jsonUtil.getNestedInt((JSONObject)ref, new String[]{JSONUtil.ID});
      int amount = jsonUtil.getNestedInt((JSONObject)ref, new String[]{JSONUtil.AMOUNT});
      setInventoryReference(Item.ITEM_MAP.get(id), amount);
    });
    JSONArray battleMoves = (JSONArray) inventory.get(JSONUtil.BATTLE_MOVES);
    battleMoves.forEach(move -> addBattleMove(BattleMove.MOVE_MAP.get(jsonUtil.getInt((JSONObject)move, JSONUtil.ID))));
  }
  /**
   * Adds an item to the inventory.
   * @param item The item to add to the inventory.
   */
  public void addToInventory(Item item) {
    if (itemMap.containsKey(item.getId())) itemMap.get(item.getId()).increaseAmount();
    else setInventoryReference(item, 1);
  }
  /**
   * Adds an item to inventory by the given quantity.
   * @param item Item to add to inventory.
   * @param quantity Amount of items to add.
   */
  public void addToInventory(Item item, int quantity) {
    if (itemMap.containsKey(item.getId())) itemMap.get(item.getId()).increaseByQuantity(quantity);
    else setInventoryReference(item, quantity);
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
   * Removes an item from the inventory by the given quantity.
   * @param item Item to be removed from inventory.
   * @param quantity Amount of items to remove.
   */
  public void removeFromInventory(Item item, int quantity) {
    itemMap.get(item.getId()).decreaseByQuantity(quantity);
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
   * Gets all the items matching the given array of types. This is returned as a map.
   * <p>
   * Example Usage:
   * </p>
   * <pre>
   * {@code
   *ArrayList<String> itemTypes = new ArrayList<>();
   * itemTypes.add(Item.EQUIPMENT);
   * itemTypes.add(Item.JUNK);
   * Map<Integer, InventoryReference> Items = manager.getItemsByMultipleTypes(itemTypes);
   * }
   * </pre>
   * @param types The types of items to fetch results for.
   * @return The map of items based on the given type.
   */
  public Map<Integer, InventoryReference> getItemsByMultipleTypes(ArrayList<String> types) {
    return itemMap.entrySet().stream()
      .filter(map -> types.contains(map.getValue().getItem().getType()))
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
   * Gets all the craftable items in the players inventory. This is returned as a map.
   * <p>
   * Example Usage:
   * </p>
   * <pre>
   * {@code
   * Map<Integer, InventoryReference> craftableItems = manager.getCraftableItems();
   * }
   * </pre>
   * @return The map of craftable items.
   */
  public Map<Integer, InventoryReference> getCraftableItems() {
    Map<Integer, InventoryReference> craftableItems = itemMap.entrySet().stream()
      .filter(map -> map.getValue().getItem().isCraftable())
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return craftableItems;
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
  /**
   * Add the given battle move to the inventory.
   * @param battleMove BattleMove object to add to the inventory.
   */
  public void addBattleMove(BattleMove battleMove) {
    battleMoveMap.put(battleMove.getId(), battleMove);
  }
  /**
   * Gets all the battle moves as a map of their ids.
   * @return The battleMoveMap object.
   */
  public HashMap<Integer, BattleMove> getBattleMoves() {
    return battleMoveMap;
  }
  /**
   * Get a list of BattleMove objects from the inventory based on the given type.
   * @param searchTerm BattleMove.type to filter by.
   * @param excludedAction BattleMove.action to exclude from type or action.
   * @return The list of BattleMove objects after they have been filtered.
   */
  public ArrayList<BattleMove> getBattleMovesByType(String searchTerm, String excludedAction) {
    return (ArrayList<BattleMove>)battleMoveMap.entrySet().stream()
      .filter(map -> map.getValue().getType().equals(searchTerm) && !map.getValue().getAction().equals(excludedAction))
      .map(entry -> entry.getValue())
      .collect(Collectors.toList());
  }
  /**
   * Get a list of BattleMove objects from the inventory based on the given action.
   * @param searchTerm BattleMove.action to filter by.
   * @return The list of BattleMove objects after they have been filtered.
   */
  public ArrayList<BattleMove> getBattleMovesByAction(String searchTerm) {
    return (ArrayList<BattleMove>)battleMoveMap.entrySet().stream()
      .filter(map -> map.getValue().getAction().equals(searchTerm))
      .map(entry -> entry.getValue())
      .collect(Collectors.toList());
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
  /**
   * Reduces gold from the supply of gold in the inventory.
   * @param amount The amount of gold to remove from inventory.
   */
  public void reduceGold(int amount) {
    gold -= amount;
  }
  /**
   * Use this for initially setting the ability points on game load.
   * @param abilityPoints 
   */
  public void setAbilityPoints(int abilityPoints) {
    this.abilityPoints = abilityPoints;
  }

  public int getAbilityPoints() {
    return abilityPoints;
  }
  /**
   * Adds ability points to the supply in the inventory.
   * @param amount The amount of ability points to add to the inventory.
   */
  public void addAbilityPoints(int amount) {
    abilityPoints += amount;
  }
  /**
   * Reduces the amount of ability points from the supply in the inventory.
   * @param amount The amount of ability points to remove from the inventory.
   */
  public void reduceAbilityPoints(int amount) {
    abilityPoints -= amount;
  }

  public HashMap<Integer, InventoryReference> getItemMap() {
    return itemMap;
  }

  @Override
  public void hydrate(JSONObject json) {
    inventory = (JSONObject) json.get(JSONUtil.INVENTORY);
    setInventoryData();
  }

  @Override
  public void populateSaveData(JSONObject saveObject) {
    saveObject.put(JSONUtil.INVENTORY, inventory);
    JSONObject inventoryToSave = (JSONObject) saveObject.get(JSONUtil.INVENTORY);
    // Gold
    inventoryToSave.put(JSONUtil.GOLD, getGold());
    // Ability Points
    inventoryToSave.put(JSONUtil.ABILITY_POINTS, getAbilityPoints());
    // Item References
    JSONArray refs = (JSONArray) inventoryToSave.get(JSONUtil.REFS);
    refs.clear();
    HashMap<Integer, InventoryReference> inventoryMap = getItemMap();
    inventoryMap.forEach((id, ref) -> {
      JSONObject item = new JSONObject();
      item.put(JSONUtil.AMOUNT, ref.getAmount());
      item.put(JSONUtil.ID, id);
      refs.add(item);
    });
    // Battle Moves
    JSONArray battleMoves = (JSONArray)inventoryToSave.get(JSONUtil.BATTLE_MOVES);
    battleMoves.clear();
    getBattleMoves().keySet().forEach(key -> {
      JSONObject battleMove = new JSONObject();
      battleMove.put(JSONUtil.ID, key);
      battleMoves.add(battleMove);
    });
  }
  
}
