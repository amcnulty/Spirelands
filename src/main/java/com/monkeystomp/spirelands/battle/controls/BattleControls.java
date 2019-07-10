package com.monkeystomp.spirelands.battle.controls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.controlls.button.BattleControlButton;
import com.monkeystomp.spirelands.gui.controlls.button.GroupButton;
import com.monkeystomp.spirelands.gui.controlls.buttongroup.ButtonGroup;
import com.monkeystomp.spirelands.input.Keyboard;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControls {

  private boolean showing = false;
  private final int buttonRowY = 160,
                    buttonMargin = 3;
  private CharacterBattleEntity entity;
  private ButtonGroup controlButtonGroup;
  private final Consumer<BattleMove> IBattleMove;
  private final int[] keyCodeByIndex = new int[]{Keyboard.NUMBER_ROW_1, Keyboard.NUMBER_ROW_2, Keyboard.NUMBER_ROW_3, Keyboard.NUMBER_ROW_4, Keyboard.NUMBER_ROW_5, Keyboard.NUMBER_ROW_6, Keyboard.NUMBER_ROW_7, Keyboard.NUMBER_ROW_8};
  
  public BattleControls(Consumer<BattleMove> IBattleMove) {
    this.IBattleMove = IBattleMove;
  }

  public void setControlsForBattleEntity(CharacterBattleEntity entity) {
    this.entity = entity;
    showing = true;
    ArrayList<BattleMove> battleMoves = this.entity.getStatModel().getEquippedMoves();
    int startingX = getStartingX(battleMoves.size());
    controlButtonGroup = new ButtonGroup();
    int index = 0;
    for (BattleMove move: battleMoves) {
      controlButtonGroup.addButton(
        new BattleControlButton(
          startingX + ((BattleControlButton.WIDTH + buttonMargin) * index),
          buttonRowY,
          keyCodeByIndex[index],
          move,
          () -> IBattleMove.accept(move)
        ));
      index++;
    }
    for (GroupButton button: controlButtonGroup.getButtons()) {
      if (((BattleControlButton)button).getMove().getManaRequired() > entity.getStatModel().getMana()) ((BattleControlButton)button).disableButton();
      if (((BattleControlButton)button).getMove().equals(entity.getCurrentMove())) ((BattleControlButton)button).clickOverride();
    }
  }
  
  private void destroyExistingButtons(ButtonGroup buttonGroup) {
    if (buttonGroup != null) {
      for (GroupButton button: buttonGroup.getButtons()) {
        ((BattleControlButton)button).destroy();
      }
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
    destroyExistingButtons(controlButtonGroup);
  }
  
  public void update() {
    controlButtonGroup.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    controlButtonGroup.render(screen, gl);
  }
  
}
