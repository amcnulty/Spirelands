package com.monkeystomp.spirelands.level;

import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.level.entity.mob.npc.BasicNPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPCConfig;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends Level {

  private final String BITMAP_PATH = "./resources/textures/worlds/beach.png";
  private int time = 0,
              shadowLevel = 0;
  private String[] welcomeText = {"This is the first message, it will fit on one line.", "And here is the second message!! Hope this works well because I know this one will have to go down to the second line.", "Hi Steph! How are you doing? I think the laundry is almost done. I've been programing this whole time working on adding dialog boxes to the game. Love You! <3"};
  public static final SpawnCoordinate eastEntrance = new SpawnCoordinate(608, 272, 3);
  
  public SpawnLevel(SpawnCoordinate coordinate) {
    this.spawnCoordinate = coordinate;
    loadLevel(BITMAP_PATH);
  }
  
  @Override
  protected void addPortals() {
    portals.add(new Portal(624, 240, TestLevel.westEntrance, "TEST_LEVEL"));
    portals.add(new Portal(624, 256, TestLevel.westEntrance, "TEST_LEVEL"));
    portals.add(new Portal(624, 272, TestLevel.westEntrance, "TEST_LEVEL"));
    
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addNpcs() {
    NPCConfig femalePurple = new NPCConfig();
    femalePurple.setX(512);
    femalePurple.setY(170);
    femalePurple.setMessages(new String[] {"Hi! Are you having fun today?", "I'm having a great day! Want to know why?", "Because I'm thinkin 'bout my ah-mazing boyfriend...that's right. He's soooo great. He's not lonely like that elf back there because I always come back after my adventures."});
    NPC npc = new BasicNPC(femalePurple, BasicNPC.FEMALE_PURPLEHAIR);
    solidEntities.add(npc);
    npc.initLevel(this);
    
  }
  
  @Override
  protected void startMusic() {
    music.play(Music.TOWN_JINGLE);
  }

  @Override
  protected void finalLevelSetup() {
    
// Example of how to open a dialog box.
//    dialogOpen = true;
//    dialogBox.openDialog(welcomeText);
  }
  
  @Override
  protected void levelUpdate() {
    if (getDialogOpen()) {
      dialogBox.update();
    }
    else {
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
      //  super.getLevelChanger().change(LevelFactory.createLevel("TEST_LEVEL", TestLevel.westEntrance));
      }
      else time += 2;
    }
  }
  
  @Override
  protected void renderUnderPlayer(Screen screen) {
    screen.setLightMap(0x121212, shadowLevel);
//    font.renderText(200, 200, "This is a test!", screen);
//    font.renderText(200, 300, "Demo version no. #1234567890", screen);
  }

  @Override
  protected void levelRenderOverLightMap(Screen screen) {
    if (getDialogOpen()) dialogBox.render(screen);
  }
}