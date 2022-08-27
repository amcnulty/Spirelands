package com.monkeystomp.spirelands.gui.controlls.button;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * A special type of button designed to be used for showing tabs.
 * The color of the tab button will change when clicked on and will remain
 * until the button is told to return to default state.
 * @author Aaron Michael McNulty
 */
public class TabButton extends Button {
  
  private final FontInfo fontInfo;
  private final Sprite  defaultState,
                        hover,
                        down,
                        disabled,
                        selectedBackground;
  private final int selectedTextColor;

  public TabButton(String text, int x, int y, Sprite defaultState, Sprite hover, Sprite down, Sprite disabled, Sprite selected, int selectedTextColor, ICallback callback) throws Error {
    super(x, y, defaultState, hover, down, disabled, callback);
    this.defaultState = defaultState;
    this.hover = hover;
    this.down = down;
    this.disabled = disabled;
    this.buttonText = text;
    this.selectedBackground = selected;
    this.selectedTextColor = selectedTextColor;
    clickSound = SoundEffects.BUTTON_CLICK;
    fontInfo = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
    setFontInfo();
  }
  
  private void setFontInfo() {
    Rectangle2D rect = fontInfo.getFont().getStringBounds(buttonText, new FontRenderContext(null, true, true));
    int textWidth = (int)rect.getWidth();
    int fontX = (int)(x + (this.width - (textWidth / Screen.getScaleX())) / 2);
    int fontY = y + this.height / 2;
    fontInfo.setText(buttonText);
    fontInfo.setX(fontX);
    fontInfo.setY(fontY);
  }
  
  /**
   * Sets this tab button in the selected state.
   * This will also disable the button.
   */
  public void selectButton() {
    button = selectedBackground;
    buttonHover = selectedBackground;
    buttonDown = selectedBackground;
    disabledButton = selectedBackground;
    fontInfo.setColor(new Color(selectedTextColor));
    setDisabled(true);
  }
  
  /**
   * Resets the button to the initial state and removes the disabled state.
   */
  public void resetButton() {
    fontInfo.setColor(GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL().getColor());
    button = defaultState;
    buttonHover = hover;
    buttonDown = down;
    disabledButton = disabled;
    setDisabled(false);
  }
   
  @Override
  protected void click() {
    super.click();
    selectButton();
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderFonts(fontInfo);
  }

}
