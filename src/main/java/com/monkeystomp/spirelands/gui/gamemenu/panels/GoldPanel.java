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
  private final FontInfo abilityPointsFont = GameFonts.getlightText_bold_23();
  
  public GoldPanel() {
    goldFont.setX(235);
    goldFont.setY(199);
    goldFont.setText("");
    abilityPointsFont.setX(300);
    abilityPointsFont.setY(199);
    abilityPointsFont.setText("");
  }
  
  public void update() {
    goldFont.setText(String.valueOf(InventoryManager.getInventoryManager().getGold()));
    abilityPointsFont.setText(String.valueOf(InventoryManager.getInventoryManager().getAbilityPoints()));
  }
  /**
   * Renders the gold panel to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 218, 197, Sprite.GOLD_INDICATOR, false);
    screen.renderFonts(goldFont);
    screen.renderSprite(gl, 283, 194, Sprite.ABILITY_POINTS_INDICATOR, false);
    screen.renderFonts(abilityPointsFont);
  }
}
