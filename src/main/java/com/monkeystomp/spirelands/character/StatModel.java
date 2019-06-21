package com.monkeystomp.spirelands.character;

/**
 * The StatModel class is a superclass for Characters and Enemies who have stats for displaying and using in battle.
 * @author Aaron Michael McNulty
 */
public class StatModel {
  /**
   * Health stat name for displaying stats.
   */
  public static final String HP = "HP";
  /**
   * Mana stat name for displaying stats.
   */
  public static final String MANA = "Mana";
  /**
   * Attack stat name for displaying stats.
   */
  public static final String STRENGTH = "Strength";
  /**
   * Defense stat name for displaying stats.
   */
  public static final String DEFENSE = "Defense";
  /**
   * Intellect stat name for displaying stats.
   */
  public static final String INTELLECT = "Intellect";
  /**
   * Spirit stat name for displaying stats.
   */
  public static final String SPIRIT = "Spirit";
  /**
   * Speed stat name for displaying stats.
   */
  public static final String SPEED = "Speed";
  /**
   * Luck stat name for displaying stats.
   */
  public static final String LUCK = "Luck";
  /**
   * Very low stat weight for setting values on level up.
   */
  public static final String VERY_LOW = "Very Low";
  /**
   * Low stat weight for setting values on level up.
   */
  public static final String LOW = "Low";
  /**
   * Average stat weight for setting values on level up.
   */
  public static final String AVERAGE = "Average";
  /**
   * High stat weight for setting values on level up.
   */
  public static final String HIGH = "High";
  /**
   * Very high stat weight for setting values on level up.
   */
  public static final String VERY_HIGH = "Very High";
  /**
   * The max for any stat other than mana or HP.
   */
  public static final int STAT_MAX = 255;
  // Character name
  protected String name;
  // Character level
  protected int level;
  // Current hit points
  protected int health;
  // Hit point max
  protected int healthMax;
  // Current mana points
  protected int mana;
  // Max mana points
  protected int manaMax;
  // Physical strength stat
  protected int strength;
  // Physical attack resistance
  protected int defense;
  // Magic attack stat
  protected int intellect;
  // Magic attack resistance
  protected int spirit;
  // Character speed
  protected int speed;
  // Luck
  protected int luck;
  
  /**
   *      !!################################################!!
   *      !!                                                !!
   *      !!              Getters and Setters               !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
  /**
   * Getter for name.
   * @return The name
   */
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
  /**
   *      !!################################################!!
   *      !!                                                !!
   *      !!           Raw Stat Changing Methods            !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
  /**
   * Increases the health stat of this character. Health cannot be increased more than the healthMax stat.
   * @param amount The amount to increase the health by.
   */
  public void increaseHealth(int amount) {
    health += amount;
    if (health > healthMax) health = healthMax;
  }
  /**
   * Decreases the health of this character. Health cannot be decreased below zero.
   * @param amount The amount to decrease the health by.
   */
  public void decreaseHealth(int amount) {
    health -= amount;
    if (health < 0) health = 0;
  }
  /**
   * Increases the mana of this character. Mana cannot be increased more than the manaMax stat.
   * @param amount The amount to increase the mana by.
   */
  public void increaseMana(int amount) {
    mana += amount;
    if (mana > manaMax) mana = manaMax;
  }
  /**
   * Decreases the mana of this character. Mana cannot be decreased below zero.
   * @param amount The amount to decrease the mana by.
   */
  public void decreaseMana(int amount) {
    mana -= amount;
    if (mana < 0) mana = 0;
  }
  /**
   * Increases the strength stat of this character.
   * @param amount The amount to change strength by.
   */
  public void increaseStrength(int amount) {
    strength += amount;
    if (strength > STAT_MAX) strength = STAT_MAX;
  }
  /**
   * Increase the defense stat of this character.
   * @param amount The amount to change defense by.
   */
  public void increaseDefense(int amount) {
    defense += amount;
    if (defense > STAT_MAX) defense = STAT_MAX;
  }
  /**
   * Increase the intellect stat of this character.
   * @param amount The amount to change intellect by.
   */
  public void increaseIntellect(int amount) {
    intellect += amount;
    if (intellect > STAT_MAX) intellect = STAT_MAX;
  }
  /**
   * Increase the spirit stat of this character.
   * @param amount The amount to change spirit by.
   */
  public void increaseSpirit(int amount) {
    spirit += amount;
    if (spirit > STAT_MAX) spirit = STAT_MAX;
  }
  /**
   * Increase the speed stat of this character.
   * @param amount The amount to change speed by.
   */
  public void increaseSpeed(int amount) {
    speed += amount;
    if (speed > STAT_MAX) speed = STAT_MAX;
  }
  /**
   * Increase the luck stat of this character.
   * @param amount The amount to change luck by.
   */
  public void increaseLuck(int amount) {
    luck += amount;
    if (luck > STAT_MAX) luck = STAT_MAX;
  }

}
