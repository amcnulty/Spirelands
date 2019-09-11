package com.monkeystomp.spirelands.battle.controls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.gui.controlls.button.BattleControlButton;
import com.monkeystomp.spirelands.gui.controlls.button.GroupButton;
import com.monkeystomp.spirelands.gui.controlls.buttongroup.ButtonGroup;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import com.monkeystomp.spirelands.util.Helpers;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControls {

  private boolean showing = false;
  private final int buttonRowY = 160,
                    buttonMargin = 3;
  private CharacterBattleEntity entity;
  private ButtonGroup controlButtonGroup,
                      itemButtonGroup,
                      currentGroup;
  private final Consumer<BattleMove> IBattleMove;
  private final int[] keyCodeByIndex = new int[]{Keyboard.NUMBER_ROW_1, Keyboard.NUMBER_ROW_2, Keyboard.NUMBER_ROW_3, Keyboard.NUMBER_ROW_4, Keyboard.NUMBER_ROW_5, Keyboard.NUMBER_ROW_6, Keyboard.NUMBER_ROW_7, Keyboard.NUMBER_ROW_8};
  
  public BattleControls(Consumer<BattleMove> IBattleMove) {
    this.IBattleMove = IBattleMove;
  }

  public void setControlsForBattleEntity(CharacterBattleEntity entity) {
    this.entity = entity;
    showing = true;
    List<BattleMove> battleMoves = this.entity.getStatModel().getEquippedMoves()
            .stream().filter(move -> !move.getType().equals(BattleMove.ITEM))
            .collect(Collectors.toList());
    List<BattleMove> itemBattleMoves = this.entity.getStatModel().getEquippedMoves()
            .stream().filter(move -> move.getType().equals(BattleMove.ITEM))
            .collect(Collectors.toList());
    int startingX = getStartingX(battleMoves.size() + ((itemBattleMoves.size() > 0) ? 1 : 0));
    int startingItemX = getStartingX(itemBattleMoves.size() + 1);
    controlButtonGroup = new ButtonGroup();
    itemButtonGroup = new ButtonGroup();
    currentGroup = controlButtonGroup;
    int index = 0;
    for (BattleMove move: battleMoves) {
      controlButtonGroup.addButton(
        new BattleControlButton(
          startingX + ((BattleControlButton.WIDTH + buttonMargin) * index),
          buttonRowY,
          keyCodeByIndex[index],
          move,
          () -> IBattleMove.accept(move)
        )
      );
      index++;
    }
    index = 0;
    for (BattleMove move: itemBattleMoves) {
      itemButtonGroup.addButton(
        new BattleControlButton(
          startingItemX + ((BattleControlButton.WIDTH + buttonMargin) * index),
          buttonRowY,
          keyCodeByIndex[index],
          move,
          () -> IBattleMove.accept(move)
        )
      );
      index++;
    }
    if (itemBattleMoves.size() > 0) {
      controlButtonGroup.addButton(
        new BattleControlButton(
          startingX + ((BattleControlButton.WIDTH + buttonMargin) * battleMoves.size()),
          buttonRowY,
          keyCodeByIndex[battleMoves.size()],
          new Sprite(16, 16, 0, 11, SpriteSheet.itemsSheet),
          () -> {
            currentGroup = itemButtonGroup;
            Helpers.setTimeout(() -> {
              for (GroupButton button: itemButtonGroup.getButtons()) {
                ((BattleControlButton)button).setListenerEnabled(true);
              }
            }, 300);
            itemButtonGroup.resetButtons();
            for (GroupButton button: controlButtonGroup.getButtons()) {
              ((BattleControlButton)button).setListenerEnabled(false);
            }
            IBattleMove.accept(null);
          }
        )
      );
      itemButtonGroup.addButton(
        new BattleControlButton(
          startingItemX + ((BattleControlButton.WIDTH + buttonMargin) * itemBattleMoves.size() + 1),
          buttonRowY,
          keyCodeByIndex[itemBattleMoves.size()],
          new Sprite(16, 16, 0, 3, SpriteSheet.itemsSheet),
          () -> {
            currentGroup = controlButtonGroup;
            Helpers.setTimeout(() -> {
              for (GroupButton button: controlButtonGroup.getButtons()) {
                ((BattleControlButton)button).setListenerEnabled(true);
              }
            }, 300);
            controlButtonGroup.resetButtons();
            for (GroupButton button: itemButtonGroup.getButtons()) {
              ((BattleControlButton)button).setListenerEnabled(false);
            }
            IBattleMove.accept(null);
          }
        )
      );
      for (GroupButton button: itemButtonGroup.getButtons()) {
        ((BattleControlButton)button).setListenerEnabled(false);
      }
    }
    for (GroupButton button: controlButtonGroup.getButtons()) {
      if (((BattleControlButton)button).getMove() != null) {
        // Disabled button if out of mana.
        if (((BattleControlButton)button).getMove().getManaRequired() > entity.getStatModel().getMana()) ((BattleControlButton)button).disableButton();
        // Automatically preselect last used move.
        if (((BattleControlButton)button).getMove().equals(entity.getCurrentMove())) ((BattleControlButton)button).clickOverride();
      }
    }
    for (GroupButton button: itemButtonGroup.getButtons()) {
      if (((BattleControlButton)button).getMove() != null) {
        // Disable item button if no more items in inventory.
        if (((BattleControlButton)button).getMove().getType().equals(BattleMove.ITEM)) {
          if (InventoryManager.getInventoryManager().getInventoryReferenceById(((BattleControlButton)button).getMove().getItem().getId()) == null) ((BattleControlButton)button).disableButton();
        }
      }      
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
    destroyExistingButtons(itemButtonGroup);
  }
  
  public void update() {
    currentGroup.update();
  }
  
  public void render(Screen screen, GL2 gl) {
    currentGroup.render(screen, gl);
  }
  
}
