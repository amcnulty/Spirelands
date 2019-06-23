package com.monkeystomp.spirelands.battle.victory;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.Item;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class VictoryScreen {
  
  private final FontInfo victoryHeader = GameFonts.getDarkHeadline();
  private final FontInfo goldAwardFont = GameFonts.getDarkText_bold_22();
  private final FontInfo goldValueFont = GameFonts.getDarkText_bold_22();
  private final int backgroundWidth = (int)(Screen.getWidth() * .9),
                    backgroundHeight = (int)(Screen.getHeight() - 55),
                    top = (Screen.getHeight() - backgroundHeight) / 2,
                    left = (int)(Screen.getWidth() * .05),
                    right = left + backgroundWidth,
                    bottom = top + backgroundHeight,
                    totalExp,
                    totalGold;
  private final Sprite background = new Sprite(backgroundWidth, backgroundHeight, GameColors.DIALOG_BOX_BACKGROUND);
  private boolean showing = false;
  private final List<Character> party;
  private final List<Enemy> enemies;
  private final ArrayList<FontInfo> characterNameFonts = new ArrayList<>();
  private final ArrayList<FontInfo> expGainedFonts = new ArrayList<>();
  private final ArrayList<FontInfo> expValueFonts = new ArrayList<>();
  private final ArrayList<Item> droppedItems = new ArrayList<>();
  private final Random random = new Random();
  
  public VictoryScreen(List<Character> party, List<Enemy> enemies) {
    this.party = party;
    this.enemies = enemies;
    totalExp = getExpAwards();
    totalGold = getGoldAwards();
    setDroppedItems();
    setupFonts();
  }
  
  private int getExpAwards() {
    int sum = 0;
    for (Enemy enemy: enemies) {
      sum += enemy.getExperienceAward();
    }
    return sum;
  }
  
  private int getGoldAwards() {
    int sum = 0;
    for (Enemy enemy: enemies) {
      sum += enemy.getGoldAward();
    }
    return sum;
  }
  
  private void setDroppedItems() {
    for (Enemy enemy: enemies) {
      if (random.nextInt(100) + 1 <= enemy.getDropRate()) droppedItems.add(enemy.getLoot());
    }
  }

  private void setupFonts() {
    victoryHeader.setText("Victory!");
    victoryHeader.setX(Screen.getWidth() / 2);
    victoryHeader.setY(top + 10);
    victoryHeader.centerText();
    
    goldAwardFont.setText("Gold Gained: ");
    goldAwardFont.setX(left + 15);
    goldAwardFont.setY(bottom - 10);
    
    goldValueFont.setText(String.valueOf(totalGold));
    goldValueFont.setColor(new Color(GameColors.GAME_MENU_SELECTED_TEXT));
    goldValueFont.setX(left + 62);
    goldValueFont.setY(bottom - 10);
    
    for (int i = 0; i < party.size(); i++) {
      FontInfo info = GameFonts.getDarkText_bold_22();
      info.setText(party.get(i).getName());
      info.setX(left + 15);
      info.setY(top + 25 + (i * 48));
      characterNameFonts.add(info);
      
      FontInfo expFont = GameFonts.getDarkText_22();
      expFont.setText("Experience Gained: ");
      expFont.setX(left + 48);
      expFont.setY(characterNameFonts.get(i).getY() + 8);
      expGainedFonts.add(expFont);
      
      FontInfo value = GameFonts.getDarkText_bold_22();
      value.setText(String.valueOf(totalExp));
      value.setColor(new Color(GameColors.GAME_MENU_LABEL_TEXT));
      value.setX(expGainedFonts.get(i).getX() + 65);
      value.setY(characterNameFonts.get(i).getY() + 8);
      expValueFonts.add(value);
    }
  }
  
  public boolean isShowing() {
    return showing;
  }

  public void setShowing(boolean showing) {
    this.showing = showing;
  }
  /**
   * Used to give the drops, experience and gold to the party at the end of a battle.
   */
  public void awardParty() {
    for (Character character: party) {
      character.addExperiencePoints(totalExp);
    }
    InventoryManager.getInventoryManager().addGold(totalGold);
    for (Item item: droppedItems) {
      InventoryManager.getInventoryManager().addToInventory(item);
    }
  }
  
  public void update() {
    
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, left, top, background, .8f, false);
    screen.renderFonts(victoryHeader);
    screen.renderFonts(goldAwardFont);
    screen.renderFonts(goldValueFont);
    screen.renderFonts(victoryHeader);
    for (int i = 0; i < party.size(); i++) {
      screen.renderFonts(characterNameFonts.get(i));
      screen.renderFonts(expGainedFonts.get(i));
      screen.renderFonts(expValueFonts.get(i));
      screen.renderSprite(gl, left + 15, top + 30 + (i * 48), party.get(i).getThumbnail(), false);
    }
  }

}
