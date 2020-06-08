package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.battle.move.BattleMoveAttribute;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.views.AbilitiesView;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.gui.util.TextUtil;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.ItemAttribute;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A display on the abilities view of the game menu for showing more detailed information of a Battle Move object.
 * @author Aaron Michael McNulty
 */
public class BattleMoveDetailCard {
  
  private final double topBorderSpriteScale = 100.0 / 3.0;
  private final int closeButtonX = AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.width - 5,
                    closeButtonY = AbilitiesView.ABILITY_LIST_BOUNDS.y + 6,
                    sidePadding = 5,
                    topBorderWidth = 600,
                    topBorderHeight = 3,
                    topBorderColor = GameColors.GAME_MENU_MUTED_TEXT,
                    centerLine =  AbilitiesView.ABILITY_LIST_BOUNDS.x +  AbilitiesView.ABILITY_LIST_BOUNDS.width / 2,
                    topBorderRenderX = centerLine - ((int)(topBorderWidth * (topBorderSpriteScale / 100.0))) / 2,
                    moveTitleY = AbilitiesView.ABILITY_LIST_BOUNDS.y + 7,
                    descriptionY = moveTitleY + 7,
                    descriptionX = AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.width / 6,
                    lineSpacing = 7,
                    attributeLabelX = AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.width * 2 / 3;
  private Character character;
  private BattleMove battleMove;
  private ICallback ICloseButtonCallback;
  private final GameMenuSecondaryButton closeButton = new GameMenuSecondaryButton(
    "\u2baa",
    closeButtonX,
    closeButtonY,
    8,
    9,
    () -> ICloseButtonCallback.execute()
  );
  private final Sprite topBorder;
  private final FontInfo  moveTitleFont = GameFonts.getGAME_MENU_PRIMARY_TEXT(),
                          descriptionFont = GameFonts.getGAME_MENU_MUTED_TEXT();
  private final ArrayList<FontInfo> moveDescriptionList = new ArrayList<>(),
                                    attributeLabelList = new ArrayList<>(),
                                    attributeValueList = new ArrayList<>();
  
  public BattleMoveDetailCard(ICallback ICloseButtonCallback) {
    int[] pixels = new int[topBorderWidth * topBorderHeight];
    Arrays.fill(pixels, 0, 599, GameColors.TRANSPARENT);
    Arrays.fill(pixels, 600, 1199, topBorderColor);
    Arrays.fill(pixels, 1200, 1799, GameColors.TRANSPARENT);
    topBorder = new Sprite(new Sprite(pixels, topBorderWidth, topBorderHeight), topBorderSpriteScale);
    this.ICloseButtonCallback = ICloseButtonCallback;
  }
  
  public void show(BattleMove battleMove, Character character) {
    this.battleMove = battleMove;
    this.character = character;
    setFontInfo();
  }
  
  private void setFontInfo() {
    moveTitleFont.setText(battleMove.getName());
    moveTitleFont.setX(centerLine);
    moveTitleFont.setY(moveTitleY);
    moveTitleFont.centerText();

    ArrayList<String> lines = TextUtil.createWrappedText(battleMove.getDescription(), descriptionFont.getFont(), AbilitiesView.ABILITY_LIST_BOUNDS.width / 3 - 2 * sidePadding);
    moveDescriptionList.clear();
    for (int i = 0; i < lines.size(); i++) {
      FontInfo info = GameFonts.getGAME_MENU_MUTED_TEXT();
      info.setText(lines.get(i));
      info.setX(descriptionX);
      info.setY(descriptionY + i * lineSpacing);
      info.centerText();
      moveDescriptionList.add(info);
    }
    
        
    ArrayList<BattleMoveAttribute> attributes = battleMove.getAttributes();
    attributeLabelList.clear();
    attributeValueList.clear();
    for (int i = 0; i < attributes.size(); i++) {
      FontInfo labelInfo = GameFonts.getGAME_MENU_LABEL_TEXT();
      labelInfo.setText(attributes.get(i).getLabel() + ":");
      labelInfo.setX(attributeLabelX);
      labelInfo.setY(descriptionY + i * lineSpacing);
      attributeLabelList.add(labelInfo);
      
      FontInfo valueInfo = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
      valueInfo.setText(attributes.get(i).getValue());
      valueInfo.setX(AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.width - sidePadding);
      valueInfo.setY(descriptionY + i * lineSpacing);
      valueInfo.rightAlignText();
      attributeValueList.add(valueInfo);
    }
    if (battleMove.getType().equals(BattleMove.ITEM)) {
      for (int i = attributes.size(); i < attributes.size() + battleMove.getItem().getAttributes().size(); i++) {
        FontInfo labelInfo = GameFonts.getGAME_MENU_LABEL_TEXT();
        labelInfo.setText(battleMove.getItem().getAttributes().get(i - attributes.size()).getLabel() + ":");
        labelInfo.setX(attributeLabelX);
        labelInfo.setY(descriptionY + i * lineSpacing);
        attributeLabelList.add(labelInfo);

        FontInfo valueInfo = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
        valueInfo.setText(battleMove.getItem().getAttributes().get(i - attributes.size()).getValue());
        valueInfo.setX(AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.width - sidePadding);
        valueInfo.setY(descriptionY + i * lineSpacing);
        valueInfo.rightAlignText();
        attributeValueList.add(valueInfo);
      }
    }
  }
  
  public void update() {
    closeButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, topBorderRenderX, AbilitiesView.ABILITY_LIST_BOUNDS.y, topBorder, false);
    screen.renderFonts(moveTitleFont);
    for (FontInfo info: moveDescriptionList) {
      screen.renderFonts(info);
    }
    for (FontInfo info: attributeLabelList) {
      screen.renderFonts(info);
    }
    for (FontInfo info: attributeValueList) {
      screen.renderFonts(info);
    }
    closeButton.render(screen, gl);
  }

}
