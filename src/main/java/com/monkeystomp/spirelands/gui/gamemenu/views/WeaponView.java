package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;

/**
 *
 * @author Aaron Michael McNulty
 */
public class WeaponView extends DisplayView {
  
  private FontInfo fontInfo = GameFonts.getlightText_bold_23();
  
  public WeaponView() {
    fontInfo.setX(128);
    fontInfo.setY(50);
    fontInfo.setText("Weapon View Works!");
  }
  
  @Override
  public void update() {
    
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(fontInfo);
  }

}
