package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.components.CharacterArmorDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.ArmorItem;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ArmorView extends DisplayView {
  
  private final InventoryManager manager = InventoryManager.getInventoryManager();
  private final ArrayList<ArrayList<InventoryListItem>> pages = new ArrayList<>();
  private final Pagination pagination = new Pagination(pageIndex -> currentPageIndex = pageIndex);
  private int itemCount = 0;
  private final int startingY = 35,
                    spaceBetweenRows = 16,
                    itemsPerPage = 8;
  private int currentPageIndex = 0;
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  private final ItemDetailCard itemDetailCard = new ItemDetailCard(card -> {
    showingArmorDetailCard = true;
    card.clearCard();
  });
  private final CharacterArmorDetailCard armorDetailCard = new CharacterArmorDetailCard(item -> showItemDetails(item), nextCharacter -> setCharacter(nextCharacter));
  private boolean showingArmorDetailCard = true;
  private HashMap<String, Consumer<ArmorItem>> equipCommandMap = new HashMap<>();
  
  public ArmorView() {
    equipCommandMap.put(ArmorItem.HELMET, item -> character.equipHelmet(item));
    equipCommandMap.put(ArmorItem.CHESTPLATE, item -> character.equipChestplate(item));
    equipCommandMap.put(ArmorItem.SHIELD, item -> character.equipShield(item));
    equipCommandMap.put(ArmorItem.BOOTS, item -> character.equipBoots(item));
  }
  
  private void showItemDetails(Item item) {
    itemDetailCard.setItem(item);
    showingArmorDetailCard = false;
  }
  
  private void handleEquipClick(ArmorItem armor) {
    showingArmorDetailCard = true;
    equipCommandMap.get(armor.getArmorType()).accept(armor);
    createListItems(manager.getItemsByType(Item.ARMOR));
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
        newPage.add(
        new InventoryListItem(
          refs.get(j),
          startingY + newPage.size() * spaceBetweenRows,
          "Equip",
          item -> {
            handleEquipClick((ArmorItem)item);
          },
          item -> showItemDetails(item))
        );
      }
      if (newPage.size() > 0) pages.add(newPage);
    }
    itemCount = manager.getItemsByType(Item.ARMOR).size();
    pagination.setListLength(itemCount);
    setCurrentPage();
  }
  
  private void setCurrentPage() {
    if (currentPageIndex >= pages.size() - 1) currentPageIndex = pages.size() - 1;
    pagination.highlightCurrentPage(currentPageIndex);
  }
  
  private void checkItemCount() {
    if (itemCount != manager.getItemsByType(Item.ARMOR).size()) {
      createListItems(manager.getItemsByType(Item.ARMOR));
    }
  }
  
  private void checkCharacter() {
    if (armorDetailCard.getCharacter() == null || character != armorDetailCard.getCharacter()) {
      armorDetailCard.setCharacter(character);
    }
  }
  
  @Override
  public void setCharacter(com.monkeystomp.spirelands.character.Character character) {
    super.setCharacter(character);
    armorDetailCard.setCharacter(character);
  }

  @Override
  public void exitingView() {
    armorDetailCard.closePopovers();
    showingArmorDetailCard = true;
    pagination.highlightCurrentPage(0);
    currentPageIndex = 0;
  }
  
  @Override
  public void update() {
    checkItemCount();
    checkCharacter();
    if (pages.size() > 0) {
      for (InventoryListItem item: pages.get(currentPageIndex)) {
        item.update();
      }
    }
    if (showingArmorDetailCard) armorDetailCard.update();
    else itemDetailCard.update();
    pagination.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 306, 23, border, false);
    if (pages.size() > 0) {
      for (InventoryListItem item: pages.get(currentPageIndex)) {
        item.render(screen, gl);
      }
    }
    if (showingArmorDetailCard) armorDetailCard.render(screen, gl);
    else itemDetailCard.render(screen, gl);
    pagination.render(screen, gl);
  }
  
  
}
