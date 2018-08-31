package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TitleScreen extends GameView {
  
  private int ticks = 0;
  private Sprite button;
  
  public TitleScreen(ViewManager viewManager) {
    super(viewManager);
    loadAssets();
  }
  
  private void loadAssets() {
    button = new Sprite(200, 50, 0x00ff00);
  }
  
  @Override
  public void update() {
    if (ticks == 60 * 4) {
      viewManager.setCurrentView(new SpawnLevel(viewManager));
    }
    else {
    ticks++;
    }
    
  }

  @Override
  public void render(Screen screen) {
    screen.renderColor(0xff0000);
    screen.renderSprite((screen.getWidth() / 2) - (button.getWidth() / 2), (screen.getHeight() / 2) - (button.getHeight() / 2), button);
  }
}