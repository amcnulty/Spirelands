package com.monkeystomp.spirelands.gui.gamemenu.views.craftingSubView;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.crafting.Recipe;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.Pagination;
import com.monkeystomp.spirelands.gui.gamemenu.components.RecipeListItem;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A sub view of the crafting view in the game menu used to show the list of crafting recipes.
 * @author Aaron Michael McNulty
 */
public class RecipesSubView {
  
  
  private final FontInfo header = GameFonts.getGAME_MENU_HEADLINE_THIN();
  private final Consumer<Recipe> handleApplyRecipe;
  private final ICallback exitSubView;
    // TODO FIX THIS LINE TO GET THE TOTAL NUMBER OF DISCOVERED RECIPES FROM THE RECIPE MANAGER
  private final Map<Integer, Recipe> recipes = Recipe.getRECIPE_INDEX();
  private int currentPageIndex = 0;
  private int recipeCount = 0;
  private final int headerX = 135,
                    headerY = 34,
                    backButtonX = 273,
                    backButtonY = 34,
                    startingY = 51,
                    listItemX = 140,
                    spaceBetweenRows = 16,
                    itemsPerPage = 7;
  private final PrimaryButton backButton = new PrimaryButton("Back To Crafting", backButtonX, backButtonY, 50, 11, () -> handleBackButtonClick());
  private final ArrayList<ArrayList<RecipeListItem>> pages = new ArrayList<>();
  private final Pagination pagination = new Pagination(8, 214, 169, pageIndex -> currentPageIndex = pageIndex);
  private final Sprite border = new Sprite(1, 156, GameColors.GAME_MENU_BORDER);
  
  public RecipesSubView(Consumer<Recipe> handleApplyRecipe, ICallback exitSubView) {
    this.handleApplyRecipe = handleApplyRecipe;
    this.exitSubView = exitSubView;
    header.setText("Discovered Recipes");
    header.setX(headerX);
    header.setY(headerY);
  }
  
  private void handleBackButtonClick() {
    exitSubView.execute();
  }
  
  private void createListItems(Map<Integer, Recipe> recipeMap) {
    pages.clear();
    Set<Integer> keys = recipeMap.keySet();
    ArrayList<Recipe> recipesToAdd = new ArrayList<>();
    keys.forEach(key -> {
      recipesToAdd.add(recipeMap.get(key));
    });
    for (int i = 0; i < recipesToAdd.size() / itemsPerPage + 1; i++) {
      ArrayList<RecipeListItem> newPage = new ArrayList<>();
      for (int j = i * itemsPerPage; j < itemsPerPage + (i * itemsPerPage); j++) {
        if (recipesToAdd.size() - 1 < j) break;
        newPage.add(new RecipeListItem(
          recipesToAdd.get(j),
          listItemX,
          startingY + newPage.size() * spaceBetweenRows,
          recipe -> handleApplyRecipe(recipe),
          recipe -> handleInfoClick(recipe)
        ));
      }
      if (newPage.size() > 0) pages.add(newPage);
    }
    // TODO FIX THIS LINE TO GET THE TOTAL NUMBER OF DISCOVERED RECIPES FROM THE RECIPE MANAGER
    recipeCount = Recipe.getRECIPE_INDEX().size();
    pagination.setListLength(recipeCount);
    setCurrentPage();
  }
  
  private void setCurrentPage() {
    if (currentPageIndex >= pages.size() - 1) {
      currentPageIndex = pages.size() - 1;
      if (currentPageIndex < 0) currentPageIndex = 0;
    }
    pagination.highlightCurrentPage(currentPageIndex);
  }
  
  private void handleApplyRecipe(Recipe recipe) {
    
  }
  
  private void handleInfoClick(Recipe recipe) {
    
  }

  private void checkRecipeCount() {
    if (recipeCount != recipes.size()) {
      createListItems(recipes);
      recipeCount = recipes.size();
    }
  }
  
  public void update() {
    checkRecipeCount();
    backButton.update();
    if (pages.size() > 0) {
      for (RecipeListItem item: pages.get(currentPageIndex)) {
        item.update();
      }
    }
//    itemDetailCard.update();
    pagination.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(header);
    backButton.render(screen, gl);
    if (pages.size() > 0) {
      for (RecipeListItem item: pages.get(currentPageIndex)) {
        item.render(screen, gl);
      }
    }
    screen.renderSprite(gl, 306, 23, border, false);
//    itemDetailCard.render(screen, gl);
    pagination.render(screen, gl);
    
  }

}
