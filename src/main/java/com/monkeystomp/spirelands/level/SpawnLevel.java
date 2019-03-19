package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.gui.gamemenu.GameMenu;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.entity.mob.npc.BasicNPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPCConfig;
import com.monkeystomp.spirelands.level.lightmap.LightMapType;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevel extends Level {

  /**
   * Id used for save and load game.
   */
  public static final String LEVEL_ID = "glRtFCx0g6";
  private final String LEVEL_NAME = "Beach Front";
  private final String BITMAP_PATH = "./resources/textures/worlds/beach.png";
  private int time = 0;
  private String[] welcomeText = {"This is the first message, it will fit on one line.", "And here is the second message!! Hope this works well because I know this one will have to go down to the second line.", "Hi Steph! How are you doing? I think the laundry is almost done. I've been programing this whole time working on adding dialog boxes to the game. Love You! <3"};
  public static final SpawnCoordinate eastEntrance = new SpawnCoordinate(608, 272, 3);
  
  public SpawnLevel(SpawnCoordinate coordinate) {
    this.spawnCoordinate = coordinate;
    this.levelName = LEVEL_NAME;
    this.levelId = LEVEL_ID;
    loadLevel(BITMAP_PATH);
  }
  
  @Override
  protected void setLevelName(GameMenu gameMenu) {
    gameMenu.setLevelName(LEVEL_NAME);
  }
  
  @Override
  protected void addPortals() {
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(239, 640, 273, 624);
    portals.add(new Portal(bounds, TestLevel.westEntrance, TestLevel.LEVEL_ID));
    
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addNpcs() {
    NPCConfig femalePurple = new NPCConfig();
    femalePurple.setX(512);
    femalePurple.setY(170);
    femalePurple.setMessages(new String[] {"Hi! Are you having fun today?", "I'm having a great day! Want to know why?", "Because I'm thinkin 'bout my ah-mazing boyfriend...that's right. He's soooo great."});
    NPC npc = new BasicNPC(femalePurple, BasicNPC.FEMALE_PURPLEHAIR);
    solidEntities.add(npc);
    npc.initLevel(this);
    
  }
  
  @Override
  protected void startMusic() {
    Music.getMusicPlayer().play(Music.TOWN_JINGLE);
  }

  @Override
  protected void finalLevelSetup() {
    lightMap.enableLightMap(LightMapType.BLENDED);
    
// Example of how to open a dialog box.
//    this.setDialogOpen(true);
//    dialogBox.openDialog(welcomeText);
  }
  
  @Override
  protected void levelUpdate() {
    if (getDialogOpen()) {
      dialogBox.update();
    }
    else {
      // 7 pm
      if (time == 420) shadowLevel = .1f;
      // 7:30 pm
      else if (time == 450) shadowLevel = .2f;
      // 8 pm
      else if (time == 480) shadowLevel = .3f;
      // 8:15 pm
      else if (time == 495) shadowLevel = .4f;
      // 8:30 pm
      else if (time == 510) shadowLevel = .5f;
      // 8:45 pm
      else if (time == 525) shadowLevel = .6f;
      // 9 pm
      else if (time == 540) shadowLevel = .7f;
      // 9:30 pm
      else if (time == 570) shadowLevel = .8f;
      // 10 pm
      else if (time == 700) shadowLevel = .9f;
      // 4 am
      else if (time == 1060) shadowLevel = .8f;
      // 4:30 am
      else if (time == 1090) shadowLevel = .7f;
      // 5 am
      else if (time == 1120) shadowLevel = .6f;
      // 5:15 am
      else if (time == 1135) shadowLevel = .5f;
      // 5:30 am
      else if (time == 1150) shadowLevel = .4f;
      // 5:45 am
      else if (time == 1165) shadowLevel = .3f;
      // 6 am
      else if (time == 1180) shadowLevel = .2f;
      // 6:30 am
      else if (time == 1210) shadowLevel = .1f;
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
  protected void renderOverSolidEntities(Screen screen, GL2 gl) {
  }
  
  @Override
  protected void renderUnderSolidEntities(Screen screen, GL2 gl) {
  }

  @Override
  protected void levelRenderOverLightMap(Screen screen, GL2 gl) {
    if (getDialogOpen()) dialogBox.render(screen, gl);
  }
}
