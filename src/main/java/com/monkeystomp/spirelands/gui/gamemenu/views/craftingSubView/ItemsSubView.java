package com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.CraftingListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A sub view of the crafting view in the game menu used to show the list of crafting items.
 * @author Aaron Michael McNulty
 */
public class ItemsSubView {

  private final FontInfo header = GameFonts.getGAME_MENU_HEADLINE_THIN();
  private final Consumer<Item> handleAddItem;
  private final ICallback exitSubView;
  private final InventoryManager manager = InventoryManager.getInventoryManager();
  private final Map<Integer, InventoryReference> items = manager.getCraftableItems();
  private final ArrayList<ArrayList<InventoryListItem>> pages = new ArrayList<>();
  private final Pagination pagination = new Pagination(8, 214, 169, pageIndex -> currentPageIndex = pageIndex);
  private int itemCount = 0;
  private final int headerX = 135,
                    headerY = 34,
                    backButtonX = 273,
                    backButtonY = 34,
                    startingY = 51,
                    listItemX = 140,
                    spaceBetweenRows = 16,
                    itemsPerPage = 7;
  private int currentPageIndex = 0;
  private final PrimaryButton backButton = new PrimaryButton("Back To Crafting", backButtonX, backButtonY, 50, 11, () -> handleBackButtonClick());
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  private final ItemDetailCard itemDetailCard = new ItemDetailCard(card -> card.clearCard());

  public ItemsSubView(Consumer<Item> handleAddItem, ICallback exitSubView) {
    this.handleAddItem = handleAddItem;
    this.exitSubView = exitSubView;
    header.setText("Craftable Items");
    header.setX(headerX);
    header.setY(headerY);
  }
  
  private void handleBackButtonClick() {
    exitSubView.execute();
  }
    
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
        newPage.add(new CraftingListItem(
          refs.get(j),
          listItemX,
          startingY + newPage.size() * spaceBetweenRows,
          "Add",
          item -> {
            this.handleAddItem.accept(item);
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
    
  private void checkItemCount() {
    if (itemCount != items.size()) {
      createListItems(items);
      itemCount = items.size();
    }
  }
  
  public void enteringView() {
    checkItemCount();
    for (ArrayList<InventoryListItem> page: pages) {
      for (InventoryListItem craftingItem: page) {
        ((CraftingListItem)craftingItem).refresh();
      }
    }
  }
  
  public void exitingView() {
    itemDetailCard.clearCard();
    pagination.highlightCurrentPage(0);
    currentPageIndex = 0;
  }

  public void update() {
    backButton.update();
    if (pages.size() > 0) {
      for (InventoryListItem item: pages.get(currentPageIndex)) {
        item.update();
      }
    }
    itemDetailCard.update();
    pagination.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(header);
    backButton.render(screen, gl);
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
