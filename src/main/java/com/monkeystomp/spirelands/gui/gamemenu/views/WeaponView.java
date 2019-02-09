package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gui.gamemenu.components.CharacterWeaponDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.inventory.WeaponItem;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WeaponView extends DisplayView {
  
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
    showingWeaponDetailCard = true;
    card.clearCard();
  });
  private final CharacterWeaponDetailCard weaponDetailCard = new CharacterWeaponDetailCard(item -> showItemDetails(item), nextCharacter -> setCharacter(nextCharacter));
  private boolean showingWeaponDetailCard = true;
  
  private void showItemDetails(Item item) {
    itemDetailCard.setItem(item);
    showingWeaponDetailCard = false;
  }
  
  private void handleEquipClick(WeaponItem weapon) {
    showingWeaponDetailCard = true;
    character.equipWeapon(weapon);
    createListItems(manager.getItemsByType(Item.WEAPON));
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
            handleEquipClick((WeaponItem)item);
          },
          item -> showItemDetails(item))
        );
      }
      if (newPage.size() > 0) pages.add(newPage);
    }
    itemCount = manager.getItemsByType(Item.WEAPON).size();
    pagination.setListLength(itemCount);
    setCurrentPage();
  }
  
  private void setCurrentPage() {
    if (currentPageIndex >= pages.size() - 1) currentPageIndex = pages.size() - 1;
    pagination.highlightCurrentPage(currentPageIndex);
  }
  
  private void checkItemCount() {
    if (itemCount != manager.getItemsByType(Item.WEAPON).size()) {
      createListItems(manager.getItemsByType(Item.WEAPON));
    }
  }
  
  private void checkCharacter() {
    if (weaponDetailCard.getCharacter() == null || character != weaponDetailCard.getCharacter()) {
      weaponDetailCard.setCharacter(character);
    }
  }
  
  @Override
  public void setCharacter(Character character) {
    super.setCharacter(character);
    weaponDetailCard.setCharacter(character);
  }

  @Override
  public void exitingView() {
    weaponDetailCard.closePopover();
    showingWeaponDetailCard = true;
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
    if (showingWeaponDetailCard) weaponDetailCard.update();
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
    if (showingWeaponDetailCard) weaponDetailCard.render(screen, gl);
    else itemDetailCard.render(screen, gl);
    pagination.render(screen, gl);
  }

}
