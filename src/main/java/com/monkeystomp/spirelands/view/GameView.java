package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameView {
  
  protected ViewManager viewManager = ViewManager.getViewManager();

  public GameView() {
  }
  /**
   * Used to change view when there is an existing view in place.
   */
  public void changeView() {
    viewManager.changeView(this);
  }
  /**
   * Used to set a view for the first time.
   */
  public void setView() {
    viewManager.setView(this);
  }
  
  public void leaveView() {
  }
  
  public void update() {
  }
  
  public void render(Screen screen, GL2 gl) {
  }
}