package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.InventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemDetailCard;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemOnCharacterButton;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
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
  private final ArrayList<ArrayList<InventoryListItem>> pages = new ArrayList<>();
  private final Pagination pagination = new Pagination(pageIndex -> currentPageIndex = pageIndex);
  private int itemCount = 0,
              selectedItemAmount = 0;
  private final int startingY = 35,
                    spaceBetweenRows = 16,
                    itemsPerPage = 8;
  private int currentPageIndex = 0;
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  private final ItemDetailCard itemDetailCard = new ItemDetailCard(card -> card.clearCard());
  private boolean selectingCharacter = false,
                  checkAnimationsBeforeClose = false;
  private final FontInfo  characterSelectorHeader = GameFonts.getlightText_bold_23(),
                          selectedItemAmountFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final PrimaryButton returnToListButton = new PrimaryButton(
          "Back To List",
          280,
          TOP + 21,
          39,
          11,
          () -> selectingCharacter = false
  );
  private EquipmentItem selectedItem;
  private InventoryReference selectedRef;
  private final ArrayList<ItemOnCharacterButton> itemOnCharacterButtons = new ArrayList<>();
  private final SoundEffects sfx = new SoundEffects();
  
  public ItemsView() {
    characterSelectorHeader.setText("Select Character To Use Item");
    characterSelectorHeader.setX(215);
    characterSelectorHeader.setY(TOP + 7);
    characterSelectorHeader.centerText();
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
        newPage.add(
        new InventoryListItem(
          refs.get(j),
          startingY + newPage.size() * spaceBetweenRows,
          "Use",
          item -> {
            EquipmentItem thisItem = (EquipmentItem) item;
            handleUseItem(thisItem);
          },
          item -> showItemDetails(item))
        );
      }
      if (newPage.size() > 0) pages.add(newPage);
    }
    itemCount = manager.getItemsByType(Item.EQUIPMENT).size();
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
    selectedItemAmountFont.setY(TOP + 21);
    selectedItemAmountFont.rightAlignText();
  }
  
  private void setItemOnCharacterButtons() {
    itemOnCharacterButtons.clear();
    CharacterManager.getCharacterManager().getPartyMembers().forEach((position, partyMember) -> {
      itemOnCharacterButtons.add(new ItemOnCharacterButton(
        "",
        215,
        74 + itemOnCharacterButtons.size() * 33,
        32,
        32,
        partyMember,
        member -> handleCharacterButtonPress(member)
      ));
    });
  }
  
  private void handleCharacterButtonPress(Character targetCharacter) {
    if (selectedRef.getAmount() == 0) {
      checkAnimationsBeforeClose = true;
      return;
    }
    selectedItem.setStatModel(targetCharacter);
    selectedItem.useItem();
  }
  
  private void checkAnimations() {
    boolean isAnimating = false;
    for (ItemOnCharacterButton button: itemOnCharacterButtons) {
      if (button.animating()) {
        isAnimating = true;
        break;
      }
    }
    selectingCharacter = isAnimating;
  }
  
  private void checkItemCount() {
    if (itemCount != manager.getItemsByType(Item.EQUIPMENT).size()) {
      createListItems(manager.getItemsByType(Item.EQUIPMENT));
      itemCount = manager.getItemsByType(Item.EQUIPMENT).size();
    }
  }

  @Override
  public void enteringView() {
  }
  
  @Override
  public void exitingView() {
    itemDetailCard.clearCard();
    selectingCharacter = false;
    pagination.highlightCurrentPage(0);
    currentPageIndex = 0;
  }
  
  @Override
  public void update() {
    checkItemCount();
    if (!selectingCharacter) {
      if (pages.size() > 0) {
        for (InventoryListItem item: pages.get(currentPageIndex)) {
          item.update();
        }
      }
    }
    itemDetailCard.update();
    if (selectingCharacter) {
      if (selectedItemAmount != selectedRef.getAmount()) setSelectedFont();
      if (checkAnimationsBeforeClose) checkAnimations();
      returnToListButton.update();
      for (ItemOnCharacterButton button: itemOnCharacterButtons) {
        button.update();
      }
    }
    pagination.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (!selectingCharacter) {
      if (pages.size() > 0) {
        for (InventoryListItem item: pages.get(currentPageIndex)) {
          item.render(screen, gl);
        }
      }
    }
    screen.renderSprite(gl, 306, 23, border, false);
    itemDetailCard.render(screen, gl);
    if (selectingCharacter) {
      screen.renderFonts(characterSelectorHeader);
      screen.renderSprite(gl, 200, TOP + 14, selectedItem.getThumbnail(), false);
      screen.renderFonts(selectedItemAmountFont);
      returnToListButton.render(screen, gl);
      for (ItemOnCharacterButton button: itemOnCharacterButtons) {
        button.render(screen, gl);
      }
    }
    pagination.render(screen, gl);
  }
  
}
