package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;

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
 *   plantEnemy = new EnemyBuilder()}
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
 *     .build();
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
   * Level of this enemy
   */
  public int level;
  /**
   * Current hit points
   */
  public int health;
  /** 
   * Hit point max
   */
  public int healthMax;
  /**
   * Current mana points
   */
  public int mana;
  /**
   * Max mana points
   */
  public int manaMax;
  /**
   * Physical strength stat
   */
  public int strength;
  /**
   * Physical attack resistance
   */
  public int defense;
  /**
   * Magic attack stat
   */
  public int intellect;
  /**
   * Magic attack resistance
   */
  public int spirit;
  /**
   * Enemy speed stat
   */
  public int speed;
  /**
   * Luck
   */
  public int luck;
  /**
   * An item that this enemy is holding.
   */
  public Item loot;
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
   * Sets the strength of this enemy.
   * @param strength Strength to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder strength(int strength) {
    this.strength = strength;
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
   * Sets the intellect of this enemy.
   * @param intellect Intellect to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder intellect(int intellect) {
    this.intellect = intellect;
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
   * Sets the speed of this enemy.
   * @param speed Speed to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder speed(int speed) {
    this.speed = speed;
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
   * Sets the loot this enemy is holding.
   * @param loot Loot to set for this enemy.
   * @return The EnemyBuilder reference.
   */
  public EnemyBuilder loot(Item loot) {
    this.loot = loot;
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
