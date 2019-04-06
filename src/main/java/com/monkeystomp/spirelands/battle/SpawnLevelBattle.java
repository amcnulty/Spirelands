package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevelBattle extends Battle {
  
  private final Sprite BACKGROUND = new Sprite(new Sprite("./resources/backgrounds/battle/dark_woods.jpg"), 38.05);
  
  public SpawnLevelBattle() {
    this.background = BACKGROUND;
    createEnemies();
  }
  
  private void createEnemies() {
    enemies.add(new EnemyBattleEntity(new SpawnCoordinate(100, 110, 1)));
    enemies.add(new EnemyBattleEntity(new SpawnCoordinate(100, 70, 1)));
    enemies.add(new EnemyBattleEntity(new SpawnCoordinate(100, 150, 1)));
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
  }

}
