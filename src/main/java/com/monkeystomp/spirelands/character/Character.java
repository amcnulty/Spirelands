package com.monkeystomp.spirelands.character;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.inventory.WeaponItem;

/**
 * A Character defines the name and stats of a group member that can be used in the player's party.
 * @author Aaron Michael McNulty
 */
public class Character {
  /**
   * Attack stat name for displaying stats.
   */
  public static final String ATTACK = "Attack";
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
  // Character name
  private String name;
  // Character level
  private int level;
  // Experience points
  private int experience;
  // Current hit points
  private int health;
  // Hit point max
  private int healthMax;
  // Current mana points
  private int mana;
  // Max mana points
  private int manaMax;
  // Physical attack stat
  private int strength;
  // Physical attack resistance
  private int defense;
  // Magic attack stat
  private int intellect;
  // Magic attack resistance
  private int spirit;
  // Character speed
  private int speed;
  // Luck
  private int luck;
  // Thumbnail image for character
  private Sprite thumbnail;
  // The currently equipped weapon
  private WeaponItem equippedWeapon;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getHealthMax() {
    return healthMax;
  }

  public void setHealthMax(int healthMax) {
    this.healthMax = healthMax;
  }

  public int getMana() {
    return mana;
  }

  public void setMana(int mana) {
    this.mana = mana;
  }

  public int getManaMax() {
    return manaMax;
  }

  public void setManaMax(int manaMax) {
    this.manaMax = manaMax;
  }

  public int getStrength() {
    return strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public int getIntellect() {
    return intellect;
  }

  public void setIntellect(int intellect) {
    this.intellect = intellect;
  }

  public int getSpirit() {
    return spirit;
  }

  public void setSpirit(int spirit) {
    this.spirit = spirit;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getLuck() {
    return luck;
  }

  public void setLuck(int luck) {
    this.luck = luck;
  }

  public Sprite getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(Sprite thumbnail) {
    this.thumbnail = thumbnail;
  }

  public WeaponItem getEquippedWeapon() {
    return equippedWeapon;
  }

  public void setEquippedWeapon(WeaponItem equippedWeapon) {
    this.equippedWeapon = equippedWeapon;
  }
  
  public void removeEquippedWeapon() {
    if (equippedWeapon != null) {
      equippedWeapon.addToInventory();
      equippedWeapon = null;
    }
  }
  
  /**
   *      !!################################################!!
   *      !!                                                !!
   *      !!         Combined Stat Getting Methods          !!
   *      !!                                                !!
   *      !!################################################!!
   */
  
  /**
   * Gets the combined attack stat for this character which is a figure based on the strength stat and the attack power of the current equipped weapon.
   * @return The combined attack stat for this character.
   */
  public int getCombinedAttack() {
    if (equippedWeapon != null) {
      return equippedWeapon.getAttackPower() + strength;
    }
    else return strength;
  }
  
  public int getCombinedDefense() {
    return defense;
  }
  
  public int getCombinedIntellect() {
    if (equippedWeapon != null) {
      return equippedWeapon.getMagicPower() + intellect;
    }
    else return intellect;
  }
  
  public int getCombinedSpirit() {
    return spirit;
  }
  
  public int getCombinedSpeed() {
    return speed;
  }
  
  public int getCombinedLuck() {
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
    
}
