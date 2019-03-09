package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SaveView extends PauseView {
  
  private final Sprite  backdrop = new Sprite(375, 175, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final int buttonRowY = 214,
                    spaceBetweenButtons = 25,
                    headingY = 60,
                    buttonRowStartX = Screen.getWidth() / 2 - 22;
  private final FontInfo  displayFont = GameFonts.getDarkText_bold_22(),
                          slot1Font = GameFonts.getDarkText_plain_18(),
                          slot2Font = GameFonts.getDarkText_plain_18(),
                          slot3Font = GameFonts.getDarkText_plain_18();
  private final PrimaryButton
    cancelButton = new PrimaryButton("Cancel", buttonRowStartX, buttonRowY, 40, 13, () -> handleCancelClick()),
    saveButton = new PrimaryButton("Save", cancelButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleSaveClick());
  
  public SaveView(Consumer<String> IPauseViewSetter) {
    super(IPauseViewSetter);
    setupFonts();
  }
  
  private void setupFonts() {
    displayFont.setText("Save Game");
    displayFont.setX(Screen.getWidth() / 2);
    displayFont.setY(headingY);
    displayFont.centerText();
    slot1Font.setText("Slot 1");
    slot1Font.setX(Screen.getWidth() / 5);
    slot1Font.setY(headingY + 30);
    slot1Font.centerText();
    slot2Font.setText("Slot 2");
    slot2Font.setX(Screen.getWidth() / 2);
    slot2Font.setY(headingY + 30);
    slot2Font.centerText();
    slot3Font.setText("Slot 3");
    slot3Font.setX(Screen.getWidth() * 4 / 5);
    slot3Font.setY(headingY + 30);
    slot3Font.centerText();
  }
  
  private void handleSaveClick() {
    
  }
  
  private void handleCancelClick() {
    IPauseViewSetter.accept(PauseMenu.HOME_VIEW);
  }

  @Override
  public void exitingView() {
  }
  
  @Override
  public void update() {
    cancelButton.update();
    saveButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2 + 20, backdrop, false);
    screen.renderFonts(displayFont);
    screen.renderFonts(slot1Font);
    screen.renderFonts(slot2Font);
    screen.renderFonts(slot3Font);
    cancelButton.render(screen, gl);
    saveButton.render(screen, gl);
  }

}
