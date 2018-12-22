package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class DefaultView extends DisplayView {
  
  // Buttons for each player
  private final CharacterManager CHARACTER_MANAGER = CharacterManager.getCharacterManager();
  
  public DefaultView() {
    createCharacterButtons();
  }
  
  private void createCharacterButtons() {
    
  }

  @Override
  public void update() {
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 25), Color.white, "Default View Works!", 175, 60));
  }
  
}
