package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.gamedata.values.GameValues;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import java.util.ArrayList;
import java.util.Arrays;

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
                    valueX,
                    popoverWidth = 70,
                    popoverPaddingY = 10,
                    popoverX,
                    popoverY,
                    popoverTextY,
                    spaceBetweenLines = 5;
  private String  buttonText,
                  popoverMessage;
  private Sprite popoverBackground;
  private int pointsToUpgrade;
  private boolean showing = false;
  private GameMenuPrimaryButton button;
  private final FontInfo costLabel = GameFonts.getGAME_MENU_LABEL_TEXT(),
                         costValue = GameFonts.getGAME_MENU_MUTED_TEXT(),
                         popoverFont = GameFonts.getDarkText_bold_16();
  private final ArrayList<FontInfo> popoverLines = new ArrayList<>();
  
  public UpgradeAbilitySlotPanel(int x, int y) {
    this.x = x;
    this.y = y;
    buttonX = x + 135;
    buttonY = y + 4;
    labelX = buttonX + buttonWidth / 2 + 10;
    labelY = buttonY - Sprite.ABILITY_POINTS_INDICATOR.getHeight() / 2 + 1;
    valueX = labelX + 16;
    popoverX = buttonX - popoverWidth / 2;
    popoverY = buttonY + 10;
    popoverTextY = buttonY + 17;
    costLabel.setText("Ability Points:");
    costLabel.setX(labelX);
    costLabel.setY(labelY);
    costValue.setX(valueX);
    costValue.setY(buttonY);
  }
  
  public void updatePanel(AbilitySlotClickEvent event) {
    if (event.getLevel() > 0) {
      buttonText = "Upgrade";
      if (!event.getType().equals(BattleMove.ITEM))
        popoverMessage = "Upgrade this slot to equip level " + (event.getLevel() + 1) + " " + event.getType().toLowerCase() + " type moves.";
      else
        popoverMessage = "Upgrade this slot to equip up to " + GameValues.AVAILABLE_ITEM_SLOTS_PER_LEVEL_MAP.get(event.getLevel() + 1) + " " + event.getType().toLowerCase() + " type moves.";
    }
    else {
      buttonText = "Unlock";
      if (!event.getType().equals(BattleMove.ITEM))
        popoverMessage = "Unlock this slot to equip level " + (event.getLevel() + 1) + " " + event.getType().toLowerCase() + " type moves.";
      else
        popoverMessage = "Unlock this slot to equip up to " + GameValues.AVAILABLE_ITEM_SLOTS_PER_LEVEL_MAP.get(event.getLevel() + 1) + " " + event.getType().toLowerCase() + " type moves.";
    }
    ArrayList<String> lines = TextUtil.createWrappedText(popoverMessage, popoverFont.getFont(), buttonWidth);
    popoverLines.clear();
    for (String line: lines) {
      FontInfo info = GameFonts.getDarkText_bold_16();
      info.setText(line);
      info.setX(buttonX);
      info.setY(popoverTextY + spaceBetweenLines * popoverLines.size());
      info.centerText();
      popoverLines.add(info);
    }
    createPopover(lines.size());
    buttonText = event.getLevel() > 0 ? "Upgrade" : "Unlock";
    if (GameValues.ABILITY_SLOT_UPGRADE_POINTS_MAP.containsKey(event.getLevel() + "_" + event.getType())) {
      pointsToUpgrade = GameValues.ABILITY_SLOT_UPGRADE_POINTS_MAP.get(event.getLevel() + "_" + event.getType());
    }
    else
      pointsToUpgrade = 0;
    button = new GameMenuPrimaryButton(buttonText, buttonX, buttonY, buttonWidth, buttonHeight, () -> {
      InventoryManager.getInventoryManager().reduceAbilityPoints(pointsToUpgrade);
      event.getSlot().upgradeSlot();
    });
    if (pointsToUpgrade > InventoryManager.getInventoryManager().getAbilityPoints()) {
      button.setDisabled(true);
    }
    costValue.setText(Integer.toString(pointsToUpgrade));
    showing = pointsToUpgrade > 0;
  }
  
  private void createPopover(int lines) {
    int popoverHeight = popoverPaddingY + spaceBetweenLines * lines;
    int[] pixels = new int[popoverWidth * popoverHeight];
    Arrays.fill(pixels, GameColors.DIALOG_BOX_BACKGROUND);
    popoverBackground = new Sprite(pixels, popoverWidth, popoverHeight);
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
  
  public void hide() {
    showing = false;
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
      if (button.isHovering()) {
        screen.renderSprite(gl, popoverX, popoverY, popoverBackground, false);
        for (FontInfo info: popoverLines) {
          screen.renderFonts(info);
        }
      }
    }
  }

}
