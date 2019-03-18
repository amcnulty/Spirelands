package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.GameMenu;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.entity.fixed.SolidEntity;
import com.monkeystomp.spirelands.level.entity.mob.npc.BasicNPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPC;
import com.monkeystomp.spirelands.level.entity.mob.npc.NPCConfig;
import com.monkeystomp.spirelands.level.lightmap.CustomLightMap;
import com.monkeystomp.spirelands.level.lightmap.LightMapType;
import com.monkeystomp.spirelands.level.wall.Wall;
import com.monkeystomp.spirelands.level.wall.WallConfig;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class HouseLevelUpstairs extends Level {
  /**
   * Id used for save and load game.
   */
  public static final String LEVEL_ID = "HOUSE_LEVEL_UPSTAIRS";
  private final String LEVEL_NAME = "Villager's Home";
  private final String  bitmapPath = "./resources/textures/worlds/houseLevel.png",
                        lightmapPath = "./resources/textures/worlds/houseLevelLightMap.png";
  public static final SpawnCoordinate ENTRANCE = new SpawnCoordinate(206, 53, 3);
  private ArrayList<Entity> belowEntities = new ArrayList<>();
  private NPCConfig npcConfig = new NPCConfig();
  
  public HouseLevelUpstairs(SpawnCoordinate spawnCoordinate) {
    this.spawnCoordinate = spawnCoordinate;
    this.levelName = LEVEL_NAME;
    this.levelId = LEVEL_ID;
    loadLevel(bitmapPath);
  }
  
  @Override
  protected void setLevelName(GameMenu gameMenu) {
    gameMenu.setLevelName(LEVEL_NAME);
  }
  
  @Override
  protected void addPortals() {
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(48, 225, 80, 220);
    portals.add(new Portal(bounds, HouseLevel.FIRST_FLOOR_STAIRS, HouseLevel.LEVEL_ID));
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addNpcs() {
    NPC npc;
    
    npcConfig.setX(96);
    npcConfig.setY(185);
    npcConfig.setMessages(new String[] {"Luke McLovin: Hey man what the fuck are you doin' in my house? Like, you totally are freaking me out right now.", "I really need a sandwich to chill out right now...do you know anyone who can make me a sandwich?", "I heard there was a guy around here who used to be part of the four horsemen. They say he makes a good sandwich.", "Have you heard of Skip-and-go-naked? Well, if you haven't then you are missing out. They are pretty much the best drink ever, but you have to be careful.", "If you have to many Skip-and-go-nakeds in one night you might end up throwing it out on the stairs in the middle of the night while your neighbors are trying to sleep and you and your friends are partying like crazy people.", "Get the fuck out of my house."});
    npc = new BasicNPC(npcConfig, BasicNPC.MALE_BLONDE);
    solidEntities.add(npc);
    npc.initLevel(this);
  }
  
  @Override
  protected void addSolidEntities() {
    SolidEntity entity = new SolidEntity(220, 48);
    entity.setSprite(Sprite.STAIRS_WOOD_DOWN_RIGHT);
    belowEntities.add(entity);
    
    Wall wall = new Wall();
    WallConfig config = new WallConfig();
    config.setStartingX(40);
    config.setStartingY(3);
    config.setInterior(true);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(13);
    config.setWindows(new int[]{3, 6, 9});
    wall.createHorizontalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(32);
    config.setStartingY(3);
    config.setHasWallFront(true);
    config.setInterior(false);
    config.setLength(12);
    wall.createVerticalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(40);
    config.setStartingY(195);
    config.setInterior(false);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(13);
    config.setWindows(new int[]{1, 6, 11});
    wall.createHorizontalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(248);
    config.setStartingY(3);
    config.setHasWallFront(true);
    config.setInterior(false);
    config.setLength(12);
    wall.createVerticalWall(config);
    
    solidEntities.add(wall);
    
    // Interior walls
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(40);
    config.setStartingY(112);
    config.setInterior(true);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(5);
    wall.createHorizontalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(120);
    config.setStartingY(112);
    config.setInterior(true);
    wall.createWallFront(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(168);
    config.setStartingY(112);
    config.setInterior(true);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(5);
    wall.createHorizontalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(160);
    config.setStartingY(112);
    config.setInterior(true);
    wall.createWallFront(config);
    
    solidEntities.add(wall);
  }
    
  @Override
  protected void finalLevelSetup() {
    lightMap.enableLightMap(LightMapType.CUSTOM);
    lightMap.setCustomLightMap(new CustomLightMap(new Sprite(lightmapPath)));
  }
  
  @Override
  protected void levelUpdate() {
    if (getDialogOpen()) {
      dialogBox.update();
    }
  }
  
  @Override
  protected void renderUnderSolidEntities(Screen screen, GL2 gl) {
    for (int i = 0; i < belowEntities.size(); i++) {
      belowEntities.get(i).render(screen, gl);
    }
  }
  
  @Override
  protected void renderOverSolidEntities(Screen screen, GL2 gl) {
  }

  @Override
  protected void levelRenderOverLightMap(Screen screen, GL2 gl) {
    if (getDialogOpen()) dialogBox.render(screen, gl);  
  }

}
