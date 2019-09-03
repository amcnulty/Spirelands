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
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControlButton extends GroupButton {
  
  public static final int WIDTH = 16,
                          HEIGHT = 16;
  private final int relatedKey,
                    padding = 3;
  private Sprite  itemShadow,
                  popoverBackground;
  private final Sprite  buttonImage,
                        disabledMask = new Sprite(WIDTH, HEIGHT, GameColors.BLACK),
                        borderDefault = new Sprite("./resources/gui/battle_move_border.png", 18),
                        borderHover = new Sprite("./resources/gui/battle_move_border_hover.png", 18);
  private final BattleMove move;
  private final AnimatedSprite rotatingBorder = new AnimatedSprite(32, 18, new SpriteSheet("./resources/gui/animated_battle_move_border.png"), AnimatedSprite.MEDIUM, 8);
  private final FontRenderContext frc = new FontRenderContext(null, true, true);
  private final FontInfo  quantityFont = GameFonts.getGAME_MENU_PRIMARY_TEXT(),
                          moveNameFont = GameFonts.getDarkText_bold_16(),
                          moveTypeLabelFont = GameFonts.getGameMenuPrimaryButtonText(),
                          moveTypeFont = GameFonts.getDarkText_bold_16(),
                          moveActionLabelFont = GameFonts.getGameMenuPrimaryButtonText(),
                          moveActionFont = GameFonts.getDarkText_bold_16(),
                          manaLabelFont = GameFonts.getGameMenuPrimaryButtonText(),
                          manaFont = GameFonts.getDarkText_bold_16();
  private final Consumer<KeyEvent> keyPressListener = event -> handleKeyPress(event);
  private boolean manuallyDisabled = false,
                  listenerEnabled = true;
  private int borderX = x - 1,
              borderY = y -1,
              popoverDelay = 45;

  public BattleControlButton(int x, int y, int relatedKey, BattleMove move, ICallback callback) {
    super("", x, y, WIDTH, HEIGHT, move.getThumbnail(), callback);
    this.relatedKey = relatedKey;
    this.move = move;
    this.buttonImage = move.getThumbnail();
    Keyboard.getKeyboard().addKeyListener(keyPressListener);
    createButtonSprites();
    setButtonSounds();
    createPopover();
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
  
  private void createPopover() {
    Rectangle2D bounds = moveNameFont.getFont().getStringBounds(move.getName(), frc);
    int nameWidth = (int)(bounds.getWidth() / Screen.getScaleX());
    bounds = moveTypeFont.getFont().getStringBounds("Action: " + move.getVariety(), frc);
    int actionWidth = (int)(bounds.getWidth() / Screen.getScaleX());
    
    moveNameFont.setText(move.getName());
    moveNameFont.setX(x + width / 2 - nameWidth / 2);
    moveNameFont.setY(y + height + 6);
    
    moveTypeLabelFont.setText("Type: ");
    moveTypeLabelFont.setX(x + width / 2 - actionWidth / 2);
    moveTypeLabelFont.setY(moveNameFont.getY() + 6);
    
    moveTypeFont.setText(move.getType());
    moveTypeFont.setX(moveTypeLabelFont.getX() + 20);
    moveTypeFont.setY(moveTypeLabelFont.getY());
    if (move.getType().equals(BattleMove.PHYSICAL)) moveTypeFont.setColor(new Color(GameColors.DANGER_BUTTON_DOWN));
    else if (move.getType().equals(BattleMove.MAGICAL)) moveTypeFont.setColor(new Color(GameColors.PRIMARY_BUTTON_BLUE_DOWN));
    else if (move.getType().equals(BattleMove.ITEM)) moveTypeFont.setColor(new Color(GameColors.DARK_GREEN));
    
    moveActionLabelFont.setText("Action: ");
    moveActionLabelFont.setX(x + width / 2 - actionWidth / 2);
    moveActionLabelFont.setY(moveTypeLabelFont.getY() + 6);
    
    moveActionFont.setText(move.getVariety());
    moveActionFont.setX(moveActionLabelFont.getX() + 20);
    moveActionFont.setY(moveActionLabelFont.getY());
    if (move.getVariety().equals(BattleMove.OFFENSIVE)) moveActionFont.setColor(new Color(GameColors.DANGER_BUTTON_DOWN));
    else if (move.getVariety().equals(BattleMove.DEFENSIVE)) moveActionFont.setColor(new Color(GameColors.DARK_GREEN));
    
    if (move.getManaRequired() > 0) {
      manaLabelFont.setText("Mana: ");
      manaLabelFont.setX(moveActionLabelFont.getX());
      manaLabelFont.setY(moveActionLabelFont.getY() + 6);
      
      manaFont.setText(String.valueOf(move.getManaRequired()));
      manaFont.setX(moveActionLabelFont.getX() + 20);
      manaFont.setY(moveActionLabelFont.getY() + 6);
      manaFont.setColor(new Color(GameColors.PRIMARY_BUTTON_BLUE_DOWN));
      popoverBackground = new Sprite(actionWidth + padding * 2, 27, GameColors.DIALOG_BOX_BACKGROUND);
    }
    else popoverBackground = new Sprite(actionWidth + padding * 2, 20, GameColors.DIALOG_BOX_BACKGROUND);
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
  protected void setDefault() {
    super.setDefault();
    popoverDelay = 45;
  }
  
  @Override
  public void update() {
    if (!manuallyDisabled) {
      super.update();
      rotatingBorder.update();
      if (move != null) {
        if (move.getType().equals(BattleMove.ITEM)) setFontInfo();
        if (isHovering()) popoverDelay--;
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
      if (isHovering() && popoverDelay < 0 && !isDisabled()) {
        screen.renderSprite(gl, moveActionLabelFont.getX() - padding, moveNameFont.getY() - padding, popoverBackground, .8f, false);
        screen.renderFonts(moveNameFont);
        screen.renderFonts(moveTypeLabelFont);
        screen.renderFonts(moveTypeFont);
        screen.renderFonts(moveActionLabelFont);
        screen.renderFonts(moveActionFont);
        if (move.getManaRequired() > 0) {
          screen.renderFonts(manaLabelFont);
          screen.renderFonts(manaFont);
        }
      }
    }
    if (manuallyDisabled) screen.renderSprite(gl, x, y, disabledMask, .5f, false);
  }

}
