package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.battle.move.EnemyMove;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Bestiary {
  /**
   * A plant monster that does physical and magical attacks
   */
  public static final EnemyBuilder PLANT_MONSTER = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/plant_battle.png"))
          .name("Plant Monster")
          .level(1)
          .health(80)
          .mana(35)
          .strength(6)
          .defense(8)
          .intellect(5)
          .spirit(11)
          .speed(70)
          .luck(25)
          .loot(Item.SMALL_HP_POTION)
          .addMove(EnemyMove.BASIC_ATTACK);
  
}
