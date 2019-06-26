package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControlButton extends Button {
  
  public static final int WIDTH = 16,
                    HEIGHT = 16;
  private final Sprite buttonImage;
  private final Sprite borderDefault = new Sprite("./resources/gui/battle_move_border.png", 16);
  private final Sprite borderHover = new Sprite("./resources/gui/battle_move_border_hover.png", 16);
  private final AnimatedSprite rotatingBorder = new AnimatedSprite(32, 16, new SpriteSheet("./resources/gui/animated_battle_move_border.png"), AnimatedSprite.SLOW, 8);
  private boolean isDown = false,
                  isDefault = true,
                  isHover = false;

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
  
  @Override
  protected void setDefault() {
    super.setDefault();
    isDown = false;
    isHover = false;
    isDefault = true;
  }
  
  @Override
  protected void hover() {
    super.hover();
    isDown = false;
    isHover = true;
    isDefault = false;
  }
  
  @Override
  protected void down() {
    super.down();
    isDown = true;
    isHover = false;
    isDefault = false;
  }
  
  @Override
  public void update() {
    super.update();
    rotatingBorder.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (isDown) {
      screen.renderSprite(gl, x, y, rotatingBorder.getSprite(), false);
    }
    else if (isHover) {
      screen.renderSprite(gl, x, y, borderHover, false);
    }
    else if (isDefault) {
      screen.renderSprite(gl, x, y, borderDefault, false);
    }
    super.render(screen, gl);
  }

}
