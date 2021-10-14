package com.monkeystomp.spirelands.gui.dialog;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;

/**
 * Toasts are quick informational messages that will self dismiss after a set amount of time.
 * This class is a singleton and all classes can call upon it to show a toast message.
 * @author Aaron Michael McNulty
 */
public class ToastMessage {
  
  private final int toastWidth = 120,
                    horizontalPadding = 10,
                    verticalPadding = 10,
                    toastX = 10,
                    toastStartingY = 10,
                    spaceBetweenToasts = 4;
  
  private final ArrayList<ToastConfig> expiredMessages = new ArrayList<>();
  private final ArrayList<ToastConfig> messages = new ArrayList<>();
  private static final ToastMessage INSTANCE = new ToastMessage();
  
  private ToastMessage() {}
  
  public static ToastMessage getToastMessage() {
    return INSTANCE;
  }
  
  public void addToast(String message) {
    addToast(message, ToastLength.SHORT);
  }
  
  public void addToast(String message, ToastLength length) {
    messages.add(new ToastConfig(message, length));
    int nextYPosition = toastStartingY;
    for (int i = 0; i < messages.size(); i++) {
      if (i > 0) {
        nextYPosition += messages.get(i - 1).toastHeight + spaceBetweenToasts;
      }
      messages.get(i).setLinesVerticalPosition(nextYPosition);
    }
  }
  
  public void update() {
    expiredMessages.clear();
    for (ToastConfig message: messages) {
      if (message.expired) expiredMessages.add(message);
      else message.update();
    }
    if (expiredMessages.size() > 0) {
      messages.removeAll(expiredMessages);
      int nextYPosition = toastStartingY;
      for (int i = 0; i < messages.size(); i++) {
        if (i > 0) {
          nextYPosition += messages.get(i - 1).toastHeight + spaceBetweenToasts;
        }
        messages.get(i).setLinesVerticalPosition(nextYPosition);
      }
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    for (int i = 0; i < messages.size(); i++) {
      screen.renderSprite(gl, toastX, messages.get(i).toastYPosition, messages.get(i).toastBackground, .8f, false);
      messages.get(i).render(screen);
    }
  }
  
  
  
  /**
   * Private inner class to hold configuration information about toast messages.
   * These messages will also count down their display time so they can be removed from the view when expired.
   */
  private class ToastConfig {
    private final String message;
    private final FontInfo messageFont = GameFonts.getDarkText_bold_16();
    private final ArrayList<FontInfo> messageLines = new ArrayList<>();
    private Sprite toastBackground;
    private int duration,
                toastHeight,
                toastLineHeight,
                toastYPosition;
    private boolean expired = false;
    private FontRenderContext frc = new FontRenderContext(null, true, true);
    
    public ToastConfig(String message, ToastLength length) {
      this.message = message;
      if (length.equals(ToastLength.SHORT)) duration = 300;
      else duration = 480;
      setupMessageLines();
      createBackground();
      setLinesVerticalPosition(toastStartingY);
    }
    
    private void setupMessageLines() {
      messageFont.setText(message);
      ArrayList<String> lines = TextUtil.createWrappedText(messageFont.getText(), messageFont.getFont(), toastWidth - 2 * horizontalPadding);
      messageLines.clear();
      for (int i = 0; i < lines.size(); i++) {
        FontInfo info = GameFonts.getDarkText_bold_16();
        info.setText(lines.get(i));
        info.setX(toastX + horizontalPadding);
        messageLines.add(info);
      }
    }
    
    private void createBackground() {
      toastLineHeight = (int)(messageLines.get(0).getFont().getStringBounds("random string", frc).getHeight() / Screen.getScaleY());
      toastHeight = toastLineHeight * messageLines.size() + verticalPadding * 2;
      toastBackground = new Sprite(toastWidth, toastHeight, GameColors.DIALOG_BOX_BACKGROUND);
    }
    
    private void setLinesVerticalPosition(int yPosition) {
      toastYPosition = yPosition;
      for (int i = 0; i < messageLines.size(); i++) {
        messageLines.get(i).setY(yPosition + (int)(verticalPadding * 1.3) + i * toastLineHeight);
      }
    }
    
    public void update() {
      if (duration-- == 0) {
        expired = true;
      }
    }
    
    public void render(Screen screen) {
      for (FontInfo line: messageLines) {
        screen.renderFonts(line);
      }
    }
    
  }

}
