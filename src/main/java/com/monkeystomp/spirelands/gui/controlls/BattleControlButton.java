package com.monkeystomp.spirelands.gui.controlls;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControlButton extends Button {
  
  public static final int WIDTH = 16,
                    HEIGHT = 16;
  private final Sprite buttonImage;

  public BattleControlButton(int x, int y, Sprite buttonImage, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, callback);
    this.buttonImage = buttonImage;
    createButtonSprites();
    setButtonSounds();
  }
  
  private void createButtonSprites() {
    button = buttonImage;
    buttonHover = buttonImage;
    buttonDown = buttonImage;
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  

}
