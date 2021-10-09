package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.crafting.Recipe;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.CraftingItemSlot;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemSlot;
import com.monkeystomp.spirelands.gui.gamemenu.components.UsableInventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView.ItemsSubView;
import com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView.RecipesSubView;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.util.Helpers;
import java.util.ArrayList;

/**
 * The game menu view for when users click on the crafting button.
 * @author Aaron Michael McNulty
 */
public class CraftingView extends DisplayView {
  
  private final FontInfo  craftingLevelLabel = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          craftingLevel = GameFonts.getGAME_MENU_PRIMARY_TEXT(),
                          description = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          outputItemLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          errorText = GameFonts.getWarningText_bold_18();
  private final int craftingLevelLabelX = LEFT + 10,
                    craftingLevelLabelY = TOP + 10,
                    craftingLevelX = LEFT + 54,
                    descriptionX = (LEFT + RIGHT) / 2,
                    descriptionY = TOP + 30,
                    recipeListButtonX = RIGHT - 40,
                    slot1X = LEFT + 60,
                    slot1Y = TOP + 60,
                    slot2X = (LEFT + RIGHT) / 2,
                    slot2Y = TOP + 80,
                    slot3X = RIGHT - 60,
                    slot3Y = TOP + 60,
                    errorTextY = TOP + 100,
                    outputItemLabelX = LEFT + 10,
                    outputItemLabelY = TOP + 130,
                    outputItemDisplayX = LEFT + 70,
                    outputItemDisplayY = TOP + 130;
  private boolean showingItemsSubView = false,
                  showingRecipesSubView = false;
  private ItemSlot currentSlot;
  private UsableInventoryListItem outputItemDisplay;
  private final GameMenuPrimaryButton recipeListButton = new GameMenuPrimaryButton("View Recipes", recipeListButtonX, craftingLevelLabelY, 45, 13, () -> this.handleShowRecipeList());
  private final ArrayList<ItemSlot> itemSlots = new ArrayList<>();
  private final ItemSlot slot1 = new CraftingItemSlot(slot1X, slot1Y, (item) -> this.handleShowInfo(item), (slot) -> this.handleOpenItemList(slot), false);
  private final ItemSlot slot2 = new CraftingItemSlot(slot2X, slot2Y, (item) -> this.handleShowInfo(item), (slot) -> this.handleOpenItemList(slot), false);
  private final ItemSlot slot3 = new CraftingItemSlot(slot3X, slot3Y, (item) -> this.handleShowInfo(item), (slot) -> this.handleOpenItemList(slot), false);
  private final ItemsSubView itemsSubView = new ItemsSubView(item -> handleAddItemToSlot(item), () -> showingItemsSubView = false);
  private final RecipesSubView recipesSubView = new RecipesSubView(recipe -> handleApplyRecipe(recipe), () -> showingRecipesSubView = false);
  
  public CraftingView() {
    craftingLevelLabel.setText("Crafting Level:");
    craftingLevelLabel.setX(craftingLevelLabelX);
    craftingLevelLabel.setY(craftingLevelLabelY);
    craftingLevel.setText("1");
    craftingLevel.setX(craftingLevelX);
    craftingLevel.setY(craftingLevelLabelY);
    description.setText("Add crafting materials to the slots below to craft into new items.");
    description.setX(descriptionX);
    description.setY(descriptionY);
    description.centerText();
    errorText.setText("This recipe requires a minimum crafting level of 2 to craft!");
    errorText.setX(descriptionX);
    errorText.setY(errorTextY);
    errorText.centerText();
    outputItemLabel.setText("Recipe will create: ");
    outputItemLabel.setX(outputItemLabelX);
    outputItemLabel.setY(outputItemLabelY);
    itemSlots.add(slot1);
    itemSlots.add(slot2);
    itemSlots.add(slot3);
    outputItemDisplay = new UsableInventoryListItem(
      new InventoryReference(Item.MEDIUM_HP_POTION, 1),
      outputItemDisplayX,
      outputItemDisplayY,
      "Craft",
      (Item item) -> System.out.println(item.getTitle()),
      (Item item) -> System.out.println(item.getTitle())
    );
  }
  
  private void handleShowRecipeList() {
    showingRecipesSubView = true;
    // Trick to not get the recipe list button to flash when returning to the main view from recipe list.
    preventButtonFlash();
  }
  
  private void preventButtonFlash() {
    Helpers.setTimeout(() -> {
      recipeListButton.update();
      if (recipeListButton.isHovering()) preventButtonFlash();
    }, 500);
  }
  
  private void handleShowInfo(Item item) {
    
  }
  
  private void handleOpenItemList(ItemSlot slot) {
    currentSlot = slot;
    showingItemsSubView = true;
  }
  
  private void handleAddItemToSlot(Item item) {
    currentSlot.setItem(item);
  }
  
  private void handleApplyRecipe(Recipe recipe) {
    
  }

  @Override
  public void enteringView() {
  }

  @Override
  public void exitingView() {
    itemsSubView.exitingView();
    showingItemsSubView = false;
    showingRecipesSubView = false;
  }

  @Override
  public void update() {
    if (!showingItemsSubView && !showingRecipesSubView) {
      recipeListButton.update();
      for (ItemSlot slot: itemSlots) {
        slot.update();
      }
      outputItemDisplay.update();
    }
    else if (showingItemsSubView) {
      itemsSubView.update();
    }
    else if (showingRecipesSubView) {
      recipesSubView.update();
    }
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    if (!showingItemsSubView && !showingRecipesSubView) {
      screen.renderFonts(craftingLevelLabel);
      screen.renderFonts(craftingLevel);
      screen.renderFonts(description);
      screen.renderFonts(errorText);
      screen.renderFonts(outputItemLabel);
      recipeListButton.render(screen, gl);
      for (ItemSlot slot: itemSlots) {
        slot.render(screen, gl);
      }
      outputItemDisplay.render(screen, gl);
    }
    else if (showingItemsSubView) {
      itemsSubView.render(screen, gl);
    }
    else if (showingRecipesSubView) {
      recipesSubView.render(screen, gl);
    }
  }

}
