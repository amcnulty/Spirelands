package com.monkeystomp.spirelands.gui.interfaces;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 * Viewable is used to represent a window view that is displayed in the the UI. Use this interface when creating a window, tab, pop up box or anything that requires UI rendering in a windowed fashion.
 * @author Aaron Michael McNulty
 */
public interface Viewable {
  /**
   * Life cycle method that is called when the view is being closed.
   */
  public void exitingView();
  /**
   * Updates the view.
   */
  public void update();
  /**
   * Renders the view to the screen.
   * @param screen Instance of the screen class.
   * @param gl OpenGL handle used for rendering.
   */
  public void render(Screen screen, GL2 gl);
  
}
