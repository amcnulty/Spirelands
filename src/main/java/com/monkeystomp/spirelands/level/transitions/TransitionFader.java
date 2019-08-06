package com.monkeystomp.spirelands.level.transitions;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.ICallback;

/**
 *
 * @author Aaron Michael McNulty
 */
public class TransitionFader {
  
  private boolean transitionRunning = true,
                  fadeIn;
  private int transitionOpacity = 100;
  private ICallback callback;
  
  public void startTransitionIn() {
    this.transitionRunning = true;
    this.fadeIn = true;
    this.transitionOpacity = 100;
  }
  
  public void startTransitionOut(ICallback callback) {
    this.transitionRunning = true;
    this.fadeIn = false;
    this.transitionOpacity = 0;
    this.callback = callback;
  }
  
  public boolean isTransitionRunning() {
    return this.transitionRunning;
  }
  
  public void update() {
    if (transitionRunning) {
      if (fadeIn) {
        transitionOpacity -= 5;
        if (transitionOpacity == 0) transitionRunning = false;
      }
      else {
        transitionOpacity += 10;
        if (transitionOpacity == 100) {
          callback.execute();
        }
      }
    }
  }
  
  public void render(Screen screen, GL2 gl) {
    if (transitionRunning) screen.renderSprite(gl, 0, 0, Sprite.TRANSITION, transitionOpacity / (float)100, false);
  }

}
