package com.monkeyStomp.spirelands.level;

import com.monkeyStomp.spirelands.level.entity.mob.Player;
import com.monkeystomp.spirelands.graphics.Screen;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends Level {
  
  public SpawnLevel(String path) {
    super(path);
  }
  
  @Override
  protected void generateLevel() {
    player = new Player(64, 1050);
    player.initLevel(this);
  }
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void render(Screen screen) {
    super.render(screen);
  }
}