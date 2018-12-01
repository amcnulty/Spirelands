package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Mouse;
import java.io.File;

/**
 * Buttons to be used on the UI.
 * @author Aaron Michael McNulty
 */
public class Button {
  
  private ICallback callback;
  private SoundEffects sfx = new SoundEffects();
  private int mouseB,
              mouseX,
              mouseY;
  private boolean startedOffButton = false,
                  startedOnButton = false,
                  hovering = false;
  protected int x, y, right, bottom,
              width,
              height;
  protected Sprite  button,
                    buttonHover,
                    buttonDown,
                    currentButton;
  protected String buttonText;
  protected File  hoverSound,
                  clickSound;

  public Button(String text, int x, int y, int width, int height, ICallback callback) {
    this.buttonText = text;
    this.width = width;
    this.height = height;
    this.x = x - width / 2;
    this.y = y - height / 2;
    this.callback = callback;
    right = x + width / 2;
    bottom = y + height / 2;
  }
  
  protected void hover() {
    if (!hovering && hoverSound != null) sfx.playSoundEffect(hoverSound);
    currentButton = buttonHover;
    hovering = true;
  }
  
  protected void down() {
    currentButton = buttonDown;
  }
  
  protected void click() {
    if (clickSound != null) sfx.playSoundEffect(clickSound);
    callback.execute();
  }
  
  protected void setDefault() {
    currentButton = button;
    hovering = false;
  }

  public void update() {
    mouseX = (int)(Mouse.getX() / Screen.getScaleX());
    mouseY = (int)(Mouse.getY() / Screen.getScaleY());
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

  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, currentButton, false);
  }
}
