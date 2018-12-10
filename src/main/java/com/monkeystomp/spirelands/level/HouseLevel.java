package com.monkeystomp.spirelands.level;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.audio.Music;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.coordinate.SpawnCoordinate;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.entity.fixed.Portal;
import com.monkeystomp.spirelands.level.entity.fixed.SolidEntity;
import com.monkeystomp.spirelands.level.lightmap.CustomLightMap;
import com.monkeystomp.spirelands.level.lightmap.LightMapType;
import com.monkeystomp.spirelands.level.wall.Wall;
import com.monkeystomp.spirelands.level.wall.WallConfig;

/**
 *
 * @author Aaron Michael McNulty
 */
public class HouseLevel extends Level {
  
  private final String BITMAP_PATH = "./resources/textures/worlds/houseLevel.png";
  public static SpawnCoordinate entrance = new SpawnCoordinate(148, 208, 0);

  public HouseLevel(SpawnCoordinate spawnCoordinate) {
    this.spawnCoordinate = spawnCoordinate;
    loadLevel(BITMAP_PATH);
  }
  
  @Override
  protected void addPortals() {
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(221, 161, 230, 111);
    portals.add(new Portal(bounds, TestLevel.houseExit, "TEST_LEVEL"));
    
    for (int i = 0; i < portals.size(); i++) {
      portals.get(i).initLevel(this);
    }
  }
  
  @Override
  protected void startMusic() {
    music.play(Music.NEUTRAL_IN_A_HOUSE);
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
    config.setStartingY(11);
    config.setInterior(true);
    config.setLength(13);
    config.setWindows(new int[]{3, 6, 9});
    wall.createHorizontalWall(config);
    solidEntities.add(wall);
    
    wall = new Wall();
    config = new WallConfig();
    config.setStartingX(32);
    config.setStartingY(19);
    config.setHasWallFront(true);
    config.setInterior(false);
    config.setLength(11);
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
    config.setStartingY(19);
    config.setHasWallFront(true);
    config.setInterior(false);
    config.setLength(11);
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
  protected void finalLevelSetup() {
    lightMap.enableLightMap(LightMapType.CUSTOM);
    lightMap.setCustomLightMap(new CustomLightMap(new Sprite("./resources/textures/worlds/houseLevelLightMap.png")));
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
