package com.monkeystomp.spirelands.battle.entity;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;

/**
 *
 * @author Aaron Michael McNulty
 */
public class EnemyBattleEntity extends BattleEntity {
  
  public EnemyBattleEntity(SpawnCoordinate slot) {
    super(slot, new SpriteSheet("./resources/enemies/plant_battle.png"));
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderFlippedSprite(gl, x, y, currentAction, false);
  }

}
