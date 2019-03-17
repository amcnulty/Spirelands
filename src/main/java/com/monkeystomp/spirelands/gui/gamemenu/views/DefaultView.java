package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.PartyMemberButton;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * The Default View is the first view that is seen when opening the game menu. It is used to display the current party and a brief snapshot of their health, magic and level.
 * @author Aaron Michael McNulty
 */
public class DefaultView extends DisplayView {
  
  private final int playerButtonX = 260,
                    playerButtonStartingY = 49,
                    verticalSpacing = 52;
  private final CharacterManager characterManager = CharacterManager.getCharacterManager();
  private final ArrayList<PartyMemberButton> buttons = new ArrayList<>();
  private Consumer<Character> openViewWithCharacter;
  /**
   * Creates a DefaultView object that displays the party member buttons.
   */
  public DefaultView() {
    createCharacterButtons();
  }
  
  private void createCharacterButtons() {
    int i = 0;
    for (Character partyMember : characterManager.getPartyMembers()) {
      buttons.add(new PartyMemberButton(partyMember, playerButtonX, playerButtonStartingY + verticalSpacing * i, () -> {handleButtonClick(partyMember);}));
      buttons.get(i).setDisabled(true);
      i++;
    }
  }

  private void handleButtonClick(Character character) {
    openViewWithCharacter.accept(character);
  }
  /**
   * Sets the consumer for changing views with a Character reference.
   * @param IChangeViewWithCharacter Method for calling a new view with a Character reference.
   */
  @Override
  public void setPartyMemberButtonPressHandler(Consumer<Character> IChangeViewWithCharacter) {
    this.openViewWithCharacter = IChangeViewWithCharacter;
  }
  /**
   * Activates the party member buttons.
   */
  public void activateCharacterButtons() {
    for (PartyMemberButton button: buttons) {
      button.setDisabled(false);
    }
  }
  /**
   * Disables the party member buttons.
   */
  public void disableCharacterButtons() {
    for (PartyMemberButton button: buttons) {
      button.setDisabled(true);
    }
  }

  @Override
  public void enteringView() {
  }
  
  @Override
  public void exitingView() {
    disableCharacterButtons();
  } 
  
  @Override
  public void update() {
    buttons.forEach(button -> {
      button.update();
    });
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    buttons.forEach(button -> {
      button.render(screen, gl);
    });
  }

}
