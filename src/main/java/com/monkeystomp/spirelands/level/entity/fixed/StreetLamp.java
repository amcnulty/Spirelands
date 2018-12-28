package com.monkeystomp.spirelands.level.entity.fixed;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.bounds.Bounds;
import com.monkeystomp.spirelands.level.lightmap.LightMapEntity;

/**
 * Street lamp entity that can illuminate its surroundings when turned on.
 * @author Aaron Michael McNulty
 */
public class StreetLamp extends Entity {
  
  private final Sprite  LAMP_OFF = new Sprite("./resources/objects/lamp_off.png"),
                        LAMP_ON = new Sprite("./resources/objects/lamp_on.png"),
                        LIGHT_MAP = new Sprite("./resources/objects/lamp_lightMap_blend_yellow.png");
  private final int SPRITE_SIZE = 32;
  private final LightMapEntity LIGHT_MAP_ENTITY;
  private Sprite currentSprite;
  private boolean lampIsOn = false;
  /**
   * Creates a new StreetLamp entity at the given coordinates.
   * @param x X coordinate to place lamp.
   * @param y Y coordinate to place lamp.
   */
  public StreetLamp(int x, int y) {
    this.x = x;
    this.y = y;
    this.LIGHT_MAP_ENTITY = new LightMapEntity(x - LIGHT_MAP.getWidth() / 2, y - LIGHT_MAP.getHeight() / 2, LIGHT_MAP);
    currentSprite = LAMP_OFF;
    setBounds();
  }
  
  private void setBounds() {
    Bounds quad = new Bounds();
    quad.setQuadBounds(
      y,
      x + SPRITE_SIZE / 2,
      y + SPRITE_SIZE / 2,
      x - SPRITE_SIZE / 2
    );
    bounds.add(quad);
  }
  
  @Override
  public void interact() {
    if (lampIsOn) {
      turnLightOff();
      lampIsOn = false;
    }
    else {
      turnLightOn();
      lampIsOn = true;
    }
  }
  /**
   * Turns light on to illuminate the surrounding area.
   */
  public void turnLightOn() {
    currentSprite = LAMP_ON;
    level.getLightMap().addBasicLightMapEntity(LIGHT_MAP_ENTITY);
  }
  /**
   * Turns light off.
   */
  public void turnLightOff() {
    currentSprite = LAMP_OFF;
    level.getLightMap().removeBasicLightMapEntity(LIGHT_MAP_ENTITY);
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    screen.renderSprite(gl, x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, currentSprite, true);
  }
}
