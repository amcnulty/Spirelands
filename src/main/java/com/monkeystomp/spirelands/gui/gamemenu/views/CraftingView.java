package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.crafting.CraftingManager;
import com.monkeystomp.spirelands.crafting.Recipe;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.CraftingItemSlot;
import com.monkeystomp.spirelands.gui.gamemenu.components.ItemSlot;
import com.monkeystomp.spirelands.gui.gamemenu.components.UsableInventoryListItem;
import com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView.ItemsSubView;
import com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView.RecipesSubView;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.util.Helpers;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The game menu view for when users click on the crafting button.
 * @author Aaron Michael McNulty
 */
public class CraftingView extends DisplayView {
  
  private final FontInfo  craftingLevelLabel = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          craftingLevel = GameFonts.getGAME_MENU_PRIMARY_TEXT(),
                          maxLevel = GameFonts.getDarkText_bold_18(),
                          craftingExpFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          description = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          outputItemLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          errorText = GameFonts.getWarningText_bold_18();
  private final int craftingLevelLabelX = LEFT + 10,
                    craftingLevelLabelY = TOP + 10,
                    craftingLevelX = LEFT + 54,
                    expBarWidth = 40,
                    expBarHeight = 4,
                    expBarX = LEFT + 65,
                    expBarY = TOP + 8,
                    expFontX = expBarX + 45,
                    expFontY = expBarY + 2,
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
                  showingRecipesSubView = false,
                  outputItemShowing = false,
                  errorTextShowing = false;
  private final Sprite expUnderline = new Sprite(expBarWidth, 1, GameColors.EXP_GAUGE_UNDERLINE);
  private final Sprite expEmpty = new Sprite(expBarWidth, expBarHeight, GameColors.EXP_GAUGE_EMPTY);
  private Sprite expFill;
  private int craftingExpAnimationValue,
              craftingLevelAnimationValue,
              tick = 0;
  private ItemSlot currentSlot;
  private UsableInventoryListItem outputItemDisplay;
  private final GameMenuPrimaryButton recipeListButton = new GameMenuPrimaryButton("View Recipes", recipeListButtonX, craftingLevelLabelY, 45, 13, () -> this.handleShowRecipeList());
  private final ArrayList<ItemSlot> itemSlots = new ArrayList<>();
  private final ItemSlot slot1 = new CraftingItemSlot(
    slot1X,
    slot1Y,
    (item) -> this.handleShowInfo(item),
    false,
    (slot) -> this.handleOpenItemList(slot),
    () -> this.handleRemoveItemFromSlot()
  );
  private final ItemSlot slot2 = new CraftingItemSlot(
    slot2X,
    slot2Y,
    (item) -> this.handleShowInfo(item),
    false,
    (slot) -> this.handleOpenItemList(slot),
    () -> this.handleRemoveItemFromSlot()
  );
  private final ItemSlot slot3 = new CraftingItemSlot(
    slot3X,
    slot3Y,
    (item) -> this.handleShowInfo(item),
    false,
    (slot) -> this.handleOpenItemList(slot),
    () -> this.handleRemoveItemFromSlot()
  );
  private final ItemsSubView itemsSubView = new ItemsSubView(item -> handleAddItemToSlot(item), () -> showingItemsSubView = false);
  private final RecipesSubView recipesSubView = new RecipesSubView(recipe -> handleApplyRecipe(recipe), () -> showingRecipesSubView = false);
  
  public CraftingView() {
    craftingLevelLabel.setText("Crafting Level:");
    craftingLevelLabel.setX(craftingLevelLabelX);
    craftingLevelLabel.setY(craftingLevelLabelY);
    craftingLevel.setText(Integer.toString(CraftingManager.getCraftingManager().getCraftingLevel()));
    craftingLevel.setX(craftingLevelX);
    craftingLevel.setY(craftingLevelLabelY);
    description.setText("Add crafting materials to the slots below to craft into new items.");
    description.setX(descriptionX);
    description.setY(descriptionY);
    description.centerText();
    outputItemLabel.setText("Recipe will create: ");
    outputItemLabel.setX(outputItemLabelX);
    outputItemLabel.setY(outputItemLabelY);
    itemSlots.add(slot1);
    itemSlots.add(slot2);
    itemSlots.add(slot3);
    craftingExpAnimationValue = CraftingManager.getCraftingManager().getCraftingExp();
    craftingLevelAnimationValue = CraftingManager.getCraftingManager().getCraftingLevel();
    craftingExpFont.setText(CraftingManager.getCraftingManager().getCraftingExp() + " / " + CraftingManager.getCraftingManager().getCraftingExpMax());
    craftingExpFont.setX(expFontX);
    craftingExpFont.setY(expFontY);
    maxLevel.setText("MAX");
    maxLevel.setX(expBarX);
    maxLevel.setY(expFontY);
    maxLevel.setColor(new Color(GameColors.GOLD_PARTICLE_COLOR));
    expFill = new Sprite((int)(craftingExpAnimationValue / (double)CraftingManager.getCraftingManager().getCraftingExpMax(craftingLevelAnimationValue) * expBarWidth), expBarHeight, GameColors.EXP_GAUGE_FILL);
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
    showingItemsSubView = false;
    handleSlotChange();
  }
  
