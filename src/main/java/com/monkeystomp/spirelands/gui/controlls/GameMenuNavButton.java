package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Font;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameMenuNavButton extends Button {
  
  private static final int  WIDTH = 94,
                            HEIGHT = 20,
                            TOP_PADDING = 6,
                            LEFT_PADDING = 2;
  private Font font = new Font();
  private final int DEFAULT_TEXT = 0xf1f1f1,
                    HOVER_TEXT = 0xBEBEBE,
                    DOWN_TEXT = 0x0079cc;
  private int textColor = 0xf1f1f1;
  private final Sprite SELECTED_BACKGROUND = new Sprite("./resources/gui/game_menu_nav_button.png");

  public GameMenuNavButton(String text, int x, int y, ICallback callback) {
    super(text, x, y, GameMenuNavButton.WIDTH, GameMenuNavButton.HEIGHT, callback);
    setNoBackground();
    setButtonSounds();
  }

  private void setNoBackground() {
    button = new Sprite(0, 0, 0x0079cc);
    buttonHover = new Sprite(0, 0, 0x004E9A);
    buttonDown = new Sprite(0, 0, 0x001366);
    currentButton = button;
  }
  
  private void setBackground() {
     button = SELECTED_BACKGROUND;
     buttonHover = SELECTED_BACKGROUND;
     buttonDown = SELECTED_BACKGROUND;
  }
  
  private void setButtonSounds() {
    hoverSound = SoundEffects.BUTTON_HOVER;
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  @Override
  protected void hover() {
    super.hover();
    textColor = HOVER_TEXT;
  }
  
  @Override
   protected void down() {
     super.down();
     textColor = DOWN_TEXT;
   }
   
  @Override
   protected void click() {
     super.click();
     setBackground();
   }
  
  @Override
  protected void setDefault() {
    super.setDefault();
    textColor = DEFAULT_TEXT;
  }
  
  public void removeBackground() {
    setNoBackground();
  }
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    font.renderText(x + LEFT_PADDING, y + TOP_PADDING, buttonText, textColor, screen);
  }
}