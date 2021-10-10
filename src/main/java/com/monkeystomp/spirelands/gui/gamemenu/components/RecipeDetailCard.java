package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.crafting.CompoundRecipe;
import com.monkeystomp.spirelands.crafting.Recipe;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * Shows all information about a recipe on the right hand side of the game menu.
 * @author Aaron Michael McNulty
 */
public class RecipeDetailCard {
  
  private final Sprite thumbnail = new Sprite(16, 16, 2, 11, SpriteSheet.itemsSheet);
  private final String  noSelectedRecipe = "Select a recipe to view more information about it here.",
                        times = "\u2A2F";
  private final FontInfo  titleFont = GameFonts.getlightText_bold_23(),
                          descriptionFont = GameFonts.getGAME_MENU_MUTED_TEXT(),
                          requiredLevelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          alternateRecipeFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final int price = 0,
                    cardWidth = 89,
                    sidePadding = 5,
                    cardTop = 23,
                    cardCenterHoriz = 351,
                    cardCenterVert = 100,
                    cardLeft = 307,
                    cardRight = cardLeft + cardWidth,
                    titleFontY = 30,
                    requiredLevelFontY = 65,
                    descriptionFontY = 75,
                    alternateRecipeFontY = 126,
                    inputFirstRowY = 100,
                    inputSecondRowY = 136,
                    spaceBetweenInputs = 17;
  private boolean recipeSet = false,
                  hasAlternateRecipes = false;
  private final ArrayList<FontInfo> noSelectedInfoList = new ArrayList<>(),
                                    recipeDescriptionList = new ArrayList<>();
  private final ArrayList<RecipeInputListItem> inputs = new ArrayList<>();
  private final ArrayList<RecipeInputListItem> alternateInputs = new ArrayList<>();
  private final ArrayList<RecipeInputListItem> alternate_inputs = new ArrayList<>();
  private final GameMenuSecondaryButton exitButton = new GameMenuSecondaryButton(
    times,
    cardLeft + sidePadding,
    cardTop + sidePadding,
    9,
    9,
    () -> {handleExitButtonClick();}
  );
  
  public RecipeDetailCard() {
    ArrayList<String> lines = TextUtil.createWrappedText(noSelectedRecipe, GameFonts.getGAME_MENU_MUTED_TEXT().getFont(), cardWidth - (2 * sidePadding));
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(cardLeft + sidePadding);
      info.setY(cardCenterVert + i * 7);
      noSelectedInfoList.add(info);
    }
  }
  
  private void handleExitButtonClick() {
    clearCard();
  }
  
  public void clearCard() {
    recipeSet = false;
  }
  
  public void showRecipeDetails(Recipe recipe) {
    setupFonts(recipe);
    setupInputs(recipe);
    recipeSet = true;
  }
  
  private void setupFonts(Recipe recipe) {
    titleFont.setText(recipe.getName());
    titleFont.setX(cardCenterHoriz);
    titleFont.setY(titleFontY);
    titleFont.centerText();
    
    requiredLevelFont.setText("Requires crafting Lv: " + Integer.toString(recipe.getCraftingLevel()));
    requiredLevelFont.setX(cardCenterHoriz);
    requiredLevelFont.setY(requiredLevelFontY);
    requiredLevelFont.centerText();
    
    alternateRecipeFont.setText("Or");
    alternateRecipeFont.setX(cardCenterHoriz);
    alternateRecipeFont.setY(alternateRecipeFontY);
    alternateRecipeFont.centerText();
    
    descriptionFont.setText("The following items are required to craft this recipe.");
    descriptionFont.setX(cardLeft + sidePadding);
    descriptionFont.setY(descriptionFontY);

    ArrayList<String> lines = TextUtil.createWrappedText(descriptionFont.getText(), descriptionFont.getFont(), cardWidth - 2 * sidePadding);
    recipeDescriptionList.clear();
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(cardLeft + sidePadding);
      info.setY(75 + i * 7);
      recipeDescriptionList.add(info);
    }
  }
  
  private void setupInputs(Recipe recipe) {
    inputs.clear();
    alternateInputs.clear();
    hasAlternateRecipes = checkAlternateRecipes(recipe);
    int startingX = recipe.getInputs().size() == 3
                    ?
                    cardCenterHoriz - spaceBetweenInputs / 2 - spaceBetweenInputs
                    :
                    cardCenterHoriz - spaceBetweenInputs;
    for (int i = 0; i < recipe.getInputs().size(); i++) {
      inputs.add(new RecipeInputListItem(recipe.getInputs().get(i), startingX + spaceBetweenInputs * i, inputFirstRowY));
    }
    if (hasAlternateRecipes) {
      List<Item> altInputsFromRecipe = ((CompoundRecipe)recipe).getAlternateInputs();
      int startingXAlt = altInputsFromRecipe.size() == 3
                    ?
                    cardCenterHoriz - spaceBetweenInputs / 2 - spaceBetweenInputs
                    :
                    cardCenterHoriz - spaceBetweenInputs;
      for (int i = 0; i < altInputsFromRecipe.size(); i++) {
        alternateInputs.add(new RecipeInputListItem(altInputsFromRecipe.get(i), startingXAlt + spaceBetweenInputs * i, inputSecondRowY));
      }
    }
  }
  
  private boolean checkAlternateRecipes(Recipe recipe) {
    return recipe.getClass().equals(CompoundRecipe.class) && ((CompoundRecipe)recipe).getAlternateInputs().size() > 0;
  }
  
  public void update() {
    if (recipeSet) exitButton.update();
    for (RecipeInputListItem item: inputs) {
      item.update();
    }
    for (RecipeInputListItem item: alternateInputs) {
      item.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (recipeSet) {
      exitButton.render(screen, gl);
      screen.renderFonts(titleFont);
      screen.renderSprite(gl, cardCenterHoriz - thumbnail.getWidth() / 2, 40, thumbnail, false);
      screen.renderFonts(requiredLevelFont);
      if(hasAlternateRecipes) screen.renderFonts(alternateRecipeFont);
      for (FontInfo info: recipeDescriptionList) {
        screen.renderFonts(info);
      }
      for (RecipeInputListItem item: inputs) {
        item.render(screen, gl);
      }
      for (RecipeInputListItem item: alternateInputs) {
        item.render(screen, gl);
      }
    }
    else {
      for (FontInfo info: noSelectedInfoList) {
        screen.renderFonts(info);
      }
    }
  }

}
