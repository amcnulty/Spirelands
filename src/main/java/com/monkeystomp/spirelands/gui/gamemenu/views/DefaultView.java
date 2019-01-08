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
  
  // Buttons for each player
  private final int PLAYER_BUTTON_X = 260,
                    PLAYER_BUTTON_STARTING_Y = 49,
                    VERTICAL_SPACING = 52;
  private final CharacterManager CHARACTER_MANAGER = CharacterManager.getCharacterManager();
  private ArrayList<PartyMemberButton> buttons = new ArrayList<>();
  private Consumer<Character> openViewWithCharacter;
  /**
   * Creates a DefaultView object that displays the party member buttons.
   */
  public DefaultView() {
    createCharacterButtons();
  }
  
  private void createCharacterButtons() {
    int i = 0;
    for (Character partyMember : CHARACTER_MANAGER.getPartyMembers()) {
      buttons.add(new PartyMemberButton(partyMember, PLAYER_BUTTON_X, PLAYER_BUTTON_STARTING_Y + VERTICAL_SPACING * i, () -> {handleButtonClick(partyMember);}));
      buttons.get(i).setDisabled(true);
      i++;
    }
  }

  private void handleButtonClick(Character character) {
    openViewWithCharacter.accept(character);
  }
  
  public void setPartyMemberButtonPressHandler(Consumer<Character> consumer) {
    this.openViewWithCharacter = consumer;
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
