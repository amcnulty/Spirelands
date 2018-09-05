package com.monkeystomp.spirelands.level;

import com.monkeystomp.spirelands.level.entity.mob.GoblinPlayer;
import com.monkeystomp.spirelands.level.entity.mob.DarkSuitPlayer;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends Level {
  
  private Sprite letterHeight = new Sprite(6, 24, 0xFFFFFF);
  
  public SpawnLevel(String path) {
    super(path);
  }
  
  @Override
  protected void generateLevel() {
   player = new DarkSuitPlayer(128, 128);
    // player = new GoblinPlayer(64, 1050);
    player.initLevel(this);
  }
  
  @Override
  public void update() {
    super.update();
  }
  
  @Override
  public void render(Screen screen) {
    super.render(screen);
//    font.renderText(200, 200, "This is a test!", screen);
//    font.renderText(200, 300, "Demo version no. #1234567890", screen);
  }
}