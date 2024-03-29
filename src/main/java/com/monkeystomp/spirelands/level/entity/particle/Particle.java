package com.monkeystomp.spirelands.level.entity.particle;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Particle extends Entity {
  
  private final Sprite sprite;
  
  public Particle(int x, int y, Sprite sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
  }
  
  public Sprite getSprite() {
    return sprite;
  }
}