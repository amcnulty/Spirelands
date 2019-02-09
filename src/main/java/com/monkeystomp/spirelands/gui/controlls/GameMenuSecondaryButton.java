package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * The Game Menu secondary button is a flat button for the game menu. This button will have no border or background and shows a slightly lighter background when hovering and clicking.
 * @author Aaron Michael McNulty
 */
public class GameMenuSecondaryButton extends Button {
  
  private final FontInfo fontInfo;
  private boolean disabled = false;
  /**
   * Creates a new GameMneuSecondaryButton.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public GameMenuSecondaryButton(String text, int x, int y, int width, int height, ICallback callback) {
    super(text, x, y, width, height, callback);
    fontInfo = GameFonts.getlightText_bold_23();
    setFontInfo();
    createButtonSprites();
    setButtonSounds();
  }
  /**
   * Creates a new GameMneuSecondaryButton.
   * @param text The text to be rendered on the button.
   * @param info The font info to use for the button text.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public GameMenuSecondaryButton(String text, FontInfo info, int x, int y, int width, int height, ICallback callback) {
    super(text, x, y, width, height, callback);
    fontInfo = info;
    setFontInfo();
    createButtonSprites();
    setButtonSounds();
  }
  
  private void setFontInfo() {
    Rectangle2D rect = GameFonts.getGameMenuPrimaryButtonText().getFont().getStringBounds(buttonText, new FontRenderContext(null, true, true));
    int textWidth = (int)rect.getWidth();
    int fontX = (int)(x + (this.width - (textWidth / Screen.getScaleX())) / 2);
    int fontY = y + this.height / 2;
    fontInfo.setText(buttonText);
    fontInfo.setX(fontX);
    fontInfo.setY(fontY);
  }

  private void createButtonSprites() {
    button = new Sprite(width, height, GameColors.GAME_MENU_BACKGROUND);
    buttonHover = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_HOVER);
    buttonDown = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_DOWN);
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  @Override
  public void update() {
    super.update();
    if (isDisabled() != disabled) {
      if (isDisabled()) fontInfo.setColor(new Color(GameColors.GAME_MENU_MUTED_TEXT));
      else fontInfo.setColor(new Color(GameColors.GAME_MENU_DEFAULT_TEXT));
      disabled = isDisabled();
    }
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderFonts(fontInfo);
  }

}
