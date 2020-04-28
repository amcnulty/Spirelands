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
                    THUMBNAIL_HEIGHT = 32;
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
    abilitySlots.add(new AbilitySlot(BattleMove.PHYSICAL));
    abilitySlots.add(new AbilitySlot(BattleMove.PHYSICAL, 100));
    abilitySlots.add(new AbilitySlot(BattleMove.MAGICAL, 100));
    abilitySlots.add(new AbilitySlot(BattleMove.MAGICAL));
    abilitySlots.add(new AbilitySlot(BattleMove.MAGICAL));
    abilitySlots.add(new AbilitySlot(BattleMove.BUFF, 75));
    abilitySlots.add(new AbilitySlot(BattleMove.BUFF, 75));
    abilitySlots.add(new AbilitySlot(BattleMove.ITEM));
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
