package com.monkeystomp.spirelands.gui.pausemenu;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.input.ICallback;

public class PauseMenu {

  private final Sprite background = new Sprite(60, 60, 0xefefef);

  private final int x = (Screen.getWidth() / 2) - (background.getWidth() / 2),
                    y = (Screen.getHeight() / 2) - (background.getHeight() / 2);

  private ICallback callback;
  
  private final Button continueButton = new Button("Continue", Screen.getWidth() / 2, Screen.getHeight() / 2 - 6, 41, 11, () -> handleContinueButtonClick());
  private final Button exitButton = new Button("Exit", Screen.getWidth() / 2, Screen.getHeight() / 2 + 6, 41, 11, () -> handleExitButtonClick());

  public void setCloseCommand(ICallback callback) {
    this.callback = callback;
  }

  private void handleContinueButtonClick() {
    callback.execute();
  }

  private void handleExitButtonClick() {
    System.exit(0);
  }

  public void update() {
    continueButton.update();
    exitButton.update();
  }

  public void render(Screen screen) {
    screen.renderSprite(x, y, background, 8, false);
    continueButton.render(screen);
    exitButton.render(screen);
  }
}