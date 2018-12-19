package com.monkeystomp.spirelands.gui.fonts;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class FontStyle {

  private final Font FONT;
  private final Color COLOR;
  
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
