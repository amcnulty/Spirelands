package com.monkeystomp.spirelands.gui.fonts;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class FontInfo {

  private Font font;
  private Color color;
  private String text;
  private int x,
              y;
  
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
