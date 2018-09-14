package com.monkeystomp.spirelands.level;

import com.monkeyStomp.spirelands.level.entity.mob.npc.BasicNPC;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeyStomp.spirelands.level.entity.mob.npc.NPC;
import com.monkeyStomp.spirelands.level.entity.mob.npc.NPCConfig;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TestLevel extends Level {

  private final String BITMAP_PATH = "./resources/textures/worlds/testLevel.png";
  private int time = 0,
              shadowLevel = 0;
  public static SpawnCoordinate westEntrance = new SpawnCoordinate(48, 256, 1);
  
  private NPCConfig lonelyMaleElf = new NPCConfig();
  
  
  public TestLevel(SpawnCoordinate coordinate) {
    this.spawnCoordinate = coordinate;
    loadLevel(BITMAP_PATH);
  }
  
  @Override
  protected void addPortals() {
    portals.add(new Portal(0, 224, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    portals.add(new Portal(0, 240, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    portals.add(new Portal(0, 256, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addNpcs() {
    NPC npc;
    // Lonely Male Elf setup
    lonelyMaleElf.setX(64);
    lonelyMaleElf.setY(64);
    lonelyMaleElf.setMessages(new String[] {"Hi how are you? I'm all alone up here.. I haven't seen anyone around for quite some time.", "Not many people come this way, so I'm guessing that you're a traveler!", "My wife and I used to be travelers until one day she left on what she said was an important adventure. I haven't seen her since she left several months ago. I'm lonely without her."});
    npc = new BasicNPC(lonelyMaleElf, BasicNPC.MALE_YELLOWJACKET);
    npcs.add(npc);
    solidEntities.add(npc);
    npc.initLevel(this);
  }
  
  @Override
  protected void finalLevelSetup() {
  }

  @Override
  protected void levelUpdate() {
    if (getDialogOpen()) {
      dialogBox.update();
    }
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

    if (time == 1440) {
      time = 0;
//      IChanger.change(new SpawnLevel());
    }
    else time++;
  }
  
  @Override
  protected void renderUnderPlayer(Screen screen) {
    screen.fillLightMap(0x121212, shadowLevel);
//    font.renderText(200, 200, "This is a test!", screen);
//    font.renderText(200, 300, "Demo version no. #1234567890", screen);
  }

  @Override
  protected void levelRenderOverLightMap(Screen screen) {
    if (getDialogOpen()) dialogBox.render(screen);
  }
}