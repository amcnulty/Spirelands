package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.gamedata.values.GameValues;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.gui.styles.GameFonts;

/**
 * Panel shown below the character ability card in the abilities screen of the game menu. Used to show a control for upgrading the current selected ability slot.
 * @author Aaron Michael McNulty
 */
public class UpgradeAbilitySlotPanel {
  
  private final int x, y,
                    BUTTON_X,
                    BUTTON_Y,
                    BUTTON_WIDTH = 60,
                    BUTTON_HEIGHT = 9,
                    LABEL_X,
                    LABEL_Y,
                    VALUE_X;
  private String buttonText;
  private int pointsToUpgrade;
  private boolean show = false;
  private GameMenuPrimaryButton button;
  private final FontInfo costLabel = GameFonts.getGAME_MENU_LABEL_TEXT(),
                         costValue = GameFonts.getGAME_MENU_MUTED_TEXT();
  
  public UpgradeAbilitySlotPanel(int x, int y) {
    this.x = x;
    this.y = y;
    BUTTON_X = x + 135;
    BUTTON_Y = y + 4;
    LABEL_X = BUTTON_X + BUTTON_WIDTH / 2 + 7;
    LABEL_Y = BUTTON_Y;
    VALUE_X = LABEL_X + 38;
    costLabel.setText("Ability Points:");
    costLabel.setX(LABEL_X);
    costLabel.setY(LABEL_Y);
    costValue.setX(VALUE_X);
    costValue.setY(LABEL_Y);
  }
  
  public void updatePanel(AbilitySlotClickEvent event) {
    buttonText = event.getLevel() > 0 ? "Upgrade" : "Unlock";
    if (GameValues.ABILITY_SLOT_UPGRADE_POINTS_MAP.containsKey(event.getLevel() + "_" + event.getType())) {
      pointsToUpgrade = GameValues.ABILITY_SLOT_UPGRADE_POINTS_MAP.get(event.getLevel() + "_" + event.getType());
    }
    else
      pointsToUpgrade = 0;
    button = new GameMenuPrimaryButton(buttonText, BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, () -> {});
    // TODO: Disable button if player doesn't have enough ability points
    costValue.setText(Integer.toString(pointsToUpgrade));
    show = pointsToUpgrade > 0;
    
  }

  public String getButtonText() {
    return buttonText;
  }

  public int getPointsToUpgrade() {
    return pointsToUpgrade;
  }

  public boolean isShow() {
    return show;
  }
  
  public void update() {
    if (show) {
      button.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (show) {
      button.render(screen, gl);
      screen.renderFonts(costLabel);
      screen.renderFonts(costValue);
    }
  }

}
