package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameView {
  
  protected ViewManager viewManager;
  
  public GameView(ViewManager viewManager) {
    this.viewManager = viewManager;
    this.viewManager.setCurrentView(this);
  }
  
  public void update() {
  }
  
  public void render(Screen screen) {
  }
}
