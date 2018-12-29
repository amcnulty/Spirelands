package com.monkeystomp.spirelands.gui.styles;

/**
 * Central location to define all color variables used in the game. This allows for easier maintenance of colors and creates a better inheritance structure that defining color variables in the classes they are used.
 * @author Aaron Michael McNulty
 */
public class GameColors {
  /**
   * Underline color for health bar #0xFFB20000
   */
  public static final int HEALTH_BAR_UNDERLINE = 0xFFB20000;
  /**
   * Health bar filled color #0xFFFF0000
   */
  public static final int HEALTH_BAR_FILL = 0xFFFF0000;
  /**
   * Health bar empty color #0xFFFF9999
   */
  public static final int HEALTH_BAR_EMPTY = 0xFFFF9999;  
  /**
   * Underline color for mana bar #0xFF0000AF
   */
  public static final int MANA_BAR_UNDERLINE = 0xFF0000AF;
  /**
   * Mana bar filled color #0xFF0000FF
   */
  public static final int MANA_BAR_FILL = 0xFF0000FF;
  /**
   * Mana bar empty color #0xFF9999FF
   */
  public static final int MANA_BAR_EMPTY = 0xFF9999FF;
  /**
   * Fully transparent color #00000000.
   */
  public static final int TRANSPARENT = 0x00000000;
  /**
   * Dark gray used for hovering game menu buttons #0xFF2C2C2C.
   */
  public static final int GAME_MENU_BUTTON_HOVER = 0xFF2C2C2C;
  /**
   * Dark gray used for down state on game menu buttons #0xFF393939.
   */
  public static final int GAME_MENU_BUTTON_DOWN = 0xFF393939;
  /**
   * Border color used in the game menu #0xE2E2E2.
   */
  public static final int GAME_MENU_BORDER = 0xFFE2E2E2;
  
  //
  //  Font Colors
  //
  /**
   * Default text color in the game menu #0xF1F1F1
   */
  public static final int GAME_MENU_DEFAULT_TEXT = 0xFFF1F1F1;
  /**
   * Selected text color in the game menu #0xDAA520
   */
  public static final int GAME_MENU_SELECTED_TEXT = 0xFFDAA520;
  /**
   * Subdued gold color for labels in the game menu #0xA6911E
   */
  public static final int GAME_MENU_LABEL_TEXT = 0xFFA6911E;
  /**
   * Muted text for descriptions and non highlighted text in game menu #0x888888
   */
  public static final int GAME_MENU_MUTED_TEXT = 0xFF888888;
  
}
