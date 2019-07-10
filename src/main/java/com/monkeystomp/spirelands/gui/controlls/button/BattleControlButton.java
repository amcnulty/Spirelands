package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.styles.GameColors;
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
  private final Sprite disabledMask = new Sprite(WIDTH, HEIGHT, GameColors.BLACK);
  private final BattleMove move;
  private final Sprite borderDefault = new Sprite("./resources/gui/battle_move_border.png", 18);
  private final Sprite borderHover = new Sprite("./resources/gui/battle_move_border_hover.png", 18);
  private final AnimatedSprite rotatingBorder = new AnimatedSprite(32, 18, new SpriteSheet("./resources/gui/animated_battle_move_border.png"), AnimatedSprite.MEDIUM, 8);
  private final Consumer<KeyEvent> keyPressListener = event -> handleKeyPress(event);
  private boolean manuallyDisabled = false;
  private int borderX = x - 1,
              borderY = y -1;

  public BattleControlButton(int x, int y, int relatedKey, BattleMove move, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, move.getThumbnail(), callback);
    this.relatedKey = relatedKey;
    this.move = move;
    this.buttonImage = move.getThumbnail();
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
    if (event.getKeyCode() == relatedKey && !manuallyDisabled) click();
  }
  
  public void destroy() {
    Keyboard.getKeyboard().removeKeyListener(keyPressListener);
  }
  
  public void disableButton() {
    manuallyDisabled = true;
  }

  public BattleMove getMove() {
    return move;
  }
  /**
   * Calls the click method on this button. Acts as if it was clicked on in the UI.
   */
  public void clickOverride() {
    clickSound = null;
    super.click();
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  @Override
  public void update() {
    if (!manuallyDisabled) {
      super.update();
      rotatingBorder.update();
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    if (!manuallyDisabled) {
      if (isDisabled()) {
        screen.renderSprite(gl, borderX, borderY, rotatingBorder.getSprite(), false);
      }
      else if (isHovering()) {
        screen.renderSprite(gl, borderX, borderY, borderHover, false);
      }
      else {
        screen.renderSprite(gl, borderX, borderY, borderDefault, false);
      }
    }
    else {
      screen.renderSprite(gl, borderX, borderY, borderDefault, false);
    }
    super.render(screen, gl);
    if (manuallyDisabled) screen.renderSprite(gl, x, y, disabledMask, .5f, false);
  }

}
