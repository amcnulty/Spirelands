package com.monkeystomp.spirelands.gui.gamemenu.panels;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryManager;

/**
 * The bottom right panel in the game menu that is used to display the current gold supply.
 * @author Aaron Michael McNulty
 */
public class GoldPanel {
  
  private final FontInfo goldFont = GameFonts.getlightText_bold_23();
  
  public GoldPanel() {
    goldFont.setX(255);
    goldFont.setY(199);
  }
  /**
   * Renders the gold panel to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 222, 193, Sprite.GOLD_INDICATOR, false);
    // Update gold amount
    goldFont.setText(String.valueOf(InventoryManager.getInventoryManager().getGold()));
    screen.renderFonts(goldFont); 
  }
}
