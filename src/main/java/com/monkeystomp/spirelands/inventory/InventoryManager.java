package com.monkeystomp.spirelands.inventory;

import java.util.ArrayList;
import java.util.HashMap;

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
  
  public ArrayList<InventoryReference> getWeapons() {
    ArrayList<InventoryReference> weapons = new ArrayList<>();
    for (int i = 0; i < ITEM_MAP.size(); i++) {
      if (ITEM_MAP.get(i).getItem().getType() == Item.WEAPON) weapons.add(ITEM_MAP.get(i));
    }
    return weapons;
  }
  
  public ArrayList<InventoryReference> getArmor() {
    ArrayList<InventoryReference> armor = new ArrayList<>();
    for (int i = 0; i < ITEM_MAP.size(); i++) {
      if (ITEM_MAP.get(i).getItem().getType() == Item.ARMOR) armor.add(ITEM_MAP.get(i));
    }
    return armor;
  }
  
  public ArrayList<InventoryReference> getEquipment() {
    ArrayList<InventoryReference> equipment = new ArrayList<>();
    for (int i = 0; i < ITEM_MAP.size(); i++) {
      if (ITEM_MAP.get(i).getItem().getType() == Item.EQUIPMENT) equipment.add(ITEM_MAP.get(i));
    }
    return equipment;
  }
  
  public ArrayList<InventoryReference> getSpecial() {
    ArrayList<InventoryReference> special = new ArrayList<>();
    for (int i = 0; i < ITEM_MAP.size(); i++) {
      if (ITEM_MAP.get(i).getItem().getType() == Item.SPECIAL) special.add(ITEM_MAP.get(i));
    }
    return special;
  }
  
  public int getAmountById(Item item) {
    if (ITEM_MAP.containsKey(item.getId())) return ITEM_MAP.get(item.getId()).getAmount();
    else if (ITEM_MAP.containsKey(item.getId())) return ITEM_MAP.get(item.getId()).getAmount();
    else if (ITEM_MAP.containsKey(item.getId())) return ITEM_MAP.get(item.getId()).getAmount();
    else if (ITEM_MAP.containsKey(item.getId())) return ITEM_MAP.get(item.getId()).getAmount();
    return 0;
  }
}