package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.DangerButton;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.util.Arrays;

/**
 * Used to show party members information on the party view in the game menu. Cards can be clicked on to add or remove characters from the party.
 * @author Aaron Michael McNulty
 */
public class PartyMemberCard {
  
  private final int x, y,
                    centerLine,
                    headerHeight = 14,
                    bodyHeight = 33,
                    footerHeight = 15,
                    cardWidth = 60,
                    cardHeight = headerHeight + bodyHeight + footerHeight,
                    cardMargin = 8,
                    unselectedBorderColor = GameColors.GAME_MENU_MUTED_TEXT,
                    selectedBorderColor = GameColors.DARK_GREEN,
                    cardHeaderColor = GameColors.EQUIPPED_ITEM_SLOT_HOVER,
                    cardBackground = GameColors.DARK_TEXT,
                    nameY,
                    thumbnailX,
                    thumbnailY,
                    labelX,
                    valueX,
                    rowStartingY,
                    spaceBetweenRows = 6,
                    positionLabelX,
                    positionValueX,
                    positionY,
                    buttonY,
                    buttonWidth,
                    buttonHeight = 9;
  private final float thumbnailScale = .625f;
  private boolean isInParty = false,
                  isPartyLeader = false,
                  isAvailable = false;
  private final Character character;
  private Sprite  unselectedCardBackground,
                  selectedCardBackground;
  private final PrimaryButton addButton;
  private final DangerButton removeButton;
  private final FontInfo  nameFont = GameFonts.getPrimaryButtonText(),
                          levelLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          hpLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          mpLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          levelValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          hpValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          mpValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          positionLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          positionValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT(),
                          notInPartyFont = GameFonts.getGAME_MENU_MUTED_TEXT(),
                          partyLeaderFont = GameFonts.getGAME_MENU_PRIMARY_TEXT(),
                          unavailableFont = GameFonts.getGAME_MENU_MUTED_TEXT();
                          
                  
  public PartyMemberCard(int x, int y, Character character) {
    this.x = x;
    this.y = y;
    nameY = y + headerHeight / 2;
    centerLine = x + cardWidth / 2;
    thumbnailX = x + cardMargin - (int)(character.getThumbnail().getWidth() * thumbnailScale / 2);
    thumbnailY = y + headerHeight + cardMargin - (int)(character.getThumbnail().getHeight() * thumbnailScale / 2);
    labelX = centerLine;
    valueX = x + cardWidth - 2;
    rowStartingY = y + headerHeight + cardMargin;
    positionLabelX = x + 14;
    positionValueX = centerLine + 16;
    positionY = y + headerHeight + bodyHeight - 4;
    buttonY = y + cardHeight - 1 - footerHeight / 2;
    buttonWidth = cardWidth - cardMargin * 2;
    this.character = character;
    addButton = new PrimaryButton("Add", centerLine, buttonY, buttonWidth, buttonHeight, () -> handleAddClick());
    removeButton = new DangerButton("Remove", centerLine, buttonY, buttonWidth, buttonHeight, () -> handleRemoveClick());
    createSprites();
    setFonts();
    checkParty();
  }
  
  private void createSprites() {
    int[] unSelectedPixels = new int[cardWidth * cardHeight];
    int[] selectedPixels = new int[cardWidth * cardHeight];
    Arrays.fill(unSelectedPixels, 0, cardWidth, unselectedBorderColor);
    Arrays.fill(selectedPixels, 0, cardWidth, selectedBorderColor);
    for (int yy = 1; yy < (cardHeight - 1); yy++) {
      for (int xx = 0; xx < cardWidth; xx++) {
        if (xx == 0 || xx == cardWidth - 1) {
          unSelectedPixels[xx + yy * cardWidth] = unselectedBorderColor;
          selectedPixels[xx + yy * cardWidth] = selectedBorderColor;
        }
        else if (yy < headerHeight + 1) {
          unSelectedPixels[xx + yy * cardWidth] = cardHeaderColor;
          selectedPixels[xx + yy * cardWidth] = cardHeaderColor;
        }
        else if (yy >= headerHeight + 1 && yy < headerHeight + bodyHeight + 1) {
          unSelectedPixels[xx + yy * cardWidth] = cardBackground;
          selectedPixels[xx + yy * cardWidth] = cardBackground;
        }
        else {
          unSelectedPixels[xx + yy * cardWidth] = cardHeaderColor;
          selectedPixels[xx + yy * cardWidth] = cardHeaderColor;
        }
          
      }
    }
    Arrays.fill(unSelectedPixels, cardWidth * (cardHeight - 1), cardWidth * cardHeight, unselectedBorderColor);
    Arrays.fill(selectedPixels, cardWidth * (cardHeight - 1), cardWidth * cardHeight, selectedBorderColor);
    unselectedCardBackground = new Sprite(unSelectedPixels, cardWidth, cardHeight);
    selectedCardBackground = new Sprite(selectedPixels, cardWidth, cardHeight);
  }
  
