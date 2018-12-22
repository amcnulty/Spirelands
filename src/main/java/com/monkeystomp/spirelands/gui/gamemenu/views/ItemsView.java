package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ItemsView extends DisplayView {

  @Override
  public void update() {
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 25), Color.white, "Items View Works!", 175, 60));
  }
  
}
