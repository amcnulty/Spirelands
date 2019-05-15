package com.monkeystomp.spirelands.battle.message;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;

/**
 * Flash message class is used to show quick messages during battle.
 * @author Aaron Michael McNulty
 */
public class FlashMessage {
  
  private final int x,
                    decay = 90;
  private int life = 0,
              y;
  private boolean floatUp = false,
                  visible = true;
  private final FontInfo damageFontInfo = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  
  public FlashMessage(int x, int y, String message) {
    this.x = x;
    this.y = y;
    damageFontInfo.setX(x);
    damageFontInfo.setY(y);
    damageFontInfo.setText(message);
    damageFontInfo.centerText();
  }
  
  public void floatMessageUp(boolean floatUp) {
    this.floatUp = floatUp;
  }

  public boolean isVisible() {
    return visible;
  }
  
  public void update() {
    if (life == decay) visible = false;
    if (floatUp && life % 12 == 0) {
      y--;
      damageFontInfo.setY(y);
    }
    life++;
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(damageFontInfo);
  }

}
