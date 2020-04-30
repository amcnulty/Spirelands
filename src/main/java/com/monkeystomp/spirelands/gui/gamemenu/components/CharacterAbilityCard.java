package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.controlls.buttongroup.ButtonGroup;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
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
                    buttonRowY,
                    spaceBetweenSlots = 22;
  private int characterIndex;
  private final Consumer<Character> ICharacterChanger;
  private final Consumer<AbilitySlotClickEvent> IAbilitySlotClickEvent;
  private Character character;
  private GameMenuSecondaryButton previousButton, nextButton;
  private final ButtonGroup buttonGroup = new ButtonGroup();
  
  public CharacterAbilityCard(int top, int left, Consumer<Character> ICharacterChanger, Consumer<AbilitySlotClickEvent> IAbilitySlotClickEvent) {
    this.top = top;
    this.left = left;
    thumbnailX = left + 14;
    thumbnailY = top + 10;
    cardBottom = thumbnailY + thumbnailHeight;
    buttonRowXStart = thumbnailX + thumbnailWidth + 24;
    buttonRowY = thumbnailY + thumbnailHeight / 2 - 9;
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
    for(AbilitySlot slot: character.getAbilitySlots()) {
      slot.setLeft(buttonRowXStart + spaceBetweenSlots * character.getAbilitySlots().indexOf(slot));
      slot.setTop(buttonRowY);
      slot.setClickHandler(IAbilitySlotClickEvent);
      buttonGroup.addButton(slot);
    }
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
    buttonGroup.update();
  }

  public void render(Screen screen, GL2 gl) {
    previousButton.render(screen, gl);
    screen.renderSprite(gl, thumbnailX, thumbnailY, character.getThumbnail(), false);
    nextButton.render(screen, gl);
    buttonGroup.render(screen, gl);
  }
}
