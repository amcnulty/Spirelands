package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.PartyMemberButton;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class DefaultView extends DisplayView {
  
  // Buttons for each player
  private final int PLAYER_BUTTON_X = 260,
                    PLAYER_BUTTON_STARTING_Y = 49,
                    VERTICAL_SPACING = 52;
  private final CharacterManager CHARACTER_MANAGER = CharacterManager.getCharacterManager();
  private ArrayList<PartyMemberButton> buttons = new ArrayList<>();
  
  public DefaultView() {
    createCharacterButtons();
  }
  
  private void createCharacterButtons() {
    int i = 0;
    for (Character partyMember : CHARACTER_MANAGER.getPartyMembers()) {
      buttons.add(new PartyMemberButton(partyMember, PLAYER_BUTTON_X, PLAYER_BUTTON_STARTING_Y + VERTICAL_SPACING * i, () -> {handleButtonClick(partyMember);}));
      i++;
    }
  }

  private void handleButtonClick(Character character) {
    System.out.println(character.getName() + " button was clicked!");
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
