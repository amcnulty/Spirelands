package com.monkeystomp.spirelands.gui.pausemenu;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.input.ICallback;

public class PauseMenu {

  private final Sprite background = new Sprite(80, 100, 0xefefef);

  private final SoundEffects sfx = new SoundEffects();
  
  private final int x = (Screen.getWidth() / 2) - (background.getWidth() / 2),
                    y = (Screen.getHeight() / 2) - (background.getHeight() / 2);

  private ICallback closeCommand,
                    quitToMenuCommand;
  
  private final Button continueButton = new PrimaryButton("Continue", Screen.getWidth() / 2, Screen.getHeight() / 2 - 16, 41, 11, () -> handleContinueButtonClick());
  private final Button mainMenuButton = new PrimaryButton("Quit To Menu", Screen.getWidth() / 2, Screen.getHeight() / 2, 41, 11, () -> handleMenuButtonClick());
  private final Button exitButton = new PrimaryButton("Exit", Screen.getWidth() / 2, Screen.getHeight() / 2 + 16, 41, 11, () -> handleExitButtonClick());

  public void setCloseCommand(ICallback callback) {
    this.closeCommand = callback;
  }
  
  public void setQuitToMenuCommand(ICallback callback) {
    this.quitToMenuCommand = callback;
  }
  
  public void openMenu() {
    playMenuOpenSound();
  }
  
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

  public void update() {
    continueButton.update();
    mainMenuButton.update();
    exitButton.update();
  }

  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(x, y, background, 8, false, false);
    continueButton.render(screen, gl);
    mainMenuButton.render(screen, gl);
    exitButton.render(screen, gl);
  }
}