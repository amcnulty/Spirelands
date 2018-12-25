package com.monkeystomp.spirelands.gui.fonts;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;

/**
 * Font info is a configuration class that holds information about a font to be rendered.
 * @author Aaron Michael McNulty
 */
public class FontInfo {

  private final Font FONT;
  private final TextRenderer TEXT_RENDERER;
  private FontStyle fontStyle;
  private Color color;
  private String  text;
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
    this.FONT = font;
    this.TEXT_RENDERER = new TextRenderer(font);
    this.color = color;
    this.text = text;
    if (this.text == null) this.text = "";
    this.x = x;
    this.y = y;
  }
  
  public FontInfo(Font font, Color color) {
    this.FONT = font;
    this.TEXT_RENDERER = new TextRenderer(font);
    this.color = color;
  }
  
  public FontInfo(FontStyle fontStyle) {
    this.fontStyle = fontStyle;
    this.TEXT_RENDERER = new TextRenderer(fontStyle.getFONT());
    this.FONT = fontStyle.getFONT();
    this.color = fontStyle.getCOLOR();
  }

  public Font getFont() {
    return FONT;
  }
  
  public TextRenderer getTextRenderer() {
    return TEXT_RENDERER;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Color getColor() {
    return color;
  }
  
  public void setColor(Color color) {
    this.color = color;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
  
}
