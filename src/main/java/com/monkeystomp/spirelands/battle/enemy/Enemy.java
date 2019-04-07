package com.monkeystomp.spirelands.battle.enemy;

import com.monkeystomp.spirelands.graphics.SpriteSheet;
import com.monkeystomp.spirelands.inventory.Item;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Enemy {
  
  // Sprite sheet for this enemy
  private final SpriteSheet spriteSheet;
  // Name of this enemy
  private final String name;
  // Level of this enemy
  private final int level;
  // Current hit points
  private int health;
  // Hit point max
  private final int healthMax;
  // Current mana points
  private int mana;
  // Max mana points
  private final int manaMax;
  // Physical strength stat
  private final int strength;
  // Physical attack resistance
  private final int defense;
  // Magic attack stat
  private final int intellect;
  // Magic attack resistance
  private final int spirit;
  // Enemy speed stat
  private final int speed;
  // Luck
  private final int luck;
  // Loot item that this enemy is holding.
  private final Item loot;
  /**
   * Creates a new Enemy object with the given configuration from the EnemyBuilder object.
   * @param builder Configuration object for creating this Enemy instance.
   */
  public Enemy(EnemyBuilder builder) {
    this.spriteSheet = builder.spriteSheet;
    this.name = builder.name;
    this.level = builder.level;
    this.health = builder.health;
    this.healthMax = builder.healthMax;
    this.mana = builder.mana;
    this.manaMax = builder.manaMax;
    this.strength = builder.strength;
    this.defense = builder.defense;
    this.intellect = builder.intellect;
    this.spirit = builder.spirit;
    this.speed = builder.speed;
    this.luck = builder.luck;
    this.loot = builder.loot;
  }
  /**
   * Increases the health stat of this Enemy.
   * @param amount Amount to increase health stat by.
   */
  public void increaseHealth(int amount) {
    health += amount;
  }
  /**
   * Decreases the health stat of this Enemy. Health stat cannot be decreased below zero.
   * @param amount Amount to decrease health by.
   */
  public void decreaseHealth(int amount) {
    health -= amount;
    if (health < 0) health = 0;
  }
  /**
   * Increases the mana stat of this Enemy.
   * @param amount Amount to increase mana by.
   */
  public void increaseMana(int amount) {
    mana += amount;
  }
  /**
   * Decreases the mana stat of this Enemy. Mana stat cannot be decreased below zero.
   * @param amount Amount to decrease mana by.
   */
  public void decreaseMana(int amount) {
    mana -= amount;
    if (mana < 0) mana = amount;
  }

  public SpriteSheet getSpriteSheet() {
    return spriteSheet;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public int getHealth() {
    return health;
  }

  public int getHealthMax() {
    return healthMax;
  }

  public int getMana() {
    return mana;
  }

  public int getManaMax() {
    return manaMax;
  }

  public int getStrength() {
    return strength;
  }

  public int getDefense() {
    return defense;
  }

  public int getIntellect() {
    return intellect;
  }

  public int getSpirit() {
    return spirit;
  }

  public int getSpeed() {
    return speed;
  }

  public int getLuck() {
    return luck;
  }

  public Item getLoot() {
    return loot;
  }

}
