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

/**
 *
 * @author Aaron Michael McNulty
 */
public class HouseLevel extends Level {
  
  private final String BITMAP_PATH = "./resources/textures/worlds/houseLevel.png";
  public static SpawnCoordinate entrance = new SpawnCoordinate(136, 192, 0);

  public HouseLevel(SpawnCoordinate spawnCoordinate) {
    this.spawnCoordinate = spawnCoordinate;
    loadLevel(BITMAP_PATH);
  }
  
  @Override
  protected void addPortals() {
    Bounds bounds = new Bounds();
    bounds.setQuadBounds(205, 161, 214, 111);
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
    SolidEntity entity = new SolidEntity(224, 32);
    entity.setSprite(Sprite.STAIRS_WOOD_TURN_LEFT);
    // Add bounds to this entity
    entity.initLevel(this);
    solidEntities.add(entity);
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
