package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class DisplayView {
  
  protected final int TOP = 23;
  protected final int RIGHT = 395;
  protected final int BOTTOM = 178;
  protected final int LEFT = 124;

  public void update() {}
  
  public void render(Screen screen, GL2 gl) {}
  
}
