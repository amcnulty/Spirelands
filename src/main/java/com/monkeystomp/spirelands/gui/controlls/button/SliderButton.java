package com.monkeystomp.spirelands.gui.controlls.button;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SliderButton extends Button {

  /**
   * Right directional arrow button. Used in the constructor of SliderButtons.
   */
  public static final String RIGHT = "Right";
  /**
   * Left directional arrow button. Used in the constructor of SliderButtons.
   */
  public static final String LEFT = "Left";
  
  private final String direction;
  
  public SliderButton(String direction, int x, int y, int width, int height, ICallback callback) {
    super("", x, y, width, height, callback);
    this.direction = direction;
    createButtonSprites();
    setButtonSounds();
  }

  private void createButtonSprites() {
    if (direction.equals(RIGHT)) {
      button = new Sprite(32, 16, 2, 0, SpriteSheet.settingsGuiSheet);
      buttonHover = new Sprite(32, 16, 2, 2, SpriteSheet.settingsGuiSheet);
      buttonDown = new Sprite(32, 16, 2, 1, SpriteSheet.settingsGuiSheet);
    }
    else if (direction.equals(LEFT)) {
      button = new Sprite(32, 16, 1, 0, SpriteSheet.settingsGuiSheet);
      buttonHover = new Sprite(32, 16, 1, 2, SpriteSheet.settingsGuiSheet);
      buttonDown = new Sprite(32, 16, 1, 1, SpriteSheet.settingsGuiSheet);
    }
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  

}
