package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.components.PartyMemberCard;
import java.util.ArrayList;

/**
 * The game menu view for when users click on the party button.
 * @author Aaron Michael McNulty
 */
public class PartyView extends DisplayView {
  
  private final int cardsStartingX = LEFT + 10,
                    cardFirstRowY = TOP + 10,
                    spaceBetweenCards = 70;
  private final ArrayList<PartyMemberCard> partyMemberCards = new ArrayList<>();

  @Override
  public void enteringView() {
    partyMemberCards.clear();
    for (int i = 0; i < CharacterManager.getCharacterManager().getCharacters().size(); i++) {
      Character member = CharacterManager.getCharacterManager().getCharacters().get(i);
      PartyMemberCard card = new PartyMemberCard(cardsStartingX + spaceBetweenCards * i, cardFirstRowY, member);
      partyMemberCards.add(card);
    }
  }

  @Override
  public void exitingView() {
  }

  @Override
  public void update() {
    for (PartyMemberCard card: partyMemberCards) {
      card.update();
    }
  }

  @Override
  public void render(Screen screen, GL2 gl) {
    for (PartyMemberCard card: partyMemberCards) {
      card.render(screen, gl);
    }
  }

}
