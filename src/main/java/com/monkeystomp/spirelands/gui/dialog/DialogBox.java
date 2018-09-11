package com.monkeystomp.spirelands.gui.dialog;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.input.INotify;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.Font;
import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class DialogBox {

  private final int DIALOG_WIDTH = 240,
                    DIALOG_LEFT = 16,
                    DIALOG_TOP = 16,
                    DIALOG_PADDING_TOP = 10,
                    DIALOG_PADDING_SIDES = 6,
                    DIALOG_PADDING_BOTTOM = 12,
                    MESSAGE_SPEED = 3,
                    BACKGROUND_COLOR = 0xEFEFEF;
                    
  
  private final Color TEXT_COLOR = new Color(0x323232);
  private String[]  messages,
                    words;
  private ArrayList<String> lineMessages = new ArrayList<>(),
                            displayLineMessages = new ArrayList<>();
  private boolean messageBuilding = false;
  private Sprite background;
  private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);
  private FontRenderContext frc = new FontRenderContext(null, true, true);
  private int timer = 0,
              messageIndex = 0,
              substringIndex = 0,
              renderLineIndex = 0,
              dialogLineHeight;
  private ArrayList<FontInfo> lines = new ArrayList<>();
  private INotify notifier = (KeyEvent e) -> handleSpaceKey(e);
  private ICallback callback;

  public void setCloseCommand(ICallback callback) {
    this.callback = callback;
  }

  private void handleSpaceKey(KeyEvent e) {
    if (e.getKeyCode() == Keyboard.SPACE_KEY) {
      if (++messageIndex < messages.length) {
        advanceDialog();
      }
      else closeDialog();
    }
  }

  private void closeDialog() {
    Keyboard.getKeyboard().removeKeyPressNotifier(notifier);
    callback.execute();
  }

  private void advanceDialog() {
    lines.clear();
    lineMessages.clear();
    displayLineMessages.clear();
    substringIndex = 0;
    displayMessage(messageIndex);
  }
  
  public void openDialog(String[] messages) {
    this.messages = messages;
    displayMessage(messageIndex);
    Keyboard.getKeyboard().addKeyPressNotifier(notifier);
  }
  
  private void displayMessage(int messageIndex) {
    words = messages[messageIndex].split(" ");
    setLines(0, 0);
    createBackground();
  }

  private void setLines(int lineIndex, int wordIndex) {
    lines.add(new FontInfo(font, TEXT_COLOR, "", DIALOG_LEFT + DIALOG_PADDING_SIDES, DIALOG_TOP + DIALOG_PADDING_TOP));
    lineMessages.add("");
    displayLineMessages.add("");
    double lineTotal = 0;
    String  testString = "";
    for (int i = wordIndex; i < words.length; i++) {
      if (lineMessages.get(lineIndex).length() == 0) {
        testString += words[i];
        lineTotal += font.getStringBounds(words[i], frc).getWidth() / Screen.getScaleX();
      }
      else {
        testString += " " + words[i];
        lineTotal += font.getStringBounds(" " + words[i], frc).getWidth() / Screen.getScaleX();
      }
      if (lineTotal < (DIALOG_WIDTH - (DIALOG_PADDING_SIDES * 2))) {
        lineMessages.set(lineIndex, testString);
      }
      else {
        if (i < words.length) {
          setLines(++lineIndex, i);
        }
        break;
      }
    }
    updateFontInfo();
    messageBuilding = true;
    renderLineIndex = 0;
  }
  
  private void createBackground() {
    dialogLineHeight = (int)(font.getStringBounds("random string", frc).getHeight() / Screen.getScaleY());
    background = new Sprite(
      DIALOG_WIDTH,
      dialogLineHeight * lineMessages.size() + DIALOG_PADDING_TOP + DIALOG_PADDING_BOTTOM,
      BACKGROUND_COLOR
    );
  }

  private void updateFontInfo() {
    for (int i = 0; i < lines.size(); i++) {
      lines.set(
        i,
        new FontInfo(
          font,
          TEXT_COLOR,
          displayLineMessages.get(i),
          DIALOG_LEFT + DIALOG_PADDING_SIDES,
          DIALOG_TOP + DIALOG_PADDING_TOP + dialogLineHeight * i + (dialogLineHeight / 2)
        )
      );
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
        else messageBuilding = false;
      }
    }
    timer++;
  }

  public void update() {
    if (messageBuilding) {
       buildCurrentMessage();
    }
    else timer = 0;
  }

  public void render(Screen screen) {
    screen.renderSprite(DIALOG_TOP, DIALOG_LEFT, background, 8, false);
    for (int i = 0; i < lines.size(); i++) {
      screen.addText(lines.get(i));
    }
  }
}