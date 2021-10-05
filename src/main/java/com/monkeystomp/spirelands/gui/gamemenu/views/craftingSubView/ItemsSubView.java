package com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
import com.monkeystomp.spirelands.gui.gamemenu.components.UsableInventoryListItem;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.EquipmentItem;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * A sub view of the crafting view in the game menu used to show the list of crafting items.
 * @author Aaron Michael McNulty
 */
public class ItemsSubView {

  private final InventoryManager manager = InventoryManager.getInventoryManager();
  private Map<Integer, InventoryReference> Items = manager.getCraftableItems();
  private final ArrayList<ArrayList<InventoryListItem>> pages = new ArrayList<>();
  private final Pagination pagination = new Pagination(8, 214, 169, pageIndex -> currentPageIndex = pageIndex);
  private int itemCount = 0;
  private final int startingY = 35,
                    listItemX = 140,
                    spaceBetweenRows = 16,
                    itemsPerPage = 8;
  private int currentPageIndex = 0;
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  private final ItemDetailCard itemDetailCard = new ItemDetailCard(card -> card.clearCard());

    
  private void showItemDetails(Item item) {
    itemDetailCard.setItem(item);
  }

  private void createListItems(Map<Integer, InventoryReference> itemsMap) {
    pages.clear();
    Set<Integer> keys = itemsMap.keySet();
    ArrayList<InventoryReference> refs = new ArrayList<>();
    keys.forEach(key -> {
      refs.add(itemsMap.get(key));
    });
    for (int i = 0; i < refs.size() / itemsPerPage + 1; i++) {
      ArrayList<InventoryListItem> newPage = new ArrayList<>();
      for (int j = i * itemsPerPage; j < itemsPerPage + (i * itemsPerPage); j++) {
        if (refs.size() - 1 < j) break;
        newPage.add(new UsableInventoryListItem(
          refs.get(j),
          listItemX,
          startingY + newPage.size() * spaceBetweenRows,
          "Add",
          item -> {
            EquipmentItem thisItem = (EquipmentItem) item;
            handleAddItem(thisItem);
          },
          item -> showItemDetails(item)
        ));
      }
      if (newPage.size() > 0) pages.add(newPage);
    }
    itemCount = manager.getCraftableItems().size();
    pagination.setListLength(itemCount);
    setCurrentPage();
  }
  
  private void setCurrentPage() {
    if (currentPageIndex >= pages.size() - 1) {
      currentPageIndex = pages.size() - 1;
      if (currentPageIndex < 0) currentPageIndex = 0;
    }
    pagination.highlightCurrentPage(currentPageIndex);
  }
  
  private void handleAddItem(Item item) {
    
  }
    
  private void checkItemCount() {
    if (itemCount != Items.size()) {
      createListItems(Items);
      itemCount = Items.size();
    }
  }
  
  public void exitingView() {
    itemDetailCard.clearCard();
    pagination.highlightCurrentPage(0);
    currentPageIndex = 0;
  }

  public void update() {
    checkItemCount();
    if (pages.size() > 0) {
      for (InventoryListItem item: pages.get(currentPageIndex)) {
        item.update();
      }
    }
    itemDetailCard.update();
    pagination.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    if (pages.size() > 0) {
      for (InventoryListItem item: pages.get(currentPageIndex)) {
        item.render(screen, gl);
      }
    }
    screen.renderSprite(gl, 306, 23, border, false);
    itemDetailCard.render(screen, gl);
    pagination.render(screen, gl);
  }

}
