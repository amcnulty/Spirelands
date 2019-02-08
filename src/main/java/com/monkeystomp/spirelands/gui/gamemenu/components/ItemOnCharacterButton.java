package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.SoundEffects;
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
  private final Sprite smallThumbnail;
  private final Sprite healthUnderline = new Sprite(65, 1, GameColors.HEALTH_BAR_UNDERLINE);
  private final Sprite healthEmpty = new Sprite(65, 4, GameColors.HEALTH_BAR_EMPTY);
  private final Sprite manaUnderline = new Sprite(65, 1, GameColors.MANA_BAR_UNDERLINE);
  private final Sprite manaEmpty = new Sprite(65, 4, GameColors.MANA_BAR_EMPTY);
  private Sprite healthFill;
  private Sprite manaFill;
  private final FontInfo healthLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final FontInfo characterHealthFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final FontInfo manaLabel = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final FontInfo characterManaFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private int characterHealth = -1,
              characterMana = -1;
  
  public ItemOnCharacterButton(String text, int x, int y, int width, int height, Character character, Consumer<Character> callback) {
    super(text, x, y, WIDTH, HEIGHT, character, callback);
    this.character = character;
    this.smallThumbnail = new Sprite(character.getThumbnail(), 24);
    healthLabel.setX(this.x + 40);
    healthLabel.setY(this.y + 4);
    healthLabel.setText("HP:");
    characterHealthFont.setX(this.x + 60);
    characterHealthFont.setY(this.y + 4);
    characterHealthFont.setText(character.getHealth() + " / " + character.getHealthMax());
    manaLabel.setX(this.x + 40);
    manaLabel.setY(this.y + 16);
    manaLabel.setText("MP:");
    characterManaFont.setX(this.x + 60);
    characterManaFont.setY(this.y + 16);
    characterManaFont.setText(character.getMana() + " / " + character.getManaMax());
    characterHealth = character.getHealth();
    healthFill = new Sprite((int)((characterHealth / (double)character.getHealthMax()) * 65), 4, GameColors.HEALTH_BAR_FILL);
    characterMana = character.getMana();
    manaFill = new Sprite((int)((characterMana / (double)character.getManaMax()) * 65), 4, GameColors.MANA_BAR_FILL);
    createButtonSprites();
    setButtonSounds();
  }

  private void createButtonSprites() {
    button = new Sprite(width, height, GameColors.GAME_MENU_BACKGROUND);
    buttonHover = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_HOVER);
    buttonDown = new Sprite(width, height, GameColors.GAME_MENU_BUTTON_DOWN);
    currentButton = button;
  }
  
  private void setButtonSounds() {
    hoverSound = SoundEffects.BUTTON_HOVER;
    clickSound = SoundEffects.BUTTON_CLICK;
  }
  
  private void updateStatText() {
    this.characterHealthFont.setText(characterHealth + " / " + character.getHealthMax());
    this.characterManaFont.setText(characterMana + " / " + character.getManaMax());
  }
  
  public void updateStatBars() {
    if (characterHealth != character.getHealth()) {
      healthFill = new Sprite((int)((++characterHealth / (double)character.getHealthMax()) * 65), 4, GameColors.HEALTH_BAR_FILL);
    }
    if (characterMana != character.getMana()) {
      manaFill = new Sprite((int)((++characterMana / (double)character.getManaMax()) * 65), 4, GameColors.MANA_BAR_FILL);
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
    screen.renderSprite(gl, x + 5, y + height / 2 - smallThumbnail.getHeight() / 2, smallThumbnail, false);
    screen.renderFonts(healthLabel);
    screen.renderFonts(characterHealthFont);
    screen.renderFonts(manaLabel);
    screen.renderFonts(characterManaFont);
    screen.renderSprite(gl, x + 40, y + 8, healthEmpty, false);
    screen.renderSprite(gl, x + 40, y + 8, healthFill, false);
    screen.renderSprite(gl, x + 40, y + 12, healthUnderline, false);
    screen.renderSprite(gl, x + 40, y + 20, manaEmpty, false);
    screen.renderSprite(gl, x + 40, y + 20, manaFill, false);
    screen.renderSprite(gl, x + 40, y + 24, manaUnderline, false);
  }
  
}
