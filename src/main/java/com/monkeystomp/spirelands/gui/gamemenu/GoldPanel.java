package com.monkeystomp.spirelands.gui.gamemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GoldPanel {
  
  private final FontInfo goldLabel = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 23), Color.GRAY, "Gold:", 222, 199);
  private final FontInfo goldAmount = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 23), new Color(0xf1f1f1), "190", 245, 199);
  
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(goldLabel);
    // Update gold amount
    goldAmount.setText(String.valueOf(InventoryManager.getInventoryManager().getGold()));
    screen.renderFonts(goldAmount); 
  }
}
