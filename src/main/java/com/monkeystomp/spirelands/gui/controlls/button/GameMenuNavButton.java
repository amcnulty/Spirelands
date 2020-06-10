package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.Color;

/**
 * The navigation button used in the game menu.
 * @author Aaron Michael McNulty
 */
public class GameMenuNavButton extends Button {
  
  private static final int  WIDTH = 94,
                            HEIGHT = 16,
                            TOP_PADDING = 6,
                            LEFT_PADDING = 10;
  private final int defaultText = GameColors.GAME_MENU_DEFAULT_TEXT,
                    selectedText = GameColors.GAME_MENU_SELECTED_TEXT;
  private final Sprite selectedBackground = new Sprite(WIDTH, HEIGHT, GameColors.GAME_MENU_BUTTON_HOVER);
  private final FontInfo fontInfo = GameFonts.getlightText_bold_23();
  /**
   * Creates a GameMenuNavButton with the given text and with a callback that gets fired when button is clicked.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public GameMenuNavButton(String text, int x, int y, ICallback callback) {
    super(text, x, y, GameMenuNavButton.WIDTH, GameMenuNavButton.HEIGHT, callback);
    createButtonSprites();
    setButtonSounds();
    fontInfo.setText(text);
    fontInfo.setX(this.x + LEFT_PADDING);
    fontInfo.setY(y);
  }

  private void createButtonSprites() {
    button = new Sprite(WIDTH, HEIGHT, GameColors.TRANSPARENT);
    buttonHover = new Sprite(WIDTH, HEIGHT, GameColors.GAME_MENU_BUTTON_HOVER);
    buttonDown = new Sprite(WIDTH, HEIGHT, GameColors.GAME_MENU_BUTTON_DOWN);
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  private void setBackground() {
    button = selectedBackground;
    buttonHover = selectedBackground;
    buttonDown = selectedBackground;
  }
   
  @Override
  protected void click() {
    super.click();
    setBackground();
    fontInfo.setColor(new Color(selectedText));
    setDisabled(true);
  }
  
  public void removeBackground() {
    fontInfo.setColor(new Color(defaultText));
    createButtonSprites();
    setDisabled(false);
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderFonts(fontInfo);
  }
  
}
