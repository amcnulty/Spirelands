package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ItemOnCharacterButton extends Button {
  
  private final static int  WIDTH = 172,
                            HEIGHT = 32;
  private final Character character;
  private final Sprite healthUnderline = new Sprite(65, 1, GameColors.HEALTH_BAR_UNDERLINE);
  private final Sprite healthEmpty = new Sprite(65, 4, GameColors.HEALTH_BAR_EMPTY);
  private final Sprite manaUnderline = new Sprite(65, 1, GameColors.MANA_BAR_UNDERLINE);
  private final Sprite manaEmpty = new Sprite(65, 4, GameColors.MANA_BAR_EMPTY);
  private Sprite healthFill;
  private Sprite manaFill;
  private final FontInfo  healthLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          characterHealthFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          manaLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          characterManaFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          strengthLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          strengthFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          defenseLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          defenseFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          intellectLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          intellectFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          spiritLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          spiritFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          speedLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          speedFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          luckLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          luckFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL(),
                          levelLabelFont = GameFonts.getGAME_MENU_LABEL_TEXT(),
                          levelFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private int characterHealth = -1,
              characterMana = -1;
  private boolean animating = false;
  
  public ItemOnCharacterButton(String text, int x, int y, int width, int height, Character character, Consumer<Character> callback) {
    super(text, x, y, WIDTH, HEIGHT, character, callback);
    this.character = character;
    healthLabel.setX(this.x + 40);
    healthLabel.setY(this.y + 7);
    healthLabel.setText("HP:");
    characterHealthFont.setX(this.x + 60);
    characterHealthFont.setY(this.y + 7);
    characterHealthFont.setText(character.getHealth() + " / " + character.getHealthMax());
    manaLabel.setX(this.x + 40);
    manaLabel.setY(this.y + 19);
    manaLabel.setText("MP:");
    characterManaFont.setX(this.x + 60);
    characterManaFont.setY(this.y + 19);
    characterManaFont.setText(character.getMana() + " / " + character.getManaMax());
    
    strengthLabelFont.setX(this.x + 110);
    strengthLabelFont.setY(this.y + 8);
    strengthLabelFont.setText("Str:");
    defenseLabelFont.setX(this.x + 110);
    defenseLabelFont.setY(this.y + 16);
    defenseLabelFont.setText("Def:");
    intellectLabelFont.setX(this.x + 110);
    intellectLabelFont.setY(this.y + 24);
    intellectLabelFont.setText("Int:");
    spiritLabelFont.setX(this.x + 140);
    spiritLabelFont.setY(this.y + 8);
    spiritLabelFont.setText("Spt:");
    speedLabelFont.setX(this.x + 140);
    speedLabelFont.setY(this.y + 16);
    speedLabelFont.setText("Spd:");
    luckLabelFont.setX(this.x + 140);
    luckLabelFont.setY(this.y + 24);
    luckLabelFont.setText("Lck:");
    
    strengthFont.setX(this.x + 125);
    strengthFont.setY(this.y + 8);
    strengthFont.setText(String.valueOf(character.getStrength()));
    defenseFont.setX(this.x + 125);
    defenseFont.setY(this.y + 16);
    defenseFont.setText(String.valueOf(character.getDefense()));
    intellectFont.setX(this.x + 125);
    intellectFont.setY(this.y + 24);
    intellectFont.setText(String.valueOf(character.getIntellect()));
    spiritFont.setX(this.x + 155);
    spiritFont.setY(this.y + 8);
    spiritFont.setText(String.valueOf(character.getSpirit()));
    speedFont.setX(this.x + 155);
    speedFont.setY(this.y + 16);
    speedFont.setText(String.valueOf(character.getSpeed()));
    luckFont.setX(this.x + 155);
    luckFont.setY(this.y + 24);
    luckFont.setText(String.valueOf(character.getLuck()));
    
    levelLabelFont.setX(this.x + 8);
    levelLabelFont.setY(this.y + 4);
    levelLabelFont.setText("Lvl:");
    levelFont.setX(this.x + 19);
    levelFont.setY(this.y + 4);
    levelFont.setText(String.valueOf(character.getLevel()));
    
    characterHealth = character.getHealth();
    healthFill = new Sprite((int)((characterHealth / (double)character.getHealthMax()) * 65), 4, GameColors.HEALTH_BAR_FILL);
    characterMana = character.getMana();
    manaFill = new Sprite((int)((characterMana / (double)character.getManaMax()) * 65), 4, GameColors.MANA_BAR_FILL);
    createButtonSprites();
  }

  private void createButtonSprites() {
    button = new Sprite(width, height, GameColors.GAME_MENU_BACKGROUND);
    buttonHover = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_HOVER);
    buttonDown = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_DOWN);
    currentButton = button;
  }
  
  private void updateStatText() {
    characterHealthFont.setText(characterHealth + " / " + character.getHealthMax());
    characterManaFont.setText(characterMana + " / " + character.getManaMax());
    strengthFont.setText(String.valueOf(character.getStrength()));
    defenseFont.setText(String.valueOf(character.getDefense()));
    intellectFont.setText(String.valueOf(character.getIntellect()));
    spiritFont.setText(String.valueOf(character.getSpirit()));
    speedFont.setText(String.valueOf(character.getSpeed()));
    luckFont.setText(String.valueOf(character.getLuck()));
    levelFont.setText(String.valueOf(character.getLevel()));
  }
  
  private void updateStatBars() {
    if (characterHealth != character.getHealth()) {
      healthFill = new Sprite((int)((++characterHealth / (double)character.getHealthMax()) * 65), 4, GameColors.HEALTH_BAR_FILL);
      animating = true;
    }
    if (characterMana != character.getMana()) {
      manaFill = new Sprite((int)((++characterMana / (double)character.getManaMax()) * 65), 4, GameColors.MANA_BAR_FILL);
      animating = true;
    }
    if (characterHealth == character.getHealth() && characterMana == character.getMana()) animating = false;
  }
  
  public boolean animating() {
    return animating;
  }
  
  @Override
  public void update() {
    super.update();
    updateStatText();
    updateStatBars();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    screen.renderSprite(gl, x + 8, y + height / 2 - 13, character.getThumbnail(), 1f, false, .625f);
    screen.renderFonts(healthLabel);
    screen.renderFonts(characterHealthFont);
    screen.renderFonts(manaLabel);
    screen.renderFonts(characterManaFont);
    screen.renderSprite(gl, x + 40, y + 11, healthEmpty, false);
    screen.renderSprite(gl, x + 40, y + 11, healthFill, false);
    screen.renderSprite(gl, x + 40, y + 15, healthUnderline, false);
    screen.renderSprite(gl, x + 40, y + 23, manaEmpty, false);
    screen.renderSprite(gl, x + 40, y + 23, manaFill, false);
    screen.renderSprite(gl, x + 40, y + 27, manaUnderline, false);
    screen.renderFonts(strengthLabelFont);
    screen.renderFonts(strengthFont);
    screen.renderFonts(defenseLabelFont);
    screen.renderFonts(defenseFont);
    screen.renderFonts(intellectLabelFont);
    screen.renderFonts(intellectFont);
    screen.renderFonts(spiritLabelFont);
    screen.renderFonts(spiritFont);
    screen.renderFonts(speedLabelFont);
    screen.renderFonts(speedFont);
    screen.renderFonts(luckLabelFont);
    screen.renderFonts(luckFont);
    screen.renderFonts(levelLabelFont);
    screen.renderFonts(levelFont);
  }
  
}
