package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ConfirmExitView extends PauseView {

  public ConfirmExitView(Consumer<String> IPauseViewSetter) {
    super(IPauseViewSetter);
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
  }

  @Override
  public void render(Screen screen, GL2 gl) {
  }

}
