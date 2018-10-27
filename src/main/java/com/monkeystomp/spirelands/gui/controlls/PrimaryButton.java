package com.monkeystomp.spirelands.gui.controlls;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Aaron Michael McNulty
 */
public class PrimaryButton extends Button {
  
  private FontInfo fontInfo;

  public PrimaryButton(String text, int x, int y, int width, int height, ICallback callback) {
    super(text, x, y, width, height, callback);
    setFontInfo();
    createButtonSprites();
    setButtonSounds();
  }
  
  private void setFontInfo() {
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    Rectangle2D rect = font.getStringBounds(buttonText, new FontRenderContext(null, true, true));
    int textWidth = (int)rect.getWidth();
    int fontX = (int)(x + (this.width - (textWidth / Screen.getScaleX())) / 2);
    int fontY = y + this.height / 2;
    fontInfo = new FontInfo(font, Color.WHITE, buttonText, fontX, fontY);
  }

  private void createButtonSprites() {
    button = new Sprite(width, height, 0x0079cc);
    buttonHover = new Sprite(width, height, 0x004E9A);
    buttonDown = new Sprite(width, height, 0x001366);
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
  public void render(Screen screen) {
    super.render(screen);
    screen.addText(fontInfo);
  }
}