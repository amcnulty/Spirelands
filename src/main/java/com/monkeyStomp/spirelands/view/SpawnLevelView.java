package com.monkeystomp.spirelands.view;

import com.monkeyStomp.spirelands.level.Level;
import com.monkeyStomp.spirelands.level.SpawnLevel;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevelView extends GameView {
  
  private int ticks = 0,
              renderColor = 0x0079CC;
  private Level level = new SpawnLevel("./resources/textures/worlds/beach.png");

  public SpawnLevelView(ViewManager viewManager) {
    super(viewManager);
  }
  
  @Override
  public void update() {
//    if (ticks == 60 * 4) {
//      viewManager.setCurrentView(new TitleScreen(viewManager));
//    }
//    ticks++;
//    renderColor++;
//    if (ticks % 20 == 0);
    level.update();
  }

  @Override
  public void render(Screen screen) {
    level.render(screen);
  }
}