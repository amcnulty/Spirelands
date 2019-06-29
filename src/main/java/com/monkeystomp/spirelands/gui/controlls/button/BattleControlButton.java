package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Keyboard;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControlButton extends GroupButton {
  
  public static final int WIDTH = 16,
                    HEIGHT = 16;
  private final int relatedKey;
  private final Sprite buttonImage;
  private final Sprite borderDefault = new Sprite("./resources/gui/battle_move_border.png", 16);
  private final Sprite borderHover = new Sprite("./resources/gui/battle_move_border_hover.png", 16);
  private final AnimatedSprite rotatingBorder = new AnimatedSprite(32, 16, new SpriteSheet("./resources/gui/animated_battle_move_border.png"), AnimatedSprite.MEDIUM, 8);
  private final Consumer<KeyEvent> keyPressListener = event -> handleKeyPress(event);

  public BattleControlButton(int x, int y, int relatedKey, Sprite buttonImage, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, buttonImage, callback);
    this.relatedKey = relatedKey;
    this.buttonImage = buttonImage;
    Keyboard.getKeyboard().addKeyListener(keyPressListener);
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
  
  private void handleKeyPress(KeyEvent event) {
    if (event.getKeyCode() == relatedKey) click();
  }
  
  public void destroy() {
    Keyboard.getKeyboard().removeKeyListener(keyPressListener);
  }
  
  @Override
  public void update() {
    super.update();
    rotatingBorder.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (isDisabled()) {
      screen.renderSprite(gl, x, y, rotatingBorder.getSprite(), false);
    }
    else if (isHovering()) {
      screen.renderSprite(gl, x, y, borderHover, false);
    }
    else {
      screen.renderSprite(gl, x, y, borderDefault, false);
    }
    super.render(screen, gl);
  }

}
