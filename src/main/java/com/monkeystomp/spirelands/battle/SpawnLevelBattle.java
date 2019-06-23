package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.battle.enemy.Bestiary;
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
    this.battleMusic = Music.BATTLE_SONG;
    this.victoryMusic = Music.BATTLE_VICTORY;
  }
  
  @Override
  public void init() {
    super.init();
    createEnemies();
  }
  
  private void createEnemies() {
    EnemyBattleEntity newEntity = new EnemyBattleEntity(new SpawnCoordinate(100, 110, 1), Bestiary.PLANT_MONSTER.build());
    newEntity.setBattle(this);
    enemies.add(newEntity);
    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 70, 1), Bestiary.PLANT_MONSTER.build());
    newEntity.setBattle(this);
    enemies.add(newEntity);
    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 150, 1), Bestiary.PLANT_MONSTER.build());
    newEntity.setBattle(this);
    enemies.add(newEntity);
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
  }

}
