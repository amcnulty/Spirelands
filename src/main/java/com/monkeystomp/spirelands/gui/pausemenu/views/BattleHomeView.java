package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.Button;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import java.util.function.Consumer;

/**
 * Main pause menu view for the battle scene.
 * @author Aaron Michael McNulty
 */
public class BattleHomeView extends PauseView {
  
  private final Sprite background = new Sprite(80, 75, GameColors.DIALOG_BOX_BACKGROUND);
  private final int x = (Screen.getWidth() / 2) - (background.getWidth() / 2),
                    y = (Screen.getHeight() / 2) - (background.getHeight() / 2),
                    buttonStartY = (Screen.getHeight() / 2) - 15,
                    spaceBetweenButtons = 10;
  private final Button 
    continueButton = new PrimaryButton("Continue", Screen.getWidth() / 2, buttonStartY, 41, 11, () -> handleContinueButtonClick()),
    mainMenuButton = new PrimaryButton("Quit To Menu", Screen.getWidth() / 2, continueButton.getBottom() + spaceBetweenButtons, 41, 11, () -> handleMenuButtonClick()),
    exitButton = new PrimaryButton("Exit", Screen.getWidth() / 2, mainMenuButton.getBottom() + spaceBetweenButtons, 41, 11, () -> handleExitButtonClick());
  
  public BattleHomeView(Consumer<String> IPauseViewSetter) {
    super(IPauseViewSetter);
  }
  
  private void handleContinueButtonClick() {
    closeCommand.execute();
  }
  
  private void handleMenuButtonClick() {
    IPauseViewSetter.accept(PauseMenu.CONFIRM_QUIT_TO_MENU_VIEW);
  }

  private void handleExitButtonClick() {
    IPauseViewSetter.accept(PauseMenu.CONFIRM_EXIT_VIEW);
  }

  @Override
  public void enteringView() {
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
    continueButton.update();
    mainMenuButton.update();
    exitButton.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, background, .8f, false);
//    screen.renderSprite(gl, x, Screen.getHeight() / 2, background, .8f, false);
    continueButton.render(screen, gl);
    mainMenuButton.render(screen, gl);
    exitButton.render(screen, gl);
  }

}
