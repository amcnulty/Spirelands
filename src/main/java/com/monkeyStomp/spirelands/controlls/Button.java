package com.monkeystomp.spirelands.controlls;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.IClickable;
import com.monkeystomp.spirelands.input.Mouse;

/**
 * Buttons to be used on the ui.
 * @author Aaron Michael McNulty
 */
public class Button {
  private int x, y, right, bottom,
              width = 200,
              height = 50;
  private IClickable IClick;
  private Sprite  button = new Sprite(width, height, 0x0079cc),
                  buttonHover = new Sprite(width, height, 0x004E9A),
                  buttonDown = new Sprite(width, height, 0x001366),
                  currentButton;

  private Mouse mouse;
  private int mouseB,
              mouseX,
              mouseY;
  private boolean startedOffButton = false,
                  startedOnButton = false;

  public Button(int x, int y, IClickable IClick) {
    this.x = x - width / 2;
    this.y = y - height / 2;
    this.IClick = IClick;
    right = x + width / 2;
    bottom = y + height / 2;
    currentButton = button;
  }

  public void update() {
    mouseX = Mouse.getX();
    mouseY = Mouse.getY();
    mouseB = Mouse.getMouseButton();
    if (mouseB != 1) {
      if (mouseX > x && mouseX < right && mouseY > y && mouseY < bottom) {
        currentButton = buttonHover;
        if (startedOnButton) IClick.onClick();
      }
      else {
        currentButton = button;
      }
      startedOnButton = false;
      startedOffButton = false;
    }
    else {
      if (mouseX > x && mouseX < right && mouseY > y && mouseY < bottom) {
        if (!startedOffButton) {
          startedOffButton = false;
          startedOnButton = true;
          currentButton = buttonDown;
        }
        else currentButton = buttonHover;
      }
      else {
        currentButton = button;
        if (!startedOnButton) {
          startedOffButton = true;
          startedOnButton = false;
        }
      }
    }
  }

  public void render(Screen screen) {
    screen.renderSprite(x, y, currentButton);
  }
}