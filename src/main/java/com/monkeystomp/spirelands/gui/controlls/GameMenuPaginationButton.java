package com.monkeystomp.spirelands.gui.controlls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameMenuPaginationButton extends Button {
  
  private FontInfo fontInfo;
  private Sprite selectedBackground;
  private final int pageIndex;
  /**
   * Creates a new GameMenuPrimaryButton
   * @param text The text to be rendered on the button.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param width The width of the button.
   * @param height The height of the button.
   * @param pageIndex The page number associated with this button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public GameMenuPaginationButton(String text, int x, int y, int width, int height, int pageIndex, Consumer<Integer> callback) {
    super(text, x, y, width, height, Integer.valueOf(pageIndex), callback);
    this.pageIndex = pageIndex;
    setFontInfo();
    createButtonSprites();
    setButtonSounds();
  }
  
  private void setFontInfo() {
    Rectangle2D rect = GameFonts.getGameMenuPrimaryButtonText().getFont().getStringBounds(buttonText, new FontRenderContext(null, true, true));
    int textWidth = (int)rect.getWidth();
    int fontX = (int)(x + (this.width - (textWidth / Screen.getScaleX())) / 2);
    int fontY = y + this.height / 2;
    fontInfo = GameFonts.getGameMenuPrimaryButtonText();
    fontInfo.setText(buttonText);
    fontInfo.setX(fontX);
    fontInfo.setY(fontY);
  }

  private void createButtonSprites() {
    button = createBorderSprite();
    buttonHover = new Sprite(width, height, GameColors.GAME_MENU_LABEL_TEXT);
    buttonDown = new Sprite(width, height, GameColors.GAME_MENU_LABEL_TEXT_DARK);
    this.selectedBackground = buttonHover;
    currentButton = button;
  }
  
  private Sprite createBorderSprite() {
    int[] pixels = new int[width * height];
    for (int yy = 0; yy < height; yy++) {
      for (int xx = 0; xx < width; xx++) {
        if (yy == 0 || yy == height - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_LABEL_TEXT;
        }
        else if (xx == 0 || xx == width - 1) {
          pixels[xx + yy * width] = GameColors.GAME_MENU_LABEL_TEXT;
        }
      }
    }
    return new Sprite(pixels, width, height);
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
  protected void hover() {
    super.hover();
    fontInfo.setColor(new Color(GameColors.GAME_MENU_DEFAULT_TEXT));
  }
   
  @Override
  protected void click() {
    super.click();
    setBackground();
    fontInfo.setColor(new Color(GameColors.GAME_MENU_DEFAULT_TEXT));
    setDisabled(true);
  }
  
  public void setActive() {
    setBackground();
    fontInfo.setColor(new Color(GameColors.GAME_MENU_DEFAULT_TEXT));
    setDisabled(true);
  }
  
  public void removeBackground() {
    createButtonSprites();
    setDisabled(false);
  }
  
  @Override
  protected void setDefault() {
    super.setDefault();
    fontInfo.setColor(new Color(GameColors.GAME_MENU_LABEL_TEXT));
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderFonts(fontInfo);
  }

}
