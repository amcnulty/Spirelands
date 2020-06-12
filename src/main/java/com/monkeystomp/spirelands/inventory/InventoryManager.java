package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.battle.move.BattleMove;
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
public class InventoryManager {
  
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
   * @param inventory The JSON data containing the inventory.
   */
  @SuppressWarnings("unchecked")
  public void setInventoryData(JSONObject inventory) {
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
  
}
