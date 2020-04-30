package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Top section of the Abilities page in the game menu where the character thumbnail and ability slots are shown.
 * @author Aaron Michael McNulty
 */
public class CharacterAbilityCard {
  
  private final int top, left, thumbnailX, thumbnailY,
                    thumbnailPadding = 6,
                    thumbnailWidth = 32,
                    thumbnailHeight = 32,
                    cardBottom,
                    buttonRowXStart,
                    buttonRowY;
  private int characterIndex;
  private final Consumer<Character> ICharacterChanger;
  private final Consumer<AbilitySlotClickEvent> IAbilitySlotClickEvent;
  private Character character;
  private GameMenuSecondaryButton previousButton, nextButton;
  private final ArrayList<AbilitySlot> abilitySlots = new ArrayList<>();
  
  public CharacterAbilityCard(int top, int left, Consumer<Character> ICharacterChanger, Consumer<AbilitySlotClickEvent> IAbilitySlotClickEvent) {
    this.top = top;
    this.left = left;
    thumbnailX = left + 14;
    thumbnailY = top + 10;
    cardBottom = thumbnailY + thumbnailHeight;
    buttonRowXStart = thumbnailX + thumbnailWidth + 24;
    buttonRowY = thumbnailY + thumbnailHeight / 2;
    this.ICharacterChanger = ICharacterChanger;
    this.IAbilitySlotClickEvent = IAbilitySlotClickEvent;
    createChevronButtons();
  }
  
  private void createChevronButtons() {
    previousButton = new GameMenuSecondaryButton(
      "\u2039",
      thumbnailX - thumbnailPadding,
      thumbnailY + thumbnailHeight / 2,
      9,
      29,
      () -> setPreviousCharacter()
    );
    nextButton = new GameMenuSecondaryButton(
      "\u203A",
      thumbnailX + thumbnailWidth + thumbnailPadding,
      thumbnailY + thumbnailHeight / 2,
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
    abilitySlots.add(new AbilitySlot(buttonRowXStart, buttonRowY, BattleMove.PHYSICAL, 3, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 22, buttonRowY, BattleMove.PHYSICAL, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 44, buttonRowY, BattleMove.MAGICAL, 2, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 66, buttonRowY, BattleMove.MAGICAL, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 88, buttonRowY, BattleMove.MAGICAL, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 110, buttonRowY, BattleMove.BUFF, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 132, buttonRowY, BattleMove.BUFF, IAbilitySlotClickEvent));
    abilitySlots.add(new AbilitySlot(buttonRowXStart + 154, buttonRowY, BattleMove.ITEM, 1, IAbilitySlotClickEvent));
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

  public int getCARD_BOTTOM() {
    return cardBottom;
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
    screen.renderSprite(gl, thumbnailX, thumbnailY, character.getThumbnail(), false);
    nextButton.render(screen, gl);
    for (AbilitySlot slot: abilitySlots) {
      slot.render(screen, gl);
    }
  }
}
