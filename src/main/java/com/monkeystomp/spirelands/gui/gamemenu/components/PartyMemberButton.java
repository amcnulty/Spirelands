package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.Button;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;

/**
 * Clickable party member buttons that are displayed in the game menu. These components are used to select a party member to apply actions to, for example equipment changes.
 * @author Aaron Michael McNulty
 */
public class PartyMemberButton extends Button {
  
  private static final int  WIDTH = 264,
                            HEIGHT = 44;
  private final Sprite THUMBNAIL;
  private final Sprite HEALTH_UNDERLINE = new Sprite(65, 1, GameColors.HEALTH_BAR_UNDERLINE);
  private final Sprite HEALTH_EMPTY = new Sprite(65, 4, GameColors.HEALTH_BAR_EMPTY);
  private final Sprite MANA_UNDERLINE = new Sprite(65, 1, GameColors.MANA_BAR_UNDERLINE);
  private final Sprite MANA_EMPTY = new Sprite(65, 4, GameColors.MANA_BAR_EMPTY);
  private final int THUMBNAIL_X,
                    THUMBNAIL_Y;
  private final FontInfo CHARACTER_NAME_FONT = GameFonts.getGAME_MENU_HEADLINE_THIN();
  private final FontInfo HEALTH_LABEL = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo CHARACTER_HEALTH_FONT = GameFonts.getGAME_MENU_PRIMARY_TEXT_THIN();
  private final FontInfo MANA_LABEL = GameFonts.getGAME_MENU_PRIMARY_TEXT();
  private final FontInfo CHARACTER_MANA_FONT = GameFonts.getGAME_MENU_PRIMARY_TEXT_THIN();
  private final FontInfo CHARACTER_LEVEL_FONT = GameFonts.getGAME_MENU_HEADLINE();
  private final Character CHARACTER;
  private int characterHealth = -1,
              characterMana = -1;
  private Sprite healthFill;
  private Sprite manaFill;
  /**
   * Creates a PartyMemberButton for the given character with a callback method to fire when button is clicked.
   * @param character The character for whom this button is related to.
   * @param x The x coordinate to render the button.
   * @param y The y coordinate to render the button.
   * @param callback The callback function that fires when the button is clicked on.
   */
  public PartyMemberButton(Character character, int x, int y, ICallback callback) {
    super(null, x, y, WIDTH, HEIGHT, callback);
    this.CHARACTER = character;
    setButtonSprites();
    updateStatText();
    updateStatBars();
    this.THUMBNAIL = character.getThumbnail();
    this.THUMBNAIL_X = this.x + 10;
    this.THUMBNAIL_Y = this.y + 6;
    this.CHARACTER_NAME_FONT.setX(this.x + 50);
    this.CHARACTER_NAME_FONT.setY(this.y + 7);
    this.CHARACTER_NAME_FONT.setText(character.getName());
    this.HEALTH_LABEL.setX(this.x + 50);
    this.HEALTH_LABEL.setY(this.y + 22);
    this.HEALTH_LABEL.setText("HP");
    this.CHARACTER_HEALTH_FONT.setX(this.x + 65);
    this.CHARACTER_HEALTH_FONT.setY(this.y + 16);
    this.MANA_LABEL.setX(this.x + 50);
    this.MANA_LABEL.setY(this.y + 35);
    this.MANA_LABEL.setText("MP");
    this.CHARACTER_MANA_FONT.setX(this.x + 65);
    this.CHARACTER_MANA_FONT.setY(this.y + 30);
    this.CHARACTER_LEVEL_FONT.setX(this.x + 140);
    this.CHARACTER_LEVEL_FONT.setY(this.y + 22);
    this.CHARACTER_LEVEL_FONT.setText("LV: " + character.getLevel());
  }
  
  private void setButtonSprites() {
    button = new Sprite(width, height, GameColors.TRANSPARENT);
    buttonHover = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_HOVER);
    buttonDown = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_DOWN);
    disabledButton = button;
    currentButton = button;
  }
  
  private void updateStatText() {
    this.CHARACTER_HEALTH_FONT.setText(CHARACTER.getHealth() + " / " + CHARACTER.getHealthMax());
    this.CHARACTER_MANA_FONT.setText(CHARACTER.getMana() + " / " + CHARACTER.getManaMax());
  }
  
  private void updateStatBars() {
    if (characterHealth != CHARACTER.getHealth()) {
      healthFill = new Sprite((int)((CHARACTER.getHealth() / (double)CHARACTER.getHealthMax()) * 65), 4, GameColors.HEALTH_BAR_FILL);
      characterHealth = CHARACTER.getHealth();
    }
    if (characterMana != CHARACTER.getMana()) {
      manaFill = new Sprite((int)((CHARACTER.getMana() / (double)CHARACTER.getManaMax()) * 65), 4, GameColors.MANA_BAR_FILL);
      characterMana = CHARACTER.getMana();
    }
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
    screen.renderSprite(gl, THUMBNAIL_X, THUMBNAIL_Y, THUMBNAIL, false);
    screen.renderFonts(CHARACTER_NAME_FONT);
    screen.renderFonts(CHARACTER_HEALTH_FONT);
    screen.renderFonts(HEALTH_LABEL);
    screen.renderSprite(gl, CHARACTER_HEALTH_FONT.getX(), CHARACTER_HEALTH_FONT.getY() + 4, HEALTH_EMPTY, false);
    screen.renderSprite(gl, CHARACTER_HEALTH_FONT.getX(), CHARACTER_HEALTH_FONT.getY() + 4, healthFill, false);
    screen.renderSprite(gl, CHARACTER_HEALTH_FONT.getX(), CHARACTER_HEALTH_FONT.getY() + 8, HEALTH_UNDERLINE, false);
    screen.renderFonts(CHARACTER_MANA_FONT);
    screen.renderFonts(MANA_LABEL);
    screen.renderSprite(gl, CHARACTER_MANA_FONT.getX(), CHARACTER_MANA_FONT.getY() + 4, MANA_EMPTY, false);
    screen.renderSprite(gl, CHARACTER_MANA_FONT.getX(), CHARACTER_MANA_FONT.getY() + 4, manaFill, false);
    screen.renderSprite(gl, CHARACTER_MANA_FONT.getX(), CHARACTER_MANA_FONT.getY() + 8, MANA_UNDERLINE, false);
    screen.renderFonts(CHARACTER_LEVEL_FONT);
  }
  
}
