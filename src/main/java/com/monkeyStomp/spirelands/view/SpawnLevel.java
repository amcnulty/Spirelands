package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends GameView {
  
  private int ticks = 0;

  public SpawnLevel(ViewManager viewManager) {
    super(viewManager);
  }
  
  @Override
  public void update() {
    if (ticks == 60 * 4) {
      viewManager.setCurrentView(new TitleScreen(viewManager));
    }
    else ticks++;
  }

  @Override
  public void render(Screen screen) {
    screen.renderColor(0x0079CC);
  }
}