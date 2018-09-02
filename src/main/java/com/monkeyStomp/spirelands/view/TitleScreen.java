package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.controlls.Button;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TitleScreen extends GameView {

  private Button startButton;
  
  public TitleScreen(ViewManager viewManager) {
    super(viewManager);
    loadAssets();
  }
  
  private void loadAssets() {
    // Using this to set the button temporarily
    startButton = new Button(Screen.getWidth() / 2, Screen.getHeight() / 2, () -> handleStartButtonClick());
  }

  private void handleStartButtonClick() {
    viewManager.setCurrentView(new SpawnLevelView(viewManager));
  }
  
  @Override
  public void update() {
    startButton.update();
  }

  @Override
  public void render(Screen screen) {
    screen.renderColor(0xff0000);
    startButton.render(screen);
  }
}