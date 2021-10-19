package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.crafting.Recipe;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

/**
 * List item to show a recipe.
 * @author Aaron Michael McNulty
 */
public class RecipeListItem {
  
  private final Recipe recipe;
  private final Sprite thumbnail = new Sprite(16, 16, 2, 11, SpriteSheet.itemsSheet);
  private final String name;
  protected final int x, y;
  private final FontInfo label = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final PrimaryButton applyButton;
  private final GameMenuPrimaryButton infoButton;
  
  public RecipeListItem(Recipe recipe, int x, int y, Consumer<Recipe> onApplyClick, Consumer<Recipe> onInfoClick) {
    this.recipe = recipe;
    this.name = recipe.getName();
    this.x = x;
    this.y = y;
    applyButton = new PrimaryButton("Apply", x + 126, y, 19, 11, () -> onApplyClick.accept(recipe));
    infoButton = new GameMenuPrimaryButton("Info", x + 148, y, 19, 11, () -> onInfoClick.accept(recipe));
    label.setText(name + (recipe.hasBeenCrafted() ? "" : " - ( new )"));
    label.setX(x + 12);
    label.setY(y);
  }
  
  public void refresh() {
    label.setText(name + (recipe.hasBeenCrafted() ? "" : " - ( new )"));
    boolean disableApply = false;
    HashMap<Item, Integer> itemToRequiredQuantityMap = new HashMap<>();
    for (int i = 0; i < recipe.getInputs().size(); i++) {
      Item key = recipe.getInputs().get(i);
      Integer replaceValue;
      if (itemToRequiredQuantityMap.get(key) == null) {
        replaceValue = 1;
      }
      else {
        replaceValue = itemToRequiredQuantityMap.get(key) + 1;
      }
      itemToRequiredQuantityMap.put(key, replaceValue);
    }
    Set<Item> keys = itemToRequiredQuantityMap.keySet();
    for (Item key: keys) {
      InventoryReference ref = InventoryManager.getInventoryManager().getInventoryReferenceById(key.getId());
      int amountInInventory = 0;
      if (ref != null) {
        amountInInventory = ref.getAmount();
      }
      int amountInRecipe = itemToRequiredQuantityMap.get(key);
      if (amountInInventory < amountInRecipe) {
        disableApply = true;
      }
    }
    applyButton.setDisabled(disableApply);
  }
  
  public void update() {
    applyButton.update();
    infoButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - thumbnail.getWidth() / 2, y - thumbnail.getHeight() / 2, thumbnail, false);
    screen.renderFonts(label);
    applyButton.render(screen, gl);
    infoButton.render(screen, gl);
  }
}
