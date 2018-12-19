package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.input.ICallback;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameMenuNavButton extends Button {
  
  private static final int  WIDTH = 94,
                            HEIGHT = 20,
                            TOP_PADDING = 6,
                            LEFT_PADDING = 10;
  private final int DEFAULT_TEXT = 0xf1f1f1,
                    SELECTED_TEXT = 0xDAA520,
                    FONT_SIZE = 27;
//  private final Sprite SELECTED_BACKGROUND = new Sprite("./resources/gui/game_menu_nav_button.png");
  private final Sprite SELECTED_BACKGROUND = new Sprite(WIDTH, HEIGHT, 0xFF3b3b3b);;
  private final FontInfo FONT_INFO = new FontInfo(new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE), new Color(DEFAULT_TEXT));

  public GameMenuNavButton(String text, int x, int y, ICallback callback) {
    super(text, x, y, GameMenuNavButton.WIDTH, GameMenuNavButton.HEIGHT, callback);
    createButtonSprites();
    setButtonSounds();
    FONT_INFO.setText(text);
    FONT_INFO.setX(this.x + LEFT_PADDING);
    FONT_INFO.setY(y);
  }

  private void createButtonSprites() {
    button = new Sprite(WIDTH, HEIGHT, 0x000079CC);
    buttonHover = new Sprite(WIDTH, HEIGHT, 0xFF3b3b3b);
    buttonDown = new Sprite(WIDTH, HEIGHT, 0xFF4e4e4e);
    currentButton = button;
  }
  
  private void setButtonSounds() {
    hoverSound = SoundEffects.BUTTON_HOVER;
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  private void setBackground() {
     button = SELECTED_BACKGROUND;
     buttonHover = SELECTED_BACKGROUND;
     buttonDown = SELECTED_BACKGROUND;
  }
  
  @Override
  protected void hover() {
    super.hover();
  }
  
  @Override
   protected void down() {
     super.down();
   }
   
  @Override
   protected void click() {
     super.click();
     setBackground();
     FONT_INFO.setColor(new Color(SELECTED_TEXT));
   }
  
  @Override
  protected void setDefault() {
      super.setDefault();
  }
  
  public void removeBackground() {
    FONT_INFO.setColor(new Color(DEFAULT_TEXT));
    createButtonSprites();
  }
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderFonts(FONT_INFO);
  }
}
