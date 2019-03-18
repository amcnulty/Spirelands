package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.gui.gamemenu.GameMenu;
import com.monkeystomp.spirelands.level.location.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.Entity;
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
public class HouseLevel extends Level {
  /**
   * Id used for save and load game.
   */
  public static final String LEVEL_ID = "HOUSE_LEVEL";
  protected final String LEVEL_NAME = "Villager's Home";
  private final String  bitmapPath = "./resources/textures/worlds/houseLevel.png",
                        lightmapPath = "./resources/textures/worlds/houseLevelLightMap.png";
  public static final SpawnCoordinate ENTRANCE = new SpawnCoordinate(148, 208, 0),
                                      FIRST_FLOOR_STAIRS = new SpawnCoordinate(232, 125, 2);
  private final ArrayList<Entity> belowEntities = new ArrayList<>();
  private final NPCConfig npcConfig = new NPCConfig();

  public HouseLevel(SpawnCoordinate spawnCoordinate) {
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
    bounds.setQuadBounds(221, 161, 230, 111);
    portals.add(new Portal(bounds, TestLevel.houseExit, TestLevel.LEVEL_ID));
    bounds = new Bounds();
    bounds.setQuadBounds(64, 239, 70, 225);
    portals.add(new Portal(bounds, HouseLevelUpstairs.ENTRANCE, HouseLevelUpstairs.LEVEL_ID));
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void addNpcs() {
    NPC npc;
    // Steph npc
    npcConfig.setX(55);
    npcConfig.setY(64);
    npcConfig.setMessages(new String[] {"Hi I'm Steph nice to meet you!!", "Welcome to Aaron's awesome video game. This house is something of an improvement over the last game.", "I think I saw Aaron upstairs if you want to talk to him."});
    npc = new BasicNPC(npcConfig, BasicNPC.FEMALE_ELF);
    solidEntities.add(npc);
    npc.initLevel(this);
  }
  
  @Override
  protected void addSolidEntities() {
    SolidEntity entity = new SolidEntity(216, 32);
    entity.setSprite(Sprite.STAIRS_WOOD_TURN_LEFT);
    Bounds quad = new Bounds();
    quad.setQuadBounds(
      entity.getY(),
      entity.getX(),
      entity.getY() + Sprite.STAIRS_WOOD_TURN_LEFT.getHeight(),
      entity.getX() - 4
    );
    entity.addBounds(quad);
    entity.initLevel(this);
    
    solidEntities.add(entity);
    
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
    config.setLength(5);
    config.setWindows(new int[]{2});
    wall.createHorizontalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(168);
    config.setStartingY(195);
    config.setInterior(false);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(5);
    config.setWindows(new int[]{2});
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
    
    // interior walls
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(40);
    config.setStartingY(99);
    config.setInterior(true);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(3);
    wall.createHorizontalWall(config);
    
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(120);
    config.setStartingY(99);
    config.setInterior(false);
    config.setLength(6);
    wall.createVerticalWall(config);
    
    solidEntities.add(wall);
  }
  
  @Override
  protected void startMusic() {
    Music.getMusicPlayer().play(Music.NEUTRAL_IN_A_HOUSE);
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
