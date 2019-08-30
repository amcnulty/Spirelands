package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.AnimatedSprite;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import java.awt.Color;
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
  private Sprite itemShadow;
  private final Sprite  buttonImage,
                        disabledMask = new Sprite(WIDTH, HEIGHT, GameColors.BLACK),
                        borderDefault = new Sprite("./resources/gui/battle_move_border.png", 18),
                        borderHover = new Sprite("./resources/gui/battle_move_border_hover.png", 18);
  private final BattleMove move;
  private final AnimatedSprite rotatingBorder = new AnimatedSprite(32, 18, new SpriteSheet("./resources/gui/animated_battle_move_border.png"), AnimatedSprite.MEDIUM, 8);
  private final FontInfo quantityFont = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final Consumer<KeyEvent> keyPressListener = event -> handleKeyPress(event);
  private boolean manuallyDisabled = false,
                  listenerEnabled = true;
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
    if (move.getType().equals(BattleMove.ITEM)) setFontInfo();
  }
  
  public BattleControlButton(int x, int y, int relatedKey, Sprite thumbnail, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, thumbnail, callback);
    this.move = null;
    this.relatedKey = relatedKey;
    this.buttonImage = thumbnail;
    Keyboard.getKeyboard().addKeyListener(keyPressListener);
    createButtonSprites();
    setButtonSounds();
  }
  
  private void createButtonSprites() {
    button = buttonImage;
    buttonHover = buttonImage;
    buttonDown = buttonImage;
    currentButton = button;
    if (move != null) {
      if (move.getType().equals(BattleMove.ITEM)) {
        int spriteHeight = 6;
        int[] pixels = new int[spriteHeight * WIDTH];
        for (int y = 0; y < spriteHeight; y++) {
          for (int x = 0; x < WIDTH; x++) {
            pixels[x + y * WIDTH] = 0x2B000000 + ((0x2B000000 * y) - (0x02000000 * x));
          }
        }
        itemShadow = new Sprite(pixels, WIDTH, spriteHeight);
      }
    }
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  private void setFontInfo() {
    quantityFont.setText(String.valueOf(InventoryManager.getInventoryManager().getAmountById(move.getItem().getId())));
    quantityFont.setX(x + 1);
    quantityFont.setY(y + 12);
    quantityFont.setColor(new Color(GameColors.GREEN));
  }
  
  private void handleKeyPress(KeyEvent event) {
    if (event.getKeyCode() == relatedKey && !manuallyDisabled && listenerEnabled) click();
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

  public void setListenerEnabled(boolean listenerEnabled) {
    this.listenerEnabled = listenerEnabled;
  }
  /**
   * Calls the click method on this button. Acts as if it was clicked on in the UI.
   */
  public void clickOverride() {
    if (!manuallyDisabled) {
      clickSound = null;
      super.click();
      clickSound = SoundEffects.BUTTON_CLICK;
    }
  }
  
  @Override
  public void update() {
    if (!manuallyDisabled) {
      super.update();
      rotatingBorder.update();
      if (move != null) {
        if (move.getType().equals(BattleMove.ITEM)) setFontInfo();
      }
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
    if (move != null) {
      if (move.getType().equals(BattleMove.ITEM)) {
        screen.renderSprite(gl, x, y + HEIGHT - itemShadow.getHeight(), itemShadow, false);
        screen.renderFonts(quantityFont);
      }
    }
    if (manuallyDisabled) screen.renderSprite(gl, x, y, disabledMask, .5f, false);
  }

}