  private void handleRemoveItemFromSlot() {
    handleSlotChange();
  }

  private void handleApplyRecipe(Recipe recipe) {
    clearSlots();
    for (int i = 0; i < recipe.getInputs().size(); i++) {
      itemSlots.get(i).setItem(recipe.getInputs().get(i));
    }
    showingRecipesSubView = false;
    handleSlotChange();
  }
  
  private void handleSlotChange() {
    Recipe recipe = checkForRecipe();
    if (recipe != null) {
      outputItemShowing = true;
      outputItemDisplay = new UsableInventoryListItem(
        new InventoryReference(recipe.getOutput(), 1),
        outputItemDisplayX,
        outputItemDisplayY,
        "Craft",
        (Item item) -> System.out.println(item.getTitle()),
        (Item item) -> System.out.println(item.getTitle())
      );
      if (CraftingManager.getCraftingManager().getCraftingLevel() < recipe.getCraftingLevel()) {
        showErrorText("This recipe requires a minimum crafting level of " + recipe.getCraftingLevel() + " to craft!");
      }
    }
    else {
      outputItemShowing = false;
      errorTextShowing = false;
    }
  }
  
  private Recipe checkForRecipe() {
    List<ItemSlot> filledSlots = itemSlots.stream()
                                  .filter(slot -> slot.getItem() != null)
                                  .collect(Collectors.toList());
    int numberOfItemsInSlots = filledSlots.size();
    if (numberOfItemsInSlots > 1) {
      Item[] inputs = new Item[numberOfItemsInSlots];
      for (int i = 0; i < numberOfItemsInSlots; i++) {
        inputs[i] = filledSlots.get(i).getItem();
      }
      return CraftingManager.getCraftingManager().checkForRecipe(inputs);
    }
    return null;
  }
  
  private void clearSlots() {
    for (ItemSlot slot: itemSlots) {
      slot.removeItem();
    }
  }
  
  private void showErrorText(String errorMsg) {
    errorText.setText(errorMsg);
    errorText.setX(descriptionX);
    errorText.setY(errorTextY);
    errorText.centerText();
    errorTextShowing = true;
  }
  
  private void updateExpFont() {
    if (++craftingExpAnimationValue == CraftingManager.getCraftingManager().getCraftingExpMax(craftingLevelAnimationValue)) {
      ++craftingLevelAnimationValue;
      craftingExpAnimationValue = 0;
    }
    craftingLevel.setText(Integer.toString(craftingLevelAnimationValue));
    if (craftingLevelAnimationValue == 3) return;
    craftingExpFont.setText(craftingExpAnimationValue + " / " + CraftingManager.getCraftingManager().getCraftingExpMax(craftingLevelAnimationValue));
  }
  
  private void updateExpFill() {
    if (craftingLevelAnimationValue == 3) return;
    expFill = new Sprite((int)(craftingExpAnimationValue / (double)CraftingManager.getCraftingManager().getCraftingExpMax(craftingLevelAnimationValue) * expBarWidth), expBarHeight, GameColors.EXP_GAUGE_FILL);
  }

  @Override
  public void enteringView() {
  }

  @Override
  public void exitingView() {
    itemsSubView.exitingView();
    showingItemsSubView = false;
    showingRecipesSubView = false;
    clearSlots();
  }

  @Override
  public void update() {
    if (!showingItemsSubView && !showingRecipesSubView) {
      recipeListButton.update();
      if ((craftingExpAnimationValue != CraftingManager.getCraftingManager().getCraftingExp()
          || craftingLevelAnimationValue != CraftingManager.getCraftingManager().getCraftingLevel())
            && craftingLevelAnimationValue < 3) {
        if (++tick % 4 == 0) {
          updateExpFont();
          updateExpFill();
        }
      }
      else tick = 0;
      for (ItemSlot slot: itemSlots) {
        slot.update();
      }
      if (outputItemShowing) outputItemDisplay.update();
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
      if (craftingLevelAnimationValue < 3) {
        screen.renderSprite(gl, expBarX, expBarY, expEmpty, false);
        screen.renderSprite(gl, expBarX, expBarY + 4, expUnderline, false);
        if (expFill.getWidth() > 0) screen.renderSprite(gl, expBarX, expBarY, expFill, false);
        screen.renderFonts(this.craftingExpFont);
      }
      else {
        screen.renderFonts(maxLevel);
      }
      screen.renderFonts(description);
      if (errorTextShowing) screen.renderFonts(errorText);
      screen.renderFonts(outputItemLabel);
      recipeListButton.render(screen, gl);
      for (ItemSlot slot: itemSlots) {
        slot.render(screen, gl);
      }
      if (outputItemShowing) outputItemDisplay.render(screen, gl);
    }
    else if (showingItemsSubView) {
      itemsSubView.render(screen, gl);
    }
    else if (showingRecipesSubView) {
      recipesSubView.render(screen, gl);
    }
  }

}
