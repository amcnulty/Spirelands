package com.monkeystomp.spirelands.level;

import com.monkeyStomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeyStomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.entity.mob.GuardPlayer;
import com.monkeystomp.spirelands.graphics.Screen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TestLevel extends Level {

  private final String bitmapPath = "./resources/textures/worlds/testLevel.png";
  private ArrayList<Portal> portals = new ArrayList<>();
  private SpawnCoordinate spawnCoordinate;
  private int time = 0,
              shadowLevel = 0;
  
  public static SpawnCoordinate westEntrance = new SpawnCoordinate(48, 256, 1);
  
  public TestLevel(SpawnCoordinate coordinate) {
    this.spawnCoordinate = coordinate;
  }
  
  @Override
  protected void loadBitmap() {
    try {
      BufferedImage image = ImageIO.read(new File(bitmapPath));
      setLevelTileWidth(image.getWidth());
      setLevelTileHeight(image.getHeight());
      bitmap = new int[getLevelTileWidth() * getLevelTileHeight()];
      image.getRGB(0, 0, getLevelTileWidth(), getLevelTileHeight(), bitmap, 0, getLevelTileWidth());
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could not upload file at path: " + bitmapPath);
    }
  }
  
  @Override
  protected void generateLevel() {
  //  player = new DarkSuitPlayer(128, 128);
    // player = new GoblinPlayer(64, 1050);
    player = new GuardPlayer(spawnCoordinate.getX(), spawnCoordinate.getY());
    player.setDirection(spawnCoordinate.getDirection());
    player.initLevel(this);
    addPortals();
  }
  
  private void addPortals() {
    portals.add(new Portal(0, 224, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    portals.add(new Portal(0, 240, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    portals.add(new Portal(0, 256, SpawnLevel.eastEntrance, "SPAWN_LEVEL"));
    
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  private void checkForPortals() {
    for (int i = 0; i < portals.size(); i++) {
      if (portals.get(i).portalHere(player.getX(), player.getY())) portals.get(i).enterPortal();
    }
  }
  
  @Override
  public void levelUpdate() {
    checkForPortals();
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
  public void levelRender(Screen screen) {
    screen.fillLightMap(0x121212, shadowLevel);
//    font.renderText(200, 200, "This is a test!", screen);
//    font.renderText(200, 300, "Demo version no. #1234567890", screen);
  }
}