package com.monkeystomp.spirelands.battle.move;

import com.monkeystomp.spirelands.battle.elemental.ElementalEffect;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  private final ArrayList<ElementalEffect> elementalEffects = new ArrayList<>();
  
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
  
  public void addElementalEffect(ElementalEffect elEffect) {
    elementalEffects.add(elEffect);
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
  /**
   * Gets the modifier amount for the given element. If no element is found will return 0.
   * @param element Element type to find modifier for.
   * @return Modifier value.
   */
  public double getElementalModifier(String element) {
    List<ElementalEffect> el = elementalEffects.stream()
            .filter(effect -> effect.getElement().equals(element)).collect(Collectors.toList());
    if (el.size() > 0) {
      return (el.get(0).getPercentage() - 100) / 100.0;
    }
    return 0;
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
