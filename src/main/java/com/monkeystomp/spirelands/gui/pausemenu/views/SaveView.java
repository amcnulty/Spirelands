package com.monkeystomp.spirelands.gui.pausemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.gamedata.saves.SaveDataManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.PrimaryButton;
import com.monkeystomp.spirelands.gui.controlls.SaveSlotButton;
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
public class SaveView extends PauseView {
  
  private final Sprite  backdrop = new Sprite(375, 175, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final int buttonRowY = 194,
                    spaceBetweenButtons = 25,
                    headingY = 40,
                    buttonRowStartX = Screen.getWidth() / 2 - 22;
  private final FontInfo  displayFont = GameFonts.getDarkText_bold_22(),
                          slot1Font = GameFonts.getDarkText_plain_18(),
                          slot2Font = GameFonts.getDarkText_plain_18(),
                          slot3Font = GameFonts.getDarkText_plain_18(),
                          errorFont = GameFonts.getWarningText_bold_18();
  private boolean slotSelected = false,
                  showErrorMessage = false;
  private final PrimaryButton
    cancelButton = new PrimaryButton("Cancel", buttonRowStartX, buttonRowY, 40, 13, () -> handleCancelClick()),
    saveButton = new PrimaryButton("Save", cancelButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleSaveClick());
  private SaveSlotButton selectedSlot;
  private final SaveSlotButton
    slot1 = new SaveSlotButton(Screen.getWidth() / 5, Screen.getHeight() / 2, "slot1.json", () -> {
      SaveDataManager.getSaveDataManager().setFileName("slot1.json");
      resetSlotButtons();
      slotSelected = true;
      selectedSlot = this.slot1;
    }),
    slot2 = new SaveSlotButton(Screen.getWidth() / 2, Screen.getHeight() / 2, "slot2.json", () -> {
      SaveDataManager.getSaveDataManager().setFileName("slot2.json");
      resetSlotButtons();
      slotSelected = true;
      selectedSlot = this.slot2;
    }),
    slot3 = new SaveSlotButton(Screen.getWidth() * 4 / 5, Screen.getHeight() / 2, "slot3.json", () -> {
      SaveDataManager.getSaveDataManager().setFileName("slot3.json");
      resetSlotButtons();
      slotSelected = true;
      selectedSlot = this.slot3;
    });
    
  
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
    slot1Font.setY(headingY + 15);
    slot1Font.centerText();
    slot2Font.setText("Slot 2");
    slot2Font.setX(Screen.getWidth() / 2);
    slot2Font.setY(headingY + 15);
    slot2Font.centerText();
    slot3Font.setText("Slot 3");
    slot3Font.setX(Screen.getWidth() * 4 / 5);
    slot3Font.setY(headingY + 15);
    slot3Font.centerText();
    errorFont.setText("Plese select a slot to save game!!");
    errorFont.setX(Screen.getWidth() / 2);
    errorFont.setY(saveButton.getTop() - 8);
    errorFont.centerText();
  }
  
  private void resetSlotButtons() {
    slot1.reset();
    slot2.reset();
    slot3.reset();
    slotSelected = false;
    selectedSlot = null;
  }
  
  private void handleSaveClick() {
    if (slotSelected && selectedSlot != null) {
      if (!selectedSlot.isSlotEmpty()) {
        IPauseViewSetter.accept(PauseMenu.CONFIRM_SAVE_VIEW);
      }
      else {
        try {
          SaveDataManager.getSaveDataManager().saveGame();
          initSlots();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    else {
      showErrorMessage = true;
    }
  }
  
  private void handleCancelClick() {
    IPauseViewSetter.accept(PauseMenu.HOME_VIEW);
  }
  
  private void initSlots() {
    slot1.initSlot();
    slot2.initSlot();
    slot3.initSlot();
  }

  @Override
  public void enteringView() {
    initSlots();
  }

  @Override
  public void exitingView() {
    resetSlotButtons();
    showErrorMessage = false;
  }
  
  @Override
  public void update() {
    slot1.update();
    slot2.update();
    slot3.update();
    cancelButton.update();
    saveButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2, backdrop, false);
    screen.renderFonts(displayFont);
    screen.renderFonts(slot1Font);
    screen.renderFonts(slot2Font);
    screen.renderFonts(slot3Font);
    slot1.render(screen, gl);
    slot2.render(screen, gl);
    slot3.render(screen, gl);
    if (showErrorMessage && !slotSelected) screen.renderFonts(errorFont);
    cancelButton.render(screen, gl);
    saveButton.render(screen, gl);
  }

}
