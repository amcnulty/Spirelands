package com.monkeystomp.spirelands.battle.message;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.BattleEntity;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Flash message class is used to show quick messages during battle.
 * @author Aaron Michael McNulty
 */
public class FlashMessage {
  
  private final int x,
                    decay = 90,
                    mainMessageY = 20,
                    padding = 4;
  private int life = 0,
              y;
  private boolean floatUp = false,
                  visible = true,
                  mainMessage = false;
  private final FontInfo damageFontInfo = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo mainMessageFontInfo = GameFonts.getDarkText_22();
  private final FontRenderContext frc = new FontRenderContext(null, true, true);
  private final Random random = new Random();
  private Sprite mainMessageBackground;
  
  public FlashMessage(int x, int y, String message) {
    this.x = x;
    this.y = y;
    damageFontInfo.setX(x);
    damageFontInfo.setY(y);
    damageFontInfo.setText(message);
    damageFontInfo.centerText();
  }
  
  public FlashMessage(BattleEntity target, String message) {
    this.x = target.getX() + 4 - random.nextInt(9);
    this.y = target.getY() - target.getCurrentAction().getHeight() / 2;
    damageFontInfo.setX(x);
    damageFontInfo.setY(y);
    damageFontInfo.setText(message);
    damageFontInfo.centerText();
  }
  
  public FlashMessage(String message) {
    this.mainMessage = true;
    Rectangle2D bounds = mainMessageFontInfo.getFont().getStringBounds(message, frc);
    int textWidth = (int)bounds.getWidth();
    int textHeight = (int)bounds.getHeight();
    mainMessageFontInfo.setText(message);
    this.x = Screen.getWidth() / 2;
    this.y = mainMessageY;
    mainMessageFontInfo.setText(message);
    mainMessageFontInfo.setX(x);
    mainMessageFontInfo.setY(y);
    mainMessageFontInfo.centerText();
    mainMessageBackground = new Sprite((int)(textWidth / Screen.getScaleX() + padding * 2), (int)(textHeight / Screen.getScaleY() + padding * 2), GameColors.DIALOG_BOX_BACKGROUND);
  }
  
  public void floatMessageUp(boolean floatUp) {
    this.floatUp = floatUp;
  }
  
  public void setColor(Color color) {
    damageFontInfo.setColor(color);
  }

  public boolean isVisible() {
    return visible;
  }

  public boolean isMainMessage() {
    return mainMessage;
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
    if (mainMessage) {
      if (mainMessageBackground != null) screen.renderSprite(gl, Screen.getWidth() / 2 - mainMessageBackground.getWidth() / 2, mainMessageFontInfo.getY() - mainMessageBackground.getHeight() / 2, mainMessageBackground, .8f, false);
      screen.renderFonts(mainMessageFontInfo);
    }
    else {
      screen.renderFonts(damageFontInfo);
    }
  }

}
