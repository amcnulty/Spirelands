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
import java.util.function.Consumer;

/**
 * List item to show a recipe.
 * @author Aaron Michael McNulty
 */
public class RecipeListItem {
  
  private final Sprite thumbnail = new Sprite(16, 16, 2, 11, SpriteSheet.itemsSheet);
  private final String name;
  protected final int x, y;
  private final FontInfo label = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final PrimaryButton applyButton;
  private final GameMenuPrimaryButton infoButton;
  
  public RecipeListItem(Recipe recipe, int x, int y, Consumer<Recipe> onApplyClick, Consumer<Recipe> onInfoClick) {
    this.name = recipe.getName();
    this.x = x;
    this.y = y;
    applyButton = new PrimaryButton("Apply", x + 126, y, 19, 11, () -> onApplyClick.accept(recipe));
    infoButton = new GameMenuPrimaryButton("Info", x + 148, y, 19, 11, () -> onInfoClick.accept(recipe));
    label.setText(name);
    label.setX(x + 12);
    label.setY(y);
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
