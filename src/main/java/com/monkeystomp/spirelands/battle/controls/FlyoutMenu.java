package com.monkeystomp.spirelands.battle.controls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.Button;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class FlyoutMenu {
  
  private final Sprite menuBackground = new Sprite(61, 45, GameColors.DIALOG_BOX_BACKGROUND);
  private int menuX = Screen.getWidth() - 80,
              menuY = -menuBackground.getHeight(),
              tick = 0;
  private final Button dropdownButton =
    new Button(
      Screen.getWidth() - 50,
      4,
      new Sprite("./resources/gui/battle_flyout_arrow_default.png", 20, 8),
      new Sprite("./resources/gui/battle_flyout_arrow_hover.png", 20, 8),
      new Sprite("./resources/gui/battle_flyout_arrow_down.png", 20, 8),
      new Sprite("./resources/gui/battle_flyout_arrow_default.png", 20, 8),
      () -> {animating = true;}
    );
  private final PrimaryButton pauseButton =
    new PrimaryButton(
      "Pause",
      menuBackground.getWidth() / 2 + menuX + 1,
      menuY + 16,
      41,
      11,
      () -> {handlePauseClick();}
    ),
    escapeButton = new PrimaryButton(
      "Escape",
      menuBackground.getWidth() / 2 + menuX + 1,
      menuY + 30,
      41,
      11,
      () -> {handleEscapeClick();}
    );
  private final Button exitButton = new Button(
    menuBackground.getWidth() + menuX - 5,
    menuY + 5,
    new Sprite("./resources/gui/battle_flyout_exit_default.png", 4, 4),
    new Sprite("./resources/gui/battle_flyout_exit_hover.png", 4, 4),
    new Sprite("./resources/gui/battle_flyout_exit_down.png", 4, 4),
    new Sprite("./resources/gui/battle_flyout_exit_default.png", 4, 4),
    () -> {animating = true;}
  );  
  private boolean menuHidden = true,
                  animating = false;
  private ICallback pauseCommand,
                    escapeCommand;
  
  public void setPauseCommand(ICallback callback) {
    pauseCommand = callback;
  }
  
  public void setEscapeCommand(ICallback callback) {
    escapeCommand = callback;
  }
  
  private void handlePauseClick() {
    pauseCommand.execute();
  }
  
  private void handleEscapeClick() {
    escapeCommand.execute();
  }
  
  public void animate() {
    if (menuHidden) {
      if (dropdownButton.getTop() != -8) {
        dropdownButton.setTop(dropdownButton.getTop() - 1);
      }
      if (menuY != 0) {
        menuY++;
        menuY++;
        menuY++;
        exitButton.setTop(exitButton.getTop() + 3);
        pauseButton.setTop(pauseButton.getTop() + 3);
        pauseButton.setFontLocation();
        escapeButton.setTop(escapeButton.getTop() + 3);
        escapeButton.setFontLocation();
      }
      else {
        menuHidden = false;
        animating = false;
      }
    }
    else {
      if (menuY != -menuBackground.getHeight()) {
        menuY--;
        menuY--;
        menuY--;
        exitButton.setTop(exitButton.getTop() - 3);
        pauseButton.setTop(pauseButton.getTop() - 3);
        pauseButton.setFontLocation();
        escapeButton.setTop(escapeButton.getTop() - 3);
        escapeButton.setFontLocation();
      }
      else {
        if (dropdownButton.getTop() != 0) {
          dropdownButton.setTop(dropdownButton.getTop() + 1);
        }
        else {
          menuHidden = true;
          animating = false;
        }
      }
    }
  }
  
  public void update() {
    if (animating) {
      animate();
    }
    else {
      dropdownButton.update();
      pauseButton.update();
      escapeButton.update();
      exitButton.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, menuX, menuY, menuBackground, .8f, false);
    dropdownButton.render(screen, gl);
    exitButton.render(screen, gl);
    pauseButton.render(screen, gl);
    escapeButton.render(screen, gl);
  }
}
