package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ConfirmExitView extends PauseView {
  
  private final Sprite  backdrop = new Sprite(120, 60, GameColors.TITLE_SCREEN_MENU_BACKDROP);

  public ConfirmExitView(Consumer<String> IPauseViewSetter) {
    super(IPauseViewSetter);
  }

  @Override
  public void enteringView() {
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2, backdrop, false);
  }

}
