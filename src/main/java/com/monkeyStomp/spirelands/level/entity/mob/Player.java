package com.monkeyStomp.spirelands.level.entity.mob;

import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.graphics.SpriteSheet;
import java.util.HashMap;

/**
 *
 * @author Aaron Michael McNulty
 */
public class Player extends Mob {
  
  protected SpriteSheet characterSheet;
  protected HashMap<String, Sprite> characterActions = new HashMap<>();
  
  public Player(int x, int y) {
    this.x = x;
    this.y = y;
    loadSpriteSheet();
    setupCharacterActions();
  }
  
  protected void loadSpriteSheet() {}
  
  protected void setupCharacterActions() {}
  
  @Override
  public void update() {}
  
  public void render(Screen screen) {}
}