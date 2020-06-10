package com.monkeystomp.spirelands.gui.styles;

/**
 * Central location to define all color variables used in the game. This allows for easier maintenance of colors and creates a better inheritance structure that defining color variables in the classes they are used.
 * @author Aaron Michael McNulty
 */
public class GameColors {
  /**       
   *        !!##################################!!
   *        !!                                  !!
   *        !!           Base Colors            !!
   *        !!                                  !!
   *        !!##################################!!
   */
  
  /**
   * Fully transparent color #00000000.
   */
  public static final int TRANSPARENT = 0x00000000;
  /**
   * White #0xFFFFFFFF
   */
  public static final int WHITE = 0xFFFFFFFF;
  /**
   * Black #0xFF000000
   */
  public static final int BLACK = 0xFF000000;
  /**
   * Red #0xFFFF0000
   */
  public static final int RED = 0xFFFF0000;
  /**
   * Green #0xFF00FF00
   */
  public static final int GREEN = 0xFF00FF00;
  /**
   * Dark Green #0xFF009900;
   */
  public static final int DARK_GREEN = 0xFF009900;
  /**
   * Blue #0xFF0000FF
   */
  public static final int BLUE = 0xFF0000FF;
  /**
   *        !!##################################################!!
   *        !!                                                  !!
   *        !!      UI, Menu, light map, particles colors       !!
   *        !!                                                  !!
   *        !!##################################################!!
   */
  /**
   * Underline color for health bar #0xFFB20000
   */
  public static final int HEALTH_BAR_UNDERLINE = 0xFFB20000;
  /**
   * Health bar filled color #0xFFFF0000
   */
  public static final int HEALTH_BAR_FILL = RED;
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
  public static final int MANA_BAR_FILL = BLUE;
  /**
   * Mana bar empty color #0xFF9999FF
   */
  public static final int MANA_BAR_EMPTY = 0xFF9999FF;
  /**
   * ATB gauge bar filled color #0xFF99FF99;
   */
  public static final int ATB_GAUGE_BAR_FILLED = 0xFF99FF99;
  /**
   * ATB gauge bar empty color #0xDDFFFFFF
   */
  public static final int ATB_GAUGE_BAR_EMPTY = 0xDDFFFFFF;
  /**
   * Experience gauge filled color #0xFF00B400;
   */
  public static final int EXP_GAUGE_FILL = 0xFF00B400;
  /**
   * Experience gauge empty color #0xFF6BB46B;
   */
  public static final int EXP_GAUGE_EMPTY = 0xFF6BB46B;
  /**
   * Dark gray used for hovering game menu buttons #0xFF2C2C2C.
   */
  public static final int GAME_MENU_BUTTON_HOVER = 0xFF2C2C2C;
  /**
   * Dark gray used for down state on game menu buttons #0xFF393939.
   */
  public static final int GAME_MENU_BUTTON_DOWN = 0xFF393939;
  /**
   * Dark gray background color for the game menu #0xFF1E1E1E;
   */
  public static final int GAME_MENU_BACKGROUND = 0xFF1E1E1E;
  /**
   * Border color used in the game menu #0xFFE2E2E2.
   */
  public static final int GAME_MENU_BORDER = 0xFFE2E2E2;
  /**
   * Background color for equipped item slot #0xFF393939.
   */
  public static final int EQUIPPED_ITEM_SLOT_BACKGROUND = 0xFF393939;
  /**
   * Background color for hovering equipped item slot #0xFF454545.
   */
  public static final int EQUIPPED_ITEM_SLOT_HOVER = 0xFF454545;
  /**
   * Background color for down state on equipped item slot #0xFF515151.
   */
  public static final int EQUIPPED_ITEM_SLOT_DOWN = 0xFF515151;
  /**
   * Color of the light map for night time effect #0xFF121212
   */
  public static final int LIGHTMAP_COLOR = 0xFF121212;
  /**
   * Ember particle color #0xFFF05E1B
   */
  public static final int EMBER_PARTICLE_COLOR = 0xFFF05E1B;
  /**
   * Gold particle color #0xFFFCC201
   */
  public static final int GOLD_PARTICLE_COLOR = 0xFFFCC201;
  /**
   * Primary button blue color #0x0079CC
   */
  public static final int PRIMARY_BUTTON_BLUE = 0xFF0079CC;
  /**
   * Primary button blue hover color #0xFF004E9A
   */
  public static final int PRIMARY_BUTTON_BLUE_HOVER = 0xFF004E9A;
  /**
   * Primary button blue down color #0xFF001366
   */
  public static final int PRIMARY_BUTTON_BLUE_DOWN = 0xFF001366;
  /**
   * Danger button default color #0xFFD22233
   */
  public static final int DANGER_BUTTON_RED = 0xFFD22233;
  /**
   * Danger button hover color #0xFF99000D
   */
  public static final int DANGER_BUTTON_HOVER = 0xFF99000D;
  /**
   * Danger button down color #0xFF6C0000
   */
  public static final int DANGER_BUTTON_DOWN = 0xFF6C0000;
  /**
   * Dialog box background color #0xFFEFEFEF
   */
  public static final int DIALOG_BOX_BACKGROUND = 0xFFEFEFEF;
  /**
   * Backdrop color for the title screen menus #0xDDFFFFFF
   */
  public static final int TITLE_SCREEN_MENU_BACKDROP = 0xDDFFFFFF;
  /**
   * Battle move slot color for physical moves #0xFFBB0A1E
   */
  public static final int BATTLE_MOVE_SLOT_PHYSICAL = 0xFFBB0A1E;
  /**
   * Battle move slot color for magical moves #0xFF1E0ABB
   */
  public static final int BATTLE_MOVE_SLOT_MAGICAL = 0xFF1E0ABB;
  /**
   * Battle move slot color for buff moves #0xFF1EBBBB
   */
  public static final int BATTLE_MOVE_SLOT_BUFF = 0xFF1EBBBB;
  /**
   * Battle move slot color for buff moves #0xFF0ABB1E
   */
  public static final int BATTLE_MOVE_SLOT_ITEM = 0xFF0ABB1E;
  /**
   *        !!########################################!!
   *        !!                                        !!
   *        !!              Font Colors               !!
   *        !!                                        !!
   *        !!########################################!!
   */
  /**
   * Primary text color for light backgrounds #0xFF323232
   */
  public static final int DARK_TEXT = 0xFF323232;
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
   * Subdued gold color for labels in the game menu #0xFF6B5600
   */
  public static final int GAME_MENU_LABEL_TEXT_DARK = 0xFF6B5600;
  /**
   * Muted text for descriptions and non highlighted text in game menu #0x888888
   */
  public static final int GAME_MENU_MUTED_TEXT = 0xFF888888;
  
}
