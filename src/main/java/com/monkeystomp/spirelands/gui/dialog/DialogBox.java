package com.monkeystomp.spirelands.gui.dialog;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import java.awt.Font;
import java.awt.Color;
import java.awt.font.FontRenderContext;

public class DialogBox {

  private final int DIALOG_WIDTH = 240;

  private String[]  messages,
                    words;
  private String[] lineMessages = new String[3];
  private boolean messageBuilding = false;
  private Sprite background = new Sprite(DIALOG_WIDTH, 80, 0xEFEFEF);
  private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
  private FontRenderContext frc = new FontRenderContext(null, true, true);
  private int x = 20,
              line1Y = 22,
              line2Y = 44,
              line3Y = 66,
              dialogLineHeight = 20,
              timer = 0,
              messageIndex = 1,
              substringIndex = 0;
  private FontInfo  line1 = new FontInfo(font, new Color(0x323232), lineMessages[0], x, line1Y),
                    line2 = new FontInfo(font, new Color(0x323232), lineMessages[1], x, line2Y),
                    line3 = new FontInfo(font, new Color(0x323232), lineMessages[2], x, line3Y);

  public DialogBox(String[] messages) {
    this.messages = messages;
    words = messages[1].split(" ");
    setLines(0, 0);
  }

  private void setLines(int lineIndex, int wordIndex) {
    double lineTotal = 0;
    String  testString = "";
    for (int i = wordIndex; i < words.length; i++) {
      if (i == 0) {
        testString += words[i];
        lineTotal += font.getStringBounds(words[i], frc).getWidth() / Screen.getScaleX();
      }
      else {
        testString += " " + words[i];
        lineTotal += font.getStringBounds(" " + words[i], frc).getWidth() / Screen.getScaleX();
      }
      if (lineTotal < (DIALOG_WIDTH)) {
        lineMessages[lineIndex] = testString;
      }
      else {
        if (++lineIndex < lineMessages.length) {
          setLines(lineIndex++, i);
        }
      }
    }
    updateFontInfo();
    messageBuilding = true;
  }

  private void updateFontInfo() {
    line1 = new FontInfo(font, new Color(0x323232), lineMessages[0], x, line1Y);
    line2 = new FontInfo(font, new Color(0x323232), lineMessages[1], x, line2Y);
    line3 = new FontInfo(font, new Color(0x323232), lineMessages[2], x, line3Y);
  }

  private void buildCurrentMessage() {
    // if (timer % 3 == 0) {
    //   if (substringIndex < this.messages[messageIndex].length() + 1) {
    //     line1Message = this.messages[messageIndex].substring(0, substringIndex++);
    //     updateFontInfo();
    //   }
    // }
    // timer++;
  }

  public void update() {
    if (messageBuilding) {
      // buildCurrentMessage();
    }
    else timer = 0;
  }

  public void render(Screen screen) {
    screen.renderSprite(16, 16, background, 8, false);
    screen.addText(line1);
    screen.addText(line2);
    screen.addText(line3);
  }
}