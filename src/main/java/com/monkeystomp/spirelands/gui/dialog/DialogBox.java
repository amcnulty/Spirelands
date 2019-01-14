package com.monkeystomp.spirelands.gui.dialog;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
/**
 * Dialog boxes are used to show conversations between NPCs and other dialog to the player.
 * @author Aaron Michael McNulty
 */
public class DialogBox {

  private final int DIALOG_WIDTH = 240,
                    DIALOG_LEFT = 16,
                    DIALOG_TOP = 16,
                    DIALOG_PADDING_TOP = 10,
                    DIALOG_PADDING_SIDES = 6,
                    DIALOG_PADDING_BOTTOM = 12,
                    MESSAGE_SPEED = 3,
                    BACKGROUND_COLOR = GameColors.DIALOG_BOX_BACKGROUND;
  private final String  DOWN_TRIANGLE = "\u25BE";
  private String[] messages;
  private ArrayList<String> lineMessages = new ArrayList<>(),
                            displayLineMessages = new ArrayList<>();
  private boolean messageBuilding = false,
                  dialogReady = false;
  private Sprite background;
  private FontRenderContext frc = new FontRenderContext(null, true, true);
  private int timer = 0,
              symbolAnim = 0,
              messageIndex = 0,
              substringIndex = 0,
              renderLineIndex = 0,
              dialogLineHeight,
              dialogCurrentHeight,
              symbolX = DIALOG_WIDTH + 4,
              symbolY = 0;
  private FontInfo symbolFontInfo = GameFonts.getDialogBoxSymbolFont();
  private ArrayList<FontInfo> lines = new ArrayList<>();
  private ICallback callback;
  /**
   * Sets the callback method that fires when the dialog gets closed.
   * @param callback The callback method that will get fired when the dialog closes.
   */
  public void setCloseCommand(ICallback callback) {
    this.callback = callback;
  }
  /**
   * Call this method when the space key is pressed to advance through the dialog when the dialog box is open.
   */
  public void handleSpaceKey() {
    if (dialogReady) {
      if (++messageIndex < messages.length) {
        advanceDialog();
      }
      else closeDialog();
    }
  }

  private void closeDialog() {
    dialogReady = false;
    messageIndex = 0;
    callback.execute();
  }

  private void advanceDialog() {
    displayMessage(messageIndex);
  }
  /**
   * Opens the dialog with the set of messages.
   * @param messages An array of messages to be displayed in the dialog. Each index will create a new slide.
   */
  public void openDialog(String[] messages) {
    dialogReady = false;
    if (messages == null) {
      closeDialog();
      return;
    }
    this.messages = messages;
    displayMessage(messageIndex);
    dialogReady = true;
  }
  
  private void displayMessage(int messageIndex) {
    lines.clear();
    lineMessages.clear();
    displayLineMessages.clear();
    substringIndex = 0;
    renderLineIndex = 0;
    lineMessages = TextUtil.createWrappedText(messages[messageIndex], GameFonts.getDarkText_bold_22().getFont(), DIALOG_WIDTH - (DIALOG_PADDING_SIDES * 2));
    for (int i = 0; i < lineMessages.size(); i++) {
      displayLineMessages.add("");
      FontInfo info = GameFonts.getDarkText_bold_22();
      info.setText("");
      info.setX(DIALOG_LEFT + DIALOG_PADDING_SIDES);
      info.setY(DIALOG_TOP + DIALOG_PADDING_TOP);
      lines.add(info);
    }
    createBackground();
    setSymbol();
    updateFontInfo();
    messageBuilding = true;
  }

  private void createBackground() {
    dialogLineHeight = (int)(lines.get(0).getFont().getStringBounds("random string", frc).getHeight() / Screen.getScaleY());
    dialogCurrentHeight = dialogLineHeight * lineMessages.size() + DIALOG_PADDING_TOP + DIALOG_PADDING_BOTTOM;
    background = new Sprite(
      DIALOG_WIDTH,
      dialogCurrentHeight,
      BACKGROUND_COLOR
    );
  }
  
  private void setSymbol() {
    if (messageIndex < messages.length - 1) symbolFontInfo.setText(DOWN_TRIANGLE);
    else symbolFontInfo.setText("");
    symbolY = DIALOG_TOP + dialogCurrentHeight - 8;
    symbolFontInfo.setX(symbolX);
    symbolFontInfo.setY(symbolY);
  }

  private void updateFontInfo() {
    for (int i = 0; i < lines.size(); i++) {
      lines.get(i).setText(displayLineMessages.get(i));
      lines.get(i).setY(DIALOG_TOP + DIALOG_PADDING_TOP + dialogLineHeight * i + (dialogLineHeight / 2));
    }
  }

  private void buildCurrentMessage() {
    if (timer % MESSAGE_SPEED == 0) {
      if (substringIndex < lineMessages.get(renderLineIndex).length()) {
        displayLineMessages.set(renderLineIndex, lineMessages.get(renderLineIndex).substring(0, ++substringIndex));
        updateFontInfo();
      }
      else {
        if (++renderLineIndex < lineMessages.size()) {
          substringIndex = 0;
          buildCurrentMessage();
        }
        else {
          messageBuilding = false;
          timer = 0;
        }
      }
    }
    timer++;
  }
  
  private void animateSymbol() {
    switch (symbolAnim % 48) {
      case 0:
        symbolY++;
      break;
      case 12:
        symbolY--;
      break;
      case 24:
        symbolY--;
      break;
      case 36:
        symbolY++;
      break;
    }
    symbolFontInfo.setY(symbolY);
    symbolAnim++;
  }
  /**
   * Updates the dialog box.
   */
  public void update() {
    if (messageBuilding) {
       buildCurrentMessage();
    }
    if (messageIndex < messages.length - 1) {
      animateSymbol();
    }
  }
  /**
   * Renders the dialog box to the screen.
   * @param screen Instance of the Screen class.
   * @param gl Instance of the GL2 class.
   */
  public void render(Screen screen, GL2 gl) {
    if (dialogReady) {
      screen.renderSprite(gl, DIALOG_LEFT, DIALOG_TOP, background, .8f, false);
      for (int i = 0; i < lines.size(); i++) {
        screen.renderFonts(lines.get(i));
      }
      screen.renderFonts(symbolFontInfo);
    }
  }
}
