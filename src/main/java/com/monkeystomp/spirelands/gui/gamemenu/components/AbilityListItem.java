package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.DangerButton;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuPrimaryButton;
import com.monkeystomp.spirelands.gui.controlls.button.PrimaryButton;
import com.monkeystomp.spirelands.gui.fonts.FontInfo;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.gui.styles.GameFonts;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Used to show battle moves in the ability list of the 'Abilities' view in the game menu.
 * @author Aaron Michael McNulty
 */
public class AbilityListItem {
  
  private final int top, left,
                    textX, textY,
                    equipButtonX, equipButtonY,
                    infoButtonX, infoButtonY,
                    badgeX, badgeY;
  private final BattleMove move;
  private final Consumer<BattleMove> infoClickHandler;
  private final Consumer<BattleMove> equipClickHandler, unequipClickHandler;
  private final FontInfo moveNameFont = GameFonts.getGAME_MENU_LABEL_TEXT();
  private final FontInfo badgeFont = GameFonts.getGAME_MENU_PRIMARY_TEXT_SMALL();
  private final int[] badgePixels = new int[49];
  private final Sprite badgeSprite;
  private final Character character;
  private final AbilitySlot slot;
  private PrimaryButton equipButton;
  private DangerButton unequipButton;
  private GameMenuPrimaryButton infoButton;
  private boolean showBadge = false;
  
  public AbilityListItem(int top, int left, BattleMove move, Character character, AbilitySlot slot, Consumer<BattleMove> infoClickHandler, Consumer<BattleMove> equipClickHandler, Consumer<BattleMove> unequipClickHandler) {
    this.top = top;
    this.left = left;
    this.move = move;
    this.character = character;
    this.slot = slot;
    this.textX = left + 18;
    this.textY = top + 8;
    this.equipButtonX = left + 97;
    this.equipButtonY = top + 8;
    this.infoButtonX = equipButtonX + 22;
    this.infoButtonY = equipButtonY;
    this.badgeX = left;
    this.badgeY = top;
    Arrays.fill(badgePixels, GameColors.PRIMARY_BUTTON_BLUE);
    this.badgeSprite = new Sprite(badgePixels, 7, 7);
    this.infoClickHandler = infoClickHandler;
    this.equipClickHandler = equipClickHandler;
    this.unequipClickHandler = unequipClickHandler;
    setFontInfo();
    createButtons();
  }
  
  private void setFontInfo() {
    moveNameFont.setText(move.getName());
    moveNameFont.setX(textX);
    moveNameFont.setY(textY);
  }
  
  private void createButtons() {
    infoButton = new GameMenuPrimaryButton("Info", infoButtonX, infoButtonY, 19, 11, () -> infoClickHandler.accept(move));
    equipButton = new PrimaryButton("Equip", equipButtonX, equipButtonY, 19, 11, () -> equipClickHandler.accept(move));
    unequipButton = new DangerButton("\u2716", equipButtonX, equipButtonY, 19, 11, () -> unequipClickHandler.accept(move));
  }
  
  public void showBadge(int value) {
    showBadge = true;
    badgeFont.setX(badgeX);
    badgeFont.setY(badgeY);
    badgeFont.setText("" + value);
  }

  public void hideBadge() {
    showBadge = false;
  }
  
  public BattleMove getMove() {
    return move;
  }
  
  public void update() {
    if (character.slotContainsMove(slot, move))
      unequipButton.update();
    else
      equipButton.update();
    infoButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, left, top, move.getThumbnail(), false);
    if (showBadge) {
      screen.renderSprite(gl, badgeX - 2, badgeY - 3, badgeSprite, false);
      screen.renderFonts(badgeFont);
    }
    screen.renderFonts(moveNameFont);
    if (character.slotContainsMove(slot, move))
      unequipButton.render(screen, gl);
    else
      equipButton.render(screen, gl);
    infoButton.render(screen, gl);
  }
  
}
