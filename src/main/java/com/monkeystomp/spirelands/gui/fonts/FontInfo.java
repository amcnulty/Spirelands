package com.monkeystomp.spirelands.gui.fonts;

import java.awt.Color;
import java.awt.Font;

/**
 * Font info is a configuration class that holds information about a font to be rendered.
 * @author Aaron Michael McNulty
 */
public class FontInfo {

  private Font font;
  private Color color;
  private String text;
  private int x,
              y;
  /**
   * Creates a FontInfo object with the specified configuration.
   * @param font The font object to set.
   * @param color The color object to set.
   * @param text The text that the configuration will be applied to.
   * @param x The x location to render this text.
   * @param y The y location to render this text.
   */
  public FontInfo(Font font, Color color, String text, int x, int y) {
    this.font = font;
    this.color = color;
    this.text = text;
    if (this.text == null) this.text = "";
    this.x = x;
    this.y = y;
  }

  public Font getFont() {
    return font;
  }

  public String getText() {
    return text;
  }

  public Color getColor() {
    return color;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
