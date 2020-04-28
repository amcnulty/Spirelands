package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CharacterAbilityCard {
  
  private final int TOP, LEFT, THUMBNAIL_X, THUMBNAIL_Y,
                    THUMBNAIL_PADDING = 6,
                    THUMBNAIL_WIDTH = 32,
                    THUMBNAIL_HEIGHT = 32,
                    BUTTON_ROW_X_START,
                    BUTTON_ROW_Y;
  private int characterIndex;
  private final Consumer<Character> ICharacterChanger;
  private Character character;
  private GameMenuSecondaryButton previousButton, nextButton;
  private final ArrayList<AbilitySlot> abilitySlots = new ArrayList<>();
  
  public CharacterAbilityCard(int top, int left, Consumer<Character> ICharacterChanger) {
    this.TOP = top;
    this.LEFT = left;
    THUMBNAIL_X = LEFT + 14;
    THUMBNAIL_Y = TOP + 10;
    BUTTON_ROW_X_START = THUMBNAIL_X + THUMBNAIL_WIDTH + 24;
    BUTTON_ROW_Y = THUMBNAIL_Y + THUMBNAIL_HEIGHT / 2;
    this.ICharacterChanger = ICharacterChanger;
    createChevronButtons();
  }
  
  private void createChevronButtons() {
    previousButton = new GameMenuSecondaryButton(
      "\u2039",
      THUMBNAIL_X - THUMBNAIL_PADDING,
      THUMBNAIL_Y + THUMBNAIL_HEIGHT / 2,
      9,
      29,
      () -> setPreviousCharacter()
    );
    nextButton = new GameMenuSecondaryButton(
      "\u203A",
      THUMBNAIL_X + THUMBNAIL_WIDTH + THUMBNAIL_PADDING,
      THUMBNAIL_Y + THUMBNAIL_HEIGHT / 2,
      9,
      29,
      () -> setNextCharacter()
    );
  }
  /**
   * Set the ability slots for the current character.
   */
  private void setAbilitySlots() {
    // TODO: get data for creating slots from the current character.
    // Temporarily setting the slots manually.
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START, BUTTON_ROW_Y, BattleMove.PHYSICAL, 1, 150, () -> {
      System.out.println("Unlocked physical slot was clicked on!");
      System.out.println("Showing a list of physical type moves.");
      System.out.println("Showing an upgrade button for 150 points");
    }));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 22, BUTTON_ROW_Y, BattleMove.PHYSICAL, 75, () -> {
      System.out.println("Locked physical slot was clicked on");
      System.out.println("Showing an unlock button for 75 points.");
    }));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 44, BUTTON_ROW_Y, BattleMove.MAGICAL, 1, 150, () -> {}));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 66, BUTTON_ROW_Y, BattleMove.MAGICAL, 75, () -> {}));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 88, BUTTON_ROW_Y, BattleMove.MAGICAL, 75, () -> {}));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 110, BUTTON_ROW_Y, BattleMove.BUFF, 75, () -> {}));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 132, BUTTON_ROW_Y, BattleMove.BUFF, 75, () -> {}));
    abilitySlots.add(new AbilitySlot(BUTTON_ROW_X_START + 154, BUTTON_ROW_Y, BattleMove.ITEM, 1, 125, () -> {}));
  }
   
  private void setPreviousCharacter() {
    characterIndex--;
    if (characterIndex == -1) characterIndex = CharacterManager.getCharacterManager().getPartyMembers().size() - 1;
    ICharacterChanger.accept(CharacterManager.getCharacterManager().getPartyMembers().get(characterIndex));
  }
  
  private void setNextCharacter() {
    characterIndex++;
    if (characterIndex == CharacterManager.getCharacterManager().getPartyMembers().size()) characterIndex = 0;
    ICharacterChanger.accept(CharacterManager.getCharacterManager().getPartyMembers().get(characterIndex));
  }
    
  public void setCharacter(Character character) {
    this.character = character;
    characterIndex = CharacterManager.getCharacterManager().getPartyMemberPosition(character);
    setAbilitySlots();
  }

  public void update() {
    previousButton.update();
    nextButton.update();
    for (AbilitySlot slot: abilitySlots) {
      slot.update();
    }
  }

  public void render(Screen screen, GL2 gl) {
    previousButton.render(screen, gl);
    screen.renderSprite(gl, THUMBNAIL_X, THUMBNAIL_Y, character.getThumbnail(), false);
    nextButton.render(screen, gl);
    for (AbilitySlot slot: abilitySlots) {
      slot.render(screen, gl);
    }
  }
}
