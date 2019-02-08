package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemOnCharacterButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
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
  private int itemCount = 0,
              selectedItemAmount = 0;
  private final int startingY = 35,
                    spaceBetweenRows = 16;
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  private final ItemDetailCard itemDetailCard = new ItemDetailCard(card -> card.clearCard());
  private boolean selectingCharacter = false;
  private final FontInfo  characterSelectorHeader = GameFonts.getlightText_bold_23(),
                          selectedItemAmountFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final PrimaryButton returnToListButton = new PrimaryButton(
          "Back To List",
          280,
          TOP + 27,
          39,
          11,
          () -> selectingCharacter = false
  );
  private EquipmentItem selectedItem;
  private InventoryReference selectedRef;
  private final ArrayList<ItemOnCharacterButton> itemOnCharacterButtons = new ArrayList<>();
  
  public ItemsView() {
    characterSelectorHeader.setText("Select Character To Use Item");
    characterSelectorHeader.setX(215);
    characterSelectorHeader.setY(TOP + 10);
    characterSelectorHeader.centerText();
  }
  
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
            handleUseItem(thisItem);
          },
          item -> showItemDetails(item))
      );
    });
  }
  
  private void handleUseItem(EquipmentItem item) {
    selectingCharacter = true;
    selectedItem = item;
    selectedRef = InventoryManager.getInventoryManager().getInventoryReferenceById(item.getId());
    selectedItemAmount = selectedRef.getAmount();
    setSelectedFont();
    setItemOnCharacterButtons();
    showItemDetails(selectedItem);
  }
  
  private void setSelectedFont() {
    selectedItemAmountFont.setText(": " + String.valueOf(selectedRef.getAmount()));
    selectedItemAmountFont.setX(228);
    selectedItemAmountFont.setY(TOP + 27);
    selectedItemAmountFont.rightAlignText();
  }
  
  private void setItemOnCharacterButtons() {
    itemOnCharacterButtons.clear();
    CharacterManager.getCharacterManager().getPartyMembers().forEach(partyMember -> {
      itemOnCharacterButtons.add(new ItemOnCharacterButton(
        "",
        215,
        80 + itemOnCharacterButtons.size() * 32,
        32,
        32,
        partyMember,
        member -> handleCharacterButtonPress(member)
      ));
    });
  }
  
  private void handleCharacterButtonPress(Character targetCharacter) {
    selectedItem.setCharacter(targetCharacter);
    selectedItem.useItem();
    if (selectedRef.getAmount() == 0) selectingCharacter = false;
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
    selectingCharacter = false;
  }
  
  @Override
  public void update() {
    checkItemCount();
    if (!selectingCharacter) {
      for (InventoryListItem item: listItems) {
        item.update();
      }
    }
    itemDetailCard.update();
    if (selectingCharacter) {
      if (selectedItemAmount != selectedRef.getAmount()) setSelectedFont();
      returnToListButton.update();
      for (ItemOnCharacterButton button: itemOnCharacterButtons) {
        button.update();
      }
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (!selectingCharacter) {
      for (InventoryListItem item: listItems) {
        item.render(screen, gl);
      }
    }
    screen.renderSprite(gl, 306, 23, border, false);
    itemDetailCard.render(screen, gl);
    if (selectingCharacter) {
      screen.renderFonts(characterSelectorHeader);
      screen.renderSprite(gl, 200, TOP + 20, selectedItem.getThumbnail(), false);
      screen.renderFonts(selectedItemAmountFont);
      returnToListButton.render(screen, gl);
      for (ItemOnCharacterButton button: itemOnCharacterButtons) {
        button.render(screen, gl);
      }
    }
  }
  
}
