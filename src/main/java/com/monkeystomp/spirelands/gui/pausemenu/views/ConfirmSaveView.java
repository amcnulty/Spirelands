package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.io.IOException;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ConfirmSaveView extends PauseView {
  
  private final Sprite  backdrop = new Sprite(120, 50, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final PrimaryButton
    saveButton = new PrimaryButton("Save", Screen.getWidth() / 2 + 22, Screen.getHeight() / 2 + 8, 40, 13, () -> {
      handleSaveButton();
    }),
    cancelButton = new PrimaryButton("Cancel", Screen.getWidth() / 2 - 22, Screen.getHeight() / 2 + 8, 40, 13, () -> {
      IPauseViewSetter.accept(PauseMenu.SAVE_VIEW);
    });
  private final FontInfo  headingFont1 = GameFonts.getDarkText_plain_18(),
                          headingFont2 = GameFonts.getDarkText_plain_18();
  
  public ConfirmSaveView(Consumer<String> IPauseViewSetter) {
    super(IPauseViewSetter);
    setupFonts();
  }
  
  private void setupFonts() {
    headingFont1.setText("The file already exists.");
    headingFont1.setX(Screen.getWidth() / 2);
    headingFont1.setY(Screen.getHeight() / 2 - 16);
    headingFont1.centerText();
    headingFont2.setText("Do you want to overwrite it?");
    headingFont2.setX(Screen.getWidth() / 2);
    headingFont2.setY(Screen.getHeight() / 2 - 8);
    headingFont2.centerText();
  }
  
  private void handleSaveButton() {
    try {
      SaveDataManager.getSaveDataManager().saveGame();
      IPauseViewSetter.accept(PauseMenu.SAVE_VIEW);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enteringView() {
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
    saveButton.update();
    cancelButton.update();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2, backdrop, false);
    screen.renderFonts(headingFont1);
    screen.renderFonts(headingFont2);
    saveButton.render(screen, gl);
    cancelButton.render(screen, gl);
  }

}
