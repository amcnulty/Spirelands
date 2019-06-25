package com.monkeystomp.spirelands.battle.victory;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import java.awt.Color;

/**
 * Displays the item details for the loot on a row within the victory screen.
 * @author Aaron Michael McNulty
 */
public class LootLineItem {
  
  private final Sprite thumbnail;
  private final int x, y, spaceBetween = 150;
  private final InventoryReference ref;
  private final FontInfo  itemNameFont = GameFonts.getDarkText_plain_18(),
                          itemAmountFont = GameFonts.getDarkText_bold_18();
  
  public LootLineItem(int x, int y, InventoryReference ref) {
    this.ref = ref;
    this.thumbnail = ref.getItem().getThumbnail();
    this.x = x;
    this.y = y;
    setUpFonts();
  }
  
  private void setUpFonts() {
    itemNameFont.setText(ref.getItem().getTitle());
    itemNameFont.setX(x + 20);
    itemNameFont.setY(y);
    
    itemAmountFont.setText(String.valueOf(ref.getAmount()));
    itemAmountFont.setColor(new Color(GameColors.DARK_GREEN));
    itemAmountFont.setX(x + spaceBetween);
    itemAmountFont.setY(y);
    itemAmountFont.rightAlignText();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - thumbnail.getWidth() / 2, y - thumbnail.getHeight() / 2, thumbnail, false);
    screen.renderFonts(itemNameFont);
    screen.renderFonts(itemAmountFont);
  }

}
