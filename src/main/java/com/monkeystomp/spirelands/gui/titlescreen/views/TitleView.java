package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.view.LevelView;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public abstract class TitleView {
  
  protected Consumer<LevelView> ILevelViewSetter;
  
  public TitleView(Consumer<LevelView> ILevelViewSetter) {
    this.ILevelViewSetter = ILevelViewSetter;
  }
  /**
   * Life cycle method that is called when view is about to close.
   */
  public void exitingView() {}

  public void update() {}
  
  public void render(Screen screen, GL2 gl) {}
}
