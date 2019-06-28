package com.monkeystomp.spirelands.battle.victory;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.input.ICallback;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.inventory.InventoryReference;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.util.Helpers;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Aaron Michael McNulty
 */
public class VictoryScreen {
  
  private final FontInfo  victoryHeader = GameFonts.getDarkHeadline(),
                          lootHeader = GameFonts.getDarkText_bold_22(),
                          goldAwardFont = GameFonts.getDarkText_bold_22(),
                          goldValueFont = GameFonts.getDarkText_bold_22();
  private final int backgroundWidth = (int)(Screen.getWidth() * .9),
                    backgroundHeight = (int)(Screen.getHeight() - 55),
                    top = (Screen.getHeight() - backgroundHeight) / 2,
                    left = (int)(Screen.getWidth() * .05),
                    right = left + backgroundWidth,
                    bottom = top + backgroundHeight,
                    animatedExpBarX = left + 48,
                    dividerX = Screen.getWidth() / 2,
                    dividerY = top + 30,
                    lootLineItemX = dividerX + 20,
                    lootLineItemStartingY = top + 35,
                    totalExp,
                    totalGold;
  private final Sprite  background = new Sprite(backgroundWidth, backgroundHeight, GameColors.DIALOG_BOX_BACKGROUND),
                        divider = new Sprite(1, backgroundHeight - 60, GameColors.GAME_MENU_MUTED_TEXT);
  private boolean showing = false;
  private final List<Character> party;
  private final List<Enemy> enemies;
  private final ArrayList<FontInfo> characterNameFonts = new ArrayList<>();
  private final ArrayList<FontInfo> expGainedFonts = new ArrayList<>(),
                                    expValueFonts = new ArrayList<>();
  private final ArrayList<Item> droppedItems = new ArrayList<>();
  private final HashMap<Integer, ArrayList<Item>> lootMap = new HashMap<>();
  private final ArrayList<LootLineItem> lootLineItems = new ArrayList<>();
  private final ArrayList<AnimatedExpBar> animatedExpBars = new ArrayList<>();
  private final Random random = new Random();
  private ICallback callback;
  private final PrimaryButton okButton = new PrimaryButton(
    "Continue",
    Screen.getWidth() / 2,
    bottom - 10,
    40,
    11,
    () -> callback.execute()
  );
  
  public VictoryScreen(List<Character> party, List<Enemy> enemies, ICallback callback) {
    this.party = party;
    this.enemies = enemies;
    this.callback = callback;
    totalExp = getExpAwards();
    totalGold = getGoldAwards();
    setAnimatedExpBars();
    setDroppedItems();
    setupFonts();
  }
  
  private void setAnimatedExpBars() {
    for (int i = 0; i < party.size(); i++) {
      animatedExpBars.add(
        new AnimatedExpBar(animatedExpBarX, top + 40 + (i * 48), totalExp, party.get(i))
      );
    }
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
    for (Item item: droppedItems) {
      if (!lootMap.containsKey(item.getId())) {
        ArrayList<Item> list = new ArrayList<>();
        list.add(item);
        lootMap.put(item.getId(), list);
      }
      else lootMap.get(item.getId()).add(item);
    }
    Object[] set = lootMap.keySet().toArray();
    for (int i = 0; i < set.length; i++) {
      lootLineItems.add(new LootLineItem(
        lootLineItemX,
        lootLineItemStartingY + i * 17,
        new InventoryReference(lootMap.get((Integer)set[i]).get(0), lootMap.get((Integer)set[i]).size())
      ));
    }
  }

  private void setupFonts() {
    victoryHeader.setText("Victory!");
    victoryHeader.setX(Screen.getWidth() / 2);
    victoryHeader.setY(top + 10);
    victoryHeader.centerText();
    
    lootHeader.setText("Loot");
    lootHeader.setX(backgroundWidth * 3 / 4 + left);
    lootHeader.setY(top + 16);
    lootHeader.centerText();
    
    goldAwardFont.setText("Gold Gained: ");
    goldAwardFont.setX(left + 15);
    goldAwardFont.setY(bottom - 10);
    
    goldValueFont.setText(String.valueOf(totalGold));
    goldValueFont.setColor(new Color(GameColors.DARK_GREEN));
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
      value.setColor(new Color(GameColors.DARK_GREEN));
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
    Helpers.setTimeout(() -> {
      for (AnimatedExpBar bar: animatedExpBars) {
        bar.setAnimating(true);
      }
    }, 2000);
  }
  
  public void update() {
    for (AnimatedExpBar bar: animatedExpBars) {
      bar.update();
    }
    okButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, left, top, background, .8f, false);
    screen.renderFonts(victoryHeader);
    screen.renderFonts(lootHeader);
    screen.renderFonts(goldAwardFont);
    screen.renderFonts(goldValueFont);
    screen.renderFonts(victoryHeader);
    for (int i = 0; i < party.size(); i++) {
      screen.renderFonts(characterNameFonts.get(i));
      screen.renderFonts(expGainedFonts.get(i));
      screen.renderFonts(expValueFonts.get(i));
      animatedExpBars.get(i).render(screen, gl);
      screen.renderSprite(gl, left + 15, top + 30 + (i * 48), party.get(i).getThumbnail(), false);
    }
    screen.renderSprite(gl, dividerX, dividerY, divider, false);
    for (LootLineItem lineItem: lootLineItems) {
      lineItem.render(screen, gl);
    }
    okButton.render(screen, gl);
  }

}
