package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.battle.enemy.Enemy;
import com.monkeystomp.spirelands.battle.enemy.EnemyBuilder;
import com.monkeystomp.spirelands.battle.entity.EnemyBattleEntity;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevelBattle extends Battle {
  
  private final Sprite BACKGROUND = new Sprite(new Sprite("./resources/backgrounds/battle/dark_woods.jpg"), 38.05);
  private final EnemyBuilder plantMonster = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/plant_battle.png"))
          .name("Plant Monster")
          .level(3)
          .health(155)
          .mana(35)
          .strength(15)
          .defense(8)
          .intellect(5)
          .spirit(11)
          .speed(7)
          .luck(6)
          .loot(Item.SMALL_HP_POTION);
  
  public SpawnLevelBattle() {
    this.background = BACKGROUND;
    createEnemies();
    this.battleMusic = Music.BATTLE_SONG;
  }
  
  private void createEnemies() {
    EnemyBattleEntity newEntity = new EnemyBattleEntity(new SpawnCoordinate(100, 110, 1), plantMonster.build());
    newEntity.setBattle(this);
    enemies.add(newEntity);
    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 70, 1), plantMonster.build());
    newEntity.setBattle(this);
    enemies.add(newEntity);
    newEntity = new EnemyBattleEntity(new SpawnCoordinate(80, 150, 1), plantMonster.build());
    newEntity.setBattle(this);
    enemies.add(newEntity);
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
  }

}
