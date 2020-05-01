package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.function.Consumer;

/**
 * Used to show battle moves in the ability list of the 'Abilities' view in the game menu.
 * @author Aaron Michael McNulty
 */
public class AbilityListItem {
  
  private final int top, left,
                    textX, textY,
                    equipButtonX, equipButtonY,
                    infoButtonX, infoButtonY;
  private final BattleMove move;
  private final ICallback infoClickHandler;
  private final Consumer<BattleMove> equipClickHandler;
  private final FontInfo moveNameFont = GameFonts.getGAME_MENU_LABEL_TEXT();
  private PrimaryButton equipButton;
  private GameMenuPrimaryButton infoButton;
  
  public AbilityListItem(int top, int left, BattleMove move, ICallback infoClickHandler, Consumer<BattleMove> equipClickHandler) {
    this.top = top;
    this.left = left;
    this.move = move;
    this.textX = left + 18;
    this.textY = top + 8;
    this.equipButtonX = left + 97;
    this.equipButtonY = top + 8;
    this.infoButtonX = equipButtonX + 22;
    this.infoButtonY = equipButtonY;
    this.infoClickHandler = infoClickHandler;
    this.equipClickHandler = equipClickHandler;
    setFontInfo();
    createButtons();
  }
  
  private void setFontInfo() {
    moveNameFont.setText(move.getName());
    moveNameFont.setX(textX);
    moveNameFont.setY(textY);
  }
  
  private void createButtons() {
    equipButton = new PrimaryButton("Equip", equipButtonX, equipButtonY, 19, 11, () -> equipClickHandler.accept(move));
    infoButton = new GameMenuPrimaryButton("Info", infoButtonX, infoButtonY, 19, 11, () -> infoClickHandler.execute());
  }
  
  public void update() {
    equipButton.update();
    infoButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, left, top, move.getThumbnail(), false);
    screen.renderFonts(moveNameFont);
    equipButton.render(screen, gl);
    infoButton.render(screen, gl);
  }
  
}
