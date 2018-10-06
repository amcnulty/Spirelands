package com.monkeystomp.spirelands.gui.controlls;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Mouse;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Buttons to be used on the UI.
 * @author Aaron Michael McNulty
 */
public class Button {
  private int x, y, right, bottom,
              width,
              height;
  private ICallback callback;
  private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
  private Sprite  button,
                  buttonHover,
                  buttonDown,
                  currentButton;
  private SoundEffects sfx = new SoundEffects();
  private FontInfo fontInfo;
  private String buttonText;
  private int mouseB,
              mouseX,
              mouseY;
  private boolean startedOffButton = false,
                  startedOnButton = false,
                  hovering = false;

  public Button(String text, int x, int y, int width, int height, ICallback callback) {
    this.buttonText = text;
    this.width = width;
    this.height = height;
    this.x = x - width / 2;
    this.y = y - height / 2;
    this.callback = callback;
    right = x + width / 2;
    bottom = y + height / 2;
    setFontInfo();
    createButtonSprites();
  }
  
  private void setFontInfo() {
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    Rectangle2D rect = font.getStringBounds(buttonText, new FontRenderContext(null, true, true));
    int textWidth = (int)rect.getWidth();
    int fontX = (int)(x + (this.width - (textWidth / Screen.getScaleX())) / 2);
    int fontY = y + this.height / 2;
    fontInfo = new FontInfo(font, Color.WHITE, buttonText, fontX, fontY);
  }

  private void createButtonSprites() {
    button = new Sprite(width, height, 0x0079cc);
    buttonHover = new Sprite(width, height, 0x004E9A);
    buttonDown = new Sprite(width, height, 0x001366);
    currentButton = button;
  }
  
  private void hover() {
    if (!hovering) sfx.playSoundEffect(SoundEffects.BUTTON_HOVER);
    currentButton = buttonHover;
    hovering = true;
  }
  
  private void down() {
    currentButton = buttonDown;
  }
  
  private void click() {
    sfx.playSoundEffect(SoundEffects.BUTTON_CLICK);
    callback.execute();
  }
  
  private void setDefault() {
    currentButton = button;
    hovering = false;
  }

  public void update() {
    mouseX = Mouse.getX() / 3;
    mouseY = Mouse.getY() / 3;
    mouseB = Mouse.getMouseButton();
    if (mouseB != 1) {
      if (mouseX > x && mouseX < right && mouseY > y && mouseY < bottom) {
        hover();
        if (startedOnButton) click();
      }
      else {
        setDefault();
      }
      startedOnButton = false;
      startedOffButton = false;
    }
    else {
      if (mouseX > x && mouseX < right && mouseY > y && mouseY < bottom) {
        if (!startedOffButton) {
          startedOffButton = false;
          startedOnButton = true;
          down();
        }
        else hover();
      }
      else {
        setDefault();
        if (!startedOnButton) {
          startedOffButton = true;
          startedOnButton = false;
        }
      }
    }
  }

  public void render(Screen screen) {
    screen.renderSprite(x, y, currentButton, false);
    screen.addText(fontInfo);
  }
}