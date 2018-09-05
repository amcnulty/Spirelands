package com.monkeystomp.spirelands.level;

import com.monkeystomp.spirelands.level.entity.mob.GoblinPlayer;
import com.monkeystomp.spirelands.level.entity.mob.DarkSuitPlayer;
import com.monkeystomp.spirelands.level.entity.mob.GuardPlayer;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends Level {
  
  private Sprite letterHeight = new Sprite(6, 24, 0xFFFFFF);

  private int time = 0,
              shadowLevel = 0;
  
  public SpawnLevel(String path) {
    super(path);
  }
  
  @Override
  protected void generateLevel() {
  //  player = new DarkSuitPlayer(128, 128);
    // player = new GoblinPlayer(64, 1050);
    player = new GuardPlayer(64, 64);
    player.initLevel(this);
  }
  
  @Override
  public void update() {
    super.update();
    // 7 pm
    if (time == 420) shadowLevel = 1;
    // 7:30 pm
    else if (time == 450) shadowLevel = 2;
    // 8 pm
    else if (time == 480) shadowLevel = 3;
    // 8:15 pm
    else if (time == 495) shadowLevel = 4;
    // 8:30 pm
    else if (time == 510) shadowLevel = 5;
    // 8:45 pm
    else if (time == 525) shadowLevel = 6;
    // 9 pm
    else if (time == 540) shadowLevel = 7;
    // 9:30 pm
    else if (time == 570) shadowLevel = 8;
    // 10 pm
    else if (time == 700) shadowLevel = 9;
    // 4 am
    else if (time == 1060) shadowLevel = 8;
    // 4:30 am
    else if (time == 1090) shadowLevel = 7;
    // 5 am
    else if (time == 1120) shadowLevel = 6;
    // 5:15 am
    else if (time == 1135) shadowLevel = 5;
    // 5:30 am
    else if (time == 1150) shadowLevel = 4;
    // 5:45 am
    else if (time == 1165) shadowLevel = 3;
    // 6 am
    else if (time == 1180) shadowLevel = 2;
    // 6:30 am
    else if (time == 1210) shadowLevel = 1;
    // 7 am
    else if (time == 1240) shadowLevel = 0;

    if (time == 1440) time = 0;
    else time++;
  }
  
  @Override
  public void render(Screen screen) {
    super.render(screen);
    screen.fillLightMap(0x121212, shadowLevel);
//    font.renderText(200, 200, "This is a test!", screen);
//    font.renderText(200, 300, "Demo version no. #1234567890", screen);
  }
}