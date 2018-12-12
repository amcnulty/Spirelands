package com.monkeystomp.spirelands.gui.pausemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.input.ICallback;
/**
 * The Pause Menu is displayed when the user pauses the game.
 * @author Aaron Michael McNulty
 */
public class PauseMenu {

  private final Sprite background = new Sprite(80, 100, 0xFFEFEFEF);

  private final SoundEffects sfx = new SoundEffects();
  
  private final int x = (Screen.getWidth() / 2) - (background.getWidth() / 2),
                    y = (Screen.getHeight() / 2) - (background.getHeight() / 2);

  private ICallback closeCommand,
                    quitToMenuCommand;
  
  private final Button continueButton = new PrimaryButton("Continue", Screen.getWidth() / 2, Screen.getHeight() / 2 - 16, 41, 11, () -> handleContinueButtonClick());
  private final Button mainMenuButton = new PrimaryButton("Quit To Menu", Screen.getWidth() / 2, Screen.getHeight() / 2, 41, 11, () -> handleMenuButtonClick());
  private final Button exitButton = new PrimaryButton("Exit", Screen.getWidth() / 2, Screen.getHeight() / 2 + 16, 41, 11, () -> handleExitButtonClick());
  /**
   * Sets the callback method that fires when the pause menu gets closed.
   * @param callback The callback method that will get fired when the dialog closes.
   */
  public void setCloseCommand(ICallback callback) {
    this.closeCommand = callback;
  }
  /**
   * Sets the callback method that fires when the user presses quit to main menu.
   * @param callback The callback method that will get fired when the quit to main menu button is pressed.
   */
  public void setQuitToMenuCommand(ICallback callback) {
    this.quitToMenuCommand = callback;
  }
  /**
   * Opens the pause menu.
   */
  public void openMenu() {
    playMenuOpenSound();
  }
  /**
   * Closes the pause menu.
   */
  public void closeMenu() {
    playMenuCloseSound();
  }

  private void playMenuOpenSound() {
    sfx.playSoundEffect(SoundEffects.PAUSE);
  }
  
  private void playMenuCloseSound() {
    sfx.playSoundEffect(SoundEffects.RESUME);
  }
  
  private void handleContinueButtonClick() {
    closeCommand.execute();
  }
  
  private void handleMenuButtonClick() {
    quitToMenuCommand.execute();
  }

  private void handleExitButtonClick() {
    System.exit(0);
  }
  /**
   * Updates the pause menu.
   */
  public void update() {
    continueButton.update();
    mainMenuButton.update();
    exitButton.update();
  }
  /**
   * Renders the pause menu to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, background, .8f, false);
    continueButton.render(screen, gl);
    mainMenuButton.render(screen, gl);
    exitButton.render(screen, gl);
  }
}
