package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ConfirmQuitToMenuView extends PauseView {
  
  private final Sprite  backdrop = new Sprite(120, 50, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final PrimaryButton
    exitButton = new PrimaryButton("Quit To Menu", Screen.getWidth() / 2 + 22, Screen.getHeight() / 2 + 8, 41, 11, () -> {
      quitToMenuCommand.execute();
    }),
    cancelButton = new PrimaryButton("Cancel", Screen.getWidth() / 2 - 22, Screen.getHeight() / 2 + 8, 41, 11, () -> {
      IPauseViewSetter.accept(PauseMenu.HOME_VIEW);
    });
  private final FontInfo  headerFont1 = GameFonts.getDarkText_plain_18(),
                          headerFont2 = GameFonts.getDarkText_plain_18();
  
  public ConfirmQuitToMenuView(Consumer<String> IPauseViewSetter) {
    super(IPauseViewSetter);
    setupFonts();
  }
  
  private void setupFonts() {
    headerFont1.setText("Are you sure you want to quit?");
    headerFont1.setX(Screen.getWidth() / 2);
    headerFont1.setY(Screen.getHeight() / 2 - 16);
    headerFont1.centerText();
    headerFont2.setText("Unsaved data will be lost.");
    headerFont2.setX(Screen.getWidth() / 2);
    headerFont2.setY(Screen.getHeight() / 2 - 8);
    headerFont2.centerText();
  }

  @Override
  public void enteringView() {
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
    exitButton.update();
    cancelButton.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2, backdrop, false);
    screen.renderFonts(headerFont1);
    screen.renderFonts(headerFont2);
    exitButton.render(screen, gl);
    cancelButton.render(screen, gl);
  }

}
