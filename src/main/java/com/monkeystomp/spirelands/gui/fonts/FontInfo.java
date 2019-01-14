package com.monkeystomp.spirelands.gui.fonts;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.monkeystomp.spirelands.graphics.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;

/**
 * Font info is a configuration class that holds information about a font to be rendered.
 * @author Aaron Michael McNulty
 */
public class FontInfo {

  private final Font font;
  private final TextRenderer textRenderer;
  private FontStyle fontStyle;
  private Color color;
  private String  text;
  private int x,
              y;
  private static FontRenderContext frc = new FontRenderContext(null, true, true);
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
    this.textRenderer = new TextRenderer(font);
    this.color = color;
    this.text = text;
    if (this.text == null) this.text = "";
    this.x = x;
    this.y = y;
  }
  
  public FontInfo(Font font, Color color) {
    this.font = font;
    this.textRenderer = new TextRenderer(font);
    this.color = color;
  }
  
  public FontInfo(FontStyle fontStyle) {
    this.fontStyle = fontStyle;
    this.textRenderer = new TextRenderer(fontStyle.getFONT());
    this.font = fontStyle.getFONT();
    this.color = fontStyle.getCOLOR();
  }
  /**
   * Centers the text horizontally around the x coordinate.
   */
  public void centerText() {
    x -= (int)((font.getStringBounds(text, frc).getBounds().getWidth() / Screen.getScaleX()) / 2);
  }
  /**
   * Right aligns text so the right edge is at the x coordinate.
   */
  public void rightAlignText() {
    x -= (int) font.getStringBounds(text, frc).getBounds().getWidth() / Screen.getScaleX();
  }

  public Font getFont() {
    return font;
  }
  
  public TextRenderer getTextRenderer() {
    return textRenderer;
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
