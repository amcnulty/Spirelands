package com.monkeystomp.spirelands.gui.gamemenu;

import com.monkeystomp.spirelands.gui.gamemenu.panels.GoldPanel;
import com.monkeystomp.spirelands.gui.gamemenu.panels.ButtonPanel;
import com.monkeystomp.spirelands.gui.gamemenu.panels.DisplayPanel;
import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.views.DisplayView;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;

/**
 * The Game Menu is the menu screen that opens to display party information, inventory, level information and other game information.
 * @author Aaron Michael McNulty
 */
public class GameMenu {
  
  private final Sprite background = Sprite.GAME_MENU_BACKGROUND;
  private final ButtonPanel buttonPanel = new ButtonPanel();
  private final GoldPanel goldPanel = new GoldPanel();
  private final DisplayPanel displayPanel = new DisplayPanel(() -> handleViewChange());
  private final FontInfo levelName = GameFonts.getlightText_bold_23();
  private final SoundEffects sfx = new SoundEffects();
  private final ICallback closeCommand;
  private final GameMenuSecondaryButton closeMenuButton = new GameMenuSecondaryButton(
    "\u2A2F",
    379,
    19,
    9,
    9,
    () -> handleExitPress()
  );
  private final GameMenuSecondaryButton backButton = new GameMenuSecondaryButton(
    "\u2baa",
    369,
    19,
    9,
    9,
    () -> handleBackButtonPress()
  );
  /**
   * Creates a GameMenu object used for displaying the game inventory and party information menu.
   * @param closeCommand The command to fire when the exit button is pressed.
   */
  public GameMenu(ICallback closeCommand) {
    this.closeCommand = closeCommand;
    buttonPanel.setViewChanger(key -> displayPanel.changeView(key));
    buttonPanel.setNextViewChanger(key -> displayPanel.prepareNextViewWithCharacter(key));
    backButton.setDisabled(true);
  }
  
  private void handleViewChange() {
    if (backButton != null) {
      if (displayPanel.isDefaultView()) backButton.setDisabled(true);
      else backButton.setDisabled(false);
    }
  }
  
  private void handleExitPress() {
    closeCommand.execute();
  }
  
  private void handleBackButtonPress() {
    if (!displayPanel.isDefaultView()) {
      displayPanel.changeView(DisplayView.DEFAULT);
      buttonPanel.resetNavButtons();
    }
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
    displayPanel.changeView(DisplayView.DEFAULT);
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
    buttonPanel.update();
    displayPanel.update();
    backButton.update();
    closeMenuButton.update();
  }
  /**
   * Renders the game menu to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, 0, 0, background, false);
    buttonPanel.render(screen, gl);
    goldPanel.render(screen, gl);
    displayPanel.render(screen, gl);
    screen.renderFonts(levelName);
    backButton.render(screen, gl);
    closeMenuButton.render(screen, gl);
  }
}
