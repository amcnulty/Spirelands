package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.events.AbilitySlotClickEvent;
import com.monkeystomp.spirelands.inventory.InventoryManager;
import java.util.ArrayList;

/**
 * The Ability list is shown on the abilities screen and allows users to equip moves.
 * @author Aaron Michael McNulty
 */
public class AbilityList {
  
  private final int top, left;
  private boolean showing = false;
  private ArrayList<BattleMove> moveList;
  
  public AbilityList(int top, int left) {
    this.top = top;
    this.left = left;
  }
  
  public void show(AbilitySlotClickEvent event) {
    if (!event.getType().equals(BattleMove.BUFF)) {
      moveList = InventoryManager.getInventoryManager().getBattleMovesByType(event.getType(), BattleMove.BUFF);
    }
    else {
      moveList = InventoryManager.getInventoryManager().getBattleMovesByAction(event.getType());
    }
    showing = true;
  }
  
  public void hide() {
    showing = false;
  }
  
  public void update() {
    if (showing) {
      
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (showing) {
      for(BattleMove move: moveList) {
        screen.renderSprite(gl, left + 40, top + 17 * moveList.indexOf(move), move.getThumbnail(), false);
      }
    }
  }

}
