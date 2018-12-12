package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.entity.fixed.SolidEntity;
import com.monkeystomp.spirelands.level.lightmap.CustomLightMap;
import com.monkeystomp.spirelands.level.lightmap.LightMapType;
import com.monkeystomp.spirelands.level.scene.Scene;
import com.monkeystomp.spirelands.level.wall.Wall;
import com.monkeystomp.spirelands.level.wall.WallConfig;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class HouseLevel extends Level {
  
  private HashMap<String, Scene> sceneMap = new HashMap<>();
  private Scene currentScene;
  private static final String FIRST_FLOOR_BITMAP = "./resources/textures/worlds/houseLevel.png",
                              UPSTAIRS_BITMAP = "./resources/textures/worlds/houseLevel_upstairs.png",
                              FIRST_FLOOR_LIGHTMAP = "./resources/textures/worlds/houseLevelLightMap.png",
                              UPSTAIRS_LIGHTMAP = "./resources/textures/worlds/houseLevelLightMap.png";
  public static SpawnCoordinate FIRST_FLOOR_ENTRANCE = new SpawnCoordinate(148, 208, 0);
  public static SpawnCoordinate UPSTAIRS_ENTRANCE = new SpawnCoordinate(148, 75, 3);
  private static final String FIRST_FLOOR_KEY = "First_Floor",
                              UPSTAIRS_KEY = "Upstairs";

  public HouseLevel(SpawnCoordinate spawnCoordinate) {
    setFirstFloorScene();
    setUpstairsScene();
    currentScene = sceneMap.get(HouseLevel.FIRST_FLOOR_KEY);
    this.spawnCoordinate = spawnCoordinate;
    loadLevel(FIRST_FLOOR_BITMAP);
  }
  
  private void setFirstFloorScene() {
    // Create the scene
    Scene firstFloor = new Scene(FIRST_FLOOR_BITMAP);
    
    // Add portals
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(221, 161, 230, 111);
    
    firstFloor.addPortal(new Portal(bounds, TestLevel.houseExit, "TEST_LEVEL"));
    
    bounds = new Bounds();
    bounds.setQuadBounds(64, 239, 70, 225);
    
    firstFloor.addPortal(new Portal(bounds, HouseLevel.UPSTAIRS_KEY, HouseLevel.UPSTAIRS_ENTRANCE));
    
    
    for (int i = 0; i < firstFloor.getPortals().size(); i++) {
      firstFloor.getPortals().get(i).initLevel(this);
    }
    
    // Add solid entities
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
    
    firstFloor.addSolidEntity(entity);
    
    Wall wall = new Wall();
    WallConfig config = new WallConfig();
    config.setStartingX(40);
    config.setStartingY(11);
    config.setInterior(true);
    config.setLength(13);
    config.setWindows(new int[]{3, 6, 9});
    wall.createHorizontalWall(config);
    
    firstFloor.addSolidEntity(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(32);
    config.setStartingY(19);
    config.setHasWallFront(true);
    config.setInterior(false);
    config.setLength(11);
    wall.createVerticalWall(config);
    
    firstFloor.addSolidEntity(wall);
    
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
    
    firstFloor.addSolidEntity(wall);
    
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
    
    firstFloor.addSolidEntity(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(248);
    config.setStartingY(19);
    config.setHasWallFront(true);
    config.setInterior(false);
    config.setLength(11);
    wall.createVerticalWall(config);
    
    firstFloor.addSolidEntity(wall);
    
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
    
    firstFloor.addSolidEntity(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(120);
    config.setStartingY(99);
    config.setInterior(false);
    config.setLength(6);
    wall.createVerticalWall(config);
    
    firstFloor.addSolidEntity(wall);
    
    // Setup light map
    firstFloor.setLightMap(() -> {
      lightMap.enableLightMap(LightMapType.CUSTOM);
      lightMap.setCustomLightMap(new CustomLightMap(new Sprite(HouseLevel.FIRST_FLOOR_LIGHTMAP)));
    });
    
    // Add scene to scene map
    sceneMap.put(FIRST_FLOOR_KEY, firstFloor);
  }
  
  public void setUpstairsScene() {
    // Create the scene
    Scene upstairs = new Scene(UPSTAIRS_BITMAP);
    
    // Add portals
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(48, 225, 80, 220);
    
    upstairs.addPortal(new Portal(bounds, HouseLevel.FIRST_FLOOR_KEY, HouseLevel.FIRST_FLOOR_ENTRANCE));
    
    for (int i = 0; i < upstairs.getPortals().size(); i++) {
      upstairs.getPortals().get(i).initLevel(this);
    }
    
    // Add solid entities
    Wall wall = new Wall();
    WallConfig config = new WallConfig();
    config.setStartingX(40);
    config.setStartingY(99);
    config.setInterior(true);
    config.setLeftCorner(false);
    config.setRightCorner(false);
    config.setLength(3);
    wall.createHorizontalWall(config);
    
    upstairs.addSolidEntity(wall);
    
    // Setup light map
    upstairs.setLightMap(() -> {
      lightMap.enableLightMap(LightMapType.CUSTOM);
      lightMap.setCustomLightMap(new CustomLightMap(new Sprite(HouseLevel.UPSTAIRS_LIGHTMAP)));
    });
    
    // Add scene to scene map
    sceneMap.put(UPSTAIRS_KEY, upstairs);
  }
  
  @Override
  protected void changeScenes(String sceneKey) {
    currentScene = sceneMap.get(sceneKey);
    loadLevel(currentScene.getBitmapPath());
  }
  
  @Override
  protected void addPortals() {
    portals = (ArrayList<Portal>)currentScene.getPortals().clone();
  }
  
  @Override
  protected void startMusic() {
    if (!music.isPlaying()) music.play(Music.NEUTRAL_IN_A_HOUSE);
  }
  
  @Override
  protected void addSolidEntities() {
    solidEntities = (ArrayList<Entity>)currentScene.getSolidEntities().clone();
  }
  
  @Override
  protected void finalLevelSetup() {
    currentScene.enableLightMap();
  }
  
  @Override
  protected void levelUpdate() {
    
  }
  
  @Override
  protected void renderOverPlayer(Screen screen, GL2 gl) {
    
  }
  
  @Override
  protected void renderUnderPlayer(Screen screen, GL2 gl) {
  }

  @Override
  protected void levelRenderOverLightMap(Screen screen, GL2 gl) {
    
  }
}
