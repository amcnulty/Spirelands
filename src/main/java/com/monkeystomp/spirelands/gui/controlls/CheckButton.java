package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CheckButton extends Button {
  
  private static final int  width = 8,
                            height = 8;
  private boolean checked = false;
  private Sprite  buttonChecked,
                  buttonHoverChecked;
  private final String value;

  public CheckButton(int x, int y, String value, ICallback callback) {
    super("", x, y, width, height, callback);
    this.value = value;
    createButtonSprites();
    setButtonSounds();
  }
  
  private void createButtonSprites() {
    button = new Sprite(32, 16, 3, 0, SpriteSheet.settingsGuiSheet);
    buttonChecked = new Sprite(32, 16, 4, 0, SpriteSheet.settingsGuiSheet);
    buttonHover = new Sprite(32, 16, 3, 1, SpriteSheet.settingsGuiSheet);
    buttonHoverChecked = new Sprite(32, 16, 4, 1, SpriteSheet.settingsGuiSheet);
    buttonDown = buttonHover;
    currentButton = button;
  }
  
  private void setButtonSounds() {
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  public void uncheck() {
    checked = false;
  }
  
  public void check() {
    checked = true;
  }
  
  public boolean isChecked() {
    return checked;
  }
  
  public String getValue() {
    return value;
  }
  
  @Override
  protected void setDefault() {
    super.setDefault();
    if (checked) currentButton = buttonChecked;
    else currentButton = button;
  }
  
  @Override
  protected void hover() {
    super.hover();
    if (checked) currentButton = buttonHoverChecked;
    else currentButton = buttonHover;
  }
  
  @Override
  protected void down() {
    if (checked) currentButton = buttonHoverChecked;
    else currentButton = buttonHover;
  }
  
  @Override
  protected void click() {
    super.click();
    if (checked) uncheck();
    else check();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - currentButton.getWidth() / 4, y - currentButton.getHeight() / 4, currentButton, false);
  }
  
}
