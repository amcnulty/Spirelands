package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ViewManager {
  
  private static ViewManager instance = new ViewManager();

  private GameView view;

  private ViewManager() {
  }

  public static ViewManager getViewManager() {
    return instance;
  }
  
  public void update() {
    view.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    view.render(screen, gl);
  }
  
  public void changeView(GameView newView) {
    view.leaveView();
    setView(newView);
  }
  
  public void setView(GameView view) {
    this.view = view;
  }
}