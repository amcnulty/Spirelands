package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.crafting.Recipe;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import java.util.ArrayList;

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
                          requiredLevelFont = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final int price = 0,
                    cardWidth = 89,
                    sidePadding = 5,
                    cardTop = 23,
                    cardCenterHoriz = 351,
                    cardCenterVert = 100,
                    cardLeft = 307,
                    cardRight = cardLeft + cardWidth,
                    inputX = cardLeft + sidePadding,
                    inputFirstRowY = 100,
                    spaceBetweenInputs = 17;
  private boolean recipeSet = false;
  private final ArrayList<FontInfo> noSelectedInfoList = new ArrayList<>(),
                                    recipeDescriptionList = new ArrayList<>();
  private final ArrayList<RecipeInputListItem> inputs = new ArrayList<>();
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
  
  public void setRecipe(Recipe recipe) {
    titleFont.setText(recipe.getName());
    titleFont.setX(cardCenterHoriz);
    titleFont.setY(30);
    titleFont.centerText();
    
    requiredLevelFont.setText("Requires crafting Lv: " + Integer.toString(recipe.getCraftingLevel()));
    requiredLevelFont.setX(cardCenterHoriz);
    requiredLevelFont.setY(65);
    requiredLevelFont.centerText();
    
    descriptionFont.setText("The following items are required to craft this recipe.");
    descriptionFont.setX(cardLeft + sidePadding);
    descriptionFont.setY(75);
    
    ArrayList<String> lines = TextUtil.createWrappedText(descriptionFont.getText(), descriptionFont.getFont(), cardWidth - 2 * sidePadding);
    recipeDescriptionList.clear();
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(cardLeft + sidePadding);
      info.setY(75 + i * 7);
      recipeDescriptionList.add(info);
    }
    
    inputs.clear();
    int startingX = recipe.getInputs().size() == 3
                    ?
                    cardCenterHoriz - spaceBetweenInputs / 2 - spaceBetweenInputs
                    :
                    cardCenterHoriz - spaceBetweenInputs;
    for (int i = 0; i < recipe.getInputs().size(); i++) {
      inputs.add(new RecipeInputListItem(recipe.getInputs().get(i), startingX + spaceBetweenInputs * i, inputFirstRowY));
    }
    recipeSet = true;
  }
  
  public void update() {
    if (recipeSet) exitButton.update();
    for (RecipeInputListItem item: inputs) {
      item.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (recipeSet) {
      exitButton.render(screen, gl);
      screen.renderFonts(titleFont);
      screen.renderSprite(gl, cardCenterHoriz - thumbnail.getWidth() / 2, 40, thumbnail, false);
      screen.renderFonts(requiredLevelFont);
      for (FontInfo info: recipeDescriptionList) {
        screen.renderFonts(info);
      }
      for (RecipeInputListItem item: inputs) {
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
