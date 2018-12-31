package com.monkeystomp.spirelands.gui.styles;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.fonts.FontStyle;
import java.awt.Color;
import java.awt.Font;

/**
 * Central location for font definitions used across the application. This allows for easier maintenance of fonts and creates a better inheritance structure that defining fonts in the classes they are used.
 * @author Aaron Michael McNulty
 */
public class GameFonts {
  /**
   * Light text for the primary button.
   * @return The font info object.
   */
  public static FontInfo getPrimaryButtonText() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(16)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Dark text for light backgrounds.
   * @return The font info object.
   */
  public static FontInfo getGameMenuPrimaryButtonText() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(16)), new Color(GameColors.GAME_MENU_LABEL_TEXT)));
  }
  /**
   * Game menu label text subdued gold.
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_LABEL_TEXT() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(18)), new Color(GameColors.GAME_MENU_LABEL_TEXT)));
  }
  /**
   * Game menu muted grey text.
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_MUTED_TEXT() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(18)), new Color(GameColors.GAME_MENU_MUTED_TEXT)));
  }
  /**
   * Game menu primary text white.
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_PRIMARY_TEXT_SMALL() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(18)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Dark text for light backgrounds.
   * @return The font info object.
   */
  public static FontInfo getDarkText_plain_18() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(18)), new Color(GameColors.DARK_TEXT)));
  }
  /**
   * Game menu primary text
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_PRIMARY_TEXT() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(21)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu primary text thin
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_PRIMARY_TEXT_THIN() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(21)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Dark text for light backgrounds.
   * @return The font info object.
   */
  public static FontInfo getDarkText_bold_22() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(22)), new Color(GameColors.DARK_TEXT)));
  }
  /**
   * Light text for darks backgrounds.
   * @return The font info object.
   */
  public static FontInfo getlightText_bold_23() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(23)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu button font style.
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_NAV_BUTTON() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(27)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu headline thin
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_HEADLINE_THIN() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(28)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu headline
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_HEADLINE() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, getAdjustedFontSize(28)), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Dialog box symbol font.
   * @return The font info object.
   */
  public static FontInfo getDialogBoxSymbolFont() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, getAdjustedFontSize(30)), new Color(GameColors.DARK_TEXT)));
  }
  
  
  
  private static int getAdjustedFontSize(int baseSize) {
    return (int)(baseSize * Screen.getScaleY() / 3.0);
  }
  
}
