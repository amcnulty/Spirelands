package com.monkeystomp.spirelands.level.lightmap;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class LightMapEntity extends Entity {
  
  private Sprite sprite;

  public LightMapEntity(int x, int y, Sprite sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
  }
  
  public Sprite getSprite() {
    return sprite;
  }
}