  private void setFonts() {
    nameFont.setText(character.getName());
    nameFont.setX(centerLine);
    nameFont.setY(nameY);
    nameFont.centerText();
    
    levelLabelFont.setText("Lv:");
    levelLabelFont.setX(labelX);
    levelLabelFont.setY(rowStartingY);
    
    levelValueFont.setText(String.valueOf(character.getLevel()));
    levelValueFont.setX(valueX);
    levelValueFont.setY(rowStartingY);
    levelValueFont.rightAlignText();
    
    hpLabelFont.setText("HP:");
    hpLabelFont.setX(labelX);
    hpLabelFont.setY(rowStartingY + spaceBetweenRows);
    
    hpValueFont.setText(String.valueOf(character.getHealthMax()));
    hpValueFont.setX(valueX);
    hpValueFont.setY(rowStartingY + spaceBetweenRows);
    hpValueFont.rightAlignText();
    
    mpLabelFont.setText("MP:");
    mpLabelFont.setX(labelX);
    mpLabelFont.setY(rowStartingY + spaceBetweenRows * 2);
    
    mpValueFont.setText(String.valueOf(character.getManaMax()));
    mpValueFont.setX(valueX);
    mpValueFont.setY(rowStartingY + spaceBetweenRows * 2);
    mpValueFont.rightAlignText();
    
    positionLabelFont.setText("Position:");
    positionLabelFont.setX(positionLabelX);
    positionLabelFont.setY(positionY);
    
    notInPartyFont.setText("Not In Party");
    notInPartyFont.setX(centerLine);
    notInPartyFont.setY(positionY);
    notInPartyFont.centerText();
    
    partyLeaderFont.setText("Leader");
    partyLeaderFont.setX(centerLine);
    partyLeaderFont.setY(buttonY);
    partyLeaderFont.centerText();
    
    unavailableFont.setText("Unavailable");
    unavailableFont.setX(centerLine);
    unavailableFont.setY(buttonY);
    unavailableFont.centerText();
  }
  
  private void checkParty() {
    isAvailable = CharacterManager.getCharacterManager().isCharacterAvailable(character);
    int position = CharacterManager.getCharacterManager().getPartyMemberPosition(character);
    if (CharacterManager.getCharacterManager().isCharacterInParty(character)) {
      if (CharacterManager.getCharacterManager().getPartyLeader().equals(character))
        isPartyLeader = true;
      isInParty = true;
      positionValueFont.setText(String.valueOf(position + 1));
      positionValueFont.setX(positionValueX);
      positionValueFont.setY(positionY);
      positionValueFont.rightAlignText();
    }
    else {
      isInParty = false;
    }
  }
  
  public void handleAddClick() {
    if (!CharacterManager.getCharacterManager().addPartyMemberAtLowestPosition(character)) {
      System.out.println("Show popup error that the party is full!");
    }
    else {
      checkParty();
    }
  }
  
  public void handleRemoveClick() {
    CharacterManager.getCharacterManager().removePartyMember(character);
    checkParty();
  }

  public void update() {
    if (!isPartyLeader && isAvailable) {
      if (isInParty) {
        removeButton.update();
      }
      else {
        addButton.update();
      }
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, isInParty ? selectedCardBackground : unselectedCardBackground, false);
    screen.renderSprite(gl, thumbnailX, thumbnailY, character.getThumbnail(), 1f, false, thumbnailScale);
    screen.renderFonts(nameFont);
    screen.renderFonts(levelLabelFont);
    screen.renderFonts(levelValueFont);
    screen.renderFonts(hpLabelFont);
    screen.renderFonts(hpValueFont);
    screen.renderFonts(mpLabelFont);
    screen.renderFonts(mpValueFont);
    if (isPartyLeader) {
      screen.renderFonts(positionLabelFont);
      screen.renderFonts(positionValueFont);
      screen.renderFonts(partyLeaderFont);
    }
    else if (isInParty) {
      screen.renderFonts(positionLabelFont);
      screen.renderFonts(positionValueFont);
      removeButton.render(screen, gl);
    }
    else {
      screen.renderFonts(notInPartyFont);
      if (isAvailable)
        addButton.render(screen, gl);
      else
        screen.renderFonts(unavailableFont);
    }
  }
  
}
