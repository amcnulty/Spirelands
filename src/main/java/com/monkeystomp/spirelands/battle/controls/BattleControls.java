package com.monkeystomp.spirelands.battle.controls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.BattleControlButton;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControls {

  private boolean showing = false;
  private final int buttonRowY = 160,
                    buttonMargin = 1;
  private CharacterBattleEntity entity;
  private final ArrayList<BattleControlButton> controlButtons = new ArrayList<>();
  private final Consumer<BattleMove> IBattleMove;
  
  public BattleControls(Consumer<BattleMove> IBattleMove) {
    this.IBattleMove = IBattleMove;
  }

  public void setControlsForBattleEntity(CharacterBattleEntity entity) {
    this.entity = entity;
    showing = true;
    this.controlButtons.clear();
    ArrayList<BattleMove> battleMoves = this.entity.getCharacter().getEquippedMoves();
    int startingX = getStartingX(battleMoves.size());
    for (BattleMove move: battleMoves) {
      controlButtons.add(
        new BattleControlButton(
          startingX + ((BattleControlButton.WIDTH + buttonMargin) * controlButtons.size()),
          buttonRowY,
          move.getThumbnail(),
          () -> IBattleMove.accept(move)
        )
      );
    }
  }
  
  private int getStartingX(int size) {
    if (size % 2 == 0) {
      return (Screen.getWidth() / 2) - ((size / 2) * (buttonMargin + BattleControlButton.WIDTH));
    }
    else {
      return ((Screen.getWidth() / 2) - (BattleControlButton.WIDTH / 2)) - (((size - 1) / 2) * (buttonMargin + BattleControlButton.WIDTH));
    }
  }
  
  public boolean isShowing() {
    return showing;
  }
  
  public void hideControls() {
    showing = false;
  }
  
  public void update() {
    for (int i = 0; i < controlButtons.size(); i++) {
      controlButtons.get(i).update();
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (entity != null) {
      for (int i = 0; i < controlButtons.size(); i++) {
        controlButtons.get(i).render(screen, gl);
      }
    }
  }
  
}
