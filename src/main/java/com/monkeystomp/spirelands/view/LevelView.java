package com.monkeystomp.spirelands.view;

import com.jogamp.opengl.GL2;
import com.monkeystomp.spirelands.level.Level;
import com.monkeystomp.spirelands.graphics.Screen;
import com.monkeystomp.spirelands.input.Keyboard;
import com.monkeystomp.spirelands.gui.pausemenu.PauseMenu;
import com.monkeystomp.spirelands.input.INotify;
import java.awt.event.KeyEvent;

/**
 *
 * @author Aaron Michael McNulty
 */
public class LevelView extends GameView {
  
  private Level level;
  private boolean gamePaused = false;
  private PauseMenu pauseMenu = new PauseMenu();
  private INotify notifier = (e) -> handleKeypress(e);

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
    Keyboard.getKeyboard().addKeyPressNotifier(notifier);
    pauseMenu.setCloseCommand(() -> resumeLevel());
    pauseMenu.setQuitToMenuCommand(() -> ViewManager.getViewManager().changeView(new TitleScreen()));
  }

  private void handleKeypress(KeyEvent e) {
    if (e.getKeyCode() == Keyboard.ESCAPE_KEY) {
      if (gamePaused) resumeLevel();
      else pauseLevel();
    }
  }
  
  private void pauseLevel() {
    pauseMenu.openMenu();
    level.getMusicPlayer().pause();
    level.saveLevelState();
    gamePaused = true;
  }
  
  private void resumeLevel() {
    pauseMenu.closeMenu();
    level.getMusicPlayer().resume();
    gamePaused = false;
  }
  
  @Override
  public void leaveView() {
    Keyboard.getKeyboard().removeKeyPressNotifier(notifier);
    level.transitionOutOfLevel();
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
  public void render(Screen screen, GL2 gl) {
    level.render(screen, gl);
    if (gamePaused) {
      pauseMenu.render(screen, gl);
    }
  }
}