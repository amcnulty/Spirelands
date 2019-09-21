package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import com.monkeystomp.spirelands.battle.move.BattleMove;
import com.monkeystomp.spirelands.character.StatModel;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * The EnemyBuilder class is used to configure an Enemy and call its constructor.
 * </p>
 * <h3>
 * Example Usage:
 * </h3>
 * <pre>
 * {@code
 * private Enemy
 *   plantEnemy = new EnemyBuilder()
 *     .spriteSheet(new SpriteSheet("./resources/path/to/spritesheet"))
 *     .name("Plant Monster")
 *     .level(3)
 *     .health(155)
 *     .mana(35)
 *     .strength(15)
 *     .defense(8)
 *     .intellect(5)
 *     .spirit(11)
 *     .speed(7)
 *     .luck(6)
 *     .loot(Item.SMALL_HP_POTION)
 *     .dropRate(20)
 *     .experienceAward(1)
 *     .goldAward(10)
 *     .build();}
 * </pre>
 * @author Aaron Michael McNulty
 */
public class EnemyBuilder {

  /**
   * Sprite sheet for this enemy
   */
  public SpriteSheet spriteSheet;
  /**
   * Name of this enemy
   */
  public String name;
  /**
   * Elemental type of this enemy.
   */
  public String element;
  /**
   * Level of this enemy
   */
  public int level = 1;
  /**
   * Current hit points
   */
  public int health = 80;
  /** 
   * Hit point max
   */
  public int healthMax = 80;
  /**
   * Health weight.
   */
  public String healthWeight = StatModel.AVERAGE;
  /**
   * Current mana points
   */
  public int mana = 35;
  /**
   * Max mana points
   */
  public int manaMax = 35;
  /**
   * Mana weight.
   */
  public String manaWeight = StatModel.AVERAGE;
  /**
   * Physical strength stat
   */
  public int strength = 7;
  /**
   * Strength weight.
   */
  public String strengthWeight = StatModel.AVERAGE;
  /**
   * Physical attack resistance
   */
  public int defense = 7;
  /**
   * Defense weight.
   */
  public String defenseWeight = StatModel.AVERAGE;
  /**
   * Magic attack stat
   */
  public int intellect = 7;
  /**
   * Intellect weight.
   */
  public String intellectWeight = StatModel.AVERAGE;
  /**
   * Magic attack resistance
   */
  public int spirit = 7;
  /**
   * Spirit weight.
   */
  public String spiritWeight = StatModel.AVERAGE;
  /**
   * Enemy speed stat
   */
  public int speed = 10;
  /**
   * Speed weight.
   */
  public String speedWeight = StatModel.AVERAGE;
  /**
   * Luck
   */
  public int luck = 10;
  /**
   * Luck weight.
   */
  public String luckWeight = StatModel.AVERAGE;
  /**
   * An item that this enemy is holding.
   */
  public Item loot;
  /**
   * The drop rate of this enemy.
   */
  public int dropRate;
  /**
   * The experience gained from defeating this enemy.
   */
  public int experienceAward;
  /**
   * The gold gained from defeating this enemy.
   */
  public int goldAward;
  /**
   * Moves that this enemy can perform.
   */
  public ArrayList<BattleMove> enemyMoves = new ArrayList<>();
  /**
   * List of elemental weaknesses and resistances for this enemy.
   */
  public ArrayList<ElementalEffect> elementalEffects = new ArrayList<>();
  /**
   * Sets the sprite sheet of the enemy.
   * @param spriteSheet SpriteSheet object to set for this enemy.
   * @return This EnemyBuilder reference.
   */
  public EnemyBuilder spriteSheet(SpriteSheet spriteSheet) {
    this.spriteSheet = spriteSheet;
    return this;
  }
  /**
   * Sets the name of the enemy.
   * @param name Name to set for this enemy.
   * @return This EnemyBuilder reference.
   */
  public EnemyBuilder name(String name) {
    this.name = name;
    return this;
  }
  /**
   * Sets the elemental type of this enemy.
   * @param element Element to set this enemy to.
   * @return This EnemyBuilder reference.
   */
  public EnemyBuilder element(String element) {
    this.element = element;
    return this;
  }
  /**
   * Sets the level of this enemy.
   * @param level Level to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder level(int level) {
    this.level = level;
    return this;
  }
  /**
   * Sets the health and max health of this enemy.
   * @param health Health to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder health(int health) {
    this.health = health;
    this.healthMax = health;
    return this;
  }
  /**
   * Sets the healthWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder healthWeight(String weight) {
    this.healthWeight = weight;
    return this;
  }
  /**
   * Sets the mana and max mana of this enemy.
   * @param mana Mana to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder mana(int mana) {
    this.mana = mana;
    this.manaMax = mana;
    return this;
  }
  /**
   * Sets the manaWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder manaWeight(String weight) {
    this.manaWeight = weight;
    return this;
  }
  /**
   * Sets the strength of this enemy.
   * @param strength Strength to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder strength(int strength) {
    this.strength = strength;
    return this;
  }
  /**
   * Sets the strengthWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder strengthWeight(String weight) {
    this.strengthWeight = weight;
    return this;
  }
  /**
   * Sets the defense of this enemy.
   * @param defense Defense to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder defense(int defense) {
    this.defense = defense;
    return this;
  }
  /**
   * Sets the defenseWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder defenseWeight(String weight) {
    this.defenseWeight = weight;
    return this;
  }
  /**
   * Sets the intellect of this enemy.
   * @param intellect Intellect to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder intellect(int intellect) {
    this.intellect = intellect;
    return this;
  }
  /**
   * Sets the intellectWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder intellectWeight(String weight) {
    this.intellectWeight = weight;
    return this;
  }
  /**
   * Sets the spirit of this enemy.
   * @param spirit Spirit to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder spirit(int spirit) {
    this.spirit = spirit;
    return this;
  }
  /**
   * Sets the spiritWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder spiritWeight(String weight) {
    this.spiritWeight = weight;
    return this;
  }
  /**
   * Sets the speed of this enemy.
   * @param speed Speed to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder speed(int speed) {
    this.speed = speed;
    return this;
  }
  /**
   * Sets the speedWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder speedWeight(String weight) {
    this.speedWeight = weight;
    return this;
  }
  /**
   * Sets the luck of this enemy.
   * @param luck Luck to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder luck(int luck) {
    this.luck = luck;
    return this;
  }
  /**
   * Sets the luckWeight for increasing levels of this enemy.
   * @param weight Weight which to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder luckWeight(String weight) {
    this.luckWeight = weight;
    return this;
  }
  /**
   * Sets the loot this enemy is holding.
   * @param loot Loot to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder loot(Item loot) {
    this.loot = loot;
    return this;
  }
  /**
   * Sets the drop rate of this enemy.
   * @param rate Number ranging from 1-100 representing the percent chance of a drop.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder dropRate(int rate) {
    this.dropRate = rate;
    return this;
  }
  /**
   * Sets the experience awarded by defeating this enemy.
   * @param award The amount of experience this enemy will give when defeated.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder experienceAward(int award) {
    this.experienceAward = award;
    return this;
  }
  /**
   * Sets the gold awarded by defeating this enemy.
   * @param award The amount of gold this enemy will give when defeated.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder goldAward(int award) {
    this.goldAward = award;
    return this;
  }
  /**
   * Adds a new move to the list of this enemy's moves.
   * @param move New move to be added to this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder addMove(BattleMove move) {
    this.enemyMoves.add(move);
    return this;
  }
  /**
   * Adds a new move to the list of this enemy's moves but gives the move a custom name.
   * @param move New move to be added to the enemy.
   * @param customMoveName Custom name to set for this move.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder addMove(BattleMove move, String customMoveName) {
    try {
      BattleMove clone = (BattleMove)move.clone();
      clone.setCustomName(customMoveName);
      this.enemyMoves.add(clone);
      return this;
    }
    catch (CloneNotSupportedException ex) {
      Logger.getLogger(EnemyBuilder.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this;
  }
  /**
   * Adds an elemental effect to this enemy.
   * @param weakness The elemental weakness to be added.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder elementalEffect(ElementalEffect weakness) {
    this.elementalEffects.add(weakness);
    return this;
  }
  /**
   * Builds this enemy by calling the Enemy class constructor with this instance of EnemyBuilder as the argument.
   * @return The newly created Enemy.
   */
  public Enemy build() {
    return new Enemy(this);
  }

}
