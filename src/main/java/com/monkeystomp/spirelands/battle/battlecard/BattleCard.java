package com.monkeystomp.spirelands.battle.battlecard;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleCard {
  
  private final Character character;
  private final CharacterBattleEntity battleEntity;
  private final int index,
                    left,
                    right,
                    sidePadding = 5,
                    top = 200,
                    barWidth = 80,
                    barHeight = 2,
                    cardWidth = 140;
  private int characterHealth,
              characterMana;
  private final Sprite backgroundSprite;
  private Sprite ATBSprite;
  private Sprite healthFill;
  private final Sprite healthEmpty = new Sprite(barWidth, barHeight, GameColors.HEALTH_BAR_EMPTY);
  private Sprite manaFill;
  private final Sprite manaEmpty = new Sprite(barWidth, barHeight, GameColors.MANA_BAR_EMPTY);
  private final FontInfo healthFont = GameFonts.getDarkText_plain_18();
  private final FontInfo manaFont = GameFonts.getDarkText_plain_18();
  
  public BattleCard(CharacterBattleEntity character, int index) {
    this.character = character.getCharacter();
    this.battleEntity = character;
    this.index = index;
    this.left = index * cardWidth + sidePadding;
    this.right = left + cardWidth - sidePadding;
    backgroundSprite = new Sprite(cardWidth - sidePadding * 2, 32, GameColors.TITLE_SCREEN_MENU_BACKDROP);
    ATBSprite = new Sprite(cardWidth - sidePadding * 2, 16, GameColors.ATB_GAUGE_BAR_FILLED);
    setupFonts();
    updateStatBars();
  }
  
  private void setupFonts() {
    healthFont.setX(left + 40);
    healthFont.setY(top + 7);
    manaFont.setX(left + 40);
    manaFont.setY(top + 17);
    updateStatText();
  }
  
  private void updateStatText() {
    this.healthFont.setText(character.getHealth() + " / " + character.getHealthMax());
    this.manaFont.setText(character.getMana() + " / " + character.getManaMax());
  }
  
  private void updateStatBars() {
    if (characterHealth != character.getHealth()) {
      if (character.getHealth() == 0) healthFill = null;
      else healthFill = new Sprite((int)((character.getHealth() / (double)character.getHealthMax()) * barWidth), barHeight, GameColors.HEALTH_BAR_FILL);
      characterHealth = character.getHealth();
    }
    if (characterMana != character.getMana()) {
      if (character.getMana() == 0) manaFill = null;
      else manaFill = new Sprite((int)((character.getMana() / (double)character.getManaMax()) * barWidth), barHeight, GameColors.MANA_BAR_FILL);
      characterMana = character.getMana();
    }
  }
  
  private void updateFillBar() {
    int height = (int)(32 * battleEntity.getReadyPercent()) == 0 ? 1 : (int)(32 * battleEntity.getReadyPercent());
    ATBSprite = new Sprite(cardWidth - sidePadding * 2, height, GameColors.ATB_GAUGE_BAR_FILLED);
  }
  
  public void update() {
    updateStatText();
    updateStatBars();
    updateFillBar();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, left, top, backgroundSprite, false);
    screen.renderSprite(gl, left, top + (32 - ATBSprite.getHeight()), ATBSprite, false);
    screen.renderSprite(gl, left, top, character.getThumbnail(), false);
    screen.renderFonts(healthFont);
    screen.renderSprite(gl, left + 40, top + 11, healthEmpty, false);
    if (healthFill != null) screen.renderSprite(gl, left + 40, top + 11, healthFill, false);
    screen.renderFonts(manaFont);
    screen.renderSprite(gl, left + 40, top + 22, manaEmpty, false);
    if (manaFill != null) screen.renderSprite(gl, left + 40, top + 22, manaFill, false);
  }

}
