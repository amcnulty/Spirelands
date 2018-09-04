package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.level.Level;
import com.monkeystomp.spirelands.level.SpawnLevel;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.graphics.Sprite;
import com.monkeystomp.spirelands.input.Keyboard;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aaron Michael McNulty
 */
public class SpawnLevelView extends GameView {
  
  private int ticks = 0,
              renderColor = 0x0079CC;
  private Level level = new SpawnLevel("./resources/textures/worlds/beach.png");
  private boolean gamePaused = false;

  public SpawnLevelView(ViewManager viewManager) {
    super(viewManager);
    setupNotifiers();
  }

  private void setupNotifiers() {
    Keyboard.getKeyboard().pauseNotifier = (KeyEvent e) -> handleEscapeKey(e);
  }

  private void handleEscapeKey(KeyEvent e) {
    System.out.println("Escape Key Pressed here in spawn level view.");
    gamePaused = !gamePaused;
  }
  
  @Override
  public void update() {
    if (!gamePaused) {
      level.update();
    }
    else if (gamePaused) {
      // update pause menu
    }
  }

  @Override
  public void render(Screen screen) {
    level.render(screen);
    if (gamePaused) {
    }
  }
}