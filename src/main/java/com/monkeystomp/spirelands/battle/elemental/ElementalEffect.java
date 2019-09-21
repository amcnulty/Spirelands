package com.monkeystomp.spirelands.battle.elemental;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ElementalEffect {

  private final String element;
  private final int percentage;
  
  public ElementalEffect(String element, int percent) {
    this.element = element;
    this.percentage = percent;
  }

  public String getElement() {
    return element;
  }

  public int getPercentage() {
    return percentage;
  }
  
}
