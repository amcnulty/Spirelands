package com.monkeystomp.spirelands.gui.gamemenu;

import com.monkeystomp.spirelands.gui.gamemenu.panels.GoldPanel;
import com.monkeystomp.spirelands.gui.gamemenu.panels.ButtonPanel;
import com.monkeystomp.spirelands.gui.gamemenu.panels.DisplayPanel;
import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;

/**
 * The Game Menu is the menu screen that opens to display party information, inventory, level information and other game information.
 * @author Aaron Michael McNulty
 */
public class GameMenu {
  
  private final Sprite background = Sprite.GAME_MENU_BACKGROUND;
  private final ButtonPanel buttonPanel = new ButtonPanel();
  private final GoldPanel goldPanel = new GoldPanel();
  private final DisplayPanel displayPanel = new DisplayPanel();
  private final FontInfo levelName = GameFonts.getlightText_bold_23();
  private final SoundEffects sfx = new SoundEffects();
  /**
   * Creates a GameMenu object used for displaying the game inventory and party information menu.
   */
  public GameMenu() {
    buttonPanel.setViewChanger(key -> displayPanel.changeView(key));
    buttonPanel.setNextViewChanger(key -> displayPanel.prepareNextViewWithCharacter(key));
  }
  /**
   * Sets the level name to display in the lower left panel.
   * @param text The name of the level to display.
   */
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
    buttonPanel.resetNavButtons();
    displayPanel.changeView(DisplayPanel.DEFAULT);
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
    buttonPanel.update();
    displayPanel.update();
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
    buttonPanel.render(screen, gl);
    goldPanel.render(screen, gl);
    displayPanel.render(screen, gl);
    // Render the level name
    screen.renderFonts(levelName);
  }
}
