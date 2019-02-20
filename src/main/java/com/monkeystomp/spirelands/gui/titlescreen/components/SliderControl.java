package com.monkeystomp.spirelands.gui.titlescreen.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.SliderButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.Mouse;
import java.awt.Color;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SliderControl {
  
  private final Consumer<Float> IValueSetter;
  private final Sprite  slideTrack = new Sprite(new Sprite("./resources/gui/slide_track.png"), 50.0),
                        slideThumb = new Sprite(32, 16, 0, 0, SpriteSheet.settingsGuiSheet);
  private final FontInfo valueDisplay = GameFonts.getGAME_MENU_HEADLINE();
  private final int x,
                    y,
                    trackSpriteWidth = 109,
                    trackWidth = 100,
                    height = 16,
                    leftBound,
                    rightBound;
  private int thumbTop,
              thumbRight,
              thumbBottom,
              thumbLeft,
              displayValue = 50,
              thumbX;
  private double controlValue = .5;
  private final SliderButton  leftButton,
                              rightButton;
  
  public SliderControl(int x, int y, Consumer<Float> IValueSetter) {
    this.x = x;
    this.y = y;
    this.IValueSetter = IValueSetter;
    leftButton = new SliderButton(SliderButton.LEFT, x - trackSpriteWidth / 2 - 8, y, 16, 16, () -> setValue(displayValue - 1));
    rightButton = new SliderButton(SliderButton.RIGHT, x + trackSpriteWidth / 2 + 8, y, 16, 16, () -> setValue(displayValue + 1));
    this.thumbTop = y - height / 2 + 3;
    this.thumbBottom = y + height / 2 - 3;
    thumbX = x - slideThumb.getWidth() / 2;
    leftBound = x - trackWidth / 2 - 4;
    rightBound = x + trackWidth / 2 - 4;
    setFontInfo();
  }
  
  private void setFontInfo() {
    valueDisplay.setText(String.valueOf(displayValue));
    valueDisplay.setX(x);
    valueDisplay.setY(y - height);
    valueDisplay.setColor(new Color(GameColors.DARK_TEXT));
    valueDisplay.centerText();
  }
  
  private void updateFontText() {
    valueDisplay.setText(String.valueOf(displayValue));
    valueDisplay.setX(x);
    valueDisplay.centerText();
  }
  
  private void checkForThumbDrag() {
    if (Mouse.getMouseButton() == 1) {
      if (Mouse.getY() / Screen.getScaleY() > thumbTop
              && Mouse.getY() / Screen.getScaleY() < thumbBottom
              && Mouse.getX() / Screen.getScaleX() > thumbLeft
              && Mouse.getX() / Screen.getScaleX() < thumbRight) {
        thumbX = (int)(Mouse.getX() / Screen.getScaleX() - 8);
      }
    }
  }
  
  private void setThumbPosition() {
    if (thumbX + 4 < leftBound) thumbX = leftBound - 4;
    else if (thumbX + 4 > rightBound) thumbX = rightBound - 4;
    thumbLeft = thumbX + 4;
    thumbRight = thumbX + 11;
    if ((double)(thumbX + 4 - leftBound) / (double)(rightBound - leftBound) != controlValue) {
      IValueSetter.accept((float)controlValue);
    }
    displayValue = (int)(controlValue * 100);
    controlValue = (double)(thumbX + 4 - leftBound) / (double)(rightBound - leftBound);
    if (controlValue > 1) controlValue = 1;
  }
  
  public void setValue(float value) {
    int initialThumbX = thumbX;
    thumbX = (int)(leftBound + value - 4);
    if (thumbX == initialThumbX) {
      if (displayValue < value) thumbX++;
      else if (displayValue > value) thumbX--;
    }
    displayValue = (int)(controlValue * 100);
    controlValue = (double)(thumbX + 4 - leftBound) / (double)(rightBound - leftBound);
    if (controlValue > 1) controlValue = 1;
    IValueSetter.accept((float)controlValue);
  }
  
  public void update() {
    leftButton.update();
    rightButton.update();
    checkForThumbDrag();
    setThumbPosition();
    updateFontText();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderFonts(valueDisplay);
    screen.renderSprite(gl, x - trackSpriteWidth / 2 , y - height / 2, slideTrack, false);
    screen.renderSprite(gl, thumbX, y - height / 2, slideThumb, false);
    leftButton.render(screen, gl);
    rightButton.render(screen, gl);
  }

}
