package com.monkeystomp.spirelands.gui.styles;

import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.fonts.FontStyle;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Aaron Michael McNulty
 */
public class GameFonts {

  /**
   * Game menu button font style.
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_NAV_BUTTON() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, 27), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu primary text
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_PRIMARY_TEXT() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, 21), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu primary text thin
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_PRIMARY_TEXT_THIN() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, 21), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu headline thin
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_HEADLINE_THIN() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.PLAIN, 28), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  /**
   * Game menu headline
   * @return The font info object.
   */
  public static FontInfo getGAME_MENU_HEADLINE() {
    return new FontInfo(new FontStyle(new Font(Font.SANS_SERIF, Font.BOLD, 28), new Color(GameColors.GAME_MENU_DEFAULT_TEXT)));
  }
  
}
