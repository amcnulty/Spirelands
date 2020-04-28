package com.monkeystomp.spirelands.gui.gamemenu.components;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class AbilitySlot {
  
  private final String type;
  private boolean unlocked = true;
  private int pointsToUnlock;
  
  public AbilitySlot(String type) {
    this.type = type;
  }
  
  public AbilitySlot(String type, int pointsToUnlock) {
    this.type = type;
    this.unlocked = false;
    this.pointsToUnlock = pointsToUnlock;
  }
  
  public void update() {
    
  }
  
  public void render(Screen screen, GL2 gl) {
    
  }

}
