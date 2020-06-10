package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
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
                    bodyHeight = 30,
                    footerHeight = 15,
                    cardWidth = 60,
                    cardHeight = headerHeight + bodyHeight + footerHeight,
                    cardMargin = 8,
                    unselectedBorderColor = GameColors.GAME_MENU_MUTED_TEXT,
                    cardHeaderColor = GameColors.EQUIPPED_ITEM_SLOT_HOVER,
                    cardBackground = GameColors.DARK_TEXT,
                    nameY,
                    thumbnailX,
                    thumbnailY,
                    labelX,
                    valueX,
                    rowStartingY,
                    spaceBetweenRows = 6,
                    buttonY,
                    buttonWidth,
                    buttonHeight = 9;
  private final float thumbnailScale = .625f;
  private final Character character;
  private Sprite  unselectedCardBackground;
  private final PrimaryButton addButton;
  private final FontInfo  nameFont = GameFonts.getPrimaryButtonText(),
                          levelLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          hpLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          mpLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          levelValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          hpValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          mpValueFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
                          
                  
  
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
    buttonY = y + cardHeight - 1 - footerHeight / 2;
    buttonWidth = cardWidth - cardMargin * 2;
    this.character = character;
    addButton = new PrimaryButton("Add", centerLine, buttonY, buttonWidth, buttonHeight, () -> {System.out.println("add button clicked");});
    createSprites();
    setFonts();
  }
  
  private void createSprites() {
    int[] pixels = new int[cardWidth * cardHeight];
    Arrays.fill(pixels, 0, cardWidth, unselectedBorderColor);
    for (int yy = 1; yy < (cardHeight - 1); yy++) {
      for (int xx = 0; xx < cardWidth; xx++) {
        if (xx == 0 || xx == cardWidth - 1)
          pixels[xx + yy * cardWidth] = unselectedBorderColor;
        else if (yy < headerHeight + 1)
          pixels[xx + yy * cardWidth] = cardHeaderColor;
        else if (yy >= headerHeight + 1 && yy < headerHeight + bodyHeight + 1)
          pixels[xx + yy * cardWidth] = cardBackground;
        else
          pixels[xx + yy * cardWidth] = cardHeaderColor;
          
      }
    }
    Arrays.fill(pixels, cardWidth * (cardHeight - 1), cardWidth * cardHeight, unselectedBorderColor);
    unselectedCardBackground = new Sprite(pixels, cardWidth, cardHeight);
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
  }

  public void update() {
    addButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x, y, unselectedCardBackground, false);
    screen.renderSprite(gl, thumbnailX, thumbnailY, character.getThumbnail(), 1f, false, thumbnailScale);
    screen.renderFonts(nameFont);
    screen.renderFonts(levelLabelFont);
    screen.renderFonts(levelValueFont);
    screen.renderFonts(hpLabelFont);
    screen.renderFonts(hpValueFont);
    screen.renderFonts(mpLabelFont);
    screen.renderFonts(mpValueFont);
    addButton.render(screen, gl);
  }
  
}
