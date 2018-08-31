package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ViewManager {
  
  private GameView view;
  
  public void update() {
    view.update();
  }
  
  public void render(Screen screen) {
    view.render(screen);
  }
  
  public void setCurrentView(GameView newView) {
    this.view = newView;
  }
}
