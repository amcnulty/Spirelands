package com.monkeystomp.spirelands.character;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Character {
  // Character name
  private String name;
  // Character level
  private int level;
  // Hit points
  private int health;
  // Mana points
  private int mana;
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

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getMana() {
    return mana;
  }

  public void setMana(int mana) {
    this.mana = mana;
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
  
  
}
