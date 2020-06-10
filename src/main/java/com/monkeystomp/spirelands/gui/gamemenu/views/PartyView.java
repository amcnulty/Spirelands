package com.monkeystomp.spirelands.gui.gamemenu.views;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.character.CharacterManager;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.gamemenu.components.PartyMemberCard;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.util.ArrayList;

/**
 * The game menu view for when users click on the party button.
 * @author Aaron Michael McNulty
 */
public class PartyView extends DisplayView {
  
  private final int cardsStartingX = (LEFT + RIGHT) / 2 - 92,
                    spaceBetweenCardsX = 62,
                    spaceBetweenCardsY = 64,
                    cardFirstRowY = TOP + 24,
                    cardSecondRowY = cardFirstRowY + spaceBetweenCardsY;
  private final ArrayList<PartyMemberCard> partyMemberCards = new ArrayList<>();
  private final FontInfo  viewHeader = GameFonts.getGAME_MENU_HEADLINE_THIN(),
                          maxPartyMessageFont = GameFonts.getGAME_MENU_MUTED_TEXT();
  
  public PartyView() {
    viewHeader.setText("Select Party");
    viewHeader.setX((LEFT + RIGHT) / 2);
    viewHeader.setY(TOP + 8);
    viewHeader.centerText();
    
    maxPartyMessageFont.setText("-- Select up to three characters for your party --");
    maxPartyMessageFont.setX((LEFT + RIGHT) / 2);
    maxPartyMessageFont.setY(TOP + 17);
    maxPartyMessageFont.centerText();
  }

  @Override
  public void enteringView() {
    partyMemberCards.clear();
    ArrayList<Character> sortedCharacters = CharacterManager.getCharacterManager().getCharacters();
    sortedCharacters.sort((char1, char2) -> {
      int pos1 = CharacterManager.getCharacterManager().getPartyMemberPosition(char1);
      pos1 = pos1 == -1 ? 100 : pos1;
      int pos2 = CharacterManager.getCharacterManager().getPartyMemberPosition(char2);
      pos2 = pos2 == -1 ? 100 : pos2;
      return pos1 - pos2;
    });
    for (int i = 0; i < sortedCharacters.size(); i++) {
      Character member = sortedCharacters.get(i);
      PartyMemberCard card = new PartyMemberCard(cardsStartingX + spaceBetweenCardsX * (i % 3) + (i < 3 ? -10 : 10), i < 3 ? cardFirstRowY : cardSecondRowY, member);
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
    screen.renderFonts(viewHeader);
    screen.renderFonts(maxPartyMessageFont);
    for (PartyMemberCard card: partyMemberCards) {
      card.render(screen, gl);
    }
  }

}
