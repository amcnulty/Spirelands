package com.monkeystomp.spirelands.gui.gamemenu.events;

import com.monkeystomp.spirelands.gui.gamemenu.components.AbilitySlot;

/**
 * Used to hold details from the ability slot click event.
 * @author Aaron Michael McNulty
 */
public class AbilitySlotClickEvent {
  private final int level;
  private final String type;
  private final AbilitySlot slot;
  
  public AbilitySlotClickEvent(int level, String type, AbilitySlot slot) {
    this.level = level;
    this.type = type;
    this.slot = slot;
  }

  public int getLevel() {
    return level;
  }

  public String getType() {
    return type;
  }

  public AbilitySlot getSlot() {
    return slot;
  }

}
