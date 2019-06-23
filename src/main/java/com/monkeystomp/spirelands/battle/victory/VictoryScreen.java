package com.monkeystomp.spirelands.battle.victory;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.character.Character;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aaron Michael McNulty
 */
public class VictoryScreen {
  
  private final FontInfo victoryHeader = GameFonts.getDarkHeadline();
  private final int backgroundWidth = (int)(Screen.getWidth() * .9),
                    backgroundHeight = (int)(Screen.getHeight() - 55),
                    top = (Screen.getHeight() - backgroundHeight) / 2,
                    left = (int)(Screen.getWidth() * .05),
                    right = left + backgroundWidth,
                    bottom = top + backgroundHeight;
  private final Sprite background = new Sprite(backgroundWidth, backgroundHeight, GameColors.DIALOG_BOX_BACKGROUND);
  private boolean showing = false;
  private final List<Character> party;
  private final ArrayList<FontInfo> characterNameFonts = new ArrayList<>();
  
  public VictoryScreen(List<Character> party) {
    this.party = party;
    setupFonts();
  }

  private void setupFonts() {
    victoryHeader.setText("Victory!");
    victoryHeader.setX(Screen.getWidth() / 2);
    victoryHeader.setY(top + 10);
    victoryHeader.centerText();
    
    for (int i = 0; i < party.size(); i++) {
      FontInfo info = GameFonts.getDarkText_bold_22();
      info.setText(party.get(i).getName());
      info.setX(left + 15);
      info.setY(top + 25 + (i * 48));
      characterNameFonts.add(info);
    }
  }
  
  public boolean isShowing() {
    return showing;
  }

  public void setShowing(boolean showing) {
    this.showing = showing;
  }
  
  public void update() {
    
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, left, top, background, .8f, false);
    screen.renderFonts(victoryHeader);
    for (int i = 0; i < party.size(); i++) {
      screen.renderFonts(characterNameFonts.get(i));
      screen.renderSprite(gl, left + 15, top + 30 + (i * 48), party.get(i).getThumbnail(), false);
    }
  }

}
