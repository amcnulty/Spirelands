package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ViewManager {
  
  private static final ViewManager INSTANCE = new ViewManager();

  private GameView view;

  private ViewManager() {
  }

  public static ViewManager getViewManager() {
    return INSTANCE;
  }
  
  public void update() {
    view.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    view.render(screen, gl);
  }
  /**
   * Method used to change to a new view. This will call leaveView() lifecycle method.
   * @param newView The view to change to.
   */
  public void changeView(GameView newView) {
    view.leaveView();
    setView(newView);
  }
  /**
   * Used to set a view for the first time on game startup. WARNING! DO NOT CALL THIS METHOD DIRECTLY USE changeView() INSTEAD.
   * @param view The view to set as the current view.
   */
  public void setView(GameView view) {
    this.view = view;
  }
}