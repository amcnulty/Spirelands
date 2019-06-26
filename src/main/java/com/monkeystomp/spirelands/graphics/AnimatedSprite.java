package com.monkeystomp.spirelands.graphics;

import java.util.ArrayList;

/**
 *
 * @author Aaron Michael McNulty
 */
public class AnimatedSprite {
  /**
   * Very fast animation every tick.
   */
  public static final int VERY_FAST = 1;
  /**
   * Fast animation every other tick.
   */
  public static final int FAST = 2;
  /**
   * Medium speed every 4th tick.
   */
  public static final int MEDIUM = 4;
  /**
   * Slow animation every 6th tick.
   */
  public static final int SLOW = 6;
  /**
   * Very slow animation every 10th tick.
   */
  public static final int VERY_SLOW = 10;
  private int x = 0,
              tick = 0,
              speed,
              currentIndex = 0;
  private ArrayList<Sprite> sprites = new ArrayList<>();
  private Sprite currentSprite;
  
  public AnimatedSprite(int rawSize, int renderSize, SpriteSheet sheet, int speed, int frames) {
    for (int i = 0; i < frames; i++) {
      sprites.add(new Sprite(rawSize, renderSize, x++, 0, sheet));
    }
    currentSprite = sprites.get(0);
    this.speed = speed;
  }
  
  public void update() {
    if (tick++ % speed == 0) {
      if (currentIndex == sprites.size()) currentIndex = 0;
      currentSprite = sprites.get(currentIndex++);
    }
  }
  
  public Sprite getSprite() {
    return currentSprite;
  }

}
