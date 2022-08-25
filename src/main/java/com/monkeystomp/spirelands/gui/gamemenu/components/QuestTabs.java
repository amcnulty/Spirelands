package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.TabButton;
import com.monkeystomp.spirelands.gui.styles.GameColors;

/**
 * Tab buttons at the top of the quests view used to switch between different types of quests.
 * @author Aaron Michael McNulty
 */
public class QuestTabs {
  
  private final int mainButtonX = 158,
                    secondaryButtonX = 225,
                    collectiblesButtonX = 292,
                    completeButtonX = 360,
                    divider1X = 191,
                    divider2X = 259,
                    divider3X = 326,
                    dividerY = 26,
                    buttonWidth = 55,
                    tabButtonY = 29,
                    underlineX = 125,
                    underlineY = 35;
  private final Sprite tabButtonDefault = new Sprite(buttonWidth, 11, GameColors.GAME_MENU_BACKGROUND);
  private final Sprite tabButtonHover = new Sprite(buttonWidth, 11, GameColors.GAME_MENU_BUTTON_HOVER);
  private final Sprite tabButtonDown = new Sprite(buttonWidth, 11, GameColors.GAME_MENU_BUTTON_DOWN);
  private final Sprite mainButtonSelected = new Sprite(buttonWidth, 11, GameColors.PRIMARY_BLUE);
  private final Sprite mainUnderline = new Sprite(270, 1, GameColors.PRIMARY_BLUE);
  private final Sprite secondaryButtonSelected = new Sprite(buttonWidth, 11, GameColors.PASTEL_GREEN);
  private final Sprite secondaryUnderline = new Sprite(270, 1, GameColors.PASTEL_GREEN);
  private final Sprite collectiblesButtonSelected = new Sprite(buttonWidth, 11, GameColors.BRIGHT_GOLD);
  private final Sprite collectiblesUnderline = new Sprite(270, 1, GameColors.BRIGHT_GOLD);
  private final Sprite completeUnderline = new Sprite(270, 1, GameColors.GAME_MENU_BUTTON_HOVER);
  private final Sprite divider = new Sprite(1, 7, GameColors.GAME_MENU_MUTED_TEXT);
  private final TabButton mainButton = new TabButton("Main", mainButtonX, tabButtonY, tabButtonDefault, tabButtonHover, tabButtonDown, tabButtonDefault, mainButtonSelected, GameColors.BLACK,
    () -> {
      activeUnderline = mainUnderline;
      handleTabButtonClick(this.mainButton);
    }
  );
  private final TabButton secondaryButton = new TabButton("Secondary", secondaryButtonX, tabButtonY, tabButtonDefault, tabButtonHover, tabButtonDown, tabButtonDefault, secondaryButtonSelected, GameColors.BLACK,
    () -> {
      activeUnderline = secondaryUnderline;
      handleTabButtonClick(this.secondaryButton);
    }
  );
  private final TabButton collectiblesButton = new TabButton("Collectibles", collectiblesButtonX, tabButtonY, tabButtonDefault, tabButtonHover, tabButtonDown, tabButtonDefault, collectiblesButtonSelected, GameColors.BLACK,
    () -> {
      activeUnderline = collectiblesUnderline;
      handleTabButtonClick(this.collectiblesButton);
    }
  );
  private final TabButton completeButton = new TabButton("Complete", completeButtonX, tabButtonY, tabButtonDefault, tabButtonHover, tabButtonDown, tabButtonDefault, tabButtonHover, GameColors.WHITE,
    () -> {
      activeUnderline = completeUnderline;
      handleTabButtonClick(this.completeButton);
    }
  );
  private Sprite activeUnderline = mainUnderline;
  
  /**
   * Returns the tabs to their initial state.
   */
  public void resetTabs() {
    mainButton.selectButton();
  }
  
  private void handleTabButtonClick(TabButton button) {
    if (button != mainButton) mainButton.resetButton();
    if (button != secondaryButton) secondaryButton.resetButton();
    if (button != collectiblesButton) collectiblesButton.resetButton();
    if (button != completeButton) completeButton.resetButton();
  }
  
  public void update() {
    mainButton.update();
    secondaryButton.update();
    collectiblesButton.update();
    completeButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    mainButton.render(screen, gl);
    screen.renderSprite(gl, divider1X, dividerY, divider, false);
    secondaryButton.render(screen, gl);
    screen.renderSprite(gl, divider2X, dividerY, divider, false);
    collectiblesButton.render(screen, gl);
    screen.renderSprite(gl, divider3X, dividerY, divider, false);
    completeButton.render(screen, gl);
    screen.renderSprite(gl, underlineX, underlineY, activeUnderline, false);
  }

}
