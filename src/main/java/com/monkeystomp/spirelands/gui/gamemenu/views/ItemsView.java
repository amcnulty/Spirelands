package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.EquipmentItem;
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
  
  private final InventoryManager manager = InventoryManager.getInventoryManager();
  private ArrayList<InventoryListItem> listItems = new ArrayList<>();
  private int itemCount = 0;
  private final int startingY = 35,
                    spaceBetweenRows = 16;
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  private final ItemDetailCard itemDetailCard = new ItemDetailCard(card -> card.clearCard());
  
  private void showItemDetails(Item item) {
    itemDetailCard.setItem(item);
  }
  
  private void createListItems(Map<Integer, InventoryReference> itemsMap) {
    listItems = new ArrayList<>();
    Set<Integer> keys = itemsMap.keySet();
    keys.forEach(key -> {
      listItems.add(
        new InventoryListItem(
          itemsMap.get(key),
          startingY + listItems.size() * this.spaceBetweenRows,
          "Use",
          item -> {
            EquipmentItem thisItem = (EquipmentItem) item;
            thisItem.useItem();
          },
          item -> showItemDetails(item))
      );
    });
  }
  
  private void checkItemCount() {
    if (itemCount != manager.getItemsByType(Item.EQUIPMENT).size()) {
      createListItems(manager.getItemsByType(Item.EQUIPMENT));
      itemCount = manager.getItemsByType(Item.EQUIPMENT).size();
    }
  }
  
  @Override
  public void exitingView() {
    itemDetailCard.clearCard();
  }
  
  @Override
  public void update() {
    checkItemCount();
    for (InventoryListItem item: listItems) {
      item.update();
    }
    itemDetailCard.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    for (InventoryListItem item: listItems) {
      item.render(screen, gl);
    }
    screen.renderSprite(gl, 306, 23, border, false);
    itemDetailCard.render(screen, gl);
  }
  
}
