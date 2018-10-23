package com.monkeystomp.spirelands.level.entity.particle;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Particle extends Entity {
  
  private final Sprite sprite;
  
  private static HashMap<Integer, Sprite> particleTypes = new HashMap<>();
  
  public static final int DUST = 0,
                          EMBER = 1;
  
  static {
    particleTypes.put(DUST, Sprite.DUST);
    particleTypes.put(EMBER, Sprite.EMBER);
  }

  public Particle(int x, int y, int type) {
    this.x = x;
    this.y = y;
    this.sprite = particleTypes.get(type);
  }
  
  public Sprite getSprite() {
    return sprite;
  }
}