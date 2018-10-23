package com.monkeystomp.spirelands.level.entity.particle;

import com.monkeystomp.spirelands.graphics.Sprite;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class ParticleFactory {
  
  private static HashMap<String, Factory<Particle, Integer, Integer, Sprite>> particleMap = new HashMap<>();
  private static HashMap<String, Sprite> typeMap = new HashMap<>();
  
  static {
    particleMap.put("DUST", Particle::new);
    typeMap.put("DUST", Sprite.DUST);
    particleMap.put("EMBER", Particle::new);
    typeMap.put("EMBER", Sprite.EMBER);
  }
  
  public static Particle createParticle(String key, int x, int y) {
    return particleMap.get(key).create(x, y, typeMap.get(key));
  }
  
  private interface Factory<T, X, Y, S> {
    T create(X ob1, Y ob2, S ob3);
  }
}