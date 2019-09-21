package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.battle.elemental.Elemental;
import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import com.monkeystomp.spirelands.battle.move.BattleMove;
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
          .element(Elemental.EARTH)
          .level(1)
          .health(80)
          .mana(35)
          .strength(6)
          .defense(8)
          .intellect(5)
          .spirit(11)
          .speed(11)
          .luck(11)
          .loot(Item.SMALL_HP_POTION)
          .dropRate(20)
          .experienceAward(1)
          .goldAward(10)
          .addMove(BattleMove.BASIC_ATTACK, "Plant Attack");
  /**
   * A plant monster that does physical and magical attacks
   */
  public static final EnemyBuilder PLANT_MONSTER_LV2 = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/plant_battle.png"))
          .name("Plant Monster 2")
          .element(Elemental.EARTH)
          .level(2)
          .health(90)
          .mana(37)
          .strength(8)
          .defense(10)
          .intellect(7)
          .spirit(13)
          .speed(13)
          .luck(13)
          .loot(Item.MEDIUM_HP_POTION)
          .dropRate(20)
          .experienceAward(2)
          .goldAward(20)
          .addMove(BattleMove.BASIC_ATTACK, "Plant Attack")
          .addMove(BattleMove.MAGIC_ENERGY, "Sap Spray");
  /**
   * A plant monster that does physical and magical attacks
   */
  public static final EnemyBuilder PLANT_MONSTER_LV2_ALT = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/plant_battle.png"))
          .name("Plant Monster 2")
          .element(Elemental.EARTH)
          .level(2)
          .health(90)
          .mana(37)
          .strength(8)
          .defense(10)
          .intellect(7)
          .spirit(13)
          .speed(13)
          .luck(13)
          .loot(Item.MANA_POTION)
          .dropRate(20)
          .experienceAward(2)
          .goldAward(20)
          .addMove(BattleMove.BASIC_ATTACK, "Plant Attack")
          .addMove(BattleMove.BLUNT_FORCE, "Vine Whip")
          .addMove(BattleMove.MAGIC_ENERGY, "Sap Attack");
  /**
   * A blob of water enemy.
   */
  public static final EnemyBuilder WATER_BLOB = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/water_blob_battle.png"))
          .name("Water Blob")
          .element(Elemental.WATER)
          .level(1)
          .health(80)
          .mana(35)
          .strength(6)
          .defense(8)
          .intellect(5)
          .spirit(11)
          .speed(11)
          .luck(11)
          .loot(Item.SMALL_HP_POTION)
          .dropRate(20)
          .experienceAward(1)
          .goldAward(10)
          .addMove(BattleMove.BASIC_ATTACK, "Splash")
          .addMove(BattleMove.BLUNT_FORCE, "Tidal Wave")
          .addMove(BattleMove.MAGIC_ENERGY, "Water Jet");
  /**
   * An ice fairy.
   */
  public static final EnemyBuilder PIXIE = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/pixie_battle.png"))
          .name("Pixie")
          .element(Elemental.ICE)
          .level(1)
          .health(80)
          .mana(35)
          .strength(6)
          .defense(8)
          .intellect(5)
          .spirit(11)
          .speed(11)
          .luck(11)
          .loot(Item.SMALL_HP_POTION)
          .dropRate(20)
          .experienceAward(1)
          .goldAward(10)
          .addMove(BattleMove.BASIC_ATTACK, "Splash")
          .addMove(BattleMove.BLUNT_FORCE, "Tidal Wave")
          .addMove(BattleMove.MAGIC_ENERGY, "Water Jet");
  
  /**
   * A poisonous lion creature.
   */
  public static final EnemyBuilder POISION_LION = new EnemyBuilder()
          .spriteSheet(new SpriteSheet("./resources/enemies/poison_lion_battle.png"))
          .name("Poison Lion")
          .element(Elemental.POISON)
          .elementalEffect(new ElementalEffect(Elemental.FIRE, Elemental.VERY_WEAK))
          .level(1)
          .health(80)
          .mana(35)
          .strength(6)
          .defense(8)
          .intellect(5)
          .spirit(11)
          .speed(11)
          .luck(11)
          .loot(Item.RIBS)
          .dropRate(20)
          .experienceAward(1)
          .goldAward(10)
          .addMove(BattleMove.BASIC_ATTACK, "Splash")
          .addMove(BattleMove.BLUNT_FORCE, "Tidal Wave")
          .addMove(BattleMove.MAGIC_ENERGY, "Water Jet");
  
}
