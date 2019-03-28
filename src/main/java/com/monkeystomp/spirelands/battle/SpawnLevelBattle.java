package com.monkeystomp.spirelands.battle;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevelBattle extends Battle {
  
  private final Sprite BACKGROUND = new Sprite(new Sprite("./resources/backgrounds/battle/dark_woods.jpg"), 38.05);
  
  public SpawnLevelBattle() {
    this.background = BACKGROUND;
  }
  
  @Override
  public void render(Screen screen, GL2 gl) {
    super.render(screen, gl);
  }

}
