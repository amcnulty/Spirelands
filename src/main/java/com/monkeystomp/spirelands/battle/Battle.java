package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.level.location.Location;
import com.monkeystomp.spirelands.level.util.LevelFactory;
import com.monkeystomp.spirelands.level.util.LocationManager;
import com.monkeystomp.spirelands.view.LevelView;
import com.monkeystomp.spirelands.view.ViewManager;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Battle {
  
  private int tick = 0;
  
  private void endBattle() {
    System.out.println("Ending battle.");
    Location lastLocation = LocationManager.getLocationManager().getCurrentLocation();
    ViewManager.getViewManager().changeView(new LevelView(LevelFactory.createLevel(lastLocation.getLevelId(), lastLocation.getCoordinate())));
  }
  
  public void update() {
    if (tick++ == 120) endBattle();
  }
  
  public void render(Screen screen, GL2 gl) {
    
  }

}
