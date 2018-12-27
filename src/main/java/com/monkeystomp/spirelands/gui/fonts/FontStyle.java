package com.monkeystomp.spirelands.gui.fonts;

import java.awt.Color;
import java.awt.Font;

/**
 * Font Styles are a combination of a font type and a color. This is useful for creating reusable font styles that FontInfo objects can inherit.
 * @author Aaron Michael McNulty
 */
public class FontStyle {

  private final Font FONT;
  private final Color COLOR;
  /**
   * Creates a FontStyle object with the given Font type and Color.
   * @param font The font to be set to this object.
   * @param color The color to be set to this object.
   */
  public FontStyle(Font font, Color color) {
    this.FONT = font;
    this.COLOR = color;
  }

  public Font getFONT() {
    return FONT;
  }

  public Color getCOLOR() {
    return COLOR;
  }
  
}
