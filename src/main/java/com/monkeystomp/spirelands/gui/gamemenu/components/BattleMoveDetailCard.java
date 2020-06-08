package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.character.Character;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.controlls.button.GameMenuSecondaryButton;
import com.monkeystomp.spirelands.gui.gamemenu.views.AbilitiesView;
import com.monkeystomp.spirelands.gui.styles.GameColors;
import com.monkeystomp.spirelands.input.ICallback;
import java.util.Arrays;

/**
 * A display on the abilities view of the game menu for showing more detailed information of a Battle Move object.
 * @author Aaron Michael McNulty
 */
public class BattleMoveDetailCard {
  
  private final double topBorderSpriteScale = 100.0 / 3.0;
  private final int closeButtonX = AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.width - 5,
                    closeButtonY = AbilitiesView.ABILITY_LIST_BOUNDS.y + 6,
                    topBorderWidth = 600,
                    topBorderHeight = 3,
                    topBorderColor = GameColors.GAME_MENU_MUTED_TEXT,
                    topBorderRenderX = (AbilitiesView.ABILITY_LIST_BOUNDS.width + AbilitiesView.ABILITY_LIST_BOUNDS.x + AbilitiesView.ABILITY_LIST_BOUNDS.x) / 2 - ((int)(topBorderWidth * (topBorderSpriteScale / 100.0))) / 2;
  private Character character;
  private BattleMove battleMove;
  private ICallback ICloseButtonCallback;
  private final GameMenuSecondaryButton closeButton = new GameMenuSecondaryButton(
    "\u2A2F",
    closeButtonX,
    closeButtonY,
    8,
    9,
    () -> ICloseButtonCallback.execute()
  );
  private final Sprite topBorder;
  
  public BattleMoveDetailCard(ICallback ICloseButtonCallback) {
    int[] pixels = new int[topBorderWidth * topBorderHeight];
    Arrays.fill(pixels, 0, 599, GameColors.TRANSPARENT);
    Arrays.fill(pixels, 600, 1199, topBorderColor);
    Arrays.fill(pixels, 1200, 1799, GameColors.TRANSPARENT);
    topBorder = new Sprite(new Sprite(pixels, topBorderWidth, topBorderHeight), topBorderSpriteScale);
    this.ICloseButtonCallback = ICloseButtonCallback;
  }
  
  public void show(BattleMove battleMove, Character character) {
    this.battleMove = battleMove;
    this.character = character;
  }
  
  public void update() {
    closeButton.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, topBorderRenderX, AbilitiesView.ABILITY_LIST_BOUNDS.y, topBorder, false);
    closeButton.render(screen, gl);
  }

}
