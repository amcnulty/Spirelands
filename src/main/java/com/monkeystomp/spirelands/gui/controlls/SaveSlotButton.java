package com.monkeystomp.spirelands.gui.controlls;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SaveSlotButton extends Button {
  
  private static final int  WIDTH = 100,
                            HEIGHT = 100;
  
  public SaveSlotButton(int x, int y, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, callback);
    createButtonSprites();
    setButtonSounds();
  }
  
  private void createButtonSprites() {
    button = new Sprite(new Sprite("./resources/gui/empty_save_slot.png"), 25f);
    buttonHover = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
    buttonDown = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  @Override
  protected void click() {
    super.click();
    button = buttonDown;
    buttonHover = buttonDown;
  }
  
  public void reset() {
    button = new Sprite(new Sprite("./resources/gui/empty_save_slot.png"), 25f);
    buttonHover = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
    buttonDown = new Sprite(new Sprite("./resources/gui/empty_save_slot_selected.png"), 25f);
  }

}
