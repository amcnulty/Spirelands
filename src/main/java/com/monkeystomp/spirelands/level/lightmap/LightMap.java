package com.monkeystomp.spirelands.level.lightmap;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.lightmap.LightMapEntity;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The LightMap class is used to handle setting the type of light map and light map entities that will be used by the level.
 * @author Aaron Michael McNulty
 */
public class LightMap {
  // A map of the different rendering commands for each type of light map.
  private final HashMap<LightMapType, ILightMapRenderer> RENDER_TYPE_MAP = new HashMap<>();
  // A list of light map entities when using subtractive type light map.
  private ArrayList<LightMapEntity> lightMapEntities = new ArrayList<>();
  // A list of light map entities when using blended type light map.
  private ArrayList<LightMapEntity> basicLightMapEntities = new ArrayList<>();
  // Light map rendering commands for the different types of light maps are defined here.
  private final ILightMapRenderer
    IBLENDED = (GL2 gl, Screen screen, float shadowLevel) -> {
      // Render the blended lightMap.
      screen.renderBlendedLightMap(gl, shadowLevel);
      // Render the lightMap entities.
      for (int i = 0; i < basicLightMapEntities.size(); i++) {
        screen.renderBlendedSprite(
          gl,
          (int)basicLightMapEntities.get(i).getX(),
          (int)basicLightMapEntities.get(i).getY(),
          basicLightMapEntities.get(i).getSprite(),
          true
        );
      }
    },
    ISUBTRACTIVE = (GL2 gl, Screen screen, float shadowLevel) -> {
      // Render lightmap entities.
      for (int i = 0; i < lightMapEntities.size(); i++) {
        screen.renderLightMapEntity(
          gl,
          (int)lightMapEntities.get(i).getX(),
          (int)lightMapEntities.get(i).getY(),
          (Sprite)lightMapEntities.get(i).getSprite()
        );
      }
      // Render the subtractive lightMap.
      screen.renderLightMap(gl, shadowLevel);
    };
  // The current rendering command that is enabled.
  private ILightMapRenderer iRenderer;
  public static final int BLENDED = 0,
                          SUBTRACTIVE = 1;

  public LightMap() {
    RENDER_TYPE_MAP.put(LightMapType.BLENDED, IBLENDED);
    RENDER_TYPE_MAP.put(LightMapType.SUBTRACTIVE, ISUBTRACTIVE);
  }
  
  public void enableLightMap(LightMapType type) {
    this.iRenderer = RENDER_TYPE_MAP.get(type);
  }
  
  public void disableLightMap() {
    this.iRenderer = null;
  }
    
  public void addLightMapEntity(LightMapEntity entity) {
    lightMapEntities.add(entity);
  }
  
  public void addBasicLightMapEntity(LightMapEntity entity) {
    basicLightMapEntities.add(entity);
  }

  public void removeLightMapEntity(LightMapEntity entity) {
    lightMapEntities.remove(entity);
  }

  public void removeBasicLightMapEntity(LightMapEntity entity) {
    basicLightMapEntities.remove(entity);
  }
  
  public void render(GL2 gl, Screen screen, float shadowLevel) {
    if (iRenderer != null) {
      iRenderer.render(gl, screen, shadowLevel);
    }
  }

}
