package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * The items view is used to show the current inventory of items that is in the player's possession.
 * @author Aaron Michael McNulty
 */
public class ItemsView extends DisplayView {
  
  private final InventoryManager MANAGER = InventoryManager.getInventoryManager();
  private ArrayList<InventoryListItem> listItems = new ArrayList<>();
  private int itemCount = 0,
              startingY = 35,
              spaceBetweenRows = 20;
  private final Sprite BORDER = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  
  private void createListItems(Map itemsMap) {
    listItems = new ArrayList<>();
    Set<?> keys = itemsMap.keySet();
    keys.forEach(key -> {
      listItems.add(new InventoryListItem((InventoryReference)itemsMap.get(key), startingY + listItems.size() * this.spaceBetweenRows));
    });
  }
  
  private void checkItemCount() {
    if (itemCount != MANAGER.getItemsByType(Item.EQUIPMENT).size()) {
      createListItems(MANAGER.getItemsByType(Item.EQUIPMENT));
      itemCount = MANAGER.getItemsByType(Item.EQUIPMENT).size();
    }
  }
  
  @Override
  public void update() {
    checkItemCount();
    for (InventoryListItem item: listItems) {
      item.update();
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    for (InventoryListItem item: listItems) {
      item.render(screen, gl);
    }
    screen.renderSprite(gl, 306, 23, BORDER, false);
  }
  
}
