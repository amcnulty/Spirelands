package com.monkeystomp.spirelands.battle.controls;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.CharacterBattleEntity;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.inventory.Item;

/**
 *
 * @author Aaron Michael McNulty
 */
public class BattleControls {

  private boolean showing = false;
  private CharacterBattleEntity entity;

  public void setControlsForBattleEntity(CharacterBattleEntity entity) {
    this.entity = entity;
    showing = true;
    System.out.println("Setting battle controls for " + entity.getCharacter().getName());
  }
  
  public boolean isShowing() {
    return showing;
  }
  
  public void update() {
    
  }
  
  public void render(Screen screen, GL2 gl) {
    if (entity != null) {
      for (int i = 0; i < entity.getCharacter().getEquippedMoves().size(); i++) {
        screen.renderSprite(gl, Screen.getWidth() / 2 + i * 17, 150, entity.getCharacter().getEquippedMoves().get(i).getThumbnail(), false);
      }
    }
  }
  
}
