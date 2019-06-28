package com.monkeystomp.spirelands.gui.titlescreen.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.gamedata.saves.DataLoader;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.controlls.button.SaveSlotButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.view.LevelView;
import java.io.IOException;
import java.util.function.Consumer;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aaron Michael McNulty
 */
public class LoadGameView extends TitleView {
  
  private final Sprite  backdrop = new Sprite(375, 175, GameColors.TITLE_SCREEN_MENU_BACKDROP);
  private final int buttonRowY = 214,
                    spaceBetweenButtons = 25,
                    headingY = 60,
                    buttonRowStartX = Screen.getWidth() / 2 - 22;
  private final FontInfo  displayFont = GameFonts.getDarkText_bold_22(),
                          slot1Font = GameFonts.getDarkText_plain_18(),
                          slot2Font = GameFonts.getDarkText_plain_18(),
                          slot3Font = GameFonts.getDarkText_plain_18(),
                          warningFont = GameFonts.getWarningText_bold_18();
  private boolean showWarning = false;
  private String selectedSlot;
  private final PrimaryButton
    cancelButton = new PrimaryButton("Cancel", buttonRowStartX, buttonRowY, 40, 13, () -> handleCancelClick()),
    loadButton = new PrimaryButton("Load", cancelButton.getRight() + spaceBetweenButtons, buttonRowY, 40, 13, () -> handleLoadClick());
  private SaveSlotButton selectedSlotButton;
  private final SaveSlotButton
    slot1 = new SaveSlotButton(Screen.getWidth() / 5, Screen.getHeight() / 2 + 20, "slot1.json", () -> {
      selectedSlot = "slot1.json";
      resetSlotButtons();
      selectedSlotButton = this.slot1;
    }),
    slot2 = new SaveSlotButton(Screen.getWidth() / 2, Screen.getHeight() / 2 + 20, "slot2.json", () -> {
      selectedSlot = "slot2.json";
      resetSlotButtons();
      selectedSlotButton = this.slot2;
    }),
    slot3 = new SaveSlotButton(Screen.getWidth() * 4 / 5, Screen.getHeight() / 2 + 20, "slot3.json", () -> {
      selectedSlot = "slot3.json";
      resetSlotButtons();
      selectedSlotButton = this.slot3;
    });
  private final DataLoader loader = new DataLoader();
  
  public LoadGameView(Consumer<LevelView> ILevelViewSetter, Consumer<TitleView> ITitleViewSetter, Consumer<Float> IVolumeSetter) {
    super(ILevelViewSetter, ITitleViewSetter, IVolumeSetter);
    setupFonts();
  }
  
  private void setupFonts() {
    displayFont.setText("Load Game");
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
  }
  
  private void setWarningText(String message) {
    warningFont.setText(message);
    warningFont.setX(Screen.getWidth() / 2);
    warningFont.setY(loadButton.getTop() - 8);
    warningFont.centerText();
  }
  
  private void handleLoadClick() {
    if (selectedSlotButton != null) {
      if (!selectedSlotButton.isSlotEmpty()) {
        try {
          ILevelViewSetter.accept(loader.getLevelView(selectedSlot));
        } catch (IOException e) {
          e.printStackTrace();
          showWarning = true;
          setWarningText("Problem loading file at saves/" + selectedSlot);
        } catch (ParseException e) {
          e.printStackTrace();
          showWarning = true;
          setWarningText("Problem reading data in file saves/" + selectedSlot);
        }
      }
      else {
        setWarningText("Selected slot does not contain save data!!");
        showWarning = true;
      }
    }
    else {
      setWarningText("Please select a slot to load game!!");
      showWarning = true;
    }
  }
  
  private void handleCancelClick() {
    exitingView();
    ITitleViewSetter.accept(new HomeTitleView(ILevelViewSetter, ITitleViewSetter, IVolumeSetter));
  }
  
  private void resetSlotButtons() {
    slot1.reset();
    slot2.reset();
    slot3.reset();
  }

  @Override
  public void enteringView() {
    showWarning = false;
  }
  
  @Override
  public void exitingView() {
    resetSlotButtons();
  }
  
  @Override
  public void update() {
    slot1.update();
    slot2.update();
    slot3.update();
    cancelButton.update();
    loadButton.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, Screen.getWidth() / 2 - backdrop.getWidth() / 2, Screen.getHeight() / 2 - backdrop.getHeight() / 2 + 20, backdrop, false);
    screen.renderFonts(displayFont);
    screen.renderFonts(slot1Font);
    screen.renderFonts(slot2Font);
    screen.renderFonts(slot3Font);
    slot1.render(screen, gl);
    slot2.render(screen, gl);
    slot3.render(screen, gl);
    if (showWarning) screen.renderFonts(warningFont);
    cancelButton.render(screen, gl);
    loadButton.render(screen, gl);
  }

}
