package com.monkeystomp.spirelands.gui.fonts;

import java.awt.Color;

/**
 *
 * @author Aaron Michael McNulty
 */
public class FontInfo {

  private Color color;
  private String text;
  private int x,
              y;
  
  public FontInfo(Color color, String text, int x, int y) {
    this.color = color;
    this.text = text;
    this.x = x;
    this.y = y;
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
