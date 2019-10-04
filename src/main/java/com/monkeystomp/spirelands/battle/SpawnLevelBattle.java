package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.battle.enemy.Bestiary;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
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
  protected void createEnemies() {
//    EnemyBattleEntity newEntity = new EnemyBattleEntity(new SpawnCoordinate(100, 110, 1), Bestiary.PLANT_MONSTER_LV2.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 70, 1), Bestiary.PLANT_MONSTER.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 150, 1), Bestiary.PLANT_MONSTER.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);

    Enemy enemy = Bestiary.PIXIE.build();
    enemy.increaseLevel(5);
    EnemyBattleEntity newEntity = new EnemyBattleEntity(new SpawnCoordinate(100, 130, 1), enemy);
    newEntity.setBattle(this);
    enemies.add(newEntity);
    
    enemy = Bestiary.PIXIE.build();
    enemy.increaseLevel(4);
    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 100, 1), enemy);
    newEntity.setBattle(this);
    enemies.add(newEntity);

    enemy = Bestiary.PIXIE.build();
    enemy.increaseLevel(5);
    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 170, 1), enemy);
    newEntity.setBattle(this);
    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(140, 110, 1), Bestiary.PLANT_MONSTER_LV2.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(120, 70, 1), Bestiary.PLANT_MONSTER_LV2.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(120, 150, 1), Bestiary.PLANT_MONSTER_LV2.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(60, 110, 1), Bestiary.PLANT_MONSTER_LV2_ALT.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(40, 70, 1), Bestiary.PLANT_MONSTER_LV2_ALT.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
//    newEntity = new EnemyBattleEntity(new SpawnCoordinate(40, 150, 1), Bestiary.PLANT_MONSTER_LV2_ALT.build());
//    newEntity.setBattle(this);
//    enemies.add(newEntity);
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
  }

}
