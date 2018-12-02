package com.monkeystomp.spirelands.level.lightmap;

import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.level.entity.Entity;

/**
 *
 * @author Aaron Michael McNulty
 */
public class CustomLightMap extends Entity{
  
  private final Sprite LIGHTMAP;
  
  public CustomLightMap(Sprite lightMap) {
    this.LIGHTMAP = lightMap;
    this.x = 0;
    this.y = 0;
  }
  
  public Sprite getLightMap() {
    return LIGHTMAP;
  }

}
