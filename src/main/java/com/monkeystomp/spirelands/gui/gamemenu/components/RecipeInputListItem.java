package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.Mouse;
import com.monkeystomp.spirelands.inventory.Item;

/**
 * Used to show an input item used in a recipe.
 * @author Aaron Michael McNulty
 */
public class RecipeInputListItem {
  
  private final int thumbnailX,
                    thumbnailY,
                    thumbnailSize = 16,
                    tooltipY,
                    tooltipTextX,
                    tooltipTextY;
  private int tooltipX;
  private final Sprite thumbnail;
  private Sprite tooltipBackground;
  private final FontInfo itemName = GameFonts.getDarkText_bold_16();
  private boolean tooltipShowing = false,
                  textWidthSet = false;
  
  public RecipeInputListItem(Item item, int x, int y) {
    this.thumbnailX = x;
    this.thumbnailY = y;
    this.tooltipTextX = x + 8;
    this.tooltipTextY = y + 22;
    this.thumbnail = item.getThumbnail();
    itemName.setText(item.getTitle());
    itemName.setX(tooltipTextX);
    itemName.setY(tooltipTextY);
    itemName.centerText();
    tooltipY = y + 18;
  }
  
  private void checkHover() {
    int mouseX = (int) (Mouse.getX() / Screen.getScaleX());
    int mouseY = (int) (Mouse.getY() / Screen.getScaleY());
    if (mouseX >= thumbnailX
        && mouseX <= thumbnailX + thumbnailSize
        && mouseY >= thumbnailY
        && mouseY <= thumbnailY + thumbnailSize) {
      tooltipShowing = true;
    }
    else {
      tooltipShowing = false;
    }
  }
  
  private void setTextWidth() {
    int textWidth = (int) (itemName.getTextRenderer().getBounds(itemName.getText()).getWidth() / Screen.getScaleX());
    tooltipBackground = new Sprite(textWidth + 6, 9, GameColors.DIALOG_BOX_BACKGROUND);
    tooltipX = thumbnailX + 4 - textWidth / 2;
    textWidthSet = true;
  }
  
  public void update() {
    checkHover();
  }
  
  public void render(Screen screen, GL2 gl) {
    if (!textWidthSet) setTextWidth();
    screen.renderSprite(gl, thumbnailX, thumbnailY, thumbnail, false);
    if (tooltipShowing) {
      screen.renderSprite(gl, tooltipX, tooltipY, tooltipBackground, false);
      screen.renderFonts(itemName);
    }
  }

}
