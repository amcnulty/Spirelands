package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.Button;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clickable party member buttons that are displayed in the game menu. These components are used to select a party member to apply actions to, for example equipment changes.
 * @author Aaron Michael McNulty
 */
public class PartyMemberButton extends Button {
  
  private static final int  WIDTH = 264,
                            HEIGHT = 44,
                            BAR_WIDTH = 65,
                            BAR_HEIGHT = 4,
                            UNDERLINE_HEIGHT = 1;
  private final Sprite thumbnail;
  private final Sprite healthUnderline = new Sprite(BAR_WIDTH, UNDERLINE_HEIGHT, GameColors.HEALTH_BAR_UNDERLINE);
  private final Sprite healthEmpty = new Sprite(BAR_WIDTH, BAR_HEIGHT, GameColors.HEALTH_BAR_EMPTY);
  private final Sprite manaUnderline = new Sprite(BAR_WIDTH, UNDERLINE_HEIGHT, GameColors.MANA_BAR_UNDERLINE);
  private final Sprite manaEmpty = new Sprite(BAR_WIDTH, BAR_HEIGHT, GameColors.MANA_BAR_EMPTY);
  private final Sprite expUnderline = new Sprite(BAR_WIDTH, UNDERLINE_HEIGHT, GameColors.EXP_GAUGE_UNDERLINE);
  private final Sprite expEmpty = new Sprite(BAR_WIDTH, BAR_HEIGHT, GameColors.EXP_GAUGE_EMPTY);
  private List<Sprite> moveIcons = new ArrayList<>();
  private final int thumbnailX,
                    thumbnailY,
                    arrowY;
  private final FontInfo characterNameFont = GameFonts.getGAME_MENU_HEADLINE_THIN();
  private final FontInfo healthLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo characterHealthFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_THIN();
  private final FontInfo manaLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo characterManaFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_THIN();
  private final FontInfo expLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo characterExpFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_THIN();
  private final FontInfo characterLevelFont = GameFonts.getGAME_MENU_HEADLINE();
  private final Character character;
  private ArrayList<BattleMove> equippedMoves;
  private int characterHealth = -1,
              characterMana = -1,
              characterExp = -1,
              arrowX = 130,
              arrowTimer;
  private Sprite  healthFill,
                  manaFill,
                  expFill;
  /**
   * Creates a PartyMemberButton for the given character with a callback method to fire when button is clicked.
   * @param character The character for whom this button is related to.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public PartyMemberButton(Character character, int x, int y, ICallback callback) {
    super(null, x, y, WIDTH, HEIGHT, callback);
    this.character = character;
    setButtonSprites();
    updateStatText();
    updateStatBars();
    this.thumbnail = character.getThumbnail();
    this.thumbnailX = this.x + 10;
    this.thumbnailY = this.y + 6;
    this.arrowY = this.y + this.height / 2 - Sprite.GAME_MENU_RIGHT_ARROW.getHeight() / 2;
    this.characterNameFont.setX(this.x + 50);
    this.characterNameFont.setY(this.y + 7);
    this.characterNameFont.setText(character.getName());
    this.healthLabel.setX(this.x + 50);
    this.healthLabel.setY(this.y + 22);
    this.healthLabel.setText("HP");
    this.characterHealthFont.setX(this.x + 65);
    this.characterHealthFont.setY(this.y + 16);
    this.manaLabel.setX(this.x + 50);
    this.manaLabel.setY(this.y + 35);
    this.manaLabel.setText("MP");
    this.characterManaFont.setX(this.x + 65);
    this.characterManaFont.setY(this.y + 30);
    this.expLabel.setX(this.x + 140);
    this.expLabel.setY(this.y + 35);
    this.expLabel.setText("EXP");
    this.characterExpFont.setX(this.x + 158);
    this.characterExpFont.setY(this.y + 30);
    this.characterLevelFont.setX(this.x + 140);
    this.characterLevelFont.setY(this.y + 17);
    this.characterLevelFont.setText("LV: " + character.getLevel());
  }
  
  private void setButtonSprites() {
    button = new Sprite(width, height, GameColors.TRANSPARENT);
    buttonHover = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_HOVER);
    buttonDown = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_DOWN);
    disabledButton = button;
    currentButton = button;
  }
  
  private void updateStatText() {
    this.characterHealthFont.setText(character.getHealth() + " / " + character.getHealthMax());
    this.characterManaFont.setText(character.getMana() + " / " + character.getManaMax());
    this.characterExpFont.setText(character.getExperience() + " / " + character.getExpToNextLevel());
    this.characterLevelFont.setText("LV: " + character.getLevel());
  }
  
  private void updateStatBars() {
    if (characterHealth != character.getHealth()) {
      healthFill = new Sprite((int)((character.getHealth() / (double)character.getHealthMax()) * BAR_WIDTH), BAR_HEIGHT, GameColors.HEALTH_BAR_FILL);
      characterHealth = character.getHealth();
    }
    if (characterMana != character.getMana()) {
      manaFill = new Sprite((int)((character.getMana() / (double)character.getManaMax()) * BAR_WIDTH), BAR_HEIGHT, GameColors.MANA_BAR_FILL);
      characterMana = character.getMana();
    }
    if (characterExp != character.getExperience()) {
      expFill = new Sprite((int)((character.getExperience() / (double)character.getExpToNextLevel()) * BAR_WIDTH), BAR_HEIGHT, GameColors.EXP_GAUGE_FILL);
      characterExp = character.getExperience();
    }
  }
  
  private void updateEquippedMoves() {
    if (equippedMoves != character.getEquippedMoves()) {
      equippedMoves = character.getEquippedMoves();
      moveIcons = equippedMoves.stream().filter(move -> !move.getType().equals(BattleMove.ITEM)).map(move -> move.getThumbnail()).collect(Collectors.toList());
    }
  }
  
  private void updateArrow() {
    if (arrowTimer % 12 == 0) {
      arrowX += (arrowX == 130) ? 2 : -2;
    }
    arrowTimer++;
  }
  
  @Override
  public void update() {
    super.update();
    updateStatText();
    updateStatBars();
    updateEquippedMoves();
    if (!isDisabled()) updateArrow();
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
    if (!isDisabled()) screen.renderSprite(gl, arrowX, arrowY, Sprite.GAME_MENU_RIGHT_ARROW, false);
    screen.renderSprite(gl, thumbnailX, thumbnailY, thumbnail, false);
    screen.renderFonts(characterNameFont);
    screen.renderFonts(characterHealthFont);
    screen.renderFonts(healthLabel);
    screen.renderSprite(gl, characterHealthFont.getX(), characterHealthFont.getY() + 4, healthEmpty, false);
    if (healthFill.getWidth() > 0) screen.renderSprite(gl, characterHealthFont.getX(), characterHealthFont.getY() + 4, healthFill, false);
    screen.renderSprite(gl, characterHealthFont.getX(), characterHealthFont.getY() + 8, healthUnderline, false);
    screen.renderFonts(characterManaFont);
    screen.renderFonts(manaLabel);
    screen.renderSprite(gl, characterManaFont.getX(), characterManaFont.getY() + 4, manaEmpty, false);
    if (manaFill.getWidth() > 0) screen.renderSprite(gl, characterManaFont.getX(), characterManaFont.getY() + 4, manaFill, false);
    screen.renderSprite(gl, characterManaFont.getX(), characterManaFont.getY() + 8, manaUnderline, false);
    screen.renderFonts(characterExpFont);
    screen.renderFonts(expLabel);
    screen.renderSprite(gl, characterExpFont.getX(), characterExpFont.getY() + 4, expEmpty, false);
    if (expFill.getWidth() > 0) screen.renderSprite(gl, characterExpFont.getX(), characterExpFont.getY() + 4, expFill, false);
    screen.renderSprite(gl, characterExpFont.getX(), characterExpFont.getY() + 8, expUnderline, false);
    screen.renderFonts(characterLevelFont);
    for (int i = 0; i < moveIcons.size(); i++) {
      screen.renderSprite(gl, characterLevelFont.getX() + 35 + (10 * i), characterLevelFont.getY() - 8, moveIcons.get(i), 1, false, .625f);
    }
  }
  
}
