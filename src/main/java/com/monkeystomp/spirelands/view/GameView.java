package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameView {
  
  protected ViewManager viewManager = ViewManager.getViewManager();

  public GameView() {
  }
  
  public void setView() {
    viewManager.setView(this);
  }
  
  public void leaveView() {
  }
  
  public void update() {
  }
  
  public void render(Screen screen) {
  }
}