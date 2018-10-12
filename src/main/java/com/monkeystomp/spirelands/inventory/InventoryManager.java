package com.monkeystomp.spirelands.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Aaron Michael McNulty
 */
public class InventoryManager {
  
  private final HashMap<Integer, InventoryReference> ITEM_MAP = new HashMap<>();
  
  private static final InventoryManager INSTANCE = new InventoryManager();

  private InventoryManager() {}
  
  public static InventoryManager getInventoryManager() {
    return INSTANCE;
  }
  
  public void addToInventory(Item item) {
    if (ITEM_MAP.containsKey(item.getId())) ITEM_MAP.get(item.getId()).increaseAmount();
    else ITEM_MAP.put(item.getId(), new InventoryReference(item, 1));
  }
  
  public void removeFromInventory(Item item) {
    ITEM_MAP.get(item.getId()).decreaseAmount();
    if (ITEM_MAP.get(item.getId()).getAmount() == 0) ITEM_MAP.remove(item.getId());
  }
  
  public Map<Integer, InventoryReference> getItemsByType(int type) {
    return ITEM_MAP.entrySet().stream()
      .filter(map -> map.getValue().getItem().getType()== type)
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
  
  public int getAmountById(int id) {
    if (ITEM_MAP.containsKey(id)) return ITEM_MAP.get(id).getAmount();
    else if (ITEM_MAP.containsKey(id)) return ITEM_MAP.get(id).getAmount();
    else if (ITEM_MAP.containsKey(id)) return ITEM_MAP.get(id).getAmount();
    else if (ITEM_MAP.containsKey(id)) return ITEM_MAP.get(id).getAmount();
    return 0;
  }
}