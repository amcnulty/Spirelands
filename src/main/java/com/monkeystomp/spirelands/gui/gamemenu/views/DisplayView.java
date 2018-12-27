package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 * Abstract class for display views that is used for switching between different views in the game menu display panel.
 * @author Aaron Michael McNulty
 */
public abstract class DisplayView {
  /**
   * Y coordinate of the top most edge in the display view in pixels.
   */
  protected final int TOP = 23;
  /**
   * X coordinate of the right most edge in the display view in pixels.
   */
  protected final int RIGHT = 395;
  /**
   * Y coordinate of the bottom most edge in the display view in pixels.
   */
  protected final int BOTTOM = 178;
  /**
   * X coordinate of the left most edge in the display view in pixels.
   */
  protected final int LEFT = 124;
  /**
   * Updates the display view.
   */
  public void update() {}
  /**
   * Renders the display view to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {}
  
}
