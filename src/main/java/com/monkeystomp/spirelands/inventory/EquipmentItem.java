package com.monkeystomp.spirelands.inventory;

import com.monkeystomp.spirelands.input.ICallback;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EquipmentItem extends Item {
  
  private final String  healthRestore = "Health Restore";
  private final HashMap<String, ICallback> actionMap = new HashMap<>();
  
  public EquipmentItem(ItemBuilder builder) {
    super(builder.type(EQUIPMENT));
  }
  
  public void setHealingPoints(int points) {
    actionMap.put(healthRestore, () -> {
      getCharacter().increaseHealth(points);
    });
  }
  
  @Override
  public void useItem() {
    Set<String> keys = actionMap.keySet();
    keys.forEach(key -> {
      actionMap.get(key).execute();
    });
    INVENTORY_MANAGER.removeFromInventory(this);
  }
  
}
