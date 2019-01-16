package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gui.gamemenu.components.CharacterWeaponDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
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
  private ArrayList<InventoryListItem> listItems = new ArrayList<>();
  private int itemCount = 0;
  private final int startingY = 35,
                    spaceBetweenRows = 16;
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
    character.removeEquippedWeapon();
    character.setEquippedWeapon(weapon);
    weapon.removeFromInventory();
  }
  
  private void createListItems(Map<Integer, InventoryReference> itemsMap) {
    listItems = new ArrayList<>();
    Set<Integer> keys = itemsMap.keySet();
    keys.forEach(key -> {
      listItems.add(
        new InventoryListItem(
          itemsMap.get(key),
          startingY + listItems.size() * this.spaceBetweenRows,
          "Equip",
          item -> {
            handleEquipClick((WeaponItem)item);
          },
          item -> showItemDetails(item))
      );
    });
  }
  
  private void checkItemCount() {
    if (itemCount != manager.getItemsByType(Item.WEAPON).size()) {
      createListItems(manager.getItemsByType(Item.WEAPON));
      itemCount = manager.getItemsByType(Item.WEAPON).size();
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
  }
  
  @Override
  public void update() {
    checkItemCount();
    checkCharacter();
    for (InventoryListItem item: listItems) {
      item.update();
    }
    if (showingWeaponDetailCard) weaponDetailCard.update();
    else itemDetailCard.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    for (InventoryListItem item: listItems) {
      item.render(screen, gl);
    }
    screen.renderSprite(gl, 306, 23, border, false);
    if (showingWeaponDetailCard) weaponDetailCard.render(screen, gl);
    else itemDetailCard.render(screen, gl);
  }

}
