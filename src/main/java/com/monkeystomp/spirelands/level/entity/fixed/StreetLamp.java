package com.monkeystomp.spirelands.level.entity.fixed;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class StreetLamp extends Entity {
  
  private Sprite  lampOff = new Sprite("./resources/objects/lamp_off.png"),
                  lampOn = new Sprite("./resources/objects/lamp_on.png"),
                  lightMap = new Sprite("./resources/objects/lamp_lightMap.png"),
                  currentSprite;
  private final int SPRITE_SIZE = 32;
  private boolean lampIsOn = false;
  private ArrayList entityRef;
  
  public StreetLamp(int x, int y) {
    this.x = x;
    this.y = y;
    currentSprite = lampOff;
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
    currentSprite = lampOn;
    entityRef = level.addLightMapEntity(x - lightMap.getWidth() / 2, y - lightMap.getHeight() / 2, lightMap);
  }
  
  public void turnLightOff() {
    currentSprite = lampOff;
    level.removeLightMapEntity(entityRef);
  }
  
  @Override
  public void render(Screen screen) {
    screen.renderSprite(x - SPRITE_SIZE / 2, y - SPRITE_SIZE / 2, currentSprite, true, true);
  }
}