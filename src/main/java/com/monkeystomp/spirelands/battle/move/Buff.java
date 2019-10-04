package com.monkeystomp.spirelands.battle.move;

/**
 * A buff is a set of stat modifiers and a time interval in which they are effective as a buff in battle.
 * @author Aaron Michael McNulty
 */
public class Buff implements Cloneable {
  
  private boolean active = false;
  private double  attackModifier,
                  defenseModifier,
                  intellectModifier,
                  spiritModifier;
  private int buffTime, timeRemaining;
  
  public void setAttackBuff(double amount) {
    this.attackModifier = amount;
  }
  
  public void setDefenseBuff(double amount) {
    this.defenseModifier = amount;
  }
  
  public void setIntellectBuff(double amount) {
    this.intellectModifier = amount;
  }
  
  public void setSpiritBuff(double amount) {
    this.spiritModifier = amount;
  }

  public double getAttackModifier() {
    return attackModifier;
  }

  public double getDefenseModifier() {
    return defenseModifier;
  }

  public double getIntellectModifier() {
    return intellectModifier;
  }

  public double getSpiritModifier() {
    return spiritModifier;
  }
  
  public void setBuffTime(int time) {
    this.buffTime = time * 60;
    this.timeRemaining = this.buffTime;
    active = true;
  }
  
  public void reduceTime() {
    timeRemaining--;
    if (timeRemaining == 0) active = false;
  }
  
  public int getBuffTime() {
    return buffTime;
  }

  public int getTimeRemaining() {
    return timeRemaining;
  }

  public boolean isActive() {
    return active;
  }
  
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}
