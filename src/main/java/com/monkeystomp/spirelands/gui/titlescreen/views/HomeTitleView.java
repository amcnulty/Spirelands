package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.gamedata.saves.DataLoader;
import com.monkeystomp.spirelands.gamedata.saves.SaveGameSlot;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.view.LevelView;
import java.util.function.Consumer;

/**
 *  The landing screen of the application after the opening brand animation.
 * @author Aaron Michael McNulty
 */
public class HomeTitleView extends TitleView {
  
  private final DataLoader loader = new DataLoader();
  private final Sprite buttonBackdrop = new Sprite(120, 100, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final PrimaryButton newGameButton,
                              loadGameButton,
                              settingsButton,
                              exitButton;
  private final int spaceBetweenButtons = 12;
  
  public HomeTitleView(Consumer<LevelView> ILevelViewSetter, Consumer<TitleView> ITitleViewSetter, Consumer<Float> IVolumeSetter) {
    super(ILevelViewSetter, ITitleViewSetter, IVolumeSetter);
    newGameButton = new PrimaryButton("New Game", Screen.getWidth() / 2, Screen.getHeight() / 2 - 29, 60, 15, () -> handleStartButtonClick());
    loadGameButton = new PrimaryButton("Load Game", Screen.getWidth() / 2, newGameButton.getBottom() + spaceBetweenButtons, 60, 15, () -> handleLoadClick());
    settingsButton = new PrimaryButton("Settings", Screen.getWidth() / 2, loadGameButton.getBottom() + spaceBetweenButtons, 60, 15, () -> handleSettingsClick());
    exitButton = new PrimaryButton("Exit", Screen.getWidth() / 2, settingsButton.getBottom() + spaceBetweenButtons, 60, 15, () -> handleExitClick());
  }

  private void handleStartButtonClick() {
    try {
      loader.loadGame(SaveGameSlot.NEW_GAME);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void handleLoadClick() {
    ITitleViewSetter.accept(new LoadGameView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  private void handleSettingsClick() {
    ITitleViewSetter.accept(new SettingsView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  private void handleExitClick() {
    System.exit(0);
  }

  @Override
  public void enteringView() {
  }
  
  @Override
  public void exitingView() {}
  
  @Override
  public void update() {
    newGameButton.update();
    loadGameButton.update();
    settingsButton.update();
    exitButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - buttonBackdrop.getWidth() / 2, Screen.getHeight() / 2 - buttonBackdrop.getHeight() / 2, buttonBackdrop, false);
    newGameButton.render(screen, gl);
    loadGameButton.render(screen, gl);
    settingsButton.render(screen, gl);
    exitButton.render(screen, gl);
  }
  

}
