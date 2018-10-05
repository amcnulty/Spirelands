package com.monkeystomp.spirelands.view;

import com.monkeystomp.spirelands.audio.SoundEffects;
import com.monkeystomp.spirelands.level.Level;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aaron Michael McNulty
 */
public class LevelView extends GameView {
  
  private int ticks = 0,
              renderColor = 0x0079CC;
  // private Level level = new SpawnLevel("./resources/textures/worlds/beach.png");
  private Level level;
  private SoundEffects sfx = new SoundEffects();
  private boolean gamePaused = false;
  private PauseMenu pauseMenu = new PauseMenu();

  public LevelView(Level level) {
    initLevel(level);
    setupNotifiers();
  }

  private void initLevel(Level level) {
    this.level = level;
    this.level.setLevelChanger((Level newLevel) -> changeLevel(newLevel));
  }

  private void changeLevel(Level level) {
    initLevel(level);
  }

  private void setupNotifiers() {
    Keyboard.getKeyboard().pauseNotifier = (KeyEvent e) -> handleEscapeKey(e);
    pauseMenu.setCloseCommand(() -> resumeLevel());
  }

  private void handleEscapeKey(KeyEvent e) {
    if (gamePaused) resumeLevel();
    else pauseLevel();
  }
  
  private void pauseLevel() {
    sfx.playSoundEffect(SoundEffects.CONFIRM_CHORD);
    level.getMusicPlayer().pause();
    gamePaused = true;
  }
  
  private void resumeLevel() {
    sfx.playSoundEffect(SoundEffects.CONFIRM);
    level.getMusicPlayer().resume();
    gamePaused = false;
  }
  
  @Override
  public void update() {
    if (!gamePaused) {
      level.update();
    }
    else if (gamePaused) {
      // update pause menu
      pauseMenu.update();
    }
  }

  @Override
  public void render(Screen screen) {
    level.render(screen);
    if (gamePaused) {
      pauseMenu.render(screen);
    }
  }
}