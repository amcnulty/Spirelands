package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import com.monkeystomp.spirelands.level.entity.lightmap.LightMapEntity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class StreetLamp extends Entity {
  
  private final Sprite  LAMP_OFF = new Sprite("./resources/objects/lamp_off.png"),
                        LAMP_ON = new Sprite("./resources/objects/lamp_on.png"),
                        LIGHT_MAP = new Sprite("./resources/objects/lamp_lightMap.png");
  private final int SPRITE_SIZE = 32;
  private final LightMapEntity LIGHT_MAP_ENTITY;
  private Sprite currentSprite;
  private boolean lampIsOn = false;
  
  public StreetLamp(int x, int y) {
    this.x = x;
    this.y = y;
    this.LIGHT_MAP_ENTITY = new LightMapEntity(x - LIGHT_MAP.getWidth() / 2, y - LIGHT_MAP.getHeight() / 2, LIGHT_MAP);
    currentSprite = LAMP_OFF;
    setBounds();
  }
  
  private void setBounds() {
    bounds[0] = y - SPRITE_SIZE / 2;
    bounds[1] = x + SPRITE_SIZE / 2;
    bounds[2] = y + SPRITE_SIZE / 2;
    bounds[3] = x - SPRITE_SIZE / 2;
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
  
  public void turnLightOn() {
    currentSprite = LAMP_ON;
    level.addLightMapEntity(LIGHT_MAP_ENTITY);
  }
  
  public void turnLightOff() {
    currentSprite = LAMP_OFF;
    level.removeLightMapEntity(LIGHT_MAP_ENTITY);
  }
  
  @Override
  public void render(Screen screen) {
    screen.renderSprite(x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, currentSprite, true, true);
  }
}