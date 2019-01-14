package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * A red button that is used for destructive actions.
 * @author Aaron Michael McNulty
 */
public class DangerButton extends Button {
  
  private FontInfo fontInfo;
  /**
   * Creates a PrimaryButton object with a callback that gets fired when button is clicked.
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public DangerButton(String text, int x, int y, int width, int height, ICallback callback) {
    super(text, x, y, width, height, callback);
    setFontInfo();
    createButtonSprites();
    setButtonSounds();
  }
  
  private void setFontInfo() {
    Rectangle2D rect = GameFonts.getPrimaryButtonText().getFont().getStringBounds(buttonText, new FontRenderContext(null, true, true));
    int textWidth = (int)rect.getWidth();
    int fontX = (int)(x + (this.width - (textWidth / Screen.getScaleX())) / 2);
    int fontY = y + this.height / 2;
    fontInfo = GameFonts.getPrimaryButtonText();
    fontInfo.setText(buttonText);
    fontInfo.setX(fontX);
    fontInfo.setY(fontY);
  }

  private void createButtonSprites() {
    button = new Sprite(width, height, GameColors.DANGER_BUTTON_RED);
    buttonHover = new Sprite(width, height, GameColors.DANGER_BUTTON_HOVER);
    buttonDown = new Sprite(width, height, GameColors.DANGER_BUTTON_DOWN);
    currentButton = button;
  }
  
  private void setButtonSounds() {
    hoverSound = SoundEffects.BUTTON_HOVER;
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderFonts(fontInfo);
  }
}
