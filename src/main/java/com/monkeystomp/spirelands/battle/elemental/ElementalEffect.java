package com.monkeystomp.spirelands.battle.elemental;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ElementalEffect {

  private final String element;
  private final double percentage;
  
  public ElementalEffect(String element, double percent) {
    this.element = element;
    this.percentage = percent;
  }

  public String getElement() {
    return element;
  }

  public double getPercentage() {
    return percentage;
  }
  
}
