package com.monkeystomp.spirelands.gui.gamemenu;

import com.monkeystomp.spirelands.gui.gamemenu.panels.GoldPanel;
import com.monkeystomp.spirelands.gui.gamemenu.panels.ButtonPanel;
import com.monkeystomp.spirelands.gui.gamemenu.panels.DisplayPanel;
import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import java.awt.Color;
import java.awt.Font;

/**
 * The Game Menu is the menu screen that opens to display party information, inventory, level information and other game information.
 * @author Aaron Michael McNulty
 */
public class GameMenu {
  
  private final Sprite background = Sprite.GAME_MENU_BACKGROUND;
  private final ButtonPanel BUTTON_PANEL = new ButtonPanel();
  private final GoldPanel GOLD_PANEL = new GoldPanel();
  private final DisplayPanel DISPLAY_PANEL = new DisplayPanel();
  private final FontInfo levelName = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, 23), Color.WHITE);
  private final SoundEffects sfx = new SoundEffects();

  public GameMenu() {
    BUTTON_PANEL.setViewChanger(key -> DISPLAY_PANEL.changeView(key));
  }
  
  public void setLevelName(String text) {
    this.levelName.setText(text);
    this.levelName.setX(32);
    this.levelName.setY(199);
  }
  
  /**
   * Opens the game menu.
   */
  public void openMenu() {
    playMenuOpenSound();
  }
  /**
   * Closes the game menu.
   */
  public void closeMenu() {
    playMenuCloseSound();
    BUTTON_PANEL.resetNavButtons();
    DISPLAY_PANEL.changeView(DisplayPanel.DEFAULT);
  }

  private void playMenuOpenSound() {
    sfx.playSoundEffect(SoundEffects.OPEN_GAME_MENU);
  }
  
  private void playMenuCloseSound() {
    sfx.playSoundEffect(SoundEffects.CLOSE_GAME_MENU);
  }
  /**
   * Updates the game menu.
   */
  public void update() {
    // Update the panels
    BUTTON_PANEL.update();
    DISPLAY_PANEL.update();
  }
  /**
   * Renders the game menu to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    // Render the background 
    screen.renderSprite(gl, 0, 0, background, false);
    // Render the panels
    BUTTON_PANEL.render(screen, gl);
    GOLD_PANEL.render(screen, gl);
    DISPLAY_PANEL.render(screen, gl);
    // Render the level name
    screen.renderFonts(levelName);
  }
}
