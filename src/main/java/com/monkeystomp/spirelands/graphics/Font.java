package com.monkeystomp.spirelands.graphics;

import com.jogamp.opengl.GL2;

/**
 * The Font class is used to draw font sprites to the screen.
 * @author Aaron Michael McNulty
 */
public class Font {
  
  private SpriteSheet fonts = new SpriteSheet("./resources/fonts/8x8_font_sheet.png");
  private Sprite[] characters = Sprite.split(fonts, 8);
  private int[] characterWidth = new int[characters.length];
  private final int CHARACTER_SPACING = 2,
                    SPACE_SIZE = 10;
  private int letterOffset;
  
  private String charactersIndex = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
  /**
   * Creates a Font object.
   */
  public Font(){
    setCharacterSpacing();
  }
  
  private void setCharacterSpacing() {
    boolean breaking = false;
    characterWidth[0] = SPACE_SIZE;
    for (int i = 1; i < characters.length; i++) {
      for (int x = characters[i].getWidth() - 1; x > -1; x--) {
        for (int y = 0; y < characters[i].getHeight(); y++) {
          if (characters[i].getPixels()[x + y * characters[i].getWidth()] != 0) {
            characterWidth[i] = x + CHARACTER_SPACING;
            breaking = true;
            break;
          }
        }
        if (breaking) {
          breaking = false;
          break;
        }
      }
    }
  }
  /**
   * Renders text to the screen at the specified x and y coordinates with the given color.
   * @param x x coordinate to begin text render.
   * @param y y coordinate to begin text render.
   * @param text The text to be rendered.
   * @param color The hex code for the color to render the font in.
   * @param screen Instance of the screen class.
   * @param gl Instance of the GL2 class.
   */
  public void renderText(int x, int y, String text, int color, Screen screen, GL2 gl) {
    letterOffset = 0;
    for (int i = 0; i < text.length(); i++) {
      screen.renderSprite(gl, x + letterOffset, y, characters[
        charactersIndex.indexOf(
          text.charAt(i)
        )
      ], color, false);
      letterOffset += characterWidth[
        charactersIndex.indexOf(
          text.charAt(i)
        )
      ];
    }
  }
}
