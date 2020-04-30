package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.gamedata.values.GameValues;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.inventory.InventoryManager;

/**
 * Panel shown below the character ability card in the abilities screen of the game menu. Used to show a control for upgrading the current selected ability slot.
 * @author Aaron Michael McNulty
 */
public class UpgradeAbilitySlotPanel {
  
  private final int x, y,
                    buttonX,
                    buttonY,
                    buttonWidth = 60,
                    buttonHeight = 9,
                    labelX,
                    labelY,
                    valueX;
  private String buttonText;
  private int pointsToUpgrade;
  private boolean showing = false;
  private GameMenuPrimaryButton button;
  private final FontInfo costLabel = GameFonts.getGAME_MENU_LABEL_TEXT(),
                         costValue = GameFonts.getGAME_MENU_MUTED_TEXT();
  
  public UpgradeAbilitySlotPanel(int x, int y) {
    this.x = x;
    this.y = y;
    buttonX = x + 135;
    buttonY = y + 4;
    labelX = buttonX + buttonWidth / 2 + 10;
    labelY = buttonY - Sprite.ABILITY_POINTS_INDICATOR.getHeight() / 2 + 1;
    valueX = labelX + 16;
    costLabel.setText("Ability Points:");
    costLabel.setX(labelX);
    costLabel.setY(labelY);
    costValue.setX(valueX);
    costValue.setY(buttonY);
  }
  
  public void updatePanel(AbilitySlotClickEvent event) {
    buttonText = event.getLevel() > 0 ? "Upgrade" : "Unlock";
    if (GameValues.ABILITY_SLOT_UPGRADE_POINTS_MAP.containsKey(event.getLevel() + "_" + event.getType())) {
      pointsToUpgrade = GameValues.ABILITY_SLOT_UPGRADE_POINTS_MAP.get(event.getLevel() + "_" + event.getType());
    }
    else
      pointsToUpgrade = 0;
    button = new GameMenuPrimaryButton(buttonText, buttonX, buttonY, buttonWidth, buttonHeight, () -> {});
    if (pointsToUpgrade > InventoryManager.getInventoryManager().getAbilityPoints()) {
      button.setDisabled(true);
    }
    costValue.setText(Integer.toString(pointsToUpgrade));
    showing = pointsToUpgrade > 0;
  }
  
  public int getPanelBottom() {
    return buttonY + buttonHeight;
  }

  public String getButtonText() {
    return buttonText;
  }

  public int getPointsToUpgrade() {
    return pointsToUpgrade;
  }

  public boolean isShowing() {
    return showing;
  }
  
  public void update() {
    if (showing) {
      button.update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (showing) {
      button.render(screen, gl);
      screen.renderSprite(gl, labelX, labelY, Sprite.ABILITY_POINTS_INDICATOR, false);
      screen.renderFonts(costValue);
    }
  }

}